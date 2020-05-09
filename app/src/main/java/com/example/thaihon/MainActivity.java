package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btndangnhap, btndangky;
    EditText edittaikhoan, editmatkhau;
    String url = "http://10.2.19.238";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btndangnhap = (Button)findViewById(R.id.btndangnhap);
        btndangky = (Button)findViewById(R.id.btndangky);
        editmatkhau = (EditText)findViewById(R.id.editmatkhau);
        edittaikhoan = (EditText)findViewById(R.id.edittaikhoan);
        //checkInternetConnection();
        final Intent[] intent = new Intent[10];
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksignin(url);
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[1] = new Intent(MainActivity.this, dangky.class);
                intent[1].putExtra("url", url);
                startActivity(intent[1]);
            }
        });
    }
    private boolean checkInternetConnection() {

        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }
    public void checksignin(final String url1){
        String url = url1+"/dangnhap.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if (response.contains(edittaikhoan.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jsonArray = new JSONArray(response.trim());
                        JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject[i] = (JSONObject)jsonArray.get(i);
                            //String idnguoidung = jsonObject[i].getString("id");
                        };
                        //Toast.makeText(getApplicationContext(), jsonObject[0].getString("id"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,trangchu.class);
                        intent.putExtra("idnguoidung", jsonObject[0].getString("id"));
                        intent.putExtra("pass", jsonObject[0].getString("matkhau"));
                        intent.putExtra("url", url1);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();

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
                params.put("taikhoan",edittaikhoan.getText().toString().trim());
                params.put("matkhau",editmatkhau.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
