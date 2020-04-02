package com.example.kaisersentinel.Tableview.Model.Holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.kaisersentinel.PopUp.TableViewModel;
import com.example.kaisersentinel.R;

public class GenderCellViewHolder extends MoodCellViewHolder {

    public GenderCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data) {
        int gender = (int) data;
        int genderDrawable = gender == TableViewModel.BOY ? R.drawable.ic_male : R.drawable.ic_female;

        cell_image.setImageResource(genderDrawable);
    }
}
