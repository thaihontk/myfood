package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lichsu extends AppCompatActivity {
    RecyclerView lichsu_rv_wait, lichsu_rv_done;
    private RecyclerViewLichsu recyclerViewLicsu;
    private RecyclerViewLichsudone recyclerViewLicsudone;
    private List<String> urlsv = new ArrayList<>();
    private List<String> idnd = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> ten = new ArrayList<>();
    private List<String> diachi = new ArrayList<>();
    private List<String> gia = new ArrayList<>();
    private List<String> ngay = new ArrayList<>();
    private List<String> hinhanh = new ArrayList<>();
    private List<String> trangthai = new ArrayList<>();
    private List<String> urlsv1 = new ArrayList<>();
    private List<String> idnd1 = new ArrayList<>();
    private List<String> idmonan1 = new ArrayList<>();
    private List<String> ten1 = new ArrayList<>();
    private List<String> diachi1 = new ArrayList<>();
    private List<String> gia1 = new ArrayList<>();
    private List<String> ngay1 = new ArrayList<>();
    private List<String> hinhanh1 = new ArrayList<>();
    private List<String> trangthai1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu);
        Intent intent = getIntent();
        final String url = admin.getUrl();
        final String idnguoidung = admin.getId();
        bien();
        dulieudone(url,idnguoidung);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dulieuwait(url,idnguoidung);
        //Toast.makeText(getApplicationContext(),"chờ:    ", Toast.LENGTH_LONG).show();


    }
    public void bien(){
        lichsu_rv_done = (RecyclerView)findViewById(R.id.lichsu_rv_done);
        lichsu_rv_wait = (RecyclerView)findViewById(R.id.lichsu_rv_wait);
    }
    public void dulieuwait(final String url1, final String idnguoidung){
        final String url = url1+"/lichsuwait.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"wait:  "+response.trim(), Toast.LENGTH_LONG).show();
                try {
                    urlsv.clear();
                    idnd.clear();
                    idmonan.clear();
                    ten.clear();
                    diachi.clear();
                    gia.clear();
                    ngay.clear();
                    hinhanh.clear();
                    trangthai.clear();

                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        urlsv.add(url1);
                        idnd.add(idnguoidung);
                        idmonan.add(jsonObject[i].getString("idmonan"));
                        ten.add(jsonObject[i].getString("tenmonan"));
                        diachi.add(jsonObject[i].getString("diachimonan"));
                        gia.add(jsonObject[i].getString("thanhtien"));
                        ngay.add(jsonObject[i].getString("ngay"));
                        hinhanh.add(jsonObject[i].getString("urlmonan"));
                        trangthai.add(jsonObject[i].getString("trangthai"));
                        //motamonan.add("test:"+i);
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    recyclerViewLicsu = new RecyclerViewLichsu(lichsu.this,urlsv,idnd,idmonan,ten,diachi,gia, ngay, hinhanh, trangthai);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    lichsu_rv_wait.setLayoutManager(layoutManager);
                    lichsu_rv_wait.setAdapter(recyclerViewLicsu);
                }catch (JSONException e1){
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idnguoidung",idnguoidung);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dulieudone(final String url1, final String idnguoidung){
        final String url = url1+"/lichsudone.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"done:  "+response.trim(), Toast.LENGTH_LONG).show();
                try {
                    urlsv1.clear();
                    idnd1.clear();
                    idmonan1.clear();
                    ten1.clear();
                    diachi1.clear();
                    gia1.clear();
                    ngay1.clear();
                    hinhanh1.clear();
                    trangthai1.clear();

                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        urlsv1.add(url1);
                        idnd1.add(idnguoidung);
                        idmonan1.add(jsonObject[i].getString("idmonan"));
                        ten1.add(jsonObject[i].getString("tenmonan"));
                        diachi1.add(jsonObject[i].getString("diachimonan"));
                        gia1.add(jsonObject[i].getString("thanhtien"));
                        ngay1.add(jsonObject[i].getString("ngay"));
                        hinhanh1.add(jsonObject[i].getString("urlmonan"));
                        trangthai1.add(jsonObject[i].getString("trangthai"));
                        //motamonan.add("test:"+i);
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    recyclerViewLicsudone = new RecyclerViewLichsudone(lichsu.this,urlsv1,idnd1,idmonan1,ten1,diachi1,gia1, ngay1, hinhanh1, trangthai1);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    lichsu_rv_done.setLayoutManager(layoutManager);
                    lichsu_rv_done.setAdapter(recyclerViewLicsudone);
                }catch (JSONException e1){
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idnguoidung",idnguoidung);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
