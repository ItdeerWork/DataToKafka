package cn.itdeer.kafka.common;

import lombok.Data;

/**
 * Description : 默认通用配置
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/14:58
 */
@Data
public class Commons {

    private Integer stringFieldDefaultLength = 4;                       //字符串的长度为4个字符组成
    private Integer stringFieldDefaultWrite = 7;                        // 1 表示大写  2 表示小写 3 表示数字 4 表示大写小写混合 5 表示小写和数字 6 表示大写和数字 7 表示大写小写和数字

    private Integer booleanFieldDefaultFlag = 0;                        // 1 表示true  -1 表示false 0 表示随机true或false

    private Integer intFieldDefaultMin = 0;                             // 表示int最小取值范围
    private Integer intFieldDefaultMax = 10000;                         // 表示int最大取值范围

    private String doubleFieldDefaultFormat = "#0.0000";                // 表示保留的小数点位数
    private Double doubleFieldDefaultMin = 0.0;                          // 表示double最小取值范围
    private Double doubleFieldDefaultMax = 10000.0;                      // 表示double最大取值范围
    private String floatFieldDefaultFormat = "#0.00";                   // 表示保留的小数点位数
    private Float floatFieldDefaultMin = 0.0f;                           // 表示float最小取值范围
    private Float floatFieldDefaultMax = 10000.0f;                       // 表示float最大取值范围

    private String dateFieldDefaultStartPoint = "now";                  // 表示开始时间
    private String dateFieldDefaultFormat = "yyyy-MM-dd HH:mm:ss";      // 表示时间格式

}
