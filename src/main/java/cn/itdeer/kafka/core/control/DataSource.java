package cn.itdeer.kafka.core.control;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.common.config.InitConfig;
import cn.itdeer.kafka.common.config.Message;
import cn.itdeer.kafka.common.init.InitCommon;
import cn.itdeer.kafka.common.init.InitKafka;
import cn.itdeer.kafka.core.type.AppointCsvProducer;
import cn.itdeer.kafka.core.type.AppointJsonProducer;
import cn.itdeer.kafka.core.type.NoAppointCsvProducer;
import cn.itdeer.kafka.core.type.NoAppointJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;


/**
 * Description : 发数数据源
 * PackageName : cn.itdeer.kafka.v2.core.control
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/17/8:45
 */

@Slf4j
public class DataSource {

    /**
     * 启动整个数据源
     */
    public void start() {
        init();
    }

    /**
     * 初始化资源
     */
    private void init() {
        InitKafka initKafka = new InitKafka();
        for (Message message : InitConfig.getInstance().getCb().getMessage()) {
            for (int i = 0; i < message.getThreads(); i++) {
                KafkaProducer<String, String> producer = initKafka.getKafkaProducer(message.getTopicName());
                log.info("Create a producer instance of Kafka as the theme [{}]", message.getTopicName());

                String threadName = message.getTopicName() + "-" + i;
                createDataInstance(producer, message, threadName);
                log.info("Create an instance of the number of posts as the topic [{}]", message.getTopicName());
            }
        }
    }

    /**
     * 创建发数实例资源
     *
     * @param producer   生产者实例
     * @param message    发送数据信息实例
     * @param threadName 线程名称
     */
    private void createDataInstance(KafkaProducer<String, String> producer, Message message, String threadName) {

        /**
         * 指定点位的发数实例和不指定点位发送实例
         */
        if (message.getDataMapping().getAppoint()) {

            /**
             * 点位为CSV配置文件，初始化点位信息
             */
            if (message.getDataMapping().getPointFile() != null) {
                InitCommon initCommon = new InitCommon();
                message.getDataMapping().setPoints(initCommon.initFilePoints(message));
                log.info("Read the point bit information of the point bit file successfully and initialize it to the point bit list of the data source");
            }

            /**
             * 点位为数据库表的配置，初始化点位信息
             */
//            if (message.getDataMapping().getPointFile() != null) {
//                //TODO
//                InitCommon initCommon = new InitCommon();
//                message.getDataMapping().setPoints(initCommon.initFilePoints(message));
//                log.info("The point bit information of the successful database point bit file is initialized to the point bit list of the data source");
//            }

            /**
             * 根据发送的格式，实例化相应的实例
             */
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new AppointCsvProducer(message, producer, threadName).start();
                    break;
                case Constants.JSON:
                    new AppointJsonProducer(message, producer, threadName).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }

        } else {
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new NoAppointCsvProducer(message, producer, threadName).start();
                    break;
                case Constants.JSON:
                    new NoAppointJsonProducer(message, producer, threadName).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }
        }

    }
}
