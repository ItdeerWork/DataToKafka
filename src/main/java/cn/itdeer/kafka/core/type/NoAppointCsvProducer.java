package cn.itdeer.kafka.core.type;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.common.config.Message;
import cn.itdeer.kafka.common.fields.FieldInterface;
import cn.itdeer.kafka.common.init.InitMessage;
import cn.itdeer.kafka.common.log.LogPrint;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Map;

/**
 * Description : 随机生成CSV格式数据
 * PackageName : cn.itdeer.kafka.core.type
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/25/23:51
 */

@Slf4j
public class NoAppointCsvProducer extends Thread {

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
    private String separator;

    /**
     * 发数值生成实例
     */
    private Map<String, Object> map;

    /**
     * 构造函数
     *
     * @param message 配置信息
     */
    public NoAppointCsvProducer(Message message, KafkaProducer<String, String> producer, String threadName) {
        super(threadName);
        this.message = message;
        this.producer = producer;

        topicName = message.getTopicName();
        dataNumber = message.getDataNumber();
        timeFrequency = message.getTimeFrequency();
        separator = message.getDataMapping().getSeparator();
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
        log.info("Random generation CSV format data sending start time is [{}]", startDate);

        /**
         * 初始化数据值获取实例
         */
        map = new InitMessage().initFieldsInstance(message.getDataMapping().getFields());
        if (map.size() == 0)
            return;
        log.info("Randomly generate CSV format data initialization values to get instances");

        /**
         * 发数
         */
        Boolean ifFinsh = sendData();
        if (ifFinsh) {
            producer.flush();
        }
        log.info("Random generation of CSV format data is completed");

        /**
         * 结束时间
         */
        long endTime = System.currentTimeMillis();
        String endDate = Constants.format.format(new Date());
        log.info("Random generation CSV format data sending completion end time [{}]", endDate);

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
        while (dataNumber > 0) {
            String message = "";
            for (String key : map.keySet()) {
                message = message + ((FieldInterface) map.get(key)).getValue() + separator;
            }
            message = message.substring(0, message.lastIndexOf(separator));
            producer.send(new ProducerRecord(topicName, message));
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
