package cn.itdeer.kafka.core.control;

import cn.itdeer.kafka.common.ConfigBean;
import cn.itdeer.kafka.common.Kafka;
import cn.itdeer.kafka.common.Message;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * Description : 初始化生产者者及处理资源
 * PackageName : cn.itdeer.kafka.core
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/14:30
 */
@Data
@Slf4j
public class InitProducer {
    private KafkaProducer<String, String> producer;

    private ConfigBean cb;
    private Message message;

    /**
     * 构造函数 初始化Producer的实例
     *
     * @param message 生成消息信息
     * @param cb      应用配置
     */
    public InitProducer(Message message, ConfigBean cb) {
        this.message = message;
        this.cb = cb;
        init(cb.getKafka());

        addShutdownHook();
    }

    /**
     * 初始化处理资源
     *
     * @param kafka Kafk的默认配置
     */
    public void init(Kafka kafka) {
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

        producer = new KafkaProducer(props);
        log.info("Initializes the producer instance object as topic [{}]", message.getTopicName());

        try {
            new ProducerFactory().createProducer(message, producer);
        } catch (Exception e) {
            log.error("Instantiate the number of send type, send the number of errors, the error message is as follows [{}]", e.getStackTrace());
        }
    }

    /**
     * 注册一个停止运行的资源清理任务(钩子程序)
     */
    private void addShutdownHook() {
        log.info("Register hooks in initProducer to turn off and recycle resources");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                close();
            }
        });
    }

    /**
     * 关闭资源
     */
    private void close() {
        if (message != null) {
            message = null;
        }
        if (cb != null) {
            cb = null;
        }
        if (producer != null) {
            producer.close();
        }
    }
}
