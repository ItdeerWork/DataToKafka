package cn.itdeer.kafka.core.control;

import cn.itdeer.kafka.common.Message;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;

/**
 * Description : 关闭资源
 * PackageName : cn.itdeer.kafka.core.control
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/30/17:58
 */
public class Close {

    public static void resourceClose(Message message, KafkaProducer<String, String> producer, String topicName, long dataNumber, int timeFrequency, Map<String, Object> living) {
        if (message != null) {
            message = null;
        }
        if (producer != null) {
            producer.close();
        }
        if (topicName != null) {
            topicName = null;
        }
        if (dataNumber != 0) {
            dataNumber = 0;
        }
        if (timeFrequency != 0) {
            timeFrequency = 0;
        }
        if (living != null) {
            living = null;
        }
    }

}
