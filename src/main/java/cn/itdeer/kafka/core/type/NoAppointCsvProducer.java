package cn.itdeer.kafka.core.type;

import cn.itdeer.kafka.common.Constants;
import cn.itdeer.kafka.common.LogPrint;
import cn.itdeer.kafka.common.Message;
import cn.itdeer.kafka.core.control.Close;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Date;
import java.util.Map;

/**
 * Description : 生成CSV格式数据
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
    private int dataNumber;
    private int timeFrequency;

    /**
     * 构造函数
     *
     * @param message 配置信息
     */
    public NoAppointCsvProducer(Message message, KafkaProducer<String, String> producer) {
        this.message = message;
        this.producer = producer;

        topicName = message.getTopicName();
        dataNumber = message.getDataNumber();
        timeFrequency = message.getTimeFrequency();

        addShutdownHook();
    }

    /**
     * 覆盖线程Run方法
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        String startDate = Constants.format.format(new Date());
        int totleNumber = dataNumber;

        /**
         * 初始化
         */
        SendData sd = new SendData();
        Map<String, Object> map = sd.initFields(message.getDataMapping().getFields());
        if (map.size() == 0)
            return;
        long initTime = System.currentTimeMillis();

        /**
         * 发数
         */
        Boolean ifFinsh = sd.sendCsvData(dataNumber, timeFrequency, topicName, message.getDataMapping().getSeparator(), map, producer);
        if (ifFinsh) {
            producer.flush();
        }

        long endTime = System.currentTimeMillis();
        String endDate = Constants.format.format(new Date());

        LogPrint.outPrint(startTime, initTime, endTime, startDate, endDate, Thread.currentThread().getName(), totleNumber, topicName);

    }

    /**
     * 注册一个停止运行的资源清理任务(钩子程序)
     */
    private void addShutdownHook() {
        log.info("Register hooks in initProducer to turn off and recycle resources");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Close.resourceClose(message, producer, topicName, dataNumber, timeFrequency, null);
            }
        });
    }

}
