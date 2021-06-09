package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.showImageRecycler;

import java.util.ArrayList;

public class ShowImages extends AppCompatActivity {
    private RecyclerView recycler;
    private DatabaseReference r;
    private ImageView i1;
    private ProgressBar p;
    private String pkey,channel,page;
    private ArrayList<String> list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        p=findViewById(R.id.q);
        recycler=findViewById(R.id.showrecycler);
        i1=findViewById(R.id.back);
        reception();
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bac();
            }
        });

    }

    private void reception() {
        if(getIntent().hasExtra("page") && getIntent().hasExtra("pkey") && getIntent().hasExtra("page")){
            pkey=getIntent().getStringExtra("pkey");
            channel=getIntent().getStringExtra("channel");
            page=getIntent().getStringExtra("page");
            setpage();
        }

    }

    private void setpage() {
        final showImageRecycler adapter= new showImageRecycler(list,this);
        r = FirebaseDatabase.getInstance().getReference("images").child(channel).child(pkey);
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String g= ds.getValue(String.class);
                        list.add(g);
                        p.setVisibility(View.GONE);
                    }
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        LinearLayoutManager layoutManager2= new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bac();
    }

    private void bac() {
        Intent intent = new Intent(this,Discussion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pkey",pkey);
        intent.putExtra("page",page);

        startActivity(intent);
    }
}
