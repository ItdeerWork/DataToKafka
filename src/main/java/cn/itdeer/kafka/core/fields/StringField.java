package cn.itdeer.kafka.core.fields;

import cn.itdeer.kafka.common.Constants;

import java.util.Random;

/**
 * Description : Int类型字段
 * PackageName : cn.itdeer.kafka.core.fields
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:36
 */
public class StringField implements FieldInterface {

    /**
     * 默认字符串长度
     */
    private int length = Constants.STRING_FIELD_DEFAULT_LENGTH;
    /**
     * 默认使用的字符编号
     */
    private int write = Constants.STRING_FIELD_DEFAULT_WRITE;

    /**
     * 默认使用的字符
     */
    private String chars;

    /**
     * 无参构造函数（getValue方法的length和chars使用默认值）
     */
    public StringField() {
        settingChars();
    }

    /**
     * 一个参数构造函数
     *
     * @param length 字符串长度
     */
    public StringField(int length) {
        this.length = length;
        settingChars();
    }

    /**
     * 设置chars的默认值
     */
    private void settingChars() {
        switch (write) {
            case 1:
                chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case 2:
                chars = "abcdefghijklmnopqrstuvwxyz";
                break;
            case 3:
                chars = "0123456789";
                break;
            case 4:
                chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                break;
            case 5:
                chars = "abcdefghijklmnopqrstuvwxyz0123456789";
                break;
            case 6:
                chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                break;
            case 7:
                chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                break;
        }
    }

    /**
     * 实现返回一个在指定长度随机生成的String类型的字符串
     *
     * @return String类型的字符串
     */
    @Override
    public Object getValue() {
        Random random = new Random();
        String value = "";
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(chars.length());
            value = value + chars.charAt(number);
        }
        return value;
    }
}
