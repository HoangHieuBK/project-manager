package com.example.demo.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.ProjectProgress;
import com.example.demo.entity.TaskProgress;

public final class Util {
    public static List<Date> getListDate(Date startDate, Date finishDate, Date lastDateFromLog) {
        List<Date> listDate = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if (lastDateFromLog.after(finishDate)) {
            finishDate = lastDateFromLog;
        }
        /*
         * finishDate.setHours(0); finishDate.setMinutes(0); finishDate.setSeconds(0);
         * startDate.setMinutes(0); startDate.setHours(0); startDate.setSeconds(0);
         */
        Date date = new Date();
        date.setTime(startDate.getTime());
        while (!date.after(finishDate)) {
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            listDate.add(date);
            date = c.getTime();
        }
        return listDate;
    }

    public static List<Double> getListExpectProgress(Date startDate, Date finishDate) {
        List<Double> listProgress = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        long duration = finishDate.getTime() - startDate.getTime();
        double progress = 0;
        /*
         * finishDate.setHours(0); finishDate.setMinutes(0); finishDate.setSeconds(0);
         * startDate.setMinutes(0); startDate.setHours(0); startDate.setSeconds(0);
         */
        Date date = new Date(startDate.getTime());
        while (!date.after(finishDate)) {
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            progress = Math.ceil((double) (100 * (date.getTime() - startDate.getTime()) / duration));
            listProgress.add(progress);
            date = c.getTime();
        }
        return listProgress;
    }

    @SuppressWarnings("deprecation")
    public static List<Double> getListActualProgress(Date startDate, List<TaskProgress> workLogList) {
        List<Double> listProgress = new ArrayList<>();
        /*
         * startDate.setHours(0); startDate.setMinutes(0); startDate.setSeconds(0);
         */
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        Date referDate = new Date(startDate.getTime());
        TaskProgress objectProgress;
        int backwardDate;
        int length = workLogList.size();
        for (int i = 0; i < length; i++) {
            objectProgress = workLogList.get(i);
            date.setTime(objectProgress.getDateLog().getTime());
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            while (referDate.before(date)) {
                c.setTime(referDate);
                c.add(Calendar.DATE, 1);
                referDate = c.getTime();
                listProgress.add(null);
            }
            if ((date.getDate() == referDate.getDate()) && (date.getMonth() == referDate.getMonth())
                    && (referDate.getYear() == date.getYear())) {
                listProgress.add(Double.valueOf(objectProgress.getProgress()));
                c.setTime(referDate);
                c.add(Calendar.DATE, 1);
                referDate = c.getTime();
            } else if (date.before(referDate)) {
                backwardDate = (int) (referDate.getTime() - date.getTime()) / (3600 * 24 * 1000);
                try {
                  listProgress.set(listProgress.size() - backwardDate, (double) objectProgress.getProgress());
                } catch (Exception e) {
                  System.out.println("bạn nhập sai ngày log ");
                }
                
            }
        }
        return listProgress;
    }

    @SuppressWarnings("deprecation")
    public static List<String> getLabelFromListDate(List<Date> listDate) {
        List<String> listLabel = new ArrayList<>();
        StringBuilder label = new StringBuilder();
        label.append("[");
        for (Date date : listDate) {
            label.setLength(0);
//            label.append("\"");
            label.append(date.getDate());
            label.append("-");
            label.append(date.getMonth() + 1);
            label.append("-");
            label.append(date.getYear() + 1900);
//            label.append("\"");
            listLabel.add(label.toString());
        }
        return listLabel;
    }

    @SuppressWarnings("deprecation")
    public static List<Double> getListActualProjectProgress(Date startDate, List<ProjectProgress> workLogList) {
        List<Double> listProgress = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        Date referDate = new Date(startDate.getTime());
        ProjectProgress objectProgress;
        int backwardDate;
        int length = workLogList.size();
        for (int i = 0; i < length; i++) {
            objectProgress = workLogList.get(i);
            date.setTime(objectProgress.getDateLog().getTime());
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            while (referDate.before(date)) {
                c.setTime(referDate);
                c.add(Calendar.DATE, 1);
                referDate = c.getTime();
                listProgress.add(null);
            }
            if ((date.getDate() == referDate.getDate()) && (date.getMonth() == referDate.getMonth())
                    && (referDate.getYear() == date.getYear())) {
                listProgress.add(Double.valueOf(objectProgress.getProgress()));
                c.setTime(referDate);
                c.add(Calendar.DATE, 1);
                referDate = c.getTime();
            } else if (date.before(referDate)) {
                backwardDate = (int) (referDate.getTime() - date.getTime()) / (3600 * 24 * 1000);
                listProgress.set(listProgress.size() - backwardDate, (double) objectProgress.getProgress());
            }
        }
        return listProgress;
    }

}
