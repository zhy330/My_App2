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

public class ShopEnterAdapter extends RecyclerView.Adapter<ShopEnterAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public ShopEnterAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShopEnterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_shop_enter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String goods_id = list.get(position).get("goods_id").toString();
        holder.shop_enter_goods_name.setText(list.get(position).get("name").toString());
        holder.shop_enter_goods_price.setText(list.get(position).get("price").toString());
        holder.btn_shop_enter_goods_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GoodsEnterActivity.class);
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
        private ImageView shop_enter_goods_picture;
        private TextView shop_enter_goods_name;
        private TextView shop_enter_goods_price;
        private Button btn_shop_enter_goods_enter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_enter_goods_picture = itemView.findViewById(R.id.shop_enter_goods_picture);
            shop_enter_goods_name = itemView.findViewById(R.id.shop_enter_goods_name);
            shop_enter_goods_price = itemView.findViewById(R.id.shop_enter_goods_price);

            btn_shop_enter_goods_enter = itemView.findViewById(R.id.btn_shop_enter_goods_enter);
        }
    }
}
