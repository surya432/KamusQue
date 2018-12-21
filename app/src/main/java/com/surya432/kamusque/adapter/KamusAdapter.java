package com.surya432.kamusque.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.surya432.kamusque.DetailActivity;
import com.surya432.kamusque.Model.KamusModel;
import com.surya432.kamusque.R;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.ViewAdapter> {
    private Activity activity;
    private Context context;
    private ArrayList<KamusModel> list = new ArrayList<>();

    public KamusAdapter() {
    }

    public void renewAdapter(Context context,ArrayList<KamusModel> items) {
        this.context = context;
        this.list = items;
        notifyDataSetChanged();
    }

    public ArrayList<KamusModel> getList() {
        return list;
    }

    public void setList(ArrayList<KamusModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter viewAdapter,final int i) {
        viewAdapter.tvKey.setText(getList().get(i).getKey());
        viewAdapter.tvValue.setText(getList().get(i).getTerjemahan());
        viewAdapter.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                KamusModel m =getList().get(i);
                intent.putExtra("EXTRAS_Movies",m);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView tvKey, tvValue;
        LinearLayout llItem;
        public ViewAdapter(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.tvKey);
            tvValue = itemView.findViewById(R.id.tvValues);
            llItem = itemView.findViewById(R.id.LLitem);
        }
    }
}
