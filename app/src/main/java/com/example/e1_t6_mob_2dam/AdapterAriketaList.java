package com.example.e1_t6_mob_2dam;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Callback.AriketaCallBack;
import dao.AriketaDao;
import objects.Ariketa;
import objects.Workout;

public class AdapterAriketaList extends RecyclerView.Adapter<AdapterAriketaList.ViewHolder> {

    private Context context;
    private ArrayList<Ariketa> ariketas;

    public AdapterAriketaList(Context context, ArrayList<Ariketa> ariketas) {
        this.context = context;
        this.ariketas = ariketas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ariketaklistformat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ariketa ariketa = ariketas.get(position);
        holder.tvListR_name.setText(ariketa.getName());
        holder.tvListR_muskulua.setText(ariketa.getWorkedMuscle());
        holder.tvListR_denbora.setText(String.valueOf(ariketa.getDenbora()));
    }

    @Override
    public int getItemCount() {
        return ariketas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListR_name, tvListR_muskulua, tvListR_denbora;

        public ViewHolder(View itemView) {
            super(itemView);
            tvListR_name = itemView.findViewById(R.id.tvListR_name);
            tvListR_muskulua = itemView.findViewById(R.id.tvListR_muskulua);
            tvListR_denbora = itemView.findViewById(R.id.tvListR_denbora);
        }
    }
}
