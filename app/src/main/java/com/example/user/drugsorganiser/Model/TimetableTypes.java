package com.example.user.drugsorganiser.Model;

import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gosia on 10.05.2017.
 */

public class TimetableTypes {
    private List<String> list=new ArrayList<String>();
    private String[] arr;
    public  TimetableTypes(View ctx){
        arr = ctx.getResources().getStringArray(R.array.timetable_array);
        for(String el : arr){
            list.add(el);
        }
    }

    public String[] getArr() {
        return arr;
    }

    public int getPosition(String timetableType){
        if(list.contains(timetableType))
            return list.indexOf(timetableType);
        else{
            Log.i("TimetableType", "returned index:"+(list.size()-1));
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
