package com.example.thaihon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
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

class RecyclerViewDanhsach extends RecyclerView.Adapter<RecyclerViewDanhsach.RecyclerViewHolder>{
    private List<String> url = new ArrayList<>();
    private List<String> idnguoidung = new ArrayList<>();
    private List<String> idmonan = new ArrayList<>();
    private List<String> urlmonan = new ArrayList<>();
    private List<String> tenmonan = new ArrayList<>();
    private List<String> diachimonan = new ArrayList<>();
    private List<String> giamonan = new ArrayList<>();
    private List<String> motamonan = new ArrayList<>();
    private Activity ctx;
    private Dialog dialog;


    public RecyclerViewDanhsach(Activity ctx, List<String> url,List<String> idnguoidung,List<String> idmonan,List<String> urlmonan,List<String> tenmonan,List<String> diachimonan,List<String> giamonan,List<String> motamonan) {
        this.ctx = ctx;
        this.url = url;
        this.idnguoidung = idnguoidung;
        this.idmonan = idmonan;
        this.urlmonan = urlmonan;
        this.tenmonan = tenmonan;
        this.diachimonan = diachimonan;
        this.giamonan = giamonan;
        this.motamonan =motamonan;
    }

    public void xoa(final RecyclerViewHolder holder,final String idmonan){
        String url = admin.getUrl()+"/xoamonan.php";
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if(response.contains("oke")){
                    dialog.cancel();
                    holder.btn_itemds_sua.setEnabled(false);
                    holder.btn_itemds_xoa.setEnabled(false);
                    Toast.makeText(ctx,"Xóa món thành công!!!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ctx,"Xóa món thất bại!!!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,error.toString(), Toast.LENGTH_LONG).show();
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

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_danhsachmonan, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        //holder.idmonan.setText(idmonan.get(position));
        holder.tenmonan.setText(tenmonan.get(position));
        holder.diachimonan.setText(diachimonan.get(position));
        holder.giamonan.setText(giamonan.get(position));
        holder.motamonan.setText(motamonan.get(position));
        Picasso.get().load(url.get(position)+urlmonan.get(position)).into(holder.urlmonan1);

        holder.btn_itemds_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, quanlymonan.class);
                intent.putExtra("idmonan",idmonan.get(position));
                context.startActivity(intent);
            }
        });
        holder.btn_itemds_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.btn_itemds_xoa.setText("âfjnnnnnnnnnnnnnnnnnn");
                dialog = new Dialog(ctx);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_xoamonan);
                dialog.setTitle("Lưu ý");
                Button btn_ok = (Button)dialog.findViewById(R.id.btn_dialogxoa_dongy);
                Button btn_no = (Button)dialog.findViewById(R.id.btn_dialogxoa_khong);
                final TextView text =(TextView)dialog.findViewById(R.id.text_dialogxoa);
                text.setText(tenmonan.get(position));
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xoa(holder,idmonan.get(position));
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
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
        Button btn_itemds_xoa, btn_itemds_sua;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            urlmonan1 = (ImageButton) itemView.findViewById(R.id.img_itemds_monan);
            tenmonan = (TextView) itemView.findViewById(R.id.edit_itemds_tenmon);
            diachimonan = (TextView) itemView.findViewById(R.id.edit_itemds_diachi);
            giamonan = (TextView) itemView.findViewById(R.id.edit_itemds_gia);
            motamonan = (TextView) itemView.findViewById(R.id.edit_itemds_mota);
            btn_itemds_sua = (Button)itemView.findViewById(R.id.btn_itemds_sua);
            btn_itemds_xoa = (Button)itemView.findViewById(R.id.btn_itemds_xoa);
        }
    }
}
