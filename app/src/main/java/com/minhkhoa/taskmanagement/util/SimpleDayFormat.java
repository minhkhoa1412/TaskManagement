package com.minhkhoa.taskmanagement.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDayFormat {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public static String formatDate(Date date){
        return sdf.format(date);
    }
    public static Date formatString (String ngay)throws Exception{
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(ngay);
        return date;
    }
}
