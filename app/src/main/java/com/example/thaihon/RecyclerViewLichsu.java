package com.example.thaihon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewLichsu extends RecyclerView.Adapter<RecyclerViewLichsu.RecyclerViewHolder> {
    private List<String> url = new ArrayList<>();
    private List<String> idnguoidung = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> ten = new ArrayList<>();
    private List<String> diachi = new ArrayList<>();
    private List<String> gia = new ArrayList<>();
    private List<String> ngaymua = new ArrayList<>();
    private List<String> hinhanh = new ArrayList<>();
    private List<String> trangthai = new ArrayList<>();
    private Activity activity;

    public RecyclerViewLichsu(Activity activity,List<String> url, List<String> idnguoidung, List<String> idmonan, List<String> ten, List<String> diachi, List<String> gia, List<String> ngaymua, List<String> hinhanh, List<String> trangthai) {
        this.url = url;
        this.activity = activity;
        this.idnguoidung = idnguoidung;
        this.idmonan = idmonan;
        this.ten = ten;
        this.diachi = diachi;
        this.gia = gia;
        this.ngaymua = ngaymua;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
    }
    public void nhanhang(final String idmonan, final String ngay, final String trangthai){
        String url = admin.getUrl()+"/nhanhang.php";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    Toast.makeText(activity,"Nhận hàng thành công!!!", Toast.LENGTH_LONG).show();
                }else Toast.makeText(activity,"thất bại!!!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity,error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ngay", ngay);
                params.put("idmonan", idmonan);
                params.put("trangthai", trangthai);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public RecyclerViewLichsu.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_lichsu, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewLichsu.RecyclerViewHolder holder, final int position) {
        //holder.idmonan.setText(idmonan.get(position));
        holder.text_itemls_ten.setText(ten.get(position));
        holder.text_itemls_diachi.setText(diachi.get(position));
        holder.text_itemls_gia.setText(gia.get(position));
        holder.text_itemls_ngaymua.setText(ngaymua.get(position));
        Picasso.get().load(admin.getUrl()+hinhanh.get(position)).into(holder.img_itemls_monan);
        if(trangthai.get(position).equals("done")){
            holder.img_itemls_trangthai.setImageResource(R.drawable.done);
        };
        if (trangthai.get(position).equals("cancel")){
            holder.img_itemls_trangthai.setImageResource(R.drawable.cancel);
        };
        if (trangthai.get(position).equals("wait")){
            holder.img_itemls_trangthai.setImageResource(R.drawable.wait);
        }
        holder.img_itemls_monan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, thongtin.class);
                intent.putExtra("idnguoidung",idnguoidung.get(position));
                intent.putExtra("idmonan",idmonan.get(position));
                intent.putExtra("url", url.get(position));
                context.startActivity(intent);
            }
        });
        holder.btn_itemls_nhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhanhang(idmonan.get(position),ngaymua.get(position),"done");
                holder.img_itemls_trangthai.setImageResource(R.drawable.done);
                //holder.text_itemls_ten.setText(idmonan.get(position)+" "+ngaymua.get(position));
            }
        });
        holder.btn_itemls_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhanhang(idmonan.get(position),ngaymua.get(position),"cancel");
                holder.img_itemls_trangthai.setImageResource(R.drawable.cancel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ten.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text_itemls_ten, text_itemls_diachi, text_itemls_gia, text_itemls_ngaymua;
        ImageView img_itemls_trangthai;
        ImageButton img_itemls_monan;
        Button btn_itemls_nhanhang, btn_itemls_huy;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            img_itemls_monan = (ImageButton) itemView.findViewById(R.id.img_itemls_monan);
            img_itemls_trangthai = (ImageView) itemView.findViewById(R.id.img_itemls_trangthai);
            text_itemls_ten = (TextView) itemView.findViewById(R.id.text_itemls_ten);
            text_itemls_diachi = (TextView) itemView.findViewById(R.id.text_itemls_diachi);
            text_itemls_gia = (TextView) itemView.findViewById(R.id.text_itemls_gia);
            text_itemls_ngaymua = (TextView) itemView.findViewById(R.id.text_itemls_ngaymua);
            btn_itemls_huy = (Button) itemView.findViewById(R.id.btn_itemls_huy);
            btn_itemls_nhanhang = (Button) itemView.findViewById(R.id.btn_itemls_nhanhang);
        }
    }
}
