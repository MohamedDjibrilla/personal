package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rpi.fabapro.Pojos.conversation;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class DiscussionRecycler extends RecyclerView.Adapter<DiscussionRecycler.viewholder> {
private ArrayList<conversation> list;
private Context mcontext;

    public DiscussionRecycler(ArrayList<conversation> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dicusssion_list,viewGroup,false);
        DiscussionRecycler.viewholder holder= new DiscussionRecycler.viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
       if(list.get(i).getPosition().equals("right")){
           viewholder.t1.setText(list.get(i).getCom());
           viewholder.t2.setVisibility(View.GONE);
       }else{
           viewholder.t2.setText(list.get(i).getCom());
           viewholder.t2.getResources().getColor(android.R.color.white);
           viewholder.t1.setVisibility(View.GONE);
       }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        private TextView t1,t2;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.thirdlayoutnom);
            t2=itemView.findViewById(R.id.thirdlayoutcom);

        }
    }
}
