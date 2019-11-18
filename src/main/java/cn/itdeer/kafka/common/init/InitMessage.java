package cn.itdeer.kafka.common.init;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.common.config.Fields;
import cn.itdeer.kafka.common.config.Points;
import cn.itdeer.kafka.common.fields.*;

import java.util.*;

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
            Double number;
            if (data_type.length() == Constants.DOUBLE.length()) {
                living.put(data_type, new DoubleField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(7, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new DoubleField(Double.parseDouble(par[0]), Double.parseDouble(par[1])));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DOUBLE.length()) {
                number = Double.parseDouble(data_type.substring(7, data_type.length() - 1));
                living.put(data_type, new DoubleField(number));
            }
        }

        /**
         * 初始化Float类型字段实例
         */
        if (data_type.contains(Constants.FLOAT)) {
            float number;
            if (data_type.length() == Constants.FLOAT.length()) {
                living.put(data_type, new FloatField());
            }
            if (data_type.contains(Constants.COMMA)) {
                String parameters = data_type.substring(6, data_type.length() - 1);
                String[] par = parameters.split(Constants.COMMA);
                living.put(data_type, new FloatField(Float.parseFloat(par[0]), Float.parseFloat(par[1])));
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.FLOAT.length()) {
                number = Float.parseFloat(data_type.substring(6, data_type.length() - 1));
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
                if (par.length == 3) {
                    living.put(data_type, new DateField(par[0], par[1], Integer.parseInt(par[2])));
                } else {
                    living.put(data_type, new DateField(par[0], par[1]));
                }
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                String format = data_type.substring(5, data_type.length() - 1);
                living.put(data_type, new DateField(format));
            }
        }

        /**
         * 初始化Switching类型字段实例
         */
        if (data_type.contains(Constants.SWITCHING)) {
            if (data_type.length() == Constants.SWITCHING.length()) {
                living.put(data_type, new SwitchingField());
            }
            if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                String type = data_type.substring(10, data_type.length() - 1);
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
                    if (par.length == 3) {
                        map.put(field_tmp[0], new DateField(par[0], par[1], Integer.parseInt(par[2])));
                    } else {
                        map.put(field_tmp[0], new DateField(par[0], par[1]));
                    }
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                    String format = data_type.substring(5, data_type.length() - 1);
                    map.put(field_tmp[0], new DateField(format));
                }
            }

            /**
             * 初始化Switching类型字段实例
             */
            if (data_type.contains(Constants.SWITCHING)) {
                if (data_type.length() == Constants.SWITCHING.length()) {
                    map.put(field_tmp[0], new SwitchingField());
                }
                if (!data_type.contains(Constants.COMMA) && data_type.length() > Constants.DATE.length()) {
                    String type = data_type.substring(10, data_type.length() - 1);
                    map.put(field_tmp[0], new SwitchingField(Integer.parseInt(type)));
                }
            }

        }
        return map;
    }

}
