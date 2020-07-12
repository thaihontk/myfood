package com.example.thaihon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class themmonan extends AppCompatActivity {
    TextView text_themmon, text_themmon_ava, text_themmon_cover;
    EditText edit_themmon_ten, edit_themmon_gia, edit_themmon_mota;
    Button btn_themmon_ava, btn_themmon_cover, btn_themmon_them;
    String realpath = "";
    String realpath1 = "";
    String message = "";
    String message1 = "";
    int check = 0;
    int check1 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themmonan);
        bien();
        btn_themmon_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 200);
            }
        });
        btn_themmon_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 100);
            }
        });
        btn_themmon_them.setOnClickListener(new View.OnClickListener() {
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
                                //Toast.makeText(getApplicationContext(),"cover "+ message1, Toast.LENGTH_LONG).show();
                                themmonan(edit_themmon_ten.getText().toString(),edit_themmon_gia.getText().toString(),edit_themmon_mota.getText().toString(),message,message1);
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
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200 && resultCode == themmonan.this.RESULT_OK) {
            String PATH = RealPathUtil.getPath(themmonan.this, data.getData());
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            //Toast.makeText(getApplicationContext(), realpath, Toast.LENGTH_LONG).show();
            // Get name
            String imgName = PATH.substring(PATH.lastIndexOf("/"));

            text_themmon_ava.setText(imgName);
            check++;
            //Toast.makeText(trangchu.this, uri.toString(), Toast.LENGTH_LONG).show();

            // Load image
        }
        if (requestCode == 100 && resultCode == themmonan.this.RESULT_OK) {
            String PATH1 = RealPathUtil.getPath(themmonan.this, data.getData());
            Uri uri1 = data.getData();
            realpath1 = getRealPathFromURI(uri1);
            Toast.makeText(getApplicationContext(),"cover 1: "+ realpath1, Toast.LENGTH_LONG).show();
            // Get name
            String imgName1 = PATH1.substring(PATH1.lastIndexOf("/"));

            text_themmon_cover.setText(imgName1);
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
        text_themmon = (TextView)findViewById(R.id.text_themmon);
        text_themmon.setText("Chào: "+admin.getTen());
        edit_themmon_ten = (EditText) findViewById(R.id.edit_themmon_ten);
        edit_themmon_gia = (EditText) findViewById(R.id.edit_themmon_gia);
        edit_themmon_mota = (EditText) findViewById(R.id.edit_themmon_mota);
        text_themmon_ava = (TextView) findViewById(R.id.text_themmon_ava);
        text_themmon_cover = (TextView) findViewById(R.id.text_themmon_cover);
        btn_themmon_ava = (Button)findViewById(R.id.btn_themmon_ava);
        btn_themmon_cover = (Button)findViewById(R.id.btn_themmon_cover);
        btn_themmon_them = (Button)findViewById(R.id.btn_themmon_them);
    }
    private void themmonan(final String ten,  final String gia, final String mota, final String hinh1, final String hinh2){
        String url = admin.getUrl()+"/themmonan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Intent intent = new Intent(themmonan.this,danhsachmonan.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Thêm món ăn thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Thêm món ăn thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("ten", ten);
                params.put("gia", gia);
                params.put("mota", mota);
                params.put("hinh1", hinh1);
                params.put("hinh2", hinh2);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
