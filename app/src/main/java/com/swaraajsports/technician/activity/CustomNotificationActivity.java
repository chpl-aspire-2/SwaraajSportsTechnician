package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.fragment.ImageViewFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class CustomNotificationActivity extends BaseActivityClass {


    @BindView(R.id.imgUrl)
    ImageView imgUrl;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.card)
    MaterialCardView card;
    @BindView(R.id.cardImage)
    CardView cardImage;

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_custom_notification;//your layout
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setSupportActionBar(toolBar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("img") != null && BasicFunctions.isValidUrl(getIntent().getStringExtra("img"))) {
                cardImage.setVisibility(View.VISIBLE);
                BasicFunctions.displayImageBG(this, imgUrl, getIntent().getStringExtra("img"));

                imgUrl.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        ImageViewFragment imageViewFragment = new ImageViewFragment(getIntent().getStringExtra("img"));
                        imageViewFragment.show(getSupportFragmentManager(), "dialogPop");

                    }
                });

            } else {
                cardImage.setVisibility(View.GONE);
            }

            tvTitle.setText(getIntent().getStringExtra("title"));
            tvDesc.setText(getIntent().getStringExtra("desc"));
            tvTime.setText(getIntent().getStringExtra("time"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}