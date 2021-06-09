package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rpi.fabapro.Pojos.crediter;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class AllTransactionRecycler extends RecyclerView.Adapter<AllTransactionRecycler.viewHolder>{
    private ArrayList<String> method;
    private ArrayList<String> montant;
    private ArrayList<crediter> list= new ArrayList<>();
    private Context mContext;

    public AllTransactionRecycler(ArrayList<crediter> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_transactions_list_view,viewGroup,false);
        AllTransactionRecycler.viewHolder holder= new AllTransactionRecycler.viewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.method.setText(list.get(i).getCanal());
        viewHolder.montant.setText(list.get(i).getMontant());
        viewHolder.status.setText(list.get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class viewHolder extends RecyclerView.ViewHolder{
        TextView method,montant,status;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            method=itemView.findViewById(R.id.method);
            montant=itemView.findViewById(R.id.montant);
            status=itemView.findViewById(R.id.status);
        }
    }
}


