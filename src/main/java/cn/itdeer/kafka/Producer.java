package cn.itdeer.kafka;

import cn.itdeer.kafka.tools.Constants;
import cn.itdeer.kafka.tools.Print;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.DecimalFormat;
import java.util.*;

import static cn.itdeer.kafka.tools.Constants.format;

/**
 * Description : 生产者发送数据
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/17:32
 */
public class Producer extends Thread {

    private String topic_name;
    private Integer data_number;
    private Integer point_number;
    private Integer time_frequency;

    private KafkaProducer<String, String> producer;

    private static DecimalFormat df = new DecimalFormat("#0.00");

    /**
     * 构造函数，初始化属性配置
     */
    public Producer(String topic_name, Integer data_number, Integer point_number, Integer time_frequency) {
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
        this.data_number = data_number;
        this.point_number = point_number;
        this.time_frequency = time_frequency;
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
            Integer totle_numbers = data_number;

            if (point_number > 0 && time_frequency > 0) {
                sendData4(producer, topic_name, data_number, point_number, time_frequency);
            } else if (point_number > 0 && time_frequency < 0) {
                sendData2(producer, topic_name, data_number, point_number);
            } else if (point_number < 0 && time_frequency > 0) {
                sendData3(producer, topic_name, data_number, time_frequency);
            } else {
                sendData1(producer, topic_name, data_number);
            }

            producer.flush();

            long endTime = System.currentTimeMillis();
            String endDate = format.format(new Date());

            Print.outPrint(startTime, endTime, startDate, endDate, Thread.currentThread().getName(), totle_numbers, topic_name);
        } catch (InterruptedException e) {

        } finally {
            if (producer != null) {
                producer.close();
            }
        }
    }

    /**
     * 发数函数1
     *
     * @param producer    生产者对象
     * @param topic_name  主题名称
     * @param data_number 发数数据量
     */
    private static void sendData1(KafkaProducer<String, String> producer, String topic_name, Integer data_number) {
        while (data_number > 0) {
            data_number--;
            int tmp1 = getRandomValue();
            int tmp2 = getRandomValue();
            String message = "{\"tagName\":\"" + tmp1 + ".HFCCP" + tmp2 + "\",\"isGood\":true," + "\"tagValue\":" + tmp2 + "." + tmp1 + "," + "\"piTS\":\"" + format.format(new Date()) + "\",\"sendTS\":\"" + format.format(new Date()) + "\"}";
            producer.send(new ProducerRecord(topic_name, message));
        }
    }

    /**
     * 发数函数2
     *
     * @param producer     生产者对象
     * @param topic_name   主题名称
     * @param data_number  发数数据量
     * @param point_number 测点数量
     */
    private static void sendData2(KafkaProducer<String, String> producer, String topic_name, Integer data_number, Integer point_number) {
        List<String> list = randomPointName(point_number);
        while (data_number > 0) {
            for (int i = 0; i < list.size() && data_number > 0; i++) {
                data_number--;
                String message = list.get(i) + getRandomValue() + "." + getRandomValue() + ",\"piTS\":\"" + format.format(new Date()) + "\",\"sendTS\":\"" + format.format(new Date()) + "\"}";
                producer.send(new ProducerRecord(topic_name, message));
            }
        }
    }

    /**
     * 发数函数3
     *
     * @param producer       生产者对象
     * @param topic_name     主题名称
     * @param data_number    发数数据量
     * @param time_frequency 时间频率
     * @throws InterruptedException 中断异常
     */
    private static void sendData3(KafkaProducer<String, String> producer, String topic_name, Integer data_number, Integer time_frequency) throws InterruptedException {
        while (data_number > 0) {
            data_number--;
            int tmp1 = getRandomValue();
            int tmp2 = getRandomValue();
            String message = "{\"tagName\":\"" + tmp1 + ".HFCCP" + tmp2 + "\",\"isGood\":true," + "\"tagValue\":" + tmp2 + "." + tmp1 + "," + "\"piTS\":\"" + format.format(new Date()) + "\",\"sendTS\":\"" + format.format(new Date()) + "\"}";
            producer.send(new ProducerRecord(topic_name, message));
            Thread.sleep(time_frequency);
        }
    }

    /**
     * 发数函数4
     *
     * @param producer       生产者对象
     * @param topic_name     主题名称
     * @param data_number    发数数据量
     * @param point_number   测点数量
     * @param time_frequency 时间频率
     * @throws InterruptedException 中断异常
     */
    private static void sendData4(KafkaProducer<String, String> producer, String topic_name, Integer data_number, Integer point_number, Integer time_frequency) throws InterruptedException {
        List<String> list = randomPointName(point_number);
        while (data_number > 0) {
            for (int i = 0; i < list.size() && data_number > 0; i++) {
                data_number--;
                String message = list.get(i) + getRandomValue() + "." + getRandomValue() + ",\"piTS\":\"" + format.format(new Date()) + "\",\"sendTS\":\"" + format.format(new Date()) + "\"}";
                producer.send(new ProducerRecord(topic_name, message));
                Thread.sleep(time_frequency);
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

    /**
     * 随机生成指定数量的测点名称
     *
     * @param point_number 指定数量
     * @return 测点名称集合
     */
    private static List<String> randomPointName(Integer point_number) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < point_number; i++) {
            String message = "{\"tagName\":\"" + getRandomNumberString(4) + "." + getRandomChartString(10) + "\",\"isGood\":true," + "\"tagValue\":";
            list.add(message);
        }
        return list;
    }

    /**
     * 返回指定长度的字符串
     *
     * @param length 指定长度
     * @return 字符串
     */
    public static String getRandomChartString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 返回指定长度的数字串
     *
     * @param length 指定长度
     * @return 数字串
     */
    public static String getRandomNumberString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
