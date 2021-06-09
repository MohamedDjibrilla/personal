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
import com.rpi.fabapro.Pojos.Consultations;
import com.rpi.fabapro.Pojos.com;
import com.rpi.fabapro.Pojos.credit;
import com.rpi.fabapro.Pojos.gal;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.Pojos.pharma;
import com.rpi.fabapro.Pojos.statusP;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.DocteursGallery;
import com.rpi.fabapro.RecyclerviewAdapters.PnpAccueilRecyclerCom;
import com.rpi.fabapro.RecyclerviewAdapters.pharmaRecycler;

import java.util.ArrayList;

public class DetailsDocteurs extends AppCompatActivity {
    private Button con;
    private ArrayList<pharma> list;
    private ProgressBar proe;
    private ImageButton back,addc;
    private TextView nom, prof, status,so,l,m,m1,j,v,s,d;
    private String Status,pkey1,fingerprint,userid,username,page,doc,nextt;
    private int currentCredit,currentCredit1,currentCredit2,nombreConsul,nombre1;
    private RecyclerView recyclerviewcom,recyclerView;
    private ArrayList<com> commentaire = new ArrayList<>();
    private ArrayList<gal> list1 = new ArrayList<>();
    private DatabaseReference r1,r2,r3,r4,r5,d1,r6,r7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_docteurs);
        nom = findViewById(R.id.nom);back=findViewById(R.id.back);addc=findViewById(R.id.pnpaddc);recyclerviewcom=findViewById(R.id.pnpaccueilrecyclercom);
        prof = findViewById(R.id.profession);recyclerView= findViewById(R.id.gallery); proe=findViewById(R.id.inte);
        status = findViewById(R.id.status);so=findViewById(R.id.serviceoffert);l=findViewById(R.id.l);m=findViewById(R.id.m);
        m1=findViewById(R.id.m1);j=findViewById(R.id.j);v=findViewById(R.id.v);s=findViewById(R.id.s1);d=findViewById(R.id.d);
        fingerprint= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");
        reception();getusername();getinfos();getcoment();getpharma();getgallery();
        con = findViewById(R.id.continuerpnp);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nextt=="non"){
                    Toast.makeText(DetailsDocteurs.this,"Credit Insuffisant! Veuillez recharger",Toast.LENGTH_SHORT).show();
                }else {  doc="doc";startpnp();}
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcoment();
            }
        });
    }


    private void getpharma() {
        final pharmaRecycler adapter1=new pharmaRecycler(list,this);
        d1 = FirebaseDatabase.getInstance().getReference("pharma");
        d1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    commentaire.clear();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        com c= ds.getValue(com.class);
                        commentaire.add(c);
                    }
                    recyclerviewcom.setAdapter(adapter1);
                }else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewcom.setLayoutManager(layoutManager);

    }


    //verification de credit
    private void startpnp() {

        if(Status.equals("Inactif")){
            Toast.makeText(this,"Desole ce professionel n'est pas en ligne en ce moment",Toast.LENGTH_SHORT).show();
        }else {
            verify();
        }
    }




    private void verify() {


        if(nombreConsul>nombre1){
            final AlertDialog.Builder builder = new AlertDialog.Builder(DetailsDocteurs.this);
            builder.setIcon(R.drawable.icon);
            builder.setTitle("Verification");
            builder.setMessage("Cette consultation vous sera facture a 2000 F CFA sur votre compte principale");
            builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    pushC();
                    nombre1=nombreConsul;
                    r5=FirebaseDatabase.getInstance().getReference("consuclient").child(pkey1);
                    Consultations ds=new Consultations(username,userid,nombre1);
                    r5.child(userid).setValue(ds);
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();

        }else{
            another();

        }


    }

    private void pushC() {
        currentCredit1 =currentCredit-2000;
        r4=FirebaseDatabase.getInstance().getReference("CreditUtilisateurs");
        credit sae=new credit(userid,username,currentCredit1);
        r4.child(userid).setValue(sae);
        another();
    }
    private void another() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle("Loi et Reglement de FABA PRO");
        builder.setMessage("Vous certifier avoir lu et compris les termes de la consultation");
        builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent inte=new Intent(DetailsDocteurs.this,Discussion.class);
                inte.putExtra("pkey",pkey1);
                inte.putExtra("page",doc);
                inte.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(inte);
                dialogInterface.dismiss();
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

    //Comment
//add comment to docteurs.
    private void addcoment() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        r3=FirebaseDatabase.getInstance().getReference("commentairesdocteurs").child(pkey1);
        String id=r3.push().getKey();
        com cm=new com(username,toString,id);
        r3.child(id).setValue(cm);
        Toast.makeText(DetailsDocteurs.this,"Commentaire Ajouté",Toast.LENGTH_SHORT).show();
    }
    //get comment
    private void getcoment() {
        final PnpAccueilRecyclerCom adapter1=new PnpAccueilRecyclerCom(commentaire,this);
        r2=FirebaseDatabase.getInstance().getReference("commentairesdocteurs").child(pkey1);
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

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewcom.setLayoutManager(layoutManager);
    }


    //starting block
    private void getgallery() {
        final DocteursGallery adapter3 = new DocteursGallery(this,list1);
        r1=FirebaseDatabase.getInstance().getReference("gdoc").child(pkey1);
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
    private void getusername() {
        Query query2=FirebaseDatabase.getInstance().getReference("Logs").orderByChild("imei").equalTo(fingerprint);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    logger lo=sn.getValue(logger.class);
                    if(lo!= null) {
                        username=lo.getUsername();
                        userid=lo.getUserID();
                        queryy(userid);
                        queery2(userid);
                        queery3(userid);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void queery3(String userid) {
        r6=FirebaseDatabase.getInstance().getReference("consuclient").child(pkey1).child(userid);
        r6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Consultations cd= dataSnapshot.getValue(Consultations.class);
                    if(cd!=null){
                        nombre1=cd.getNombre();
                    }

                }else {
                    Toast.makeText(DetailsDocteurs.this,"Vous n'avez aucune consultation active",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void queery2(String userid) {
        r7=FirebaseDatabase.getInstance().getReference("consudoc").child(pkey1).child(userid);
        r7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Consultations c= dataSnapshot.getValue(Consultations.class);
                    if(c!=null){
                        nombreConsul=c.getNombre();
                    }

                }else {
                    Toast.makeText(DetailsDocteurs.this,"Vous n'avez aucune consultation active",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void queryy(final String des) {

        Query query=FirebaseDatabase.getInstance().getReference("CreditUtilisateurs").orderByChild("uid").equalTo(des);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    credit cd=sn.getValue(credit.class);
                    if(cd!= null) {
                        currentCredit=cd.getCredit();
                        if(currentCredit<1999){Toast.makeText(DetailsDocteurs.this,"Vous n'avez pas assez de credit pour effectuer cette consultation",Toast.LENGTH_SHORT).show();
                            nextt="non";
                        }else{
                            nextt="oui";
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailsDocteurs.this,"Une Erreur s'est produite. Veuillez reessayer plutard ",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getinfos() {
        Query query= FirebaseDatabase.getInstance().getReference("statusdocteurs").orderByChild("pkey").equalTo(pkey1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    statusP st=sn.getValue(statusP.class);
                    if(st!= null) {
                        nom.setText(st.getNom());
                        prof.setText(st.getProfession());
                        Status=st.getDisponibilite();
                        status.setText(Status);
                        so.setText(st.getServiceOffert());
                        l.setText(st.getL());
                        m.setText(st.getM());
                        m1.setText(st.getM1());
                        j.setText(st.getJ());
                        v.setText(st.getV());
                        s.setText(st.getS());
                        d.setText(st.getS1()); }
                    proe.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailsDocteurs.this,"Une erreur s'est produite Veillez re-essayer plus tard",Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void reception() {
        if (getIntent().hasExtra("pkey")) {
            pkey1=getIntent().getStringExtra("pkey");
        }
        else Toast.makeText(this,"no text",Toast.LENGTH_SHORT).show();
    }

//next activity to start
//and verification


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    //back action
    private void back(){ Intent inte= new Intent(this,Docteurs.class);
        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(inte);}


}
