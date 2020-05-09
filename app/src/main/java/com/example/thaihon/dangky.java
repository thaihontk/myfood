package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class dangky extends AppCompatActivity {

    Button btndangky;
    EditText edittaikhoan, editemail, editmatkhau1, editmatkhau2;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        bien();
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemtra(url);
            }
        });
    }
    public void bien(){
        edittaikhoan = (EditText)findViewById(R.id.edittaikhoan);
        editemail = (EditText)findViewById(R.id.editemail);
        editmatkhau1 = (EditText)findViewById(R.id.editmatkhau1);
        editmatkhau2 = (EditText)findViewById(R.id.editmatkhau2);
        btndangky = (Button)findViewById(R.id.btndangky);
    }
    public void kiemtra(final String url1){
        String url = url1+"/kiemtra.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if (response.contains(edittaikhoan.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(),"Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }else {
                    if(!editmatkhau1.getText().toString().equals(editmatkhau2.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
                    }else{
                        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
                        guimail(url1,editemail.getText().toString(), String.valueOf(code));
                        showDialog(url1,editemail.getText().toString(), String.valueOf(code), editmatkhau1.getText().toString(), edittaikhoan.getText().toString());
                    }
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
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void showDialog(final String url ,final String email, final String code, final String matkhau, final String taikhoan) {
        dialog = new Dialog(dangky.this);
        dialog.setTitle("xác nhận email");
        dialog.setContentView(R.layout.dangky_dialog);
        TextView view = (TextView) dialog.findViewById(R.id.viewdialog);
        final EditText editview = (EditText) dialog.findViewById(R.id.editdialog);
        Button btnguilai = (Button) dialog.findViewById(R.id.btnguilai);
        Button btnxacnhan = (Button) dialog.findViewById(R.id.btnxacnhan);
        btnguilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guimail(url,email,code);
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.equals(editview.getText().toString())){
                    dangky(url,taikhoan, email, matkhau);
                    //Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_LONG).show();
                    //dialog.cancel();
                }else {
                    Toast.makeText(getApplicationContext(),"Mã xác nhận chưa đúng", Toast.LENGTH_LONG).show();
                }
            }
        });
        view.setText(email);
        dialog.setCancelable(true);
        dialog.show();
    }
    public void guimail(final String url1, final String email, final String code){
        String url = url1+"/guimail.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
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
                params.put("email",email);
                params.put("code", code);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dangky(final String url1, final String taikhoan, final String email, final String matkhau){
        String url = url1+"/dangky.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("thanh cong")){
                    Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }else Toast.makeText(getApplicationContext(),"Đăng ký thất bại", Toast.LENGTH_LONG).show();

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
                params.put("taikhoan",taikhoan);
                params.put("email",email);
                params.put("matkhau", matkhau);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
