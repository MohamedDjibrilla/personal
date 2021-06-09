package com.rpi.fabapro.FragmentsAdapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.rpi.fabapro.Fragments.AllTransactionsCompte;
import com.rpi.fabapro.Fragments.CompteUtilisateur;
import com.rpi.fabapro.Fragments.CrediterMonCompte;

public class Compte extends FragmentPagerAdapter {
    public Compte(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                CompteUtilisateur fragment = new CompteUtilisateur();
                return fragment;
            case 1:
                CrediterMonCompte fragment1 = new CrediterMonCompte();
                return fragment1;
            case 2:
                AllTransactionsCompte fragment3 = new AllTransactionsCompte();
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Informations";
            case 1 :
                return "Credité";
            case 2 :
                return "Relevés";
            default:
                return null;
                //é
        }
    }
}
