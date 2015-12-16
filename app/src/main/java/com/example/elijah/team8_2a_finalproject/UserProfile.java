package com.example.elijah.team8_2a_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserProfile extends AppCompatActivity {

    Button backButton;
    Button editButton;

    TextView username;
    TextView gender;
    TextView firstLastName;

    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backButton = (Button) findViewById(R.id.profile_done);
        editButton = (Button) findViewById(R.id.profile_edit);

        username = (TextView) findViewById(R.id.profile_name);
        gender = (TextView) findViewById(R.id.profile_gender_Edit);
        firstLastName = (TextView) findViewById(R.id.profile_name_editable);

        profilePicture = (ImageView) findViewById(R.id.profile_imageView);

        ParseUser user = ParseUser.getCurrentUser();




        gender.setText(user.get("gender").toString());
        username.setText(user.getUsername());
        firstLastName.setText(user.get("FName").toString() + " " + user.get("LName").toString());
        String url = user.getParseFile("profileImage").getUrl();
        Picasso.with(UserProfile.this).load(url).resize(300,300).into(profilePicture);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
