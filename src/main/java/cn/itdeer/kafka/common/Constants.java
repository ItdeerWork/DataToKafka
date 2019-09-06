package cn.itdeer.kafka.common;

import java.text.SimpleDateFormat;

/**
 * Description : 常量
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/14:46
 */
public class Constants {

    /**
     * Data Type Config
     */
    public static final String CSV = "csv";
    public static final String JSON = "json";

    /**
     * Symbolic Config
     */
    public static final String EQUAL = "==";
    public static final String COMMA = ",";
    public static final String AND = "&&";

    /**
     * Other Config
     */
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Data Field Config （-string -boolean -double -int -float -date）
     */
    public static final Integer STRING_FIELD_DEFAULT_LENGTH = InitConfig.getCommons().getStringFieldDefaultLength();
    public static final Integer STRING_FIELD_DEFAULT_WRITE = InitConfig.getCommons().getStringFieldDefaultWrite();
    public static final Integer BOOLEAN_FIELD_DEFAULT_FLAG = InitConfig.getCommons().getBooleanFieldDefaultFlag();

    public static final Integer INT_FIELD_DEFAULT_MIN = InitConfig.getCommons().getIntFieldDefaultMin();
    public static final Integer INT_FIELD_DEFAULT_MAX = InitConfig.getCommons().getIntFieldDefaultMax();

    public static final String DOUBLE_FIELD_DEFAULT_FORMAT = InitConfig.getCommons().getDoubleFieldDefaultFormat();
    public static final Double DOUBLE_FIELD_DEFAULT_MIN = InitConfig.getCommons().getDoubleFieldDefaultMin();
    public static final Double DOUBLE_FIELD_DEFAULT_MAX = InitConfig.getCommons().getDoubleFieldDefaultMax();

    public static final String FLOAT_FIELD_DEFAULT_FORMAT = InitConfig.getCommons().getDoubleFieldDefaultFormat();
    public static final Float FLOAT_FIELD_DEFAULT_MIN = InitConfig.getCommons().getFloatFieldDefaultMin();
    public static final Float FLOAT_FIELD_DEFAULT_MAX = InitConfig.getCommons().getFloatFieldDefaultMax();


    public static final String DATE_FIELD_DEFAULT_START_POINT = InitConfig.getCommons().getDateFieldDefaultStartPoint();
    public static final String DATE_FIELD_DEFAULT_FORMAT = InitConfig.getCommons().getDateFieldDefaultFormat();

    /**
     * Data Field Type Config
     */
    public static final String STRING = "string";
    public static final String BOOLEAN = "boolean";
    public static final String DOUBLE = "double";
    public static final String INT = "int";
    public static final String FLOAT = "float";
    public static final String DATE = "date";

}
