package cn.itdeer.kafka.common;

/**
 * Description : 输出发送日志信息
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/29/11:11
 */
public class LogPrint {

    /**
     * @param startTime  开始时间
     * @param initTime   初始化时间
     * @param endTime    结束时间
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param threadName 线程名字
     * @param dataNumber 总数据条数
     * @param topicName  主题名称
     */
    public static void outPrint(long startTime, long initTime, long endTime, String startDate, String endDate, String threadName, long dataNumber, String topicName) {
        double totle_time = (endTime - startTime) / 1000;
        double init_time = (initTime - startTime) / 1000;
        double send_time = (endTime - initTime) / 1000;

        System.out.printf("\nThreadNum \t" + "TopicName \t" + "TotleTime(s) \t" + "InitTime(s) \t" + "SendTime(s) \t" + "StartDate \t" + "EndDate \t" + "TotleMessageNums \t" + "Speed \t" + "\n" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t", threadName, topicName, totle_time, init_time, send_time, startDate, endDate, dataNumber, dataNumber / send_time);
        System.out.println();
    }
}
