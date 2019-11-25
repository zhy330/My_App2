package com.example.myapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyGoodsAdapter extends RecyclerView.Adapter<MyGoodsAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public MyGoodsAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyGoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String goods_id = list.get(position).get("goods_id").toString();
        holder.goods_name.setText(list.get(position).get("name").toString());
        holder.goods_price.setText(list.get(position).get("price").toString());

        holder.btn_goods_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyGoodsDataActivity.class);
                intent.putExtra("goods_id",goods_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView goods_picture;
        private TextView goods_name;
        private TextView goods_price;

        private Button btn_goods_edit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            goods_picture = itemView.findViewById(R.id.goods_picture);
            goods_name = itemView.findViewById(R.id.goods_name);
            goods_price = itemView.findViewById(R.id.goods_price);

            btn_goods_edit = itemView.findViewById(R.id.btn_goods_edit);
        }
    }
}
