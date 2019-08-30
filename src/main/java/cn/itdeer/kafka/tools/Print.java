package cn.itdeer.kafka.tools;

/**
 * Description : 测试输出
 * PackageName : cn.itdeer.kafka.tools
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/15:28
 */
public class Print {

    /**
     * 打印测试结果
     *
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @param threadName    线程名称
     * @param totle_numbers 总数据量
     * @param topic_name    主题名称
     */
    public static void outPrint(long startTime, long endTime, String startDate, String endDate, String threadName, Integer totle_numbers, String topic_name) {
        double totle_time = (endTime - startTime) / 1000;
        System.out.printf("\nThreadNum \t" + "TopicName \t" + "TotleTime(s) \t" + "StartDate \t" + "EndDate \t" + "TotleMessageNums \t" + "Speed \t" + "\n" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t" + "%s \t", threadName, topic_name, totle_time, startDate, endDate, totle_numbers, totle_numbers / totle_time);
        System.out.println();
    }

}
