package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class termsrecycler extends RecyclerView.Adapter<termsrecycler.holder>  {
private ArrayList<String> list;
private Context context;

    public termsrecycler(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.terms_list,viewGroup,false);
        termsrecycler.holder holder= new termsrecycler.holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
       holder.t1.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class holder extends RecyclerView.ViewHolder{
private TextView t1;
    public holder(@NonNull View itemView) {
        super(itemView);
        t1=itemView.findViewById(R.id.text);
    }
}

}
