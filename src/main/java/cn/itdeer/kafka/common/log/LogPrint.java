package cn.itdeer.kafka.common.log;

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
     * @param endTime    结束时间
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param threadName 线程名字
     * @param dataNumber 总数据条数
     * @param topicName  主题名称
     */
    public static void outPrint(long startTime, long endTime, String startDate, String endDate, String threadName, long dataNumber, String topicName) {
        double totle_time = (endTime - startTime) / 1000;

        System.out.printf("\nThreadNum \t" + "TopicName \t" + "TotleTime(s) \t" + "StartDate \t" + "EndDate \t" + "TotleMessageNums \t" + "Speed \t" + "\n" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t", threadName, topicName, totle_time, startDate, endDate, dataNumber, dataNumber / totle_time);
        System.out.println();
    }
}
