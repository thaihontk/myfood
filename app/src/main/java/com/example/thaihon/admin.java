package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class admin extends AppCompatActivity {
    public static String url;
    public static String idnguoidung;
    public static String pass;
    public static String ten;
    public static String diachi;
    public static String lat;
    public static String lng;
    public static String ship1;
    public static String ship2;
    public static String ship3;
    public static String tinh;
    public static String quan;
    public static String phuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        idnguoidung = intent.getStringExtra("idnguoidung");
        pass = intent.getStringExtra("pass");
        ten = intent.getStringExtra("ten");
        diachi = intent.getStringExtra("diachi");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        ship1 = intent.getStringExtra("ship1");
        ship2 = intent.getStringExtra("ship2");
        ship3 = intent.getStringExtra("ship3");
        tinh = intent.getStringExtra("tinh");
        quan = intent.getStringExtra("quan");
        phuong = intent.getStringExtra("phuong");
        Intent intent1 = new Intent(admin.this,trangchu.class);
        startActivity(intent1);

    }
    public static String getUrl(){
        return url;
    }
    public static String getId(){
        return idnguoidung;
    }
    public static String getPass(){
        return pass;
    }
    public static String getTen(){
        return ten;
    }
    public static String getDiachi(){
        return diachi;
    }
    public static String getLat(){
        return lat;
    }
    public static String getLng(){
        return lng;
    }
    public static String getShip1(){
        return ship1;
    }
    public static String getShip2(){
        return ship2;
    }
    public static String getShip3(){
        return ship3;
    }
    public static String getTinh(){
        return tinh;
    }
    public static String getQuan(){
        return quan;
    }
    public static String getPhuong(){
        return phuong;
    }
}
