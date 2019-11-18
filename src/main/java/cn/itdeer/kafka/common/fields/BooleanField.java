package cn.itdeer.kafka.common.fields;

import cn.itdeer.kafka.common.config.Constants;

import java.util.Random;

/**
 * Description : Boolean类型字段
 * PackageName : cn.itdeer.kafka.core.fields
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/11:46
 */

public class BooleanField implements FieldInterface {

    /**
     * 默认flag的值
     */
    private int flag = Constants.BOOLEAN_FIELD_DEFAULT_FLAG;

    /**
     * 无参构造函数（getValue方法的flag使用默认值）
     */
    public BooleanField() {
    }

    /**
     * 一个参数构造函数
     *
     * @param flag 标志true false
     */
    public BooleanField(int flag) {
        this.flag = flag;
    }

    /**
     * 实现返回一个指定Boolean类型的对象
     *
     * @return Boolean类型的对象
     */
    @Override
    public Object getValue() {
        if (flag > 0) {
            return true;
        }
        if (flag < 0) {
            return false;
        }
        return new Random().nextBoolean();
    }
}
