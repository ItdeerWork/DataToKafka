package cn.itdeer.kafka.common.config;

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
     * 配置文件名称
     */
    public static final String CONFIG_FILE_NAME = "runtime.json";
    public static final String CONFIG_FILE_DIRECTORY = "config";

    /**
     * 默认生产者名称
     */
    public static final String DEFAULT_PRODUCER_NAME = "producer";

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
     * Data Field Config （-string -boolean -double -int -float -date -switching<开关量值>）
     */
    public static final Integer STRING_FIELD_DEFAULT_LENGTH = InitConfig.getInstance().getCb().getCommons().getStringFieldDefaultLength();
    public static final Integer STRING_FIELD_DEFAULT_WRITE = InitConfig.getInstance().getCb().getCommons().getStringFieldDefaultWrite();
    public static final Integer BOOLEAN_FIELD_DEFAULT_FLAG = InitConfig.getInstance().getCb().getCommons().getBooleanFieldDefaultFlag();

    public static final Integer INT_FIELD_DEFAULT_MIN = InitConfig.getInstance().getCb().getCommons().getIntFieldDefaultMin();
    public static final Integer INT_FIELD_DEFAULT_MAX = InitConfig.getInstance().getCb().getCommons().getIntFieldDefaultMax();

    public static final String DOUBLE_FIELD_DEFAULT_FORMAT = InitConfig.getInstance().getCb().getCommons().getDoubleFieldDefaultFormat();
    public static final Double DOUBLE_FIELD_DEFAULT_MIN = InitConfig.getInstance().getCb().getCommons().getDoubleFieldDefaultMin();
    public static final Double DOUBLE_FIELD_DEFAULT_MAX = InitConfig.getInstance().getCb().getCommons().getDoubleFieldDefaultMax();

    public static final String FLOAT_FIELD_DEFAULT_FORMAT = InitConfig.getInstance().getCb().getCommons().getFloatFieldDefaultFormat();
    public static final Float FLOAT_FIELD_DEFAULT_MIN = InitConfig.getInstance().getCb().getCommons().getFloatFieldDefaultMin();
    public static final Float FLOAT_FIELD_DEFAULT_MAX = InitConfig.getInstance().getCb().getCommons().getFloatFieldDefaultMax();


    public static final String DATE_FIELD_DEFAULT_START_POINT = InitConfig.getInstance().getCb().getCommons().getDateFieldDefaultStartPoint();
    public static final String DATE_FIELD_DEFAULT_FORMAT = InitConfig.getInstance().getCb().getCommons().getDateFieldDefaultFormat();
    public static final Integer DATE_FIELD_DEFAULT_INTERVAL = InitConfig.getInstance().getCb().getCommons().getDateFieldDefaultInterval();

    public static final Integer SWITCHING_FIELD_DEFAULT_TYPE = InitConfig.getInstance().getCb().getCommons().getSwitchingFieldDefaultType();
    public static final Integer SWITCHING_FIELD_DEFAULT_MAX_VALUE = InitConfig.getInstance().getCb().getCommons().getSwitchingFieldDefaultMaxValue();
    public static final Integer SWITCHING_FIELD_DEFAULT_MIN_VALUE = InitConfig.getInstance().getCb().getCommons().getSwitchingFieldDefaultMinValue();

    /**
     * Data Field Type Config
     */
    public static final String STRING = "string";
    public static final String BOOLEAN = "boolean";
    public static final String DOUBLE = "double";
    public static final String INT = "int";
    public static final String FLOAT = "float";
    public static final String DATE = "date";
    public static final String SWITCHING = "switching";

}
