package com.hms.appointment.utility;

import java.util.List;

public class StringListConverter {
    public static  String convertStringListToString(List<String> stringList){
    if(stringList==null || stringList.isEmpty()) return "";
    return String.join(",", stringList);
    }

    public static List<String> convertStringToStringList(String str){
    if(str==null || str.isEmpty()) return List.of();
    return List.of(str.split(","));
    }
}
