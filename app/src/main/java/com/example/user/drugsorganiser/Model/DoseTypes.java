package com.example.user.drugsorganiser.Model;

import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-05-02.
 */

public class DoseTypes {
    private List<String> list=new ArrayList<String>();
    private String[] arr;
    public  DoseTypes(View ctx){
        arr = ctx.getResources().getStringArray(R.array.dose_types_array);
        for(String el : arr){
            list.add(el);
        }
    }

    public String[] getArr() {
        return arr;
    }

    public int getPosition(String doseType){
        if(list.contains(doseType))
            return list.indexOf(doseType);
        else{
            Log.i("DoseTypes", "returned index:"+(list.size()-1));
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
