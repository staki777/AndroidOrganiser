package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;

/**
 * Created by user on 2017-05-24.
 */

public class TermViewHolder extends RecyclerView.ViewHolder {
    TextView itemDateView;
    TextView itemTimeView;
    TextView itemOptionsView;

    public TermViewHolder(View itemView, final Context appContext) {
        super(itemView);
        this.itemDateView = (TextView) itemView.findViewById(R.id.item_date);
        this.itemTimeView = (TextView) itemView.findViewById(R.id.item_time);
        this.itemOptionsView = (TextView) itemView.findViewById(R.id.item_options);
    }
}


