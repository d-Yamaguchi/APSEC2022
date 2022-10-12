package com.tongcheng.soothsay.dialog;
import com.tongcheng.soothsay.R;
import com.tongcheng.soothsay.other.BaZi;
import com.tongcheng.soothsay.utils.LogUtil;
import static com.tongcheng.soothsay.R.layout.dialog;
/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @author 
 * @Override public void onClick(InputView v) { DateTimePickDialogUtil
dateTimePicKDialog=new
DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
dateTimePicKDialog.dateTimePicKDialog(inputDate);
<p>
} });
 */
public class DateTimePickDialogUtil implements android.widget.DatePicker.OnDateChangedListener , android.widget.TimePicker.OnTimeChangedListener {
    private android.widget.DatePicker datePicker;

    private android.widget.TimePicker timePicker;

    private android.app.AlertDialog ad;

    private java.lang.String dateTime;

    private java.lang.String initDateTime;

    private android.app.Activity activity;

    private android.widget.Button btnSure;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity
     * 		：调用的父activity
     * @param initDateTime
     * 		初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DateTimePickDialogUtil(android.app.Activity activity, java.lang.String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
    }

    public void init(android.widget.DatePicker datePicker, android.widget.TimePicker timePicker) {
        if (!((null == initDateTime) || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = (((((((calendar.get(java.util.Calendar.YEAR) + "年") + calendar.get(java.util.Calendar.MONTH)) + "月") + calendar.get(java.util.Calendar.DAY_OF_MONTH)) + "日 ") + calendar.get(java.util.Calendar.HOUR_OF_DAY)) + ":") + calendar.get(java.util.Calendar.MINUTE);
        }
        datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), this);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int lastHour = 0;
        timePicker.setHour(lastHour);
        timePicker.setCurrentMinute(calendar.get(java.util.Calendar.MINUTE));
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate
     * 		:为需要设置的日期时间文本编辑框
     * @return 
     */
    public android.app.AlertDialog dateTimePicKDialog(final android.widget.TextView inputDate) {
        android.widget.RelativeLayout dateTimeLayout = ((android.widget.RelativeLayout) (activity.getLayoutInflater().inflate(R.layout.dialog_lingfu_datatime, null)));
        datePicker = ((android.widget.DatePicker) (dateTimeLayout.findViewById(R.id.datepicker)));
        timePicker = ((android.widget.TimePicker) (dateTimeLayout.findViewById(R.id.timepicker)));
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        ad = new android.app.AlertDialog.Builder(activity).setView(dateTimeLayout).setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int whichButton) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy年MM月dd日HH:mm");
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(sdf.parse(dateTime));
                    com.tongcheng.soothsay.other.BaZi bazi = new com.tongcheng.soothsay.other.BaZi(cal);
                    int min = java.lang.Integer.parseInt(dateTime.substring(12, 14));
                    java.lang.String lunar = bazi.getYearGanZhi(min);
                    java.lang.String lunarY = lunar.split(",")[0];
                    java.lang.String lunarM = lunar.split(",")[1];
                    java.lang.String lunarD = lunar.split(",")[2];
                    java.lang.String lunarS = lunar.split(",")[3];
                    inputDate.setText(((((((lunarY + "年") + lunarM) + "月") + lunarD) + "日") + lunarS) + "时");
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int whichButton) {
                inputDate.setText("");
            }
        }).show();
        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 获得日历实例
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    /**
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime
     * 		初始日期时间值 字符串型
     * @return Calendar
     */
    private java.util.Calendar getCalendarByInintData(java.lang.String initDateTime) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
        java.lang.String date = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(initDateTime, "日", "index", "front");// 日期

        java.lang.String time = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(initDateTime, "日", "index", "back");// 时间

        java.lang.String yearStr = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(date, "年", "index", "front");// 年份

        java.lang.String monthAndDay = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(date, "年", "index", "back");// 月日

        java.lang.String monthStr = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(monthAndDay, "月", "index", "front");// 月

        java.lang.String dayStr = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(monthAndDay, "月", "index", "back");// 日

        java.lang.String hourStr = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(time, ":", "index", "front");// 时

        java.lang.String minuteStr = com.tongcheng.soothsay.dialog.DateTimePickDialogUtil.spliteString(time, ":", "index", "back");// 分

        int currentYear = java.lang.Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = java.lang.Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = java.lang.Integer.valueOf(dayStr.trim()).intValue();
        int currentHour = java.lang.Integer.valueOf(hourStr.trim()).intValue();
        int currentMinute = java.lang.Integer.valueOf(minuteStr.trim()).intValue();
        calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
        return calendar;
    }

    /**
     * 截取子串
     *
     * @param srcStr
     * 		源串
     * @param pattern
     * 		匹配模式
     * @param indexOrLast
     * 		
     * @param frontOrBack
     * 		
     * @return 
     */
    public static java.lang.String spliteString(java.lang.String srcStr, java.lang.String pattern, java.lang.String indexOrLast, java.lang.String frontOrBack) {
        java.lang.String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern);// 取得字符串第一次出现的位置

        } else {
            loc = srcStr.lastIndexOf(pattern);// 最后一个匹配串的位置

        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != (-1))
                result = srcStr.substring(0, loc);
            // 截取子串

        } else if (loc != (-1))
            result = srcStr.substring(loc + 1, srcStr.length());
        // 截取子串

        return result;
    }
}