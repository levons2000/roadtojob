package com.example.student.gotojobapplication.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.student.gotojobapplication.utils.DataProvider;
import com.example.student.gotojobapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class InfoActivity extends AppCompatActivity {

    private CircleImageView infoImage;
    private TextView infoName;
    private TextView infoPhone;
    private TextView infoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        infoImage = findViewById(R.id.info_image);
        infoName = findViewById(R.id.info_name);
        infoPhone = findViewById(R.id.info_phone);
        infoEmail = findViewById(R.id.info_email);
        setInfo();
    }

    private void setInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Picasso.get().load(DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY)).getPicture().getMedium()).into(infoImage);
            infoName.setText(DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY)).getName().getFirst());
            infoPhone.setText(DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY)).getPhone());
            infoEmail.setText(DataProvider.list.get(Objects.requireNonNull(getIntent().getExtras()).getInt(DataProvider.POSITION_KEY)).getEmail());
        }
    }
}
