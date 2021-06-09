package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rpi.fabapro.FragmentsAdapters.Compte;
import com.rpi.fabapro.R;

public class CompteCredit extends AppCompatActivity {
    private Compte compte;
    private ViewPager mViewPager;
    private TabLayout tabLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_credit);
        compte = new Compte(getSupportFragmentManager());

        mViewPager = findViewById(R.id.pager1);
        mViewPager.setAdapter(compte);

        tabLayout=findViewById(R.id.tab1);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // back();
    }

    private void back() {
        Intent intent= new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
