package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;

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
    TextView itemCommentView;

    public DrugViewHolder(View itemView, final Context appContext) {
        super(itemView);
        this.itemNameView = (TextView) itemView.findViewById(R.id.item_name);
        this.itemDoseView = (TextView) itemView.findViewById(R.id.item_dose);
        this.itemCommentView = (TextView) itemView.findViewById(R.id.item_comment);
        this.itemImportantView = (TextView) itemView.findViewById(R.id.item_important);
        this.itemOptionsView = (TextView) itemView.findViewById(R.id.item_options);
    }
}

