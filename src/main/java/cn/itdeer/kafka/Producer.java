package cn.itdeer.kafka;

import cn.itdeer.kafka.tools.Constants;
import cn.itdeer.kafka.tools.Print;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

import static cn.itdeer.kafka.tools.Constants.format;

/**
 * Description : Kafka生产者
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/17:32
 */
public class Producer extends Thread {

    private String topic_name;
    private Integer single_data;
    private KafkaProducer<String, String> producer;

    /**
     * 构造函数，初始化属性配置
     */
    public Producer(String topic_name, Integer single_data) {
        Properties props = new Properties();
        props.put(Constants.BOOTSTRAP_SERVERS, Constants.KAFKA_BOOTSTRAP_SERVERS);
        props.put(Constants.ACKS, Constants.KAFKA_ACKS);
        props.put(Constants.RETRIES, Constants.KAFKA_RETRIES);
        props.put(Constants.LINGER_MS, Constants.KAFKA_LINGER_MS);
        props.put(Constants.BATCH_SIZE, Constants.KAFKA_BATCH_SIZE);
        props.put(Constants.BUFFER_MEMORY, Constants.KAFKA_BUFFER_MEMORY);
        props.put(Constants.MAX_REQUEST_SIZE, Constants.KAFKA_MAX_REQUEST_SIZE);
        props.put(Constants.COMPRESSION_TYPE, Constants.KAFKA_COMPRESSION_TYPE);
        props.put(Constants.REQUEST_TIMEOUT_MS, Constants.KAFKA_REQUEST_TIMEOUT_MS);
        props.put(Constants.KEY_SERIALIZER_CLASS, Constants.KAFKA_KEY_SERIALIZER_CLASS_STRING);
        props.put(Constants.VALUE_SERIALIZER_CLASS, Constants.KAFKA_VALUE_SERIALIZER_CLASS_STRING);
        props.put(Constants.MAX_IN_FLIGHT_REQUESTS_PER, Constants.KAFKA_MAX_IN_FLIGHT_REQUESTS_PER);

        this.topic_name = topic_name;
        this.single_data = single_data;
        producer = new KafkaProducer(props);
    }

    /**
     * 覆盖父类的run方法
     */
    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            String startDate = format.format(new Date());
            Integer totle_numbers = single_data;

            while (single_data > 0) {
                single_data--;
                int tmp1 = getRandomValue();
                int tmp2 = getRandomValue();
                String message = "{\"tagName\":\"" + tmp1 + ".HFCCP" + tmp2 + "\",\"tagValue\":" + tmp2 + "." + tmp1 + ",\"isGood\":true,\"piTS\":\"" + format.format(new Date()) + "\",\"sendTS\":\"" + format.format(new Date()) + "\"}";
                producer.send(new ProducerRecord(topic_name, message));
            }

            producer.flush();

            long endTime = System.currentTimeMillis();
            String endDate = format.format(new Date());

            Print.outPrint(startTime, endTime, startDate, endDate, Thread.currentThread().getName(), totle_numbers, topic_name);
        } finally {
            if (producer != null) {
                producer.close();
            }
        }
    }


    /**
     * 随机产生一个0--2000的一个数字
     *
     * @return
     */
    private static int getRandomValue() {
        return (int) (Math.random() * 2000);
    }
}
