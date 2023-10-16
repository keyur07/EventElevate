package com.example.eventelevate.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.example.eventelevate.R;

public class ProgressRound extends Dialog {

    @SuppressLint("ValidFragment")
    public ProgressRound(Activity context) {
        super(context);
        setContentView(R.layout.small_progress_bar);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setDimAmount(0.0f);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}