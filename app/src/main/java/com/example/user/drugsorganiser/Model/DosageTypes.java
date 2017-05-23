package com.example.user.drugsorganiser.Model;

import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gosia on 10.05.2017.
 */

public class DosageTypes {
    private List<String> list=new ArrayList<String>();
    private String[] arr;
    public DosageTypes(View ctx){
        arr = ctx.getResources().getStringArray(R.array.dosage_array);
        for(String el : arr){
            list.add(el);
        }
    }

    public String[] getArr() {
        return arr;
    }

    public int getPosition(String dosageType){
        if(list.contains(dosageType))
            return list.indexOf(dosageType);
        else{
            Log.i("DosageType", "returned index:"+(list.size()-1));
            return  getPositionOfOther();
        }

    }
    public int getPositionOfOther(){
        return  list.size()-1;
    }
    public String getOtherString(){
        return  list.get(list.size()-1);
    }

}
