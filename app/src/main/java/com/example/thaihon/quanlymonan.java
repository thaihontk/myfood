package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class quanlymonan extends AppCompatActivity {
    TextView text_qlmon, text_qlmon_ava, text_qlmon_cover;
    EditText edit_qlmon_ten, edit_qlmon_gia, edit_qlmon_mota;
    Button btn_qlmon_ava, btn_qlmon_cover, btn_qlmon_sua, btn_qlmon_xacnhan;
    int check=0,check1=0;
    String message="", message1="";
    String realpath="", realpath1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlymonan);
        bien();
        Intent intent = getIntent();
        final String idmonan = intent.getStringExtra("idmonan");
        dulieu(idmonan);
        btn_qlmon_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 200);
            }
        });
        btn_qlmon_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 100);
            }
        });
        btn_qlmon_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check*check1!=0){
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." +mangtenfile[1];

                    //Toast.makeText(trangchu.this, file_path, Toast.LENGTH_LONG).show();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                    DataClient dataClient = APIUtils.getData();
                    Call<String> callback = dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                            if(response != null){
                                message = "/img/" + response.body();
                                text_qlmon_ava.setText(message);
                                //Toast.makeText(getApplicationContext(),"ava "+ message, Toast.LENGTH_LONG).show();
                                //themmonan(edit_themmon_ten.getText().toString(),edit_themmon_gia.getText().toString(),edit_themmon_mota.getText().toString(),message,message1);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    file = new File(realpath1);
                    file_path = file.getAbsolutePath();
                    mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." +mangtenfile[1];

                    //Toast.makeText(trangchu.this, file_path, Toast.LENGTH_LONG).show();

                    requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                    body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                    dataClient = APIUtils.getData();
                    callback = dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                            if(response != null){
                                message1 = "/img/" + response.body();
                                text_qlmon_cover.setText(message1);
                                //Toast.makeText(getApplicationContext(),"cover "+ message1, Toast.LENGTH_LONG).show();
                                //themmonan(edit_themmon_ten.getText().toString(),edit_themmon_gia.getText().toString(),edit_themmon_mota.getText().toString(),message,message1);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }else Toast.makeText(getApplicationContext(), "Bạn chưa chọn đủ 2 hình", Toast.LENGTH_LONG).show();
            }

        });
        btn_qlmon_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"oke con dê", Toast.LENGTH_LONG).show();
                sua(idmonan);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200 && resultCode == quanlymonan.this.RESULT_OK) {
            String PATH = RealPathUtil.getPath(quanlymonan.this, data.getData());
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            //Toast.makeText(getApplicationContext(), realpath, Toast.LENGTH_LONG).show();
            // Get name
            String imgName = PATH.substring(PATH.lastIndexOf("/"));

            text_qlmon_ava.setText(imgName);
            check++;
            //Toast.makeText(trangchu.this, uri.toString(), Toast.LENGTH_LONG).show();

            // Load image
        }
        if (requestCode == 100 && resultCode == quanlymonan.this.RESULT_OK) {
            String PATH1 = RealPathUtil.getPath(quanlymonan.this, data.getData());
            Uri uri1 = data.getData();
            realpath1 = getRealPathFromURI(uri1);
            Toast.makeText(getApplicationContext(),"cover 1: "+ realpath1, Toast.LENGTH_LONG).show();
            // Get name
            String imgName1 = PATH1.substring(PATH1.lastIndexOf("/"));

            text_qlmon_cover.setText(imgName1);
            check1++;
            // Load image
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    public void bien(){
        text_qlmon = (TextView)findViewById(R.id.text_qlmon);
        text_qlmon.setText("Chào: "+admin.getTen());
        edit_qlmon_ten = (EditText) findViewById(R.id.edit_qlmon_ten);
        edit_qlmon_gia = (EditText) findViewById(R.id.edit_qlmon_gia);
        edit_qlmon_mota = (EditText) findViewById(R.id.edit_qlmon_mota);
        text_qlmon_ava = (TextView) findViewById(R.id.text_qlmon_ava);
        text_qlmon_cover = (TextView) findViewById(R.id.text_qlmon_cover);
        btn_qlmon_ava = (Button)findViewById(R.id.btn_qlmon_ava);
        btn_qlmon_cover = (Button)findViewById(R.id.btn_qlmon_cover);
        btn_qlmon_sua = (Button)findViewById(R.id.btn_qlmon_sua);
        btn_qlmon_xacnhan = (Button)findViewById(R.id.btn_qlmon_xacnhan);
    }
    public void sua(final String idmonan){
        final String url = admin.getUrl()+"/suamonan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    if(response.equals("oke")){
                        Toast.makeText(getApplicationContext(),"Cập nhật thành công", Toast.LENGTH_LONG).show();
                    }else Toast.makeText(getApplicationContext(),"Cập nhật thất bại", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();

                }catch (Exception e1){
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
                params.put("idmonan",idmonan);
                params.put("ten",edit_qlmon_ten.getText().toString());
                params.put("gia",edit_qlmon_gia.getText().toString());
                params.put("mota",edit_qlmon_mota.getText().toString());
                params.put("ava",text_qlmon_ava.getText().toString());
                params.put("cover",text_qlmon_cover.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dulieu(final String idmonan){
        final String url = admin.getUrl()+"/thongtinmonan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);

                        text_qlmon_ava.setText(jsonObject[i].getString("avamonan"));
                        text_qlmon_cover.setText(jsonObject[i].getString("covermonan"));
                        edit_qlmon_ten.setText(jsonObject[i].getString("tenmonan"));
                        edit_qlmon_gia.setText(jsonObject[i].getString("giamonan"));
                        edit_qlmon_mota.setText(jsonObject[i].getString("motamonan"));
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();

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
                params.put("idmonan",idmonan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
