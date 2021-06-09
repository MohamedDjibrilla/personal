package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.Pojos.professionnels;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.DocteursRecyclerView;

import java.util.ArrayList;

public class Docteurs extends AppCompatActivity {
    private ProgressBar proe;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<professionnels> list=new ArrayList<>();
    private DatabaseReference reference;
    private String usables=null;
    private RecyclerView recyclerview=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docteurs);

        proe=findViewById(R.id.prod);
        recyclerview = findViewById(R.id.recycler);
        usables= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");
        refreshLayout = findViewById(R.id.refresh2);
        getlog();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(Docteurs.this, "Liste mise a jour", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlog();
    }

    private void getlog() {
        Query query= FirebaseDatabase.getInstance().getReference("Logs")
                .orderByChild("imei").equalTo(usables);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                for(DataSnapshot sn:dataSnapshot.getChildren()) {
                    logger log = sn.getValue(logger.class);
                    if (!log.equals(null)) {setpage();
                        proe.setVisibility(View.GONE);}}
               }
                else{
                   Intent intent = new Intent(Docteurs.this,MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                    Toast.makeText(Docteurs.this,"Vous Devez etre connecter pour  acceder a cette page",Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Docteurs.this,"error"+ databaseError,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setpage() {

        final DocteursRecyclerView adapter = new DocteursRecyclerView(this, list);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        reference = FirebaseDatabase.getInstance().getReference("profdocteurs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    professionnels prof = ds.getValue(professionnels.class);
                    list.add(prof);
                }
                recyclerview.setAdapter(adapter);}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
