package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rpi.fabapro.Pojos.clients;
import com.rpi.fabapro.Pojos.conversation;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.DiscussionRecycler;

import java.util.ArrayList;
import java.util.Random;

public class Discussion extends AppCompatActivity {
    private static final String CHANNEL ="";
    private RecyclerView recyclerView;
    private ArrayList<conversation> list = new ArrayList<>();
    private ImageButton i1;
    private ImageView i2,i3,i4,i5;
    private EditText e1;
    private TextView t1;
    private Uri uri;
    private String ew;
    private ProgressBar p1,p2;
    private StorageReference storage;
    private final String DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Random random = new Random();
    private static final int PICK_IMAGE = 3;
    private static final int MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 7;
    private static final int IMAGE_CAPTURE_CODE = 4;
    private static final int MY_PERMISSION_CAMERA =10 ;
    private String pkey,uid,usables,page,imageId,username,profession;
    private DatabaseReference r1,r,r2,r3,r4,r5;
    private int i=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        recyclerView=findViewById(R.id.discussionm);
        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        i1=findViewById(R.id.back);
        i3=findViewById(R.id.opengallery);
        i4=findViewById(R.id.send);
        i5=findViewById(R.id.opencamera);
        e1=findViewById(R.id.messageedit);
        t1=findViewById(R.id.text);
        getCredentials();
        storage = FirebaseStorage.getInstance().getReference();
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showgallery();
            }
        });
       /* i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();

            }
        });*/
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomString(i);
                getimage();
            }
        });
    }

    private void getRandomString(Integer len){

        StringBuilder sb=new StringBuilder(len);
        for(int lonff = 0; lonff < len; lonff++){
            sb.append(DATA.charAt(random.nextInt(DATA.length())));
            imageId=sb.toString();}
    }
    private void getimage() {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE:
                if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
                    uri=data.getData();
                    p1.setVisibility(View.VISIBLE);
                    p2.setVisibility(View.VISIBLE);
                    pushimage();

                    break;
                }
        }
    }

    private void pushimage() {
        storage.child("gallery/"+imageId).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                geturl();
            }
        });
    }

    private void geturl() {
        final StorageReference push= storage.child("gallery/"+imageId);
        push.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ew=uri.toString();
                save();
            }
        });
    }

    private void save() {
        r5= FirebaseDatabase.getInstance().getReference("images").child(uid).child(pkey);
        String q=r5.push().getKey();
        r5.child(q).setValue(ew);
        p1.setVisibility(View.GONE);
        p2.setVisibility(View.GONE);
    }

    private void showgallery() {
        Intent intent= new Intent(this,ShowImages.class);
        intent.putExtra("pkey",pkey);
        intent.putExtra("channel",uid);
        intent.putExtra("page",page);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    //first information to use
    private void getCredentials() {
        usables= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");
        Query query=FirebaseDatabase.getInstance().getReference("Logs").orderByChild("imei").equalTo(usables);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    logger log=sn.getValue(logger.class);
                    if(!log.equals(null)) {
                        uid=log.getUserID();
                        reception();
                    }
                    else{
                        Toast.makeText(Discussion.this,"Erreur veuillez reessayer",Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void reception() {
        if ( getIntent().hasExtra("pkey") && getIntent().hasExtra("page")) {
            pkey=getIntent().getStringExtra("pkey");
            page=getIntent().getStringExtra("page");
            getUsername();
            readDb();
        }
    }

    private void getUsername() {
        if(page.equals("doc")) {
            r3 = FirebaseDatabase.getInstance().getReference("conseils").child(pkey).child(uid);
        }else{
            r3=FirebaseDatabase.getInstance().getReference("client").child(pkey).child(uid);
        }
        r3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    clients ds = dataSnapshot.getValue(clients.class);
                    username = ds.getNom();
                    profession = ds.getProfession();
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //reading from the db
    private void readDb() {
        final DiscussionRecycler adapter = new DiscussionRecycler(list,this);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        r1=FirebaseDatabase.getInstance().getReference("conve").child(pkey).child(uid);
        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    conversation cn= ds.getValue(conversation.class);
                    list.add(cn);
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {  }
        });
    }



//adding to the DB

    private void verify() {
        String text=e1.getText().toString();
        if(text.isEmpty()){
            e1.setError("vous ne pouvez pas envoyez un message null");
            e1.requestFocus();
            return;}
        pushIt();
    }

    private void pushIt() {
        //apply the same rules as in the green app.
        String text=e1.getText().toString();
        e1.setText("");
        notifyOn();
        r=FirebaseDatabase.getInstance().getReference("conve").child(pkey).child(uid);
        String id=r.push().getKey();
        conversation c= new conversation("right","white",text,uid);
        r.child(id).setValue(c);

    }
    private void notifyOn() {
        if(page.equals("doc")) {
            r2 = FirebaseDatabase.getInstance().getReference("conseils");

        }else{
            r2=FirebaseDatabase.getInstance().getReference("client");}
        clients sq = new clients(username, profession, pkey, "Nouveau", "nul", uid);
        r2.child(pkey).child(uid).setValue(sq);
    }




    //back action
    private void back() {
        Intent intent =new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // back();
    }
}
