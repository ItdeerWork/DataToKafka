package cn.itdeer.kafka.common.init;

import cn.itdeer.kafka.common.config.Fields;
import cn.itdeer.kafka.common.config.Points;
import cn.itdeer.kafka.common.fields.*;

import java.util.*;

import static cn.itdeer.kafka.common.config.Constants.*;

/**
 * Description : 初始化消息值的获取实例
 * PackageName : cn.itdeer.kafka.common.init
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/17/0:08
 */

public class InitMessage {

    /**
     * 初始化Points类型发数值实例资源
     *
     * @param points Points列表
     * @param living 实例存储集合
     * @return Point集合
     */
    public Map<Integer, List<Map<String, Object>>> initPointsInstance(List<Points> points, Map<String, Object> living) {
        Map<Integer, List<Map<String, Object>>> map = new LinkedHashMap<>();
        int number = 0;
        /**
         * 初始化数据字段及数据类型实例
         */
        for (Points point : points) {
            if (point.getPoint().contains(AND)) {
                String[] point_tmp = point.getPoint().split(AND);
                List<Map<String, Object>> ll = new LinkedList<>();
                for (String tmp : point_tmp) {
                    Map<String, Object> point_map = new HashMap<>();
                    String[] name_value = tmp.split(EQUAL);
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
        if (data_type.contains(STRING)) {
            int number;
            if (data_type.length() == STRING.length()) {
                living.put(data_type, new StringField());
            } else {
                number = Integer.parseInt(data_type.substring(STRING.length() + 1, data_type.length() - 1));
                living.put(data_type, new StringField(number));
            }
        }

        /**
         * 初始化Int类型字段实例
         */
        if (data_type.contains(INT)) {
            int number;
            if (data_type.length() == INT.length()) {
                living.put(data_type, new IntField());
            }
            if (data_type.contains(COMMA)) {
                String parameters = data_type.substring(INT.length() + 1, data_type.length() - 1);
                String[] par = parameters.split(COMMA);
                living.put(data_type, new IntField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
            }
            if (!data_type.contains(COMMA) && data_type.length() > INT.length()) {
                number = Integer.parseInt(data_type.substring(INT.length() + 1, data_type.length() - 1));
                living.put(data_type, new IntField(number));
            }
        }

        /**
         * 初始化Boolean类型字段实例
         */
        if (data_type.contains(BOOLEAN)) {
            int number;
            if (data_type.length() == BOOLEAN.length()) {
                living.put(data_type, new BooleanField());
            } else {
                number = Integer.parseInt(data_type.substring(BOOLEAN.length() + 1, data_type.length() - 1));
                living.put(data_type, new BooleanField(number));
            }
        }

        /**
         * 初始化Double类型字段实例
         */
        if (data_type.contains(DOUBLE)) {
            Double number;
            if (data_type.length() == DOUBLE.length()) {
                living.put(data_type, new DoubleField());
            }
            if (data_type.contains(COMMA)) {
                String parameters = data_type.substring(DOUBLE.length() + 1, data_type.length() - 1);
                String[] par = parameters.split(COMMA);
                living.put(data_type, new DoubleField(Double.parseDouble(par[0]), Double.parseDouble(par[1])));
            }
            if (!data_type.contains(COMMA) && data_type.length() > DOUBLE.length()) {
                number = Double.parseDouble(data_type.substring(DOUBLE.length() + 1, data_type.length() - 1));
                living.put(data_type, new DoubleField(number));
            }
        }

        /**
         * 初始化Float类型字段实例
         */
        if (data_type.contains(FLOAT)) {
            float number;
            if (data_type.length() == FLOAT.length()) {
                living.put(data_type, new FloatField());
            }
            if (data_type.contains(COMMA)) {
                String parameters = data_type.substring(FLOAT.length() + 1, data_type.length() - 1);
                String[] par = parameters.split(COMMA);
                living.put(data_type, new FloatField(Float.parseFloat(par[0]), Float.parseFloat(par[1])));
            }
            if (!data_type.contains(COMMA) && data_type.length() > FLOAT.length()) {
                number = Float.parseFloat(data_type.substring(FLOAT.length() + 1, data_type.length() - 1));
                living.put(data_type, new FloatField(number));
            }
        }

        /**
         * 初始化Date类型字段实例
         */
        if (data_type.contains(DATE)) {
            if (data_type.length() == DATE.length()) {
                living.put(data_type, new DateField());
            }
            if (data_type.contains(COMMA)) {
                String parameters = data_type.substring(DATE.length() + 1, data_type.length() - 1);
                String[] par = parameters.split(COMMA);
                if (par.length == 3) {
                    living.put(data_type, new DateField(par[0], par[1], Integer.parseInt(par[2])));
                } else {
                    living.put(data_type, new DateField(par[0], par[1]));
                }
            }
            if (!data_type.contains(COMMA) && data_type.length() > DATE.length()) {
                String format = data_type.substring(DATE.length() + 1, data_type.length() - 1);
                living.put(data_type, new DateField(format));
            }
        }

        /**
         * 初始化Switching类型字段实例
         */
        if (data_type.contains(SWITCHING)) {
            if (data_type.length() == SWITCHING.length()) {
                living.put(data_type, new SwitchingField());
            }
            //和其他的数据类型一列配置时，点位配置表的上限和下限值为两列，这里截取前一列当做Switching类型的参数
            if (data_type.contains(COMMA)) {
                String type = data_type.substring(SWITCHING.length() + 1, data_type.indexOf(COMMA));
                living.put(data_type, new SwitchingField(Integer.parseInt(type)));
            }
            if (!data_type.contains(COMMA) && data_type.length() > SWITCHING.length()) {
                String type = data_type.substring(SWITCHING.length() + 1, data_type.length() - 1);
                living.put(data_type, new SwitchingField(Integer.parseInt(type)));
            }
        }

    }

    /**
     * 初始化Fields类型发数值实例资源
     *
     * @param fields Fields列表
     * @return 实例集合
     */
    public Map<String, Object> initFieldsInstance(List<Fields> fields) {
        Map<String, Object> map = new LinkedHashMap<>();

        /**
         * 初始化数据字段及数据类型实例
         */
        for (Fields field : fields) {
            String[] field_tmp = field.getField().split(EQUAL);
            String data_type = field_tmp[1];

            /**
             * 初始化String类型字段实例
             */
            if (data_type.contains(STRING)) {
                int number;
                if (data_type.length() == STRING.length()) {
                    map.put(field_tmp[0], new StringField());
                } else {
                    number = Integer.parseInt(data_type.substring(STRING.length() + 1, data_type.length() - 1));
                    map.put(field_tmp[0], new StringField(number));
                }
            }

            /**
             * 初始化Int类型字段实例
             */
            if (data_type.contains(INT)) {
                int number;
                if (data_type.length() == INT.length()) {
                    map.put(field_tmp[0], new IntField());
                }
                if (data_type.contains(COMMA)) {
                    String parameters = data_type.substring(INT.length() + 1, data_type.length() - 1);
                    String[] par = parameters.split(COMMA);
                    map.put(field_tmp[0], new IntField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(COMMA) && data_type.length() > INT.length()) {
                    number = Integer.parseInt(data_type.substring(INT.length() + 1, data_type.length() - 1));
                    map.put(field_tmp[0], new IntField(number));
                }
            }

            /**
             * 初始化Boolean类型字段实例
             */
            if (data_type.contains(BOOLEAN)) {
                int number;
                if (data_type.length() == BOOLEAN.length()) {
                    map.put(field_tmp[0], new BooleanField());
                } else {
                    number = Integer.parseInt(data_type.substring(BOOLEAN.length() + 1, data_type.length() - 1));
                    map.put(field_tmp[0], new BooleanField(number));
                }
            }

            /**
             * 初始化Double类型字段实例
             */
            if (data_type.contains(DOUBLE)) {
                int number;
                if (data_type.length() == DOUBLE.length()) {
                    map.put(field_tmp[0], new DoubleField());
                }
                if (data_type.contains(COMMA)) {
                    String parameters = data_type.substring(DOUBLE.length() + 1, data_type.length() - 1);
                    String[] par = parameters.split(COMMA);
                    map.put(field_tmp[0], new DoubleField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(COMMA) && data_type.length() > DOUBLE.length()) {
                    number = Integer.parseInt(data_type.substring(DOUBLE.length() + 1, data_type.length() - 1));
                    map.put(field_tmp[0], new DoubleField(number));
                }
            }

            /**
             * 初始化Float类型字段实例
             */
            if (data_type.contains(FLOAT)) {
                int number;
                if (data_type.length() == FLOAT.length()) {
                    map.put(field_tmp[0], new FloatField());
                }
                if (data_type.contains(COMMA)) {
                    String parameters = data_type.substring(FLOAT.length() + 1, data_type.length() - 1);
                    String[] par = parameters.split(COMMA);
                    map.put(field_tmp[0], new FloatField(Integer.parseInt(par[0]), Integer.parseInt(par[1])));
                }
                if (!data_type.contains(COMMA) && data_type.length() > FLOAT.length()) {
                    number = Integer.parseInt(data_type.substring(FLOAT.length() + 1, data_type.length() - 1));
                    map.put(field_tmp[0], new FloatField(number));
                }
            }

            /**
             * 初始化Date类型字段实例
             */
            if (data_type.contains(DATE)) {
                if (data_type.length() == DATE.length()) {
                    map.put(field_tmp[0], new DateField());
                }
                if (data_type.contains(COMMA)) {
                    String parameters = data_type.substring(DATE.length() + 1, data_type.length() - 1);
                    String[] par = parameters.split(COMMA);
                    if (par.length == 3) {
                        map.put(field_tmp[0], new DateField(par[0], par[1], Integer.parseInt(par[2])));
                    } else {
                        map.put(field_tmp[0], new DateField(par[0], par[1]));
                    }
                }
                if (!data_type.contains(COMMA) && data_type.length() > DATE.length()) {
                    String format = data_type.substring(DATE.length() + 1, data_type.length() - 1);
                    map.put(field_tmp[0], new DateField(format));
                }
            }

            /**
             * 初始化Switching类型字段实例
             */
            if (data_type.contains(SWITCHING)) {
                if (data_type.length() == SWITCHING.length()) {
                    map.put(field_tmp[0], new SwitchingField());
                }
                //和其他的数据类型一列配置时，点位配置表的上限和下限值为两列，这里截取前一列当做Switching类型的参数
                if (data_type.contains(COMMA)) {
                    String type = data_type.substring(SWITCHING.length() + 1, data_type.indexOf(COMMA));
                    map.put(field_tmp[0], new SwitchingField(Integer.parseInt(type)));
                }
                if (!data_type.contains(COMMA) && data_type.length() > SWITCHING.length()) {
                    String type = data_type.substring(SWITCHING.length() + 1, data_type.length() - 1);
                    map.put(field_tmp[0], new SwitchingField(Integer.parseInt(type)));
                }
            }

        }
        return map;
    }

}
