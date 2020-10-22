package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewHolder> {

Context context;
private ArrayList<ModelData> item;

public AdapterProcess(Context context, ArrayList<ModelData> item){
    this.context = context;
    this.item = item;
}

@Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user,parent, false);
    ViewHolder processHolder = new
            ViewHolder(view);
    return
            processHolder;
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelData data = item.get(position);
        holder.nama_data.setText(data.getNamaData());
    }

    @Override
    public int getItemCount(){ return item.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nama_data;

    public ViewHolder(View itemView){
        super(itemView);
        nama_data = (TextView)
                itemView.findViewById(R.id.list_data);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ModelData.class);
                intent.putExtra("title", getAdapterPosition());
            }
        });
        }
    }
}
