package com.txt.conference.utils;
import com.common.utlis.ULog;
import com.txt.conference.R;
/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @Override public void onClick(View v) { DateTimePickDialogUtil
dateTimePicKDialog=new
DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
dateTimePicKDialog.dateTimePicKDialog(inputDate);

} });
 */
public class DateTimePickDialogUtil implements android.widget.DatePicker.OnDateChangedListener , android.widget.TimePicker.OnTimeChangedListener {
    private android.widget.DatePicker datePicker;

    private android.widget.TimePicker timePicker;

    private android.app.AlertDialog ad;

    private java.lang.String dateTime;

    private java.lang.String initDateTime;

    private android.app.Activity activity;

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
        /* if (calendar.get(Calendar.MINUTE) < 58) {
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE) + 2);
        } else {
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY) + 1);
        timePicker.setCurrentMinute(0);
        }
         */
    }

    public android.app.AlertDialog dateTimePicKDialog() {
        android.widget.LinearLayout dateTimeLayout = ((android.widget.LinearLayout) (activity.getLayoutInflater().inflate(R.layout.common_datetime, null)));
        datePicker = ((android.widget.DatePicker) (dateTimeLayout.findViewById(R.id.datepicker)));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ((android.widget.LinearLayout) (((android.view.ViewGroup) (datePicker.getChildAt(0))).getChildAt(0))).getChildAt(0).setVisibility(android.view.View.GONE);
        } else {
            try {
                java.lang.reflect.Field[] f = datePicker.getClass().getDeclaredFields();
                // 隐藏年
                for (java.lang.reflect.Field field : f) {
                    if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                        field.setAccessible(true);
                        java.lang.Object yearPicker = new java.lang.Object();
                        yearPicker = field.get(datePicker);
                        ((android.view.View) (yearPicker)).setVisibility(android.view.View.GONE);
                    }
                }
            } catch (java.lang.SecurityException e) {
                com.common.utlis.ULog.e("ERROR", e.getMessage());
            } catch (java.lang.IllegalArgumentException e) {
                com.common.utlis.ULog.e("ERROR", e.getMessage());
            } catch (java.lang.IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        timePicker = ((android.widget.TimePicker) (dateTimeLayout.findViewById(R.id.timepicker)));
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        ad = new android.app.AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout).setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int whichButton) {
                // inputDate.setText(dateTime);
                if (dialogListener != null) {
                    dialogListener.onConfirm(dateTime);
                }
            }
        }).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int whichButton) {
                // inputDate.setText("");
                // dialogListener.onCancel();
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
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
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
        java.lang.String date = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(initDateTime, "日", "index", "front");// 日期

        java.lang.String time = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(initDateTime, "日", "index", "back");// 时间

        java.lang.String yearStr = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(date, "年", "index", "front");// 年份

        java.lang.String monthAndDay = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(date, "年", "index", "back");// 月日

        java.lang.String monthStr = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(monthAndDay, "月", "index", "front");// 月

        java.lang.String dayStr = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(monthAndDay, "月", "index", "back");// 日

        java.lang.String hourStr = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(time, ":", "index", "front");// 时

        java.lang.String minuteStr = com.txt.conference.utils.DateTimePickDialogUtil.spliteString(time, ":", "index", "back");// 分

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

    public com.txt.conference.utils.DateTimePickDialogUtil.ITimePickDialogClick dialogListener;

    public void setTimePickeristener(com.txt.conference.utils.DateTimePickDialogUtil.ITimePickDialogClick dialogListener) {
        this.dialogListener = dialogListener;
    }

    public interface ITimePickDialogClick {
        void onConfirm(java.lang.String str);

        void onCancel();
    }
}