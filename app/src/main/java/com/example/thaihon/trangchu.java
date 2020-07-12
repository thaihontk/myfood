package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class trangchu extends AppCompatActivity {
    private List<String> urlsv = new ArrayList<>();
    private List<String> idnguoidung1 = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> urlmonan = new ArrayList<>();
    private List<String> tenmonan = new ArrayList<>();
    private List<String> diachimonan = new ArrayList<>();
    private List<String> giamonan = new ArrayList<>();
    private List<String> motamonan = new ArrayList<>();

    private RecyclerView recyclerView;
    private ImageView hinh;
    String realpath = "";
    SearchView sv_tc_timkiem;
    Dialog dialog;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        final String url = admin.getUrl();
        final String idnguoidung = admin.getId();
        final String pass = admin.getPass();
        addControl();
        layhinh();
        createData(url,idnguoidung);

        //showHinh();
        sv_tc_timkiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                timkiemmonan(url,sv_tc_timkiem.getQuery().toString(),idnguoidung);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void addControl(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        hinh = (ImageView) findViewById(R.id.hinh);
        sv_tc_timkiem = (SearchView)findViewById(R.id.sv_tc_timkiem);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        switch (item.getItemId()) {
            case R.id.menu_lichsu:
                Intent intent0 = new Intent(trangchu.this,lichsu.class);
                intent0.putExtra("url", admin.getUrl());
                intent0.putExtra("idnguoidung", admin.getId());
                startActivity(intent0);
                return true;
            case R.id.menu_thich:
                thich(admin.getUrl(),admin.getId());
                return true;
            case R.id.menu_taikhoan:
                Intent intent4 = new Intent(trangchu.this,thongtintaikhoan.class);
                startActivity(intent4);
                return true;
            case R.id.menu_dangxuat:
                Intent intent1 = new Intent(trangchu.this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                return true;
            case R.id.menu_themmonan:
                Intent intent2 = new Intent(trangchu.this,themmonan.class);
                startActivity(intent2);
                return true;
            case R.id.menu_quanlymonan:
                Intent intent3 = new Intent(trangchu.this,danhsachmonan.class);
                startActivity(intent3);
                return true;
            case R.id.menu_donhang:
                Intent intent5 = new Intent(trangchu.this,donhang.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void thich(final String url1,final String idnguoidung){
        final String url = url1+"/thich.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    urlsv.clear();
                    idnguoidung1.clear();
                    idmonan.clear();
                    urlmonan.clear();
                    tenmonan.clear();
                    diachimonan.clear();
                    giamonan.clear();
                    motamonan.clear();

                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        idnguoidung1.add(idnguoidung);
                        urlsv.add(url1);
                        idmonan.add(jsonObject[i].getString("idmonan"));
                        urlmonan.add(jsonObject[i].getString("urlmonan"));
                        //urlmonan.add("test");
                        tenmonan.add(jsonObject[i].getString("tenmonan"));
                        diachimonan.add(jsonObject[i].getString("diachimonan"));
                        giamonan.add(jsonObject[i].getString("giamonan"));
                        motamonan.add(jsonObject[i].getString("motamonan"));
                        //motamonan.add("test:"+i);
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    recyclerViewAdapter = new RecyclerViewAdapter(urlsv,idnguoidung1,idmonan, urlmonan, tenmonan, diachimonan, giamonan, motamonan);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);


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
    private void layhinh(){
        try {
            Picasso.get().load("https://i.imgur.com/zS795OK.jpg").into(hinh);

            //Picasso.get().load("http://10.2.19.238/img/tuc.jpg").into(hinh);
            //Picasso.get().load("file:///storage/emulated/0/Download/Gioithieubp-1.jpg").into(hinh);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void createData(final String url1,final String idnguoidung){
        String url = url1+"/monan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    idnguoidung1.clear();
                    idmonan.clear();
                    urlmonan.clear();
                    tenmonan.clear();
                    diachimonan.clear();
                    giamonan.clear();
                    motamonan.clear();
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        idnguoidung1.add(idnguoidung);
                        urlsv.add(url1);
                        idmonan.add(jsonObject[i].getString("idmonan"));
                        urlmonan.add(jsonObject[i].getString("urlmonan"));
                        //urlmonan.add("test");
                        tenmonan.add(jsonObject[i].getString("tenmonan"));
                        diachimonan.add(jsonObject[i].getString("diachimonan"));
                        giamonan.add(jsonObject[i].getString("giamonan"));
                        motamonan.add(jsonObject[i].getString("motamonan"));
                        //motamonan.add("test:"+i);
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    recyclerViewAdapter = new RecyclerViewAdapter(urlsv,idnguoidung1,idmonan, urlmonan, tenmonan, diachimonan, giamonan, motamonan);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);


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
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void timkiemmonan(final String url1,final String monan,final String idnguoidung){
        final String url = url1+"/timkiemmonan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    idnguoidung1.clear();
                    idmonan.clear();
                    urlmonan.clear();
                    tenmonan.clear();
                    diachimonan.clear();
                    giamonan.clear();
                    motamonan.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        idnguoidung1.add(idnguoidung);
                        urlsv.add(url1);
                        idmonan.add(jsonObject[i].getString("idmonan"));
                        urlmonan.add(jsonObject[i].getString("urlmonan"));
                        tenmonan.add(jsonObject[i].getString("tenmonan"));
                        diachimonan.add(jsonObject[i].getString("diachimonan"));
                        giamonan.add(jsonObject[i].getString("giamonan"));
                        motamonan.add(jsonObject[i].getString("motamonan"));
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();

                    recyclerViewAdapter = new RecyclerViewAdapter(urlsv,idnguoidung1,idmonan, urlmonan, tenmonan, diachimonan, giamonan, motamonan);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);

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
                params.put("timkiem",monan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showDialog(final String url,final String idnguoidung ,final String matkhau) {
        dialog = new Dialog(trangchu.this);
        dialog.setTitle("Quản lý tài khoản");
        dialog.setContentView(R.layout.dialog_taikhoan);
        final TextView text_dialog_tk = (TextView)dialog.findViewById(R.id.text_dialog_tk);
        final EditText edit_dialog_tk = (EditText)dialog.findViewById(R.id.edit_dialog_tk);
        final EditText edit1_dialog_tk = (EditText)dialog.findViewById(R.id.edit1_dialog_tk);
        final EditText edit2_dialog_tk = (EditText)dialog.findViewById(R.id.edit2_dialog_tk);
        Button btn_dialog_tk = (Button)dialog.findViewById(R.id.btn_dialog_tk);
        btn_dialog_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), text_dialog_tk.getText().toString() +" : "+matkhau, Toast.LENGTH_SHORT).show();
                if(edit_dialog_tk.getText().toString().equals(matkhau)){
                    if(edit1_dialog_tk.getText().toString().equals(edit2_dialog_tk.getText().toString())){
                        doimatkhau(url,idnguoidung, edit1_dialog_tk.getText().toString());

                    }else Toast.makeText(getApplicationContext(), "Mật khẩu mới chưa khớp", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(getApplicationContext(), "Mật khẩu cũ nhập sai", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void showDialog3() {
        final Intent intent = getIntent();
        dialog = new Dialog(trangchu.this);
        dialog.setTitle("Lưu ý");
        dialog.setContentView(R.layout.dialog_luuy);
        Button btn_dialogly = (Button)dialog.findViewById(R.id.btn_dialogly);
        btn_dialogly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void doimatkhau(final String url1,final String idnguoidung, final String matkhau ){
        String url = url1+"/doimatkhau.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(),"Đổi mật khẩu thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Đổi mật khẩu thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("idnguoidung", idnguoidung);
                params.put("matkhau", matkhau);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
