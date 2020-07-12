package com.example.thaihon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dathang extends AppCompatActivity {

    EditText edit_dh_diachi, edit_dh_ghichu, edit_dh_sdt, edit_dh_magiamgia;
    TextView text_dh_mathang, text_dh_gia, text_dh_phiship, text_dh_giamgia, text_dh_tong, text_dh_ten;
    Button btn_dh_xacnhanthongtin, btn_dh_xaccnhandonhang;
    Spinner tinh, quan, phuong, soluong;
    ImageView img_dh_hinhnen;
    private List<String> list_tinh = new ArrayList<>();
    private List<String> list_quan = new ArrayList<>();
    private List<String> list_phuong = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String idmonan = intent.getStringExtra("idmonan");
        final String idnguoidung = intent.getStringExtra("idnguoidung");
        final String urlhinnen = intent.getStringExtra("urlhinhnen");
        bien();
        themsoluong();
        Picasso.get().load(urlhinnen).into(img_dh_hinhnen);
        text_dh_ten.setText("Chào bạn: "+admin.getTen());
        btn_dh_xacnhanthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dulieu(url,idmonan);
                btn_dh_xaccnhandonhang.setEnabled(true);
            }
        });
        btn_dh_xaccnhandonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
                //Toast.makeText(getApplicationContext(),sdf.format(date), Toast.LENGTH_LONG).show();
                themlichsu(url,idnguoidung, idmonan, text_dh_tong.getText().toString(),sdf.format(date),edit_dh_sdt.getText().toString(),edit_dh_magiamgia.getText().toString(),edit_dh_diachi.getText().toString()+", "+phuong.getSelectedItem()+", "+quan.getSelectedItem()+", "+tinh.getSelectedItem(),edit_dh_ghichu.getText().toString(),soluong.getSelectedItem().toString());
            }
        });
        dulieutinh();
        tinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
                String msg = "position :" + position + " value :" + list_tinh.get(position);
                //Toast.makeText(dathang.this, msg, Toast.LENGTH_SHORT).show();
                dulieuquan(list_tinh.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(dathang.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        quan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dulieuphuong(tinh.getSelectedItem().toString(),list_quan.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(dathang.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void bien(){
        edit_dh_sdt = (EditText)findViewById(R.id.edit_dh_sdt);
        edit_dh_diachi = (EditText)findViewById(R.id.edit_dh_diachi);
        edit_dh_magiamgia = (EditText)findViewById(R.id.edit_dh_giamgia);
        edit_dh_ghichu = (EditText)findViewById(R.id.edit_dh_ghichu);
        text_dh_mathang = (TextView)findViewById(R.id.text_dh_mathang);
        text_dh_gia = (TextView)findViewById(R.id.text_dh_gia);
        text_dh_phiship = (TextView)findViewById(R.id.text_dh_phiship);
        text_dh_giamgia = (TextView)findViewById(R.id.text_dh_giamgia);
        text_dh_tong = (TextView)findViewById(R.id.text_dh_tong);
        text_dh_ten = (TextView)findViewById(R.id.text_dh_ten);
        img_dh_hinhnen = (ImageView) findViewById(R.id.img_dh_hinhnen);
        btn_dh_xaccnhandonhang = (Button)findViewById(R.id.btn_dh_xacnhandonhang);
        btn_dh_xaccnhandonhang.setEnabled(false);
        btn_dh_xacnhanthongtin = (Button)findViewById(R.id.btn_dh_xacnhanthongtin);
        tinh = (Spinner)findViewById(R.id.spin_dh_thanhpho);
        quan = (Spinner)findViewById(R.id.spin_dh_quan);
        phuong = (Spinner)findViewById(R.id.spin_dh_phuong);
        soluong = (Spinner)findViewById(R.id.spin_dh_soluong);
    }
    public void themsoluong(){
        String soluong1[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext (), R.layout.support_simple_spinner_dropdown_item, soluong1);
        soluong.setAdapter(spinnerAdapter);
    }
    public void dulieutinh(){
        final String url = admin.getUrl()+"/tentinh.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    list_tinh.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_tinh.add(jsonObject[i].getString("tentinh"));
                    }
                    //Toast.makeText(getApplicationContext(),"sdấdâda" + list_tinh.get(0), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext (), R.layout.support_simple_spinner_dropdown_item, list_tinh);
                    tinh.setAdapter(spinnerAdapter);
                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void dulieuphuong(final String tentinh, final String tenquan){
        final String url = admin.getUrl()+"/tenphuong.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    list_phuong.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_phuong.add(jsonObject[i].getString("tenphuong"));
                    }
                    //Toast.makeText(getApplicationContext(),jsonObject[9].getString("tentinh"), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list_phuong);
                    phuong.setAdapter(spinnerAdapter);
                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Lỗi json phuong "+e1.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tentinh",tentinh);
                params.put("tenquan",tenquan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dulieuquan(final String tentinh){
        final String url = admin.getUrl()+"/tenquan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    list_quan.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_quan.add(jsonObject[i].getString("tenquan"));
                    }
                    //Toast.makeText(getApplicationContext(),jsonObject[9].getString("tentinh"), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list_quan);

                    quan.setAdapter(spinnerAdapter);
                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tentinh",tentinh);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dulieu(final String url1,final String idmonan){
        final String url = url1+"/thongtin.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        text_dh_mathang.setText(jsonObject[i].getString("tenmonan"));
                        int gia1 = Integer.parseInt(jsonObject[i].getString("giamonan"));
                        int gia2 = Integer.parseInt(soluong.getSelectedItem().toString());
                        int gia = gia1 * gia2;
                        text_dh_gia.setText(gia+"");
                        text_dh_giamgia.setText("0");
                        if(jsonObject[i].getString("tinh").equals(tinh.getSelectedItem())){
                            if(jsonObject[i].getString("quan").equals(quan.getSelectedItem())){
                                if(jsonObject[i].getString("phuong").equals(phuong.getSelectedItem())){
                                    text_dh_phiship.setText(jsonObject[i].getString("ship1"));
                                }else text_dh_phiship.setText(jsonObject[i].getString("ship2"));
                            }else text_dh_phiship.setText(jsonObject[i].getString("ship3"));
                        }else text_dh_phiship.setText(jsonObject[i].getString("ship3"));
                    }
                    dulieu2(edit_dh_magiamgia.getText().toString());
                    ;
                    //Toast.makeText(getApplicationContext(),lat.toString(), Toast.LENGTH_LONG).show();

                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idmonan", idmonan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dulieu2(final String magiamgia){
        final String url = admin.getUrl()+"/magiamgia.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);

                        if(jsonObject[i].getString("kieu").equals("ship")){
                            if(jsonObject[i].getInt("giatri")<Integer.parseInt(text_dh_phiship.getText().toString())){
                                text_dh_giamgia.setText(jsonObject[i].getString("giatri"));
                            }else {
                                text_dh_giamgia.setText(text_dh_phiship.getText().toString());
                            }
                        }else if(jsonObject[i].getInt("giatri")<Integer.parseInt(text_dh_gia.getText().toString())){
                            text_dh_giamgia.setText(jsonObject[i].getString("giatri"));
                        }else {
                            int ma = Integer.parseInt(text_dh_gia.getText().toString())-1;
                            text_dh_giamgia.setText(String.valueOf(ma));
                        }
                    }
                    int tong = Integer.parseInt(text_dh_phiship.getText().toString()) + Integer.parseInt(text_dh_gia.getText().toString()) - Integer.parseInt(text_dh_giamgia.getText().toString());
                    text_dh_tong.setText(String.valueOf(tong));
                    //Toast.makeText(getApplicationContext(),lat.toString(), Toast.LENGTH_LONG).show();

                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("magiamgia", magiamgia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void themlichsu(final String url1, final String idnguoidung, final String idmonan, final String thanhtien, final String ngay, final String sdt, final String magiamgia, final String diachi,final String ghichu,final String soluong){
        String url = url1+"/themlichsu.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("idmonan", idmonan);
                params.put("sdt", sdt);
                params.put("thanhtien", thanhtien);
                params.put("ngay", ngay);
                params.put("magiamgia", magiamgia);
                params.put("diachi", diachi);
                params.put("ghichu", ghichu);
                params.put("soluong", soluong);
                params.put("ten", admin.getTen());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
