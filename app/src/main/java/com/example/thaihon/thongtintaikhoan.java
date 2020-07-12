package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thongtintaikhoan extends AppCompatActivity {
    EditText edit_tttk_ten, edit_tttk_diachi, edit_tttk_mk1, edit_tttk_mk2, edit_tttk_mk3, edit_tttk_lat, edit_tttk_lng, edit_tttk_phuong, edit_tttk_quan, edit_tttk_tinh;
    Button btn_tttk_matkhau, btn_tttk_quanan, btn_tttk_capnhat, btn_tttk_doimatkhau, btn_tttk_xacnhanquanan;
    LinearLayout line_tttk_matkhau, line_tttk_quanan;
    Spinner spin_tttk_quan, spin_tttk_tinh, spin_tttk_phuong;
    int checkmk=1;
    int checkqa=1;
    private List<String> list_tinh = new ArrayList<>();
    private List<String> list_quan = new ArrayList<>();
    private List<String> list_phuong = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtintaikhoan);
        bien();
        dulieu();
        dulieutinh();
        spin_tttk_tinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
                String msg = "position :" + position + " value :" + list_tinh.get(position);
                //Toast.makeText(dathang.this, msg, Toast.LENGTH_SHORT).show();
                dulieuquan(list_tinh.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(thongtintaikhoan.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        spin_tttk_quan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dulieuphuong(spin_tttk_tinh.getSelectedItem().toString(),list_quan.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(thongtintaikhoan.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        btn_tttk_matkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkmk++;
                if(checkmk%2==0){
                    line_tttk_matkhau.setVisibility(LinearLayout.VISIBLE);
                }else line_tttk_matkhau.setVisibility(LinearLayout.GONE);

            }
        });
        btn_tttk_quanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkqa++;
                if(checkqa%2==0){
                    line_tttk_quanan.setVisibility(LinearLayout.VISIBLE);
                }else line_tttk_quanan.setVisibility(LinearLayout.GONE);
            }
        });
        btn_tttk_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(thongtintaikhoan.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_matkhau);
                dialog.setTitle("Lưu ý");
                Button btn_ok = (Button)dialog.findViewById(R.id.btn_dialogmk);
                final EditText text = (EditText)dialog.findViewById(R.id.edit_dialogmk);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(text.getText().toString().equals(admin.getPass())){
                            capnhatnguoidung();
                            dialog.cancel();
                            Intent intent1 = new Intent(thongtintaikhoan.this,MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            //Toast.makeText(getApplicationContext(),"ngon",Toast.LENGTH_LONG).show();
                        }else Toast.makeText(getApplicationContext(),"Sai mật khẩu",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();

            }
        });
        btn_tttk_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_tttk_mk1.getText().toString().equals(edit_tttk_mk2.getText().toString())){
                    final Dialog dialog = new Dialog(thongtintaikhoan.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_matkhau);
                    dialog.setTitle("Lưu ý");
                    Button btn_ok = (Button)dialog.findViewById(R.id.btn_dialogmk);
                    final EditText text = (EditText)dialog.findViewById(R.id.edit_dialogmk);

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(text.getText().toString().equals(admin.getPass())){
                                capnhatmatkhau();
                                dialog.cancel();
                                Intent intent1 = new Intent(thongtintaikhoan.this,MainActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                //Toast.makeText(getApplicationContext(),"ngon",Toast.LENGTH_LONG).show();
                            }else Toast.makeText(getApplicationContext(),"Sai mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }else Toast.makeText(getApplicationContext(),"Mật khẩu chưa trùng khớp",Toast.LENGTH_LONG).show();
            }
        });
        btn_tttk_xacnhanquanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_tttk_mk1.getText().toString().equals(edit_tttk_mk2.getText().toString())){
                    final Dialog dialog = new Dialog(thongtintaikhoan.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_matkhau);
                    dialog.setTitle("Lưu ý");
                    Button btn_ok = (Button)dialog.findViewById(R.id.btn_dialogmk);
                    final EditText text = (EditText)dialog.findViewById(R.id.edit_dialogmk);

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(text.getText().toString().equals(admin.getPass())){
                                capnhatquanan();
                                dialog.cancel();
                                Intent intent1 = new Intent(thongtintaikhoan.this,MainActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                                //Toast.makeText(getApplicationContext(),"ngon",Toast.LENGTH_LONG).show();
                            }else Toast.makeText(getApplicationContext(),"Sai mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }else Toast.makeText(getApplicationContext(),"Mật khẩu chưa trùng khớp",Toast.LENGTH_LONG).show();
            }
        });


    }
    public void bien(){
        edit_tttk_ten = (EditText)findViewById(R.id.edit_tttk_ten);
        edit_tttk_diachi = (EditText)findViewById(R.id.edit_tttk_diachi);
        edit_tttk_mk1 = (EditText)findViewById(R.id.edit_tttk_mk1);
        edit_tttk_mk2 = (EditText)findViewById(R.id.edit_tttk_mk2);
        edit_tttk_lat = (EditText)findViewById(R.id.edit_tttk_lat);
        edit_tttk_lng = (EditText)findViewById(R.id.edit_tttk_lng);
        edit_tttk_phuong = (EditText)findViewById(R.id.edit_tttk_phuong);
        edit_tttk_quan = (EditText)findViewById(R.id.edit_tttk_quan);
        edit_tttk_tinh = (EditText)findViewById(R.id.edit_tttk_tinh);

        btn_tttk_matkhau = (Button)findViewById(R.id.btn_tttk_matkhau);
        btn_tttk_quanan = (Button)findViewById(R.id.btn_tttk_quanan);
        btn_tttk_capnhat = (Button)findViewById(R.id.btn_tttk_capnhat);
        btn_tttk_doimatkhau = (Button)findViewById(R.id.btn_tttk_doimatkhau);
        btn_tttk_xacnhanquanan = (Button)findViewById(R.id.btn_tttk_xacnhanquanan);

        spin_tttk_quan = (Spinner)findViewById(R.id.spin_tttk_quan);
        spin_tttk_tinh = (Spinner)findViewById(R.id.spin_tttk_tinh);
        spin_tttk_phuong = (Spinner)findViewById(R.id.spin_tttk_phuong);

        line_tttk_matkhau = (LinearLayout)findViewById(R.id.line_tttk_matkhau);
        line_tttk_quanan = (LinearLayout)findViewById(R.id.line_tttk_quanan);
        line_tttk_matkhau.setVisibility(LinearLayout.GONE);
        line_tttk_quanan.setVisibility(LinearLayout.GONE);
    }
    public void dulieu(){
        edit_tttk_ten.setText(admin.getTen());
        edit_tttk_diachi.setText(admin.getDiachi());
        edit_tttk_lat.setText(admin.getLat());
        edit_tttk_lng.setText(admin.getLng());
        edit_tttk_phuong.setText(admin.getShip1());
        edit_tttk_quan.setText(admin.getShip2());
        edit_tttk_tinh.setText(admin.getShip3());


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
                    list_tinh.add(admin.getTinh());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_tinh.add(jsonObject[i].getString("tentinh"));
                    }
                    //Toast.makeText(getApplicationContext(),"sdấdâda" + list_tinh.get(0), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext (), R.layout.support_simple_spinner_dropdown_item, list_tinh);
                    spin_tttk_tinh.setAdapter(spinnerAdapter);
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
                    list_phuong.add(admin.getPhuong());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_phuong.add(jsonObject[i].getString("tenphuong"));
                    }
                    //Toast.makeText(getApplicationContext(),jsonObject[9].getString("tentinh"), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list_phuong);
                    spin_tttk_phuong.setAdapter(spinnerAdapter);
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
                    list_quan.add(admin.getQuan());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject[i] = (JSONObject) jsonArray.get(i);
                        list_quan.add(jsonObject[i].getString("tenquan"));
                    }
                    //Toast.makeText(getApplicationContext(),jsonObject[9].getString("tentinh"), Toast.LENGTH_LONG).show();
                    ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list_quan);

                    spin_tttk_quan.setAdapter(spinnerAdapter);
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
    public void capnhatnguoidung(){
        String url = admin.getUrl()+"/capnhatnguoidung.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Cập nhật thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("idnguoidung", admin.getId());
                params.put("ten", edit_tttk_ten.getText().toString());
                params.put("diachi", edit_tttk_diachi.getText().toString());
                params.put("tinh", spin_tttk_tinh.getSelectedItem().toString());
                params.put("quan", spin_tttk_quan.getSelectedItem().toString());
                params.put("phuong", spin_tttk_phuong.getSelectedItem().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void capnhatmatkhau(){
        String url = admin.getUrl()+"/capnhatmatkhau.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Cập nhật thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("idnguoidung", admin.getId());
                params.put("matkhau", edit_tttk_mk1.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void capnhatquanan(){
        String url = admin.getUrl()+"/capnhatquanan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Cập nhật thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("idnguoidung", admin.getId());
                params.put("lat", edit_tttk_lat.getText().toString());
                params.put("lng", edit_tttk_lng.getText().toString());
                params.put("ship1", edit_tttk_phuong.getText().toString());
                params.put("ship2", edit_tttk_quan.getText().toString());
                params.put("ship3", edit_tttk_tinh.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
