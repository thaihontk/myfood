package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
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

public class donhang extends AppCompatActivity {
    TextView text_donhang, text_donhang_1;
    RecyclerView rec_donhang, rec_donhangdone;
    RecyclerViewDonhang recyclerViewDonhang;
    RecyclerViewDonhangdone recyclerViewDonhangdone;
    private List<String> iddonhang = new ArrayList<>();
    private List<String> tenmon = new ArrayList<>();
    private List<String> soluong = new ArrayList<>();
    private List<String> giatri = new ArrayList<>();
    private List<String> ngay = new ArrayList<>();
    private List<String> ghichu = new ArrayList<>();
    private List<String> tenkhachhang = new ArrayList<>();
    private List<String> sdt = new ArrayList<>();
    private List<String> diachi = new ArrayList<>();
    private List<String> iddonhang1 = new ArrayList<>();
    private List<String> tenmon1 = new ArrayList<>();
    private List<String> soluong1 = new ArrayList<>();
    private List<String> giatri1 = new ArrayList<>();
    private List<String> ngay1 = new ArrayList<>();
    private List<String> ghichu1 = new ArrayList<>();
    private List<String> tenkhachhang1 = new ArrayList<>();
    private List<String> sdt1 = new ArrayList<>();
    private List<String> diachi1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        bien();
        danhsachdonhang();
        danhsachdonhangdone();
    }
    public void bien(){
        text_donhang = (TextView)findViewById(R.id.text_donhang);
        text_donhang_1 = (TextView)findViewById(R.id.text_donhang_1);
        rec_donhang = (RecyclerView)findViewById(R.id.rec_donhang);
        rec_donhangdone = (RecyclerView)findViewById(R.id.rec_donhangdone);
    }
    public void danhsachdonhang(){
        final String url = admin.getUrl()+"/donhang.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    tenmon.clear();
                    iddonhang.clear();
                    soluong.clear();
                    giatri.clear();
                    ngay.clear();
                    ghichu.clear();
                    tenkhachhang.clear();
                    sdt.clear();
                    diachi.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        iddonhang.add(jsonObject[i].getString("idlichsu"));
                        tenmon.add(jsonObject[i].getString("tenmon"));
                        soluong.add(jsonObject[i].getString("soluong"));
                        giatri.add(jsonObject[i].getString("giatri"));
                        ngay.add(jsonObject[i].getString("ngay"));
                        ghichu.add(jsonObject[i].getString("ghichu"));
                        tenkhachhang.add(jsonObject[i].getString("tenkhachhang"));
                        sdt.add(jsonObject[i].getString("sdt"));
                        diachi.add(jsonObject[i].getString("diachi"));
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    text_donhang.setText("Bạn có "+iddonhang.size()+" đơn hàng chưa thanh toán");
                    recyclerViewDonhang = new RecyclerViewDonhang(donhang.this,iddonhang,tenmon,soluong,giatri, ngay, ghichu, tenkhachhang, sdt, diachi);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    rec_donhang.setLayoutManager(layoutManager);
                    rec_donhang.setAdapter(recyclerViewDonhang);

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
                params.put("idnguoidung",admin.getId());
                params.put("thanhtoan","0");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void danhsachdonhangdone(){
        final String url = admin.getUrl()+"/donhang.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    tenmon1.clear();
                    iddonhang1.clear();
                    soluong1.clear();
                    giatri1.clear();
                    ngay1.clear();
                    ghichu1.clear();
                    tenkhachhang1.clear();
                    sdt1.clear();
                    diachi1.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        iddonhang1.add(jsonObject[i].getString("idlichsu"));
                        tenmon1.add(jsonObject[i].getString("tenmon"));
                        soluong1.add(jsonObject[i].getString("soluong"));
                        giatri1.add(jsonObject[i].getString("giatri"));
                        ngay1.add(jsonObject[i].getString("ngay"));
                        ghichu1.add(jsonObject[i].getString("ghichu"));
                        tenkhachhang1.add(jsonObject[i].getString("tenkhachhang"));
                        sdt1.add(jsonObject[i].getString("sdt"));
                        diachi1.add(jsonObject[i].getString("diachi"));
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    text_donhang_1.setText("Bạn có "+iddonhang1.size()+" đơn hàng đã thanh toán");
                    recyclerViewDonhangdone = new RecyclerViewDonhangdone(donhang.this,iddonhang1,tenmon1,soluong1,giatri1, ngay1, ghichu1, tenkhachhang1, sdt1, diachi1);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    rec_donhangdone.setLayoutManager(layoutManager);
                    rec_donhangdone.setAdapter(recyclerViewDonhangdone);

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
                params.put("idnguoidung",admin.getId());
                params.put("thanhtoan","1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
