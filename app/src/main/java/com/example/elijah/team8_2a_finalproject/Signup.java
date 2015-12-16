package com.example.elijah.team8_2a_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Signup extends AppCompatActivity {

    static final int PICK_IMAGE = 1;

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText passwordReEnter;

    Switch isMale;
    ImageView profilePicture;

    Button backToLogin;
    Button signup;
    Button selectImage;

    Bitmap bitmap;
    ParseFile file;

    Boolean setPicture = true;
    String gender = "Male";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.signup_username);
        firstName = (EditText) findViewById(R.id.signup_firstName);
        lastName = (EditText) findViewById(R.id.signup_lastName);
        password = (EditText) findViewById(R.id.signup_password);
        passwordReEnter = (EditText) findViewById(R.id.signup_passwordAgain);

        isMale = (Switch) findViewById(R.id.signup_isMale);
        profilePicture = (ImageView) findViewById(R.id.signup_imageView);

        backToLogin = (Button) findViewById(R.id.signup_back_button);
        signup = (Button) findViewById(R.id.signup_signup_Button);
        selectImage = (Button) findViewById(R.id.signup_selectPicture_button);

        isMale.setChecked(true);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().matches("") || firstName.getText().toString().matches("") ||
                        lastName.getText().toString().matches("") || password.getText().toString().matches("") ||
                        passwordReEnter.getText().toString().matches("") || file.toString().matches("")) {
                    Toast.makeText(Signup.this, "Please fill in all the fields", Toast.LENGTH_SHORT);
                } else {
                    if (!password.getText().toString().matches(passwordReEnter.getText().toString())) {
                        Toast.makeText(Signup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    } else {
                        if (setPicture) {
                            ParseUser user = new ParseUser();
                            user.setUsername(username.getText().toString());
                            user.setPassword(password.getText().toString());
                            user.put("FName", firstName.getText().toString());
                            user.put("LName", lastName.getText().toString());
                            if (isMale.isChecked()) {
                                gender = isMale.getTextOn().toString();
                            } else {
                                gender = isMale.getTextOff().toString();
                            }
                            user.put("gender", gender);
                            //user.put("gender", gender);

                            //Toast.makeText(Signup.this, gender.toString(), Toast.LENGTH_SHORT).show();

                            Log.d("demo file", file.toString());
                            //Toast.makeText(Signup.this, file.toString(), Toast.LENGTH_SHORT).show();
                            user.put("profileImage", file);


                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(Signup.this, UserAlbum.class);
                                        Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.d("demo error", e.toString());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Signup.this, "Please select a picture", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE);
            }
        });



        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                file = new ParseFile("androidImage",(byte[]) image);

                file.saveInBackground();

                Picasso.with(Signup.this).load(uri).resize(300,300).into(profilePicture);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
