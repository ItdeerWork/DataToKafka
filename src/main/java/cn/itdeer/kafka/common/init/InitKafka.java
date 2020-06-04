package cn.itdeer.kafka.common.init;

import cn.itdeer.kafka.common.config.InitConfig;
import cn.itdeer.kafka.common.config.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

import static cn.itdeer.kafka.common.config.Constants.DEFAULT_PRODUCER_NAME;

/**
 * Description : 初始化生产者实例
 * PackageName : cn.itdeer.kafka.common.init
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/16/23:51
 */

public class InitKafka {

    private static final Logger log = LogManager.getLogger(InitKafka.class);

    /**
     * 获取一个生产者实例
     *
     * @return 生产者实例
     */
    public KafkaProducer<String, String> getKafkaProducer() {
        return init();
    }

    /**
     * 获取一个生产者实例
     *
     * @param topic 主题名称
     * @return 生产者实例
     */
    public KafkaProducer<String, String> getKafkaProducer(String topic) {
        return init(topic);
    }

    /**
     * 初始化生产者实例
     *
     * @return 生产者实例
     */
    private KafkaProducer<String, String> init() {
        return init(DEFAULT_PRODUCER_NAME);
    }

    /**
     * 初始化生产者实例
     *
     * @param topic 主题名称
     * @return 生产者实例
     */
    private KafkaProducer<String, String> init(String topic) {
        Kafka kafka = InitConfig.getInstance().getCb().getKafka();

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, kafka.getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG, kafka.getRetries());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafka.getLingerMs());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafka.getBatchSize());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafka.getBufferMemory());
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafka.getMaxRequestSize());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafka.getCompressionType());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafka.getRequestTimeoutMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafka.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafka.getValueSerializer());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafka.getMaxInFlightRequestsPer());

        KafkaProducer<String, String> producer = new KafkaProducer(props);
        log.info("Initialize a producer instance as the topic [{}]", topic);
        return producer;
    }
}
