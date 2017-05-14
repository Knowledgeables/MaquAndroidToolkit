/*
 * ========================================================
 * Copyright(c) 2014 杭州偶尔科技-版权所有
 * ========================================================
 * 本软件由杭州龙骞科技所有, 未经书面许可, 任何单位和个人不得以
 * 任何形式复制代码的部分或全部, 并以任何形式传播。
 * 公司网址
 */

package im.maqu.matk.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {

    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SIMPLE_TIME_FORMAT = "HH:mm:ss";
    private static final String CHINESE_WEEKS[] = {"星期天", "星期一", "星期二",
            "星期三", "星期四", "星期五", "星期六"};
    private static final String DATE_WITH_TIME = "MM/dd hh:mm";
    public static final String DATA_NOW = "yyyymmddhhmmssSSS";


    /**
     * 获取中国星期
     *
     * @param calendar
     * @return
     */
    public static String getChineseWeek(Calendar calendar) {
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        return CHINESE_WEEKS[weekIndex - 1];
    }


    /**
     * 格式化时间
     *
     * @param calendar
     * @param format
     * @return
     */
    public static String formatDate(Calendar calendar, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    public static String formatDateWithTime(String datetime) {
        //datetime.replace(":", "-");
        String temp = datetime;
        String args[] = temp.split("\\.");
        temp = args[0];
        UtilLog.e("获取列表日期length" + args.length);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(temp);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            temp = formatDate(calendar, "MM/dd hh:mm");
            UtilLog.e("获取列表日期time" + temp);

        } catch (ParseException e) {
            e.printStackTrace();
            UtilLog.e("获取列表日期错误" + e.getMessage());
        }
        return temp;
    }

    /**
     * 格式化时间
     *
     * @param
     * @param format
     * @return
     */
    public static String formatDate(long mills, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(mills));
    }

    public static String getDateNow(String Format) {
        SimpleDateFormat df = new SimpleDateFormat(Format);
        return df.format(new Date());
    }

    public static String getDateNow() {
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return df.format(new Date());
    }

    public static String getTimeNow() {
        SimpleDateFormat df = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
        return df.format(new Date());
    }

}
