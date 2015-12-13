package com.example.elijah.team8_2a_finalproject;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity {

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText passwordReEnter;

    Switch isMale;
    ImageView profilePicture;

    Button backToLogin;
    Button signup;

    Boolean setPicture = true;


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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().matches("") || firstName.getText().toString().matches("") ||
                        lastName.getText().toString().matches("") || password.getText().toString().matches("") ||
                        passwordReEnter.getText().toString().matches("")) {
                    Toast.makeText(Signup.this, "Please fill in all the fields", Toast.LENGTH_SHORT);
                }   else {
                    if(!password.getText().toString().matches(passwordReEnter.getText().toString())) {
                        Toast.makeText(Signup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    } else {
                        if(setPicture) {
                            ParseUser user = new ParseUser();
                            user.setUsername(username.getText().toString());
                            user.setPassword(password.getText().toString());
                            user.put("FName", firstName.getText().toString());
                            user.put("LName", lastName.getText().toString());

                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Intent intent = new Intent(Signup.this, UserAlbum.class);
                                    Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(Signup.this, "Please select a picture", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
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

}
