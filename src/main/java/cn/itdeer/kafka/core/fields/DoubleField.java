package cn.itdeer.kafka.core.fields;

import cn.itdeer.kafka.common.Constants;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Description : Double类型字段
 * PackageName : cn.itdeer.kafka.core.fields
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:43
 */
public class DoubleField implements FieldInterface {

    /**
     * 默认最小最大值
     */
    private int min = Constants.DOUBLE_FIELD_DEFAULT_MIN;
    private int max = Constants.DOUBLE_FIELD_DEFAULT_MAX;

    private DecimalFormat df = new DecimalFormat(Constants.DOUBLE_FIELD_DEFAULT_FORMAT);

    /**
     * 无参构造函数（getValue方法的最大最小值使用默认值）
     */
    public DoubleField() {
    }

    /**
     * 一个参数构造函数（getValue方法的最小值使用默认值）
     *
     * @param max 最大值
     */
    public DoubleField(int max) {
        this.max = max;
    }

    /**
     * 两个参数构造函数
     *
     * @param min 最小值
     * @param max 最大值
     */
    public DoubleField(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * 实现返回一个在指定范围内随机生成的Double类型的数值
     *
     * @return Double类型的数值
     */
    @Override
    public Object getValue() {
        return Double.parseDouble(df.format(min + ((max - min) * new Random().nextDouble())));
    }
}
