package cn.itdeer.kafka.common.fields;

import cn.itdeer.kafka.common.config.Constants;

import java.util.Random;

/**
 * Description : Int类型字段
 * PackageName : cn.itdeer.kafka.core.fields
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:43
 */

public class IntField implements FieldInterface {

    /**
     * 默认最小最大值
     */
    private int min = Constants.INT_FIELD_DEFAULT_MIN;
    private int max = Constants.INT_FIELD_DEFAULT_MAX;

    /**
     * 无参构造函数（getValue方法的最大最小值使用默认值）
     */
    public IntField() {
    }

    /**
     * 一个参数构造函数（getValue方法的最小值使用默认值）
     *
     * @param max 最大值
     */
    public IntField(int max) {
        this.max = max;
    }

    /**
     * 两个参数构造函数
     *
     * @param min 最小值
     * @param max 最大值
     */
    public IntField(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * 实现返回一个在指定范围内随机生成的Int类型的数值
     *
     * @return Int类型的数值
     */
    @Override
    public Object getValue() {
        return new Random().nextInt(max - min + 1) + min;
    }
}
