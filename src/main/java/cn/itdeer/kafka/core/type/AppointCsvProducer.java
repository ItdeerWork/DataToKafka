package cn.itdeer.kafka.core.type;

import cn.itdeer.kafka.common.Constants;
import cn.itdeer.kafka.common.LogPrint;
import cn.itdeer.kafka.common.Message;
import cn.itdeer.kafka.core.control.Close;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.*;

/**
 * Description : 按照模板生成CSV格式数据
 * PackageName : cn.itdeer.kafka.core.type
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/28/8:35
 */
@Slf4j
public class AppointCsvProducer extends Thread {

    /**
     * 应用配置信息
     */
    private Message message;
    private KafkaProducer<String, String> producer;

    /**
     * 基本信息
     */
    private String topicName;
    private int dataNumber;
    private int timeFrequency;

    /**
     * 数据中转集合
     */
    private Map<String, Object> living;

    /**
     * 构造函数
     *
     * @param message 配置信息
     */
    public AppointCsvProducer(Message message, KafkaProducer<String, String> producer) {
        this.message = message;
        this.producer = producer;

        topicName = message.getTopicName();
        dataNumber = message.getDataNumber();
        timeFrequency = message.getTimeFrequency();

        living = new HashMap<>();

        addShutdownHook();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        String startDate = Constants.format.format(new Date());
        int totleNumber = dataNumber;

        /**
         * 初始化
         */
        SendData sd = new SendData();
        Map<Integer, List<Map<String, Object>>> map = sd.initPoints(message.getDataMapping().getPoints(), living);
        if (map.size() == 0)
            return;
        long initTime = System.currentTimeMillis();

        /**
         * 发数
         */
        Boolean ifFinsh = sd.sendAppointCsvData(dataNumber, timeFrequency, topicName, message.getDataMapping().getSeparator(), map, producer, living);
        if (ifFinsh) {
            producer.flush();
        }

        long endTime = System.currentTimeMillis();
        String endDate = Constants.format.format(new Date());

        LogPrint.outPrint(startTime, initTime, endTime, startDate, endDate, Thread.currentThread().getName(), totleNumber, topicName);
        System.exit(0);
    }

    /**
     * 注册一个停止运行的资源清理任务(钩子程序)
     */
    private void addShutdownHook() {
        log.info("Register hooks in initProducer to turn off and recycle resources");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Close.resourceClose(message, producer, topicName, dataNumber, timeFrequency, living);
            }
        });
    }

}
