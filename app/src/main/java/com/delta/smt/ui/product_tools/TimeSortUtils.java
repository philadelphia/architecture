package com.delta.smt.ui.product_tools;

import android.support.annotation.NonNull;
import android.util.Log;


import com.delta.smt.entity.JsonProductBorrowList;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public class TimeSortUtils {

    private String sourceTime1;

    private String sourceTime2;

    private String resEx = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";

    private String mDate1;

    private String mTime1;

    private String mDate2;

    private String mTime2;

    private boolean parseSuccess = true;

    public TimeSortUtils(String sourceTime1, String sourceTime2) {

        this.sourceTime1 = sourceTime1;
        this.sourceTime2 = sourceTime2;

        boolean isMatcher = isMatcher();

        if (isMatcher) {

            mDate1 = this.sourceTime1.split(" ")[0];
            mTime1 = this.sourceTime1.split(" ")[1];

            mDate2 = this.sourceTime2.split(" ")[0];
            mTime2 = this.sourceTime2.split(" ")[1];

            Log.e("ss","success");
            Log.e("Matcher","mDate2 "+mDate2+" "+"mTime2 "+mTime2+1);
        } else {
            this.parseSuccess = false;
            Log.e("ss","fail");
        }
    }

    private boolean isMatcher() {
        Pattern pattern = Pattern.compile(resEx);

        Matcher matcher1 = pattern.matcher(this.sourceTime1);
        Matcher matcher2 = pattern.matcher(this.sourceTime2);

        return matcher1.matches() && matcher2.matches();
    }

    public int comPareDate(String date1, String date2) {

        /**
         * @see return返回值为1则左侧的值大，为0则右侧大，为2是年月日都相等
         * */
        String[] dateList1 = date1.split("-");
        String[] dateList2 = date2.split("-");
        if (Integer.parseInt(dateList1[0]) != Integer.parseInt(dateList2[0])) {
            return Integer.parseInt(dateList1[0]) > Integer.parseInt(dateList2[0]) ? 1 : 0;
        } else if (Integer.parseInt(dateList1[1]) != Integer.parseInt(dateList2[1])) {
            return Integer.parseInt(dateList1[1]) > Integer.parseInt(dateList2[1]) ? 1 : 0;
        } else if (Integer.parseInt(dateList1[2]) != Integer.parseInt(dateList2[2])) {
            return Integer.parseInt(dateList1[2]) > Integer.parseInt(dateList2[2]) ? 1 : 0;
        }
        return 2;
    }

    public int comPareTime(String time1, String time2) {

        /**
         * @see return返回值为1则左侧的值大，为0则右侧大
         * */

        String[] timeList1 = time1.split(":");
        String[] timeList2 = time2.split(":");
        if (Integer.parseInt(timeList1[0]) != Integer.parseInt(timeList2[0])) {
            return Integer.parseInt(timeList1[0]) > Integer.parseInt(timeList2[0]) ? 1 : 0;
        } else if (Integer.parseInt(timeList1[1]) != Integer.parseInt(timeList2[1])) {
            return Integer.parseInt(timeList1[1]) > Integer.parseInt(timeList2[1]) ? 1 : 0;
        } else if (Integer.parseInt(timeList1[2]) != Integer.parseInt(timeList2[2])) {
            return Integer.parseInt(timeList1[2]) > Integer.parseInt(timeList2[2]) ? 1 : 0;
        }
        return 1;
    }

    public int compare() {
        if (comPareDate(this.mDate1, this.mDate2) != 2) {
            return comPareDate(this.mDate1, this.mDate2);
        } else {
            return comPareTime(this.mTime1, this.mTime2);
        }
    }

    public static String getContentFromSource(String string, int from, int to) {

        char[] chars = new char[string.length()];
        for (int i = 0; i < string.length(); i++) {

            chars[i] = string.charAt(i);

        }
        return String.valueOf(Arrays.copyOfRange(chars, from, to));
    }

    @NonNull
    public static String getMyStyleTime(JsonProductBorrowList j) {
        String[] date = j.getPlanPrdTime().split(" ");
        String lastDate = date[2] + "-";
        String month;
        switch (TimeSortUtils.getContentFromSource(date[0], 0, 3)) {
            case "Jan":
                month = "01-";
                break;
            case "Feb":
                month = "02-";
                break;
            case "Mar":
                month = "03-";
                break;
            case "Apr":
                month = "04-";
                break;
            case "May":
                month = "05-";
                break;
            case "Jun":
                month = "06-";
                break;
            case "Jul":
                month = "07-";
                break;
            case "Aug":
                month = "08-";
                break;
            case "Sep":
                month = "09-";
                break;
            case "Oct":
                month = "10-";
                break;
            case "Nov":
                month = "11-";
                break;
            case "Dec":
                month = "12-";
                break;
            default:
                month = "";
        }

        lastDate += month + date[1].replace(",", "") + " ";

        String[] time = date[3].split(":");
        if (date[4].equals("PM")) {
            time[0] = String.valueOf(Integer.parseInt(time[0]) + 12);
        }

        lastDate += time[0] + ":" + time[1] + ":" + time[2];


        TimeSortUtils.getContentFromSource(date[0], 0, 2);
        Log.e("ProduceToolsBorrowModel", lastDate);
        return lastDate;
    }

}
