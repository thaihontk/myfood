package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class danhsachmonan extends AppCompatActivity {

    private List<String> urlsv = new ArrayList<>();
    private List<String> idnguoidung1 = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> urlmonan = new ArrayList<>();
    private List<String> tenmonan = new ArrayList<>();
    private List<String> diachimonan = new ArrayList<>();
    private List<String> giamonan = new ArrayList<>();
    private List<String> motamonan = new ArrayList<>();
    Activity activity = new Activity();
    private RecyclerView recyclerView;
    Button them;
    TextView text;
    private RecyclerViewDanhsach recyclerViewDanhsach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachmonan);
        recyclerView = (RecyclerView)findViewById(R.id.rec_danhsachmonan);
        them = (Button)findViewById(R.id.btn_danhsachmonan);
        text = (TextView)findViewById(R.id.text_danhsachmonan);
        monan();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(danhsachmonan.this,themmonan.class);
                startActivity(intent2);
            }
        });
    }
    public void monan(){
        final String url = admin.getUrl()+"/quanlymonan.php";
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
                        idnguoidung1.add(admin.getId());
                        urlsv.add(admin.getUrl());
                        idmonan.add(jsonObject[i].getString("idmonan"));
                        urlmonan.add(jsonObject[i].getString("urlmonan"));
                        tenmonan.add(jsonObject[i].getString("tenmonan"));
                        diachimonan.add(jsonObject[i].getString("diachimonan"));
                        giamonan.add(jsonObject[i].getString("giamonan"));
                        motamonan.add(jsonObject[i].getString("motamonan"));
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    text.setText("Bạn có "+idmonan.size()+" món ăn đang đăng bán");
                    recyclerViewDanhsach = new RecyclerViewDanhsach(danhsachmonan.this,urlsv,idnguoidung1,idmonan, urlmonan, tenmonan, diachimonan, giamonan, motamonan);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewDanhsach);

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
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
