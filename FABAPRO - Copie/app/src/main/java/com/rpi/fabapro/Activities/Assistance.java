package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Pojos.conversation;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.Pojos.problems;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.DiscussionRecycler;

import java.util.ArrayList;

public class Assistance extends AppCompatActivity {
    private ImageView back,send;
    private EditText e1;
    private String fingerprint, userid,nom1;
    private DatabaseReference r,r1;
    private RecyclerView recyclerView;
    private ArrayList<conversation> nom= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        e1=findViewById(R.id.messageedit);
        recyclerView=findViewById(R.id.recycler);
        send=findViewById(R.id.send);
        fingerprint= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");
        getuserid();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });

    }


    private void verify() {
        String text= e1.getText().toString();
        if(text.isEmpty()){
            e1.setError("vous ne pouvez envoyer une message vide");
            e1.requestFocus();
            return;
        }sending();
    }



    private void sending() {

        String text = e1.getText().toString();
        e1.setText("");
        r = FirebaseDatabase.getInstance().getReference("help").child(userid);
        String id = r.push().getKey();
        conversation c = new conversation("right", "black",text,userid);
        r.child(id).setValue(c);

        r1=FirebaseDatabase.getInstance().getReference("helpl").child(userid);
        problems sl=new problems(nom1,userid,"Nouveau");
        r1.setValue(sl);

    }


    private void bck() {
        Intent intent= new Intent(Assistance.this,MainActivity.class);
        startActivity(intent);
    }



    private void getuserid() {

        Query query= FirebaseDatabase.getInstance().getReference("Logs").orderByChild("imei").equalTo(fingerprint);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        logger L=ds.getValue(logger.class);
                        userid=L.getUserID();
                        nom1=L.getUsername();
                    }
                    getProblems();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getProblems() {

        final DiscussionRecycler adapter=new DiscussionRecycler(nom,this);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        r1=FirebaseDatabase.getInstance().getReference("help").child(userid);
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    nom.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        conversation cn= ds.getValue(conversation.class);
                        nom.add(cn);
                    }
                    recyclerView.setAdapter(adapter);
                }else {
                    Intent intent = new Intent(Assistance.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(Assistance.this,"Connectez vous pour utiliser cette option ",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
