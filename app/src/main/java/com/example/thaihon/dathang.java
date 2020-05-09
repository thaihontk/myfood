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
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dathang extends FragmentActivity implements OnMapReadyCallback {

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    LatLng latLng;
    Location diachi = new Location("địa chỉ");
    Location vitri = new Location("vị trí");
    EditText edit_dh_ten, edit_dh_sdt;
    TextView text_dh_mathang, text_dh_gia, text_dh_phiship, text_dh_giamgia, text_dh_tong;
    Button btn_dh_xacnhanthongtin, btn_dh_xaccnhandonhang, btn_dh_vitri;
    SearchView sv_dh_diachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String idmonan = intent.getStringExtra("idmonan");
        final String idnguoidung = intent.getStringExtra("idnguoidung");
        bien();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapdathang);
        mapFragment.getMapAsync(this);


        sv_dh_diachi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String search = sv_dh_diachi.getQuery().toString();
                List<Address> addressList = null;
                if (search != null || !search.equals("")){
                    Geocoder geocoder = new Geocoder(dathang.this);
                    try {
                        addressList = geocoder.getFromLocationName(search,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng2 = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng2).title("my home"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f ) );
                    vitri.setLongitude(address.getLongitude());
                    vitri.setLatitude(address.getLatitude());
                }
                Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_LONG);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        btn_dh_xacnhanthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dulieu(url,idmonan);
            }
        });
        btn_dh_xaccnhandonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
                //Toast.makeText(getApplicationContext(),sdf.format(date), Toast.LENGTH_LONG).show();
                themlichsu(url,idnguoidung, idmonan, text_dh_tong.getText().toString(),sdf.format(date),edit_dh_sdt.getText().toString(),edit_dh_ten.getText().toString());
            }
        });

    }


    public void bien(){
        edit_dh_ten = (EditText)findViewById(R.id.edit_dh_ten);
        edit_dh_sdt = (EditText)findViewById(R.id.edit_dh_sdt);
        text_dh_mathang = (TextView)findViewById(R.id.text_dh_mathang);
        text_dh_gia = (TextView)findViewById(R.id.text_dh_gia);
        text_dh_phiship = (TextView)findViewById(R.id.text_dh_phiship);
        text_dh_giamgia = (TextView)findViewById(R.id.text_dh_giamgia);
        text_dh_tong = (TextView)findViewById(R.id.text_dh_tong);
        btn_dh_xaccnhandonhang = (Button)findViewById(R.id.btn_dh_xacnhandonhang);
        btn_dh_xacnhanthongtin = (Button)findViewById(R.id.btn_dh_xacnhanthongtin);
        btn_dh_vitri = (Button)findViewById(R.id.btn_dh_vitri);
        sv_dh_diachi = (SearchView)findViewById(R.id.sv_dh_diachi);
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
                        text_dh_gia.setText(jsonObject[i].getString("giamonan"));
                        text_dh_giamgia.setText("0 VNĐ");
                        int gia = Integer.parseInt(jsonObject[i].getString("giamonan"));
                        Double lat = jsonObject[i].getDouble("lat");
                        Double lng = jsonObject[i].getDouble("lng");

                        diachi.setLatitude(lat);
                        diachi.setLongitude(lng);
                        double distance=vitri.distanceTo(diachi)/1000;
                        //text_tt_khoangcach.setText(Math.round(distance*100)/100+"");
                        //text_tt_khoangcach.setText("Khoảng cách: "+(double)Math.round(distance*100)/100+" km");
                        if((double)Math.round(distance*100)/100<5){
                            int ship = 15000;
                            text_dh_phiship.setText(ship+" VNĐ");
                            text_dh_tong.setText(gia+ship+"");
                        }else {
                            int ship = 20000;
                            text_dh_phiship.setText(ship+" VNĐ");
                            text_dh_tong.setText(gia+ship+"");
                        }
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
    public void themlichsu(final String url1,final String idnguoidung, final String idmonan, final String thanhtien, final String ngay, final String sdt, final String ten){
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
                params.put("ten", ten);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //LatLng location = new LatLng(lat, lng);
        //LatLng location = new LatLng(10.038394, 105.779115);
        //mMap.addMarker(new MarkerOptions().position(location).title("test"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f ) );
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
                                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(""));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
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
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(""));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
