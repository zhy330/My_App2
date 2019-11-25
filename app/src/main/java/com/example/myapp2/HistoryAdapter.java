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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Map<String, Object>> list;
    private Context context;


    public HistoryAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(list.size()==0){
            View empty = LayoutInflater.from(context).inflate(R.layout.empty_view,parent,false);
            return new ViewHolder(empty);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, final int position) {

        holder.history_name.setText(list.get(position).get("goods_name").toString());
        holder.history_price.setText(list.get(position).get("goods_price").toString());
        final String goods_id = list.get(position).get("goods_id").toString();

        holder.btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GoodsEnterActivity.class);
                intent.putExtra("goods_id",goods_id);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {

        return list.size()== 0 ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView history_name;
        private TextView history_price;
        private Button btn_history;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_history);
            history_name = itemView.findViewById(R.id.name_history);
            history_price = itemView.findViewById(R.id.price_history);
            btn_history = itemView.findViewById(R.id.btn_history);
        }
    }
}
