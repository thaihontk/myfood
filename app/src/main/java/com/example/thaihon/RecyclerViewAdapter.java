package com.example.thaihon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private List<String> url = new ArrayList<>();
    private List<String> idnguoidung = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> urlmonan = new ArrayList<>();
    private List<String> tenmonan = new ArrayList<>();
    private List<String> diachimonan = new ArrayList<>();
    private List<String> giamonan = new ArrayList<>();
    private List<String> motamonan = new ArrayList<>();

    public RecyclerViewAdapter(List<String> url,List<String> idnguoidung,List<String> idmonan,List<String> urlmonan,List<String> tenmonan,List<String> diachimonan,List<String> giamonan,List<String> motamonan) {
        this.url = url;
        this.idnguoidung = idnguoidung;
        this.idmonan = idmonan;
        this.urlmonan = urlmonan;
        this.tenmonan = tenmonan;
        this.diachimonan = diachimonan;
        this.giamonan = giamonan;
        this.motamonan =motamonan;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemmonan, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        //holder.idmonan.setText(idmonan.get(position));
        holder.tenmonan.setText(tenmonan.get(position));
        holder.diachimonan.setText(diachimonan.get(position));
        holder.giamonan.setText(giamonan.get(position));
        holder.motamonan.setText(motamonan.get(position));
        Picasso.get().load(url.get(position)+urlmonan.get(position)).into(holder.urlmonan1);
        holder.urlmonan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, thongtin.class);
                intent.putExtra("idnguoidung",admin.getId());
                intent.putExtra("idmonan",idmonan.get(position));
                intent.putExtra("url", admin.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idmonan.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView idmonan, tenmonan, diachimonan, giamonan, motamonan;
        ImageButton urlmonan1;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            urlmonan1 = (ImageButton) itemView.findViewById(R.id.imgmonan);
            tenmonan = (TextView) itemView.findViewById(R.id.edittenmon);
            diachimonan = (TextView) itemView.findViewById(R.id.editdiachi);
            giamonan = (TextView) itemView.findViewById(R.id.editgia);
            motamonan = (TextView) itemView.findViewById(R.id.editmota);
        }
    }
}
