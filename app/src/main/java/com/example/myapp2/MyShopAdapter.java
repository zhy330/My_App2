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

public class MyShopAdapter extends RecyclerView.Adapter<MyShopAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public MyShopAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.shop_name.setText(list.get(position).get("name").toString());
        final String shop_id = list.get(position).get("shop_id").toString();

        holder.btn_shop_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyShopDataActivity.class);
                intent.putExtra("shop_id",shop_id);
                context.startActivity(intent);
            }
        });
        holder.btn_shop_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyGoodsActivity.class);
                intent.putExtra("shop_id",shop_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView shop_image;
        private TextView shop_name;
        private Button btn_shop_enter;
        private Button btn_shop_edit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_image = itemView.findViewById(R.id.shop_img);
            shop_name = itemView.findViewById(R.id.shop_name);
            btn_shop_edit = itemView.findViewById(R.id.btn_shop_edit);
            btn_shop_enter = itemView.findViewById(R.id.btn_shop_enter);
        }
    }
}
