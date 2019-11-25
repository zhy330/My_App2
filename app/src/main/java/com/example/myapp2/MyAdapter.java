package com.example.myapp2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Map<String, Object>> list;
    private Context context;


    public MyAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {

        holder.textView1.setText(list.get(position).get("name").toString());
        holder.textView2.setText(list.get(position).get("price").toString());
        final String goods_id = list.get(position).get("goods_id").toString();
        final String user_id = list.get(position).get("user_id").toString();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GoodsEnterActivity.class);
                intent.putExtra("goods_id",goods_id);
                intent.putExtra("user_id",user_id);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {

        return list.size()== 0 ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private Button button;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            textView1 = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.price);
            button = itemView.findViewById(R.id.btn);
        }
    }
}