package com.example.thaihon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

class RecyclerViewDonhangdone extends RecyclerView.Adapter<RecyclerViewDonhangdone.RecyclerViewHolder>{
    private List<String> id = new ArrayList<>();
    private List<String> tenmon = new ArrayList<>();
    private List<String> soluong = new ArrayList<>();
    private List<String> giatri = new ArrayList<>();
    private List<String> ngay = new ArrayList<>();
    private List<String> ghichu = new ArrayList<>();
    private List<String> tenkhachhang = new ArrayList<>();
    private List<String> sdt = new ArrayList<>();
    private List<String> diachi = new ArrayList<>();
    Activity activity;

    public RecyclerViewDonhangdone(Activity activity,List<String> id, List<String> tenmon,List<String> soluong,List<String> giatri,List<String> ngay,List<String> ghichu,List<String> tenkhachhang,List<String> sdt,List<String> diachi) {
        this.activity = activity;
        this.id = id;
        this.tenmon = tenmon;
        this.soluong = soluong;
        this.giatri = giatri;
        this.ngay = ngay;
        this.ghichu = ghichu;
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.diachi =diachi;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_donhangdone, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        //holder.idmonan.setText(idmonan.get(position));
        holder.text_donhang_tenmon.setText(tenmon.get(position));
        holder.text_donhang_soluong.setText(soluong.get(position));
        holder.text_donhang_giatri.setText(giatri.get(position));
        holder.text_donhang_ngay.setText(ngay.get(position));
        holder.text_donhang_ghichu.setText(ghichu.get(position));
        holder.text_donhang_tenkhachhang.setText(tenkhachhang.get(position));
        holder.text_donhang_sdt.setText(sdt.get(position));
        holder.text_donhang_diachi.setText(diachi.get(position));
    }

    @Override
    public int getItemCount() {
        return tenkhachhang.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text_donhang_tenmon, text_donhang_soluong, text_donhang_giatri, text_donhang_ngay, text_donhang_ghichu, text_donhang_tenkhachhang, text_donhang_sdt, text_donhang_diachi;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            text_donhang_tenmon = (TextView) itemView.findViewById(R.id.text_donhang_tenmon);
            text_donhang_soluong = (TextView) itemView.findViewById(R.id.text_donhang_soluong);
            text_donhang_giatri = (TextView) itemView.findViewById(R.id.text_donhang_giatri);
            text_donhang_ngay = (TextView) itemView.findViewById(R.id.text_donhang_ngay);
            text_donhang_ghichu = (TextView) itemView.findViewById(R.id.text_donhang_ghichu);
            text_donhang_tenkhachhang = (TextView) itemView.findViewById(R.id.text_donhang_tenkhachhang);
            text_donhang_sdt = (TextView) itemView.findViewById(R.id.text_donhang_sdt);
            text_donhang_diachi = (TextView) itemView.findViewById(R.id.text_donhang_diachi);

        }
    }
}
