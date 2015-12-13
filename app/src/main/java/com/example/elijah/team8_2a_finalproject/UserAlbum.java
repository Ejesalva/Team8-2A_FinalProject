package com.example.elijah.team8_2a_finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class UserAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            Intent intent = new Intent(UserAlbum.this, UserProfile.class);
            startActivity(intent);

            return true;
        } else if(id == R.id.logout) {
            ParseUser.logOut();
            Intent logoutIntent = new Intent(UserAlbum.this, Login.class);
            Toast.makeText(UserAlbum.this, "Logout successful!", Toast.LENGTH_SHORT).show();
            startActivity(logoutIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
