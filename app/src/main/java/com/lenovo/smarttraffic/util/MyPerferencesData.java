package com.lenovo.smarttraffic.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPerferencesData {

    private Context mcontext;

    public MyPerferencesData(Context mcontext) {
        this.mcontext = mcontext;
    }

    public boolean saveData(String ip){
       SharedPreferences sp =  mcontext.getSharedPreferences("ip",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("ip",ip);
        edit.commit();
        return true;
    }

}
