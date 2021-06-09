package com.rpi.fabapro.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Pojos.com;
import com.rpi.fabapro.Pojos.gal;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.Pojos.statusP;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.PnpAccueilRecyclerCom;
import com.rpi.fabapro.RecyclerviewAdapters.ServicesGallery;

import java.util.ArrayList;

public class DetailsServices extends AppCompatActivity {

    private Button con;
    private TextView nom, prof, status, so, l, m, m1, j, v, s, d;
    private ImageButton back,addc;
    private ProgressBar prot;
    private String Statut, pkey, fingerprint, username,doc;
    private ArrayList<com> commentaire = new ArrayList<>();
    private DatabaseReference r1,r2,r3;
    private ArrayList<gal> list1 = new ArrayList<>();
    private RecyclerView recyclerviewcom, recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_services);
        nom = findViewById(R.id.nom);
        recyclerviewcom= findViewById(R.id.pnpaccueilrecyclercom);
        prof = findViewById(R.id.profession);
        recyclerView= findViewById(R.id.gallery);
        back = findViewById(R.id.back);
        status = findViewById(R.id.status);
        con = findViewById(R.id.continuerpnp);
        so = findViewById(R.id.so1);
        l = findViewById(R.id.l11);
        m = findViewById(R.id.m22);
        m1 = findViewById(R.id.m23);
        j = findViewById(R.id.j2);
        v = findViewById(R.id.v2);
        s = findViewById(R.id.s22);
        d = findViewById(R.id.s23);
        prot = findViewById(R.id.preee);
        addc=findViewById(R.id.pnpaddc);
        reception();
        getusername();
        getinfos();
        getcoment();
        getgallery();
        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcomment();
            }
        });
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doc="client";startpp();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

    }

    private void addcomment() {
        final android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle("Ajouter un Commentaire");
        builder.setMessage("veillez entrer le commentaire");
        final View view = getLayoutInflater().inflate(R.layout.addcomment, null);
        builder.setView(view);
        builder.setPositiveButton("Enregister", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView t1= view.findViewById(R.id.commentname);
                t1.setText(username);
                EditText e1= view.findViewById(R.id.commentedit);
                e1.getText();
                pushs(username,e1.getText().toString());

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void pushs(String username, String toString) {
        r3= FirebaseDatabase.getInstance().getReference("commentairesb2c").child(pkey);
        String id=r3.push().getKey();
        com cm=new com(username,toString,id);
        r3.child(id).setValue(cm);
        Toast.makeText(DetailsServices.this,"Commentaire Ajouté",Toast.LENGTH_SHORT).show();

    }

    private void getgallery() {

        final ServicesGallery adapter3 = new ServicesGallery(this,list1);
        adapter3.notifyDataSetChanged();
        r1=FirebaseDatabase.getInstance().getReference("gb2c").child(pkey);
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list1.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        gal g= ds.getValue(gal.class);
                        list1.add(g);
                    }
                    recyclerView.setAdapter(adapter3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager layoutManager2= new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager2);

    }

    private void getcoment() {

        final PnpAccueilRecyclerCom adapter1=new PnpAccueilRecyclerCom(commentaire,this);
        adapter1.notifyDataSetChanged();
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewcom.setLayoutManager(layoutManager);
        r2=FirebaseDatabase.getInstance().getReference("commentairesb2c").child(pkey);
        r2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    commentaire.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        com c= ds.getValue(com.class);
                        commentaire.add(c);
                    }
                    recyclerviewcom.setAdapter(adapter1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getusername() {
        fingerprint = Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/", "");
        Query query2 = FirebaseDatabase.getInstance().getReference("Logs").orderByChild("imei").equalTo(fingerprint);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    logger lo = sn.getValue(logger.class);
                    if (lo != null) {
                        username = lo.getUsername();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getinfos() {
        Query query = FirebaseDatabase.getInstance().getReference("statusb2c").orderByChild("pkey").equalTo(pkey);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    con.setClickable(false);
                    statusP st = sn.getValue(statusP.class);
                    if (st != null) {
                        nom.setText(st.getNom());
                        prof.setText(st.getProfession());
                        Statut = st.getDisponibilite();
                        status.setText(Statut);
                        so.setText(st.getServiceOffert()); //need a new prof and status :(
                        l.setText(st.getL());
                        m.setText(st.getM());
                        m1.setText(st.getM1());
                        j.setText(st.getJ());
                        v.setText(st.getV());
                        s.setText(st.getS());
                        d.setText(st.getS1());
                    }

                }
                prot.setVisibility(View.GONE);
                con.setClickable(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailsServices.this, "Une erreur s'est produite Veillez re-essayer plus tard", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void reception() {
        if (getIntent().hasExtra("pkey")) {
            pkey = getIntent().getStringExtra("pkey");
        } else Toast.makeText(this, "no text", Toast.LENGTH_SHORT).show();
    }

    private void startpp() {
        if (Statut.equals("Actif")) {
            Intent in = new Intent(this,Discussion.class);
            in.putExtra("pkey",pkey);
            in.putExtra("page",doc);
            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(in);
        } else {
            Toast.makeText(this, "Desolé ce professionel n'est pas en ligne en ce moment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        Intent inte = new Intent(this, Services.class);
        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(inte);
    }

}
