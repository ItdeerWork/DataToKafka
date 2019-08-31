package cn.itdeer.kafka.core.control;

import cn.itdeer.kafka.common.Constants;
import cn.itdeer.kafka.common.Message;
import cn.itdeer.kafka.core.type.AppointCsvProducer;
import cn.itdeer.kafka.core.type.AppointJsonProducer;
import cn.itdeer.kafka.core.type.NoAppointCsvProducer;
import cn.itdeer.kafka.core.type.NoAppointJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * Description : 生产者工厂类
 * PackageName : cn.itdeer.kafka.core
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/26/17:32
 */
@Slf4j
public class ProducerFactory {

    /**
     * 获取生产者的具体实例
     *
     * @param message 生成消息信息
     * @return 生产者的具体实例
     */
    public void createProducer(Message message, KafkaProducer<String, String> producer) {

        if (message.getDataMapping().getAppoint()) {
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new AppointCsvProducer(message, producer).start();
                    break;
                case Constants.JSON:
                    new AppointJsonProducer(message, producer).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }
        } else {
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new NoAppointCsvProducer(message, producer).start();
                    break;
                case Constants.JSON:
                    new NoAppointJsonProducer(message, producer).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }
        }
    }
}
