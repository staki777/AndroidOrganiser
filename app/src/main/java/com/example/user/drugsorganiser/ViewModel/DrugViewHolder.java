package com.example.user.drugsorganiser.ViewModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;

/**
 * Created by user on 2017-04-14.
 */

public class DrugViewHolder extends RecyclerView.ViewHolder {
    TextView itemNameView;
    TextView itemDoseView;
    TextView itemIntervalView;
    TextView itemImportantView;
    TextView itemOptionsView;

    public DrugViewHolder(View itemView, final Context appContext) {
        super(itemView);
        this.itemNameView = (TextView) itemView.findViewById(R.id.item_name);
        this.itemDoseView = (TextView) itemView.findViewById(R.id.item_dose);
        this.itemIntervalView = (TextView) itemView.findViewById(R.id.item_interval);
        this.itemImportantView = (TextView) itemView.findViewById(R.id.item_important);
        this.itemOptionsView = (TextView) itemView.findViewById(R.id.item_options);
    }
}

