package cn.itdeer.kafka.core.type;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.common.config.Message;
import cn.itdeer.kafka.common.fields.FieldInterface;
import cn.itdeer.kafka.common.init.InitMessage;
import cn.itdeer.kafka.common.log.LogPrint;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Description : 按照模板生成JSON格式数据
 * PackageName : cn.itdeer.kafka.core.type
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/28/8:35
 */

public class AppointJsonProducer extends Thread {

    private static final Logger log = LogManager.getLogger(AppointJsonProducer.class);

    /**
     * 应用配置信息
     */
    private Message message;
    private KafkaProducer<String, String> producer;

    /**
     * 基本信息
     */
    private String topicName;
    private long dataNumber;
    private int timeFrequency;

    /**
     * 数据中转集合
     */
    private Map<String, Object> living;
    private Map<Integer, List<Map<String, Object>>> map;

    /**
     * 构造函数
     *
     * @param message 配置信息
     */
    public AppointJsonProducer(Message message, KafkaProducer<String, String> producer, String threadName) {
        super(threadName);
        this.message = message;
        this.producer = producer;

        topicName = message.getTopicName();
        dataNumber = message.getDataNumber();
        timeFrequency = message.getTimeFrequency();

        living = new HashMap<>();
    }

    /**
     * 覆盖线程Run方法
     */
    @Override
    public void run() {

        /**
         * 开始时间
         */
        long startTime = System.currentTimeMillis();
        String startDate = Constants.format.format(new Date());
        long totleNumber = dataNumber;
        log.info("Generate the start time of sending data in JSON format according to the template [{}]", startDate);

        /**
         * 初始化数据值获取实例
         */
        map = new InitMessage().initPointsInstance(message.getDataMapping().getPoints(), living);
        if (map.size() == 0)
            return;
        log.info("Send JSON format data value instance initialization based on template generation");

        /**
         * 发数
         */
        Boolean ifFinsh = sendData();
        if (ifFinsh) {
            producer.flush();
        }
        log.info("According to the template generated send JSON format data times completed");


        /**
         * 结束时间
         */
        long endTime = System.currentTimeMillis();
        String endDate = Constants.format.format(new Date());
        log.info("End time to send JSON format data based on template generation [{}]", endDate);


        /**
         * 输出发数信息
         */
        LogPrint.outPrint(startTime, endTime, startDate, endDate, Thread.currentThread().getName(), totleNumber, topicName);
    }

    /**
     * 发送数据
     *
     * @return 发送完成状态
     */
    private Boolean sendData() {
        List<String> list = new LinkedList<>();

        while (dataNumber > 0) {
            for (Integer num : map.keySet()) {
                List<Map<String, Object>> ll = map.get(num);
                Map<String, Object> value = new LinkedHashMap<>();
                for (Map<String, Object> mm : ll) {
                    for (String key : mm.keySet()) {
                        if (living.containsKey(mm.get(key))) {
                            Object tmp = ((FieldInterface) living.get(mm.get(key))).getValue();
                            value.put(key, tmp);
                        } else {
                            value.put(key, mm.get(key));
                        }
                    }
                }
                list.add(JSONObject.toJSONString(value));
            }

            for (int i = 0; i < list.size(); i++) {
                producer.send(new ProducerRecord(topicName, list.get(i)));
            }
            list.clear();
            if (timeFrequency > 0) {
                try {
                    Thread.sleep(timeFrequency);
                } catch (Exception e) {
                    log.error("When sending JSON format data for topic [{}], the thread has an interrupt exception. The exception information is as follows: [{}]", topicName, e.getStackTrace());
                }
            }
            dataNumber--;
        }
        return true;
    }

}
