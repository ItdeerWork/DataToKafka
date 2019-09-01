package cn.itdeer.kafka.core.type;

import cn.itdeer.kafka.common.Constants;
import cn.itdeer.kafka.common.Fields;
import cn.itdeer.kafka.common.Points;
import cn.itdeer.kafka.core.fields.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Description : 初始化及发送数据
 * PackageName : cn.itdeer.kafka.core.type
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/29/11:39
 */
@Slf4j
public class SendData {

    /**
     * 初始化Points资源
     *
     * @param points Points列表
     * @param living 实例存储集合
     * @return Point集合
     */
    public Map<Integer, List<Map<String, Object>>> initPoints(List<Points> points, Map<String, Object> living) {
        Map<Integer, List<Map<String, Object>>> map = new LinkedHashMap<>();
        int number = 0;
        /**
         * 初始化数据字段及数据类型实例
         */
        for (Points point : points) {
            if (point.getPoint().contains(Constants.AND)) {
                String[] point_tmp = point.getPoint().split(Constants.AND);
                List<Map<String, Object>> ll = new LinkedList<>();
                for (String tmp : point_tmp) {
                    Map<String, Object> point_map = new HashMap<>();
                    String[] name_value = tmp.split(Constants.EQUAL);
                    point_map.put(name_value[0], name_value[1]);
                    if (!living.containsKey(name_value[1])) {
                        createLiving(name_value[1], living);
                    }
                    ll.add(point_map);
                }
                map.put(number++, ll);
            }
        }
        return map;
    }

    /**
     * 初始化随机生成值的实例
     *
     * @param data_type 需要生成实例的数据类型名称
     * @param living    存储实例对象的集合
     */
    private void createLiving(String data_type, Map<String, Object> living) {
        /**
         * 初始化String类型字段实例
         */
        if (data_type.contains(Constants.STRING)) {
            int number;
            if (data_type.length() == Constants.STRING.length()) {
                living.put(data_type, new StringField());
            } else {
                number = Integer.parseInt(data_type.substring(7, data_type.length() - 1));
                living.put(data_type, new StringField(number));
            }
        }

        /**
         * 初始化Int类型字段实例
         */
        if (data_type.contains(Constants.INT)) {
            int number;
            if (data_type.length() == Constants.INT.length()) {
                living.put(data_type, new IntField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(4, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new IntField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.INT.length()) {
                number = Integer.parseInt(data_type.substring(4, data_type.length() - 1));
                living.put(data_type, new IntField(number));
            }
        }

        /**
         * 初始化Boolean类型字段实例
         */
        if (data_type.contains(Constants.BOOLEAN)) {
            int number;
            if (data_type.length() == Constants.BOOLEAN.length()) {
                living.put(data_type, new BooleanField());
            } else {
                number = Integer.parseInt(data_type.substring(8, data_type.length() - 1));
                living.put(data_type, new BooleanField(number));
            }
        }

        /**
         * 初始化Double类型字段实例
         */
        if (data_type.contains(Constants.DOUBLE)) {
            int number;
            if (data_type.length() == Constants.DOUBLE.length()) {
                living.put(data_type, new DoubleField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(7, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new DoubleField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DOUBLE.length()) {
                number = Integer.parseInt(data_type.substring(7, data_type.length() - 1));
                living.put(data_type, new DoubleField(number));
            }
        }

        /**
         * 初始化Float类型字段实例
         */
        if (data_type.contains(Constants.FLOAT)) {
            int number;
            if (data_type.length() == Constants.FLOAT.length()) {
                living.put(data_type, new FloatField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(6, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new FloatField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.FLOAT.length()) {
                number = Integer.parseInt(data_type.substring(6, data_type.length() - 1));
                living.put(data_type, new FloatField(number));
            }
        }

        /**
         * 初始化Date类型字段实例
         */
        if (data_type.contains(Constants.DATE)) {
            if (data_type.length() == Constants.DATE.length()) {
                living.put(data_type, new DateField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(5, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new DateField(par[0], par[1]));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                String format = data_type.substring(5, data_type.length() - 1);
                living.put(data_type, new DateField(format));
            }
        }
    }

    /**
     * 初始化Fields资源
     *
     * @param fields Fields列表
     * @return 实例集合
     */
    public Map<String, Object> initFields(List<Fields> fields) {
        Map<String, Object> map = new LinkedHashMap<>();

        /**
         * 初始化数据字段及数据类型实例
         */
        for (Fields field : fields) {
            String[] field_tmp = field.getField().split(Constants.EQUAL);
            String data_type = field_tmp[1];

            /**
             * 初始化String类型字段实例
             */
            if (data_type.contains(Constants.STRING)) {
                int number;
                if (data_type.length() == Constants.STRING.length()) {
                    map.put(field_tmp[0], new StringField());
                } else {
                    number = Integer.parseInt(data_type.substring(7, data_type.length() - 1));
                    map.put(field_tmp[0], new StringField(number));
                }
            }

            /**
             * 初始化Int类型字段实例
             */
            if (data_type.contains(Constants.INT)) {
                int number;
                if (data_type.length() == Constants.INT.length()) {
                    map.put(field_tmp[0], new IntField());
                }
                if (data_type.contains(Constants.COMMA)) {
                    String parameters = data_type.substring(4, data_type.length() - 1);
                    String[] par = parameters.split(Constants.COMMA);
                    map.put(field_tmp[0], new IntField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.INT.length()) {
                    number = Integer.parseInt(data_type.substring(4, data_type.length() - 1));
                    map.put(field_tmp[0], new IntField(number));
                }
            }

            /**
             * 初始化Boolean类型字段实例
             */
            if (data_type.contains(Constants.BOOLEAN)) {
                int number;
                if (data_type.length() == Constants.BOOLEAN.length()) {
                    map.put(field_tmp[0], new BooleanField());
                } else {
                    number = Integer.parseInt(data_type.substring(8, data_type.length() - 1));
                    map.put(field_tmp[0], new BooleanField(number));
                }
            }

            /**
             * 初始化Double类型字段实例
             */
            if (data_type.contains(Constants.DOUBLE)) {
                int number;
                if (data_type.length() == Constants.DOUBLE.length()) {
                    map.put(field_tmp[0], new DoubleField());
                }
                if (data_type.contains(Constants.COMMA)) {
                    String parameters = data_type.substring(7, data_type.length() - 1);
                    String[] par = parameters.split(Constants.COMMA);
                    map.put(field_tmp[0], new DoubleField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DOUBLE.length()) {
                    number = Integer.parseInt(data_type.substring(7, data_type.length() - 1));
                    map.put(field_tmp[0], new DoubleField(number));
                }
            }

            /**
             * 初始化Float类型字段实例
             */
            if (data_type.contains(Constants.FLOAT)) {
                int number;
                if (data_type.length() == Constants.FLOAT.length()) {
                    map.put(field_tmp[0], new FloatField());
                }
                if (data_type.contains(Constants.COMMA)) {
                    String parameters = data_type.substring(6, data_type.length() - 1);
                    String[] par = parameters.split(Constants.COMMA);
                    map.put(field_tmp[0], new FloatField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.FLOAT.length()) {
                    number = Integer.parseInt(data_type.substring(6, data_type.length() - 1));
                    map.put(field_tmp[0], new FloatField(number));
                }
            }

            /**
             * 初始化Date类型字段实例
             */
            if (data_type.contains(Constants.DATE)) {
                if (data_type.length() == Constants.DATE.length()) {
                    map.put(field_tmp[0], new DateField());
                }
                if (data_type.contains(Constants.COMMA)) {
                    String parameters = data_type.substring(5, data_type.length() - 1);
                    String[] par = parameters.split(Constants.COMMA);
                    map.put(field_tmp[0], new DateField(par[0], par[1]));
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                    String format = data_type.substring(5, data_type.length() - 1);
                    map.put(field_tmp[0], new DateField(format));
                }
            }
        }
        return map;
    }

    /**
     * 发送JSON数据
     *
     * @param dataNumber    发送数据量
     * @param timeFrequency 时间间隔
     * @param topicName     主题名称
     * @param map           实例集合
     * @param producer      生成这对象
     * @return 是否发送完毕状态
     */
    public Boolean sendJsonData(Integer dataNumber, Integer timeFrequency, String topicName, Map<String, Object> map, KafkaProducer<String, String> producer) {
        Map<String, Object> value = new LinkedHashMap<>();
        while (dataNumber > 0) {
            for (String key : map.keySet()) {
                value.put(key, ((FieldInterface) map.get(key)).getValue());
            }
            String message = JSONObject.toJSONString(value);
            producer.send(new ProducerRecord(topicName, message));
            value.clear();
            if (timeFrequency > 0) {
                try {
                    Thread.sleep(timeFrequency);
                } catch (Exception e) {
                    log.error("When sending JSON format data for topic [{}], the thread has an interrupt exception. The exception information is as follows: [{}]", topicName, e.getStackTrace());
                }
            }
            dataNumber--;
        }
        return true;
    }

    /**
     * 发送CSV数据
     *
     * @param dataNumber    发送数据量
     * @param timeFrequency 时间间隔
     * @param topicName     主题名称
     * @param separator     分隔符
     * @param map           实例集合
     * @param producer      生成这对象
     * @return 是否发送完毕状态
     */
    public Boolean sendCsvData(int dataNumber, int timeFrequency, String topicName, String separator, Map<String, Object> map, KafkaProducer<String, String> producer) {
        while (dataNumber > 0) {
            String message = "";
            for (String key : map.keySet()) {
                message = message + ((FieldInterface) map.get(key)).getValue() + separator;
            }
            message = message.substring(0, message.lastIndexOf(separator));
            producer.send(new ProducerRecord(topicName, message));
            if (timeFrequency > 0) {
                try {
                    Thread.sleep(timeFrequency);
                } catch (Exception e) {
                    log.error("When sending JSON format data for topic [{}], the thread has an interrupt exception. The exception information is as follows: [{}]", topicName, e.getStackTrace());
                }
            }
            dataNumber--;
        }
        return true;
    }

    /**
     * 发送指定模板的JSON格式数据
     *
     * @param dataNumber    发送数据量
     * @param timeFrequency 时间间隔
     * @param topicName     主题名称
     * @param map           模板集合
     * @param producer      生成这对象
     * @param living        实例集合
     * @return 是否发送完毕状态
     */
    public Boolean sendAppointJsonData(int dataNumber, int timeFrequency, String topicName, Map<Integer, List<Map<String, Object>>> map, KafkaProducer<String, String> producer, Map<String, Object> living) {

        List<String> list = new LinkedList<>();

        while (dataNumber > 0) {
            for (Integer num : map.keySet()) {
                List<Map<String, Object>> ll = map.get(num);
                Map<String, Object> value = new LinkedHashMap<>();
                for (Map<String, Object> mm : ll) {
                    for (String key : mm.keySet()) {
                        if (living.containsKey(mm.get(key))) {
                            Object tmp = ((FieldInterface) living.get(mm.get(key))).getValue();
                            value.put(key, tmp);
                        } else {
                            value.put(key, mm.get(key));
                        }
                    }
                }
                list.add(JSONObject.toJSONString(value));
            }

            for (int i = 0; i < list.size(); i++) {
                producer.send(new ProducerRecord(topicName, list.get(i)));
            }
            list.clear();
            if (timeFrequency > 0) {
                try {
                    Thread.sleep(timeFrequency);
                } catch (Exception e) {
                    log.error("When sending JSON format data for topic [{}], the thread has an interrupt exception. The exception information is as follows: [{}]", topicName, e.getStackTrace());
                }
            }
            dataNumber--;
        }
        return true;
    }

    /**
     * 发送指定模板的CSV格式数据
     *
     * @param dataNumber    发送数据量
     * @param timeFrequency 时间间隔
     * @param topicName     主题名称
     * @param separator     分隔符
     * @param map           模板集合
     * @param producer      生成这对象
     * @param living        实例集合
     * @return 是否发送完毕状态
     */
    public Boolean sendAppointCsvData(int dataNumber, int timeFrequency, String topicName, String separator, Map<Integer, List<Map<String, Object>>> map, KafkaProducer<String, String> producer, Map<String, Object> living) {

        List<String> list = new LinkedList<>();

        while (dataNumber > 0) {
            for (Integer num : map.keySet()) {
                List<Map<String, Object>> ll = map.get(num);
                String value = "";
                for (Map<String, Object> mm : ll) {
                    for (String key : mm.keySet()) {
                        if (living.containsKey(mm.get(key))) {
                            Object tmp = ((FieldInterface) living.get(mm.get(key))).getValue();
                            value = value + tmp + separator;
                        } else {
                            value = value + mm.get(key) + separator;
                        }
                    }
                }
                value = value.substring(0, value.lastIndexOf(separator));
                list.add(value);
            }

            for (int i = 0; i < list.size(); i++) {
                producer.send(new ProducerRecord(topicName, list.get(i)));
            }
            list.clear();
            if (timeFrequency > 0) {
                try {
                    Thread.sleep(timeFrequency);
                } catch (Exception e) {
                    log.error("When sending JSON format data for topic [{}], the thread has an interrupt exception. The exception information is as follows: [{}]", topicName, e.getStackTrace());
                }
            }
            dataNumber--;
        }
        return true;
    }
}
