package com.example.thaihon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDanhgia extends RecyclerView.Adapter<RecyclerViewDanhgia.RecyclerViewHolder> {

    private List<String> ten = new ArrayList<>();
    private List<String> danhgia = new ArrayList<>();
    private List<String> mucdo = new ArrayList<>();


    public RecyclerViewDanhgia(List<String> ten, List<String> mucdo, List<String> danhgia) {
        this.ten = ten;
        this.danhgia = danhgia;
        this.mucdo = mucdo;
    }

    @Override
    public RecyclerViewDanhgia.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_danhgia, parent, false);
        return new RecyclerViewDanhgia.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewDanhgia.RecyclerViewHolder holder, final int position) {
        //holder.idmonan.setText(idmonan.get(position));
        holder.text_itemdg_danhgia.setText(danhgia.get(position));
        holder.text_itemdg_ten.setText(ten.get(position));
        switch (mucdo.get(position)){
            case "1":
                holder.img_itemdag.setImageResource(R.drawable.star1);
                break;
            case "2":
                holder.img_itemdag.setImageResource(R.drawable.star2);
                break;
            case "3":
                holder.img_itemdag.setImageResource(R.drawable.star3);
                break;
            case "4":
                holder.img_itemdag.setImageResource(R.drawable.star4);
                break;
            case "5":
                holder.img_itemdag.setImageResource(R.drawable.star5);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mucdo);
        }

    }

    @Override
    public int getItemCount() {
        return ten.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text_itemdg_ten, text_itemdg_danhgia;
        ImageView img_itemdag;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            img_itemdag = (ImageView) itemView.findViewById(R.id.img_itemdg);
            text_itemdg_danhgia = (TextView) itemView.findViewById(R.id.text_itemdg_danhgia);
            text_itemdg_ten = (TextView) itemView.findViewById(R.id.text_itemdg_ten);
        }
    }
}
