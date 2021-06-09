package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.termsrecycler;

import java.util.ArrayList;

public class Terms extends AppCompatActivity {
    private RecyclerView r1;
    private ArrayList<String> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        r1=findViewById(R.id.recyclerterms);
initregisters();
        final termsrecycler adapter = new termsrecycler( list,this);
        r1.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        r1.setLayoutManager(layoutManager);
    }

    private void initregisters() {
        list.add("Bienvenue");
        list.add("Terms");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  back();
    }

    private void back() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
