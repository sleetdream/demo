package com.example.masterslave.holder;


import java.util.ArrayList;
import java.util.List;

public class DataSourceContextHolder {

    private static ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static List<Object> dataSourceKeys = new ArrayList<Object>();

    public static void setDataSourceKey(String key){
        CONTEXT_HOLDER.set(key);
    }

    public static Object getDataSourceKey(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey(){
        CONTEXT_HOLDER.remove();
    }

    public static Boolean containDataSourceKey(String key){
        return dataSourceKeys.contains(key);
    }

} 