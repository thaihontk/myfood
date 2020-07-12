package com.example.thaihon;

public class APIUtils {
    public static final String Base_Url = "http://10.2.19.238/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
