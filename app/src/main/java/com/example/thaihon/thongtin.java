package com.example.thaihon;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.google.android.gms.maps.MapFragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thongtin extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView text_tt_ten, text_tt_diachi, text_tt_gia, text_tt_mota, text_tt_ship, text_tt_khoangcach;
    private ImageView img_tt;
    private Button btn_tt_dathang, btn_tt_thich, btn_tt_danhgia;
    private RecyclerView recyclerView;
    Location vitri, quanan;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private RecyclerViewDanhgia recyclerViewDanhgia;
    Dialog dialog;
    String urlhinhnen;
    private List<String> ten = new ArrayList<>();
    private List<String> mucdo = new ArrayList<>();
    private List<String> danhgia = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);

        Intent intent = getIntent();
        final String url = admin.getUrl();
        final String idmonan = intent.getStringExtra("idmonan");
        final String idnguoidung = admin.getId();
        bien();
        timkiemmonan(url,idmonan);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        createData(url,idmonan);
        btn_tt_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongtin.this,dathang.class);
                intent.putExtra("idmonan",idmonan);
                intent.putExtra("idnguoidung",idnguoidung);
                intent.putExtra("url",url);
                intent.putExtra("urlhinhnen",urlhinhnen);
                startActivity(intent);
            }
        });
        btn_tt_thich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themthich(url,idnguoidung,idmonan);
            }
        });
        btn_tt_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(url,idnguoidung,idmonan);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0,0,0,0);
        //mMap.getUiSettings().setScrollGesturesEnabled(true);
        //mMap.getUiSettings().setZoomGesturesEnabled(true);
        /*LatLng location = new LatLng(10.038394, 105.779115);
        mMap.addMarker(new MarkerOptions().position(location).title("test"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f ) );*/
    }
    @Override
    public void onPause() {
        super.onPause();
        MapFragment f = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (f != null){
            getFragmentManager().beginTransaction().remove(f).commit();
        }
    }
    public void bien() {
        text_tt_ten = (TextView) findViewById(R.id.text_tt_ten);
        text_tt_diachi = (TextView) findViewById(R.id.text_tt_diachi);
        text_tt_gia = (TextView) findViewById(R.id.text_tt_gia);
        text_tt_mota = (TextView) findViewById(R.id.text_tt_mota);
        btn_tt_dathang = (Button) findViewById(R.id.btn_tt_dathang);
        btn_tt_thich = (Button) findViewById(R.id.btn_tt_thich);
        btn_tt_danhgia = (Button) findViewById(R.id.btn_tt_danhgia);
        img_tt = (ImageView) findViewById(R.id.img_tt);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_danhgia);

    }
    public void showDialog(final String url,final String idnguoidung, final String idmonan) {
        dialog = new Dialog(thongtin.this);
        dialog.setTitle("đánh giá");
        dialog.setContentView(R.layout.dialog_danhgia);
        final String[] stars = new String[1];
        final EditText editview = (EditText) dialog.findViewById(R.id.edit_dialog_dg);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingbar_dialog_dg);
        Button btnhoantat = (Button) dialog.findViewById(R.id.btn_dialog_dg);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                stars[0] = String.valueOf(rating);
            }
        });

        btnhoantat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themdanhgia(url,idnguoidung, idmonan, stars[0],editview.getText().toString());
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }
    private void themdanhgia(final String url1,final String idnguoidung, final String idmonan, final String mucdodanhgia, final String danhgia ){
        String url = url1+"/themdanhgia.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"Thêm đánh giá thành công!!!", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }else Toast.makeText(getApplicationContext(),"Thêm đánh giá thất bại!!!", Toast.LENGTH_LONG).show();
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
                params.put("mucdodanhgia", mucdodanhgia);
                params.put("danhgia", danhgia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void themthich(final String url1,final String idnguoidung, final String idmonan ){
        String url =url1+"/themthich.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(getApplicationContext(),"Thêm thích thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(),"Bạn đã thích rồi!!!", Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void createData(final String url1,final String idmonan){
        String url = url1+"/danhgia.php";
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
                        ten.add(jsonObject[i].getString("tentk"));
                        mucdo.add(jsonObject[i].getString("mucdodanhgia"));
                        danhgia.add(jsonObject[i].getString("danhgia"));

                        //motamonan.add("test:"+i);
                    };
                    //Toast.makeText(getApplicationContext(),jsonObject[0].getString("idmonan"), Toast.LENGTH_LONG).show();
                    recyclerViewDanhgia = new RecyclerViewDanhgia(ten, mucdo, danhgia);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewDanhgia);


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
                params.put("idmonan", idmonan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void timkiemmonan(final String url1,final String idmonan) {
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
                        //jsonObject[i].getString("idmonan");
                        Picasso.get().load(url1+jsonObject[i].getString("url1")).into(img_tt);
                        text_tt_ten.setText(jsonObject[i].getString("tenmonan"));
                        String dc = jsonObject[i].getString("diachi")+", "+jsonObject[i].getString("phuong")+", "+jsonObject[i].getString("quan")+", "+jsonObject[i].getString("tinh");
                        text_tt_diachi.setText(dc);
                        text_tt_gia.setText("Giá: " + jsonObject[i].getString("giamonan"));
                        text_tt_mota.setText("Mô tả: " + jsonObject[i].getString("motamonan"));
                        //urlhinhnen = jsonObject[i].getString("urlhinhnen");
                        Double lat = jsonObject[i].getDouble("lat");
                        Double lng = jsonObject[i].getDouble("lng");
                        String ten = jsonObject[i].getString("diachimonan");
                        urlhinhnen = url1+jsonObject[i].getString("url1");
                        final LatLng mlocation = new LatLng(lat, lng);
                        quanan = new Location(jsonObject[i].getString("diachimonan"));
                        quanan.setLatitude(lat);
                        quanan.setLongitude(lng);
                        mMap.addMarker(new MarkerOptions().position(mlocation).title(ten));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mlocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

                    }
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

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    //latTextView.setText(location.getLatitude()+"");
                                    //lonTextView.setText(location.getLongitude()+"");
                                    /*vitri = new Location("vị trí");
                                    vitri.setLatitude(10.029448);
                                    vitri.setLongitude(105.769499);
                                    double distance=vitri.distanceTo(quanan)/1000;
                                    //text_tt_khoangcach.setText(Math.round(distance*100)/100+"");
                                    text_tt_khoangcach.setText("Khoảng cách: "+(double)Math.round(distance*100)/100+" km");
                                    if((double)Math.round(distance*100)/100<5){
                                        int ship = 15000;
                                        text_tt_ship.setText("Phí ship: "+ship+" VNĐ");
                                    }else {
                                        int ship = 20000;
                                        text_tt_ship.setText("Phí ship: "+ship+" VNĐ");
                                    }*/
                                    //text_tt_ship.setText("Phí ship: "+" test VNĐ");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
            /*vitri = new Location("vị trí");
            vitri.setLatitude(10.029448);
            vitri.setLongitude(105.769499);
            double distance=vitri.distanceTo(quanan)/1000;
            text_tt_khoangcach.setText(Math.round(distance*100)/100+"");*/
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

}
