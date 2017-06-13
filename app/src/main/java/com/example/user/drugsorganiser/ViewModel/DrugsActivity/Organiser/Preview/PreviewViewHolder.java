package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;

/**
 * Created by Gosia on 31.05.2017.
 */

public class PreviewViewHolder extends RecyclerView.ViewHolder {

    TextView itemDateView;

    public PreviewViewHolder(View itemView, final Context appContext) {
        super(itemView);
        this.itemDateView = (TextView) itemView.findViewById(R.id.item_date);
    }
}
