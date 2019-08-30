package cn.itdeer.kafka.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : 加载运行配置设置为常量
 * PackageName : cn.itdeer.kafka.tools
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/15:28
 */
@Slf4j
public class Constants {

    /**
     * 配置文件默认名称
     */
    public static final String configFileName = "runtime.properties";


    /**
     * 参数默认值
     */
    public static String THREAD_NUMBER = Integer.toString(1);
    public static String SINGLE_THREAD_SEND_DATA = ResolverConfig.getValueByKey("SINGLE_THREAD_SEND_DATA") != null ? ResolverConfig.getValueByKey("SINGLE_THREAD_SEND_DATA") : Integer.toString(10000);
    public static Map<String, String> TOPIC_NAME_AND_THREADS = ResolverConfig();


    /**
     * 解析Topic名称个具体执行的线程数
     *
     * @return
     */
    private static Map ResolverConfig() {
        Map<String, String> map = new HashMap<>();
        try {

            String tn_and_t = ResolverConfig.getValueByKey("TOPIC_NAME_AND_THREADS");
            if (!tn_and_t.endsWith(",")) {
                tn_and_t = tn_and_t + ",";
            }
            String[] tn_and_ts = tn_and_t.split("],");

            for (int i = 0; i < tn_and_ts.length; i++) {
                String topic = tn_and_ts[i].substring(1);

                String thread = THREAD_NUMBER;
                String single_data = SINGLE_THREAD_SEND_DATA;

                try {
                    thread = topic.split(",")[1];
                    single_data = topic.split(",")[2];
                } catch (Exception e) {
                }

                map.put(topic.split(",")[0], thread + "_" + single_data);
            }
            log.info("resolver config message finish");
        } catch (Exception e) {
            log.error("resolver config message appear exception: " + e.getStackTrace());
        }
        return map;
    }


    /**
     * 时间格式
     */
    public static String DATA_FORMAT = ResolverConfig.getValueByKey("DATA_FORMAT") != null ? ResolverConfig.getValueByKey("DATA_FORMAT") : "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat format = new SimpleDateFormat(DATA_FORMAT);


    /**
     * bootstrap.servers:用于初始化时建立链接到kafka集群,
     * 以host:port形式,多个以逗号分隔host1:port1,host2:port2
     */
    public static final String BOOTSTRAP_SERVERS = ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
    public static String KAFKA_BOOTSTRAP_SERVERS = ResolverConfig.getValueByKey("KAFKA_BOOTSTRAP_SERVERS") != null ? ResolverConfig.getValueByKey("KAFKA_BOOTSTRAP_SERVERS") : "localhost:6667";

    /**
     * acks:生产者需要server端在接收到消息后,进行反馈确认的尺度,主要用于消息的可靠性传输
     * acks=0表示生产者不需要来自server的确认
     * acks=1表示server端将消息保存后即可发送ack,而不必等到其他follower角色的都收到了该消息
     * acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认
     */
    public static final String ACKS = ProducerConfig.ACKS_CONFIG;
    public static String KAFKA_ACKS = ResolverConfig.getValueByKey("KAFKA_ACKS") != null ? ResolverConfig.getValueByKey("KAFKA_ACKS") : Integer.toString(0);

    /**
     * retries: producer消息发送失败后，重试的次数
     * 默认值为0，不进行重试
     */
    public static final String RETRIES = ProducerConfig.RETRIES_CONFIG;
    public static String KAFKA_RETRIES = ResolverConfig.getValueByKey("KAFKA_RETRIES") != null ? ResolverConfig.getValueByKey("KAFKA_RETRIES") : Integer.toString(3);

    /**
     * linger.ms: 默认值为0,默认情况下缓冲区的消息会被立即发送到服务端，即使缓冲区的空间并没有被用完。
     * 可以将该值设置为大于0的值，这样发送者将等待一段时间后，再向服务端发送请求，以实现每次请求可以尽可能多的发送批量消息。
     * batch.size和linger.ms是两种实现让客户端每次请求尽可能多的发送消息的机制，它们可以并存使用，并不冲突
     */
    public static final String LINGER_MS = ProducerConfig.LINGER_MS_CONFIG;
    public static String KAFKA_LINGER_MS = ResolverConfig.getValueByKey("KAFKA_LINGER_MS") != null ? ResolverConfig.getValueByKey("KAFKA_LINGER_MS") : Integer.toString(100);

    /**
     * batch.size:当多条消息发送到同一个partition时,该值控制生产者批量发送消息的大小,
     * 批量发送可以减少生产者到服务端的请求数,有助于提高客户端和服务端的性能
     * 默认 1048576 B
     */
    public static final String BATCH_SIZE = ProducerConfig.BATCH_SIZE_CONFIG;
    public static String KAFKA_BATCH_SIZE = ResolverConfig.getValueByKey("KAFKA_BATCH_SIZE") != null ? ResolverConfig.getValueByKey("KAFKA_BATCH_SIZE") : Integer.toString(102400);

    /**
     * buffer.memory: 制定producer端用于缓存消息的缓冲区大小，保存的是还未来得及发送到server端的消息，
     * 如果生产者的发送速度大于消息被提交到server端的速度，该缓冲区将被耗尽
     * 默认值为 33554432 ,即 32MB
     */
    public static final String BUFFER_MEMORY = ProducerConfig.BUFFER_MEMORY_CONFIG;
    public static String KAFKA_BUFFER_MEMORY = ResolverConfig.getValueByKey("KAFKA_BUFFER_MEMORY") != null ? ResolverConfig.getValueByKey("KAFKA_BUFFER_MEMORY") : Integer.toString(33554432);

    /**
     * max.request.size: 官网上解释该参数用于控制producer发送请求的大小
     * 实际上该参数控制的是producer端能够发送的最大消息大小
     */
    public static final String MAX_REQUEST_SIZE = ProducerConfig.MAX_REQUEST_SIZE_CONFIG;
    public static String KAFKA_MAX_REQUEST_SIZE = ResolverConfig.getValueByKey("KAFKA_MAX_REQUEST_SIZE") != null ? ResolverConfig.getValueByKey("KAFKA_MAX_REQUEST_SIZE") : Integer.toString(10485760);

    /**
     * 压缩数据的压缩类型。压缩最好用于批量处理，批量处理消息越多，压缩性能越好
     * none : 无压缩,默认值。
     * gzip :
     * snappy : 由于kafka源码的某个关键设置，使得snappy表现不如lz4
     * lz4 : producer 结合lz4 的性能较好
     * 性能：lz4 >> snappy >> gzip
     */
    public static final String COMPRESSION_TYPE = ProducerConfig.COMPRESSION_TYPE_CONFIG;
    public static String KAFKA_COMPRESSION_TYPE = ResolverConfig.getValueByKey("KAFKA_COMPRESSION_TYPE") != null ? ResolverConfig.getValueByKey("KAFKA_COMPRESSION_TYPE") : "lz4";

    /**
     * 消息发送的最长等待时间
     * 当producer发送请求给broker后，broker需要在规定的时间范围内将处理结果返回给producer
     * request.timeout.ms 即控制这个时间，默认值为30s
     * 通常情况下，超时会在回调函数中抛出TimeoutException异常交由用户处理
     */
    public static final String REQUEST_TIMEOUT_MS = ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG;
    public static String KAFKA_REQUEST_TIMEOUT_MS = ResolverConfig.getValueByKey("KAFKA_REQUEST_TIMEOUT_MS") != null ? ResolverConfig.getValueByKey("KAFKA_REQUEST_TIMEOUT_MS") : Integer.toString(60000);

    /**
     * 限制producer在单个broker连接上能够发送的未响应请求的数量
     */
    public static final String MAX_IN_FLIGHT_REQUESTS_PER = ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION;
    public static String KAFKA_MAX_IN_FLIGHT_REQUESTS_PER = ResolverConfig.getValueByKey("KAFKA_MAX_IN_FLIGHT_REQUESTS_PER") != null ? ResolverConfig.getValueByKey("KAFKA_MAX_IN_FLIGHT_REQUESTS_PER") : Integer.toString(1);

    /**
     * key.serializer, value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节
     */
    public static final String KEY_SERIALIZER_CLASS = ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
    public static String KAFKA_KEY_SERIALIZER_CLASS_STRING = ResolverConfig.getValueByKey("KAFKA_KEY_SERIALIZER_CLASS_STRING") != null ? ResolverConfig.getValueByKey("KAFKA_KEY_SERIALIZER_CLASS_STRING") : "org.apache.kafka.common.serialization.StringSerializer";
    public static final String VALUE_SERIALIZER_CLASS = ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
    public static String KAFKA_VALUE_SERIALIZER_CLASS_STRING = ResolverConfig.getValueByKey("KAFKA_VALUE_SERIALIZER_CLASS_STRING") != null ? ResolverConfig.getValueByKey("KAFKA_VALUE_SERIALIZER_CLASS_STRING") : "org.apache.kafka.common.serialization.StringSerializer";

}
