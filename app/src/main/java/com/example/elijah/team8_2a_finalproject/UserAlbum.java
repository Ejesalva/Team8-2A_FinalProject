package com.example.elijah.team8_2a_finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class UserAlbum extends AppCompatActivity {

    ListView listView;
    List<ParseObject> dataList;
    UserAlbumAdapter adapter;
    ParseQueryAdapter<ParseObject> mainAdapter;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_album);

        user = ParseUser.getCurrentUser();

        listView = (ListView) findViewById(R.id.listView);

        mainAdapter = new ParseQueryAdapter<ParseObject>(UserAlbum.this, "albumn");
        mainAdapter.setTextKey("name");

        adapter = new UserAlbumAdapter(this, user.getObjectId().toString());

        listView.setAdapter(adapter);

        adapter.loadObjects();



    }

    public void refresh() {
        ParseQuery query = new ParseQuery("albumn");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    dataList = objects;
                }
            }
        });
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
        } else if(id ==R.id.add_album) {
            final EditText txtUrl = new EditText(this);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Enter the name of the albumn");
            alert.setTitle("Add Albumn");

            alert.setView(txtUrl);

            alert.setPositiveButton("Add Albumn", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String value = txtUrl.getText().toString();
                    if (value.toString().matches("")) {
                        Toast.makeText(UserAlbum.this, "Please enter in a name", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserAlbum.this, "Albumn added", Toast.LENGTH_SHORT).show();
                        ParseObject object = new ParseObject("albumn");
                        object.put("name", value.toString());
                        object.put("userID", user.getObjectId().toString());
                        object.put("user_name", user.getUsername().toString());
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                adapter.loadObjects();
                            }
                        });
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alert.show();

        } else if(id == R.id.otherAlbumn) {
            Intent intent = new Intent(UserAlbum.this, OtherAlbumn.class);
            startActivity(intent);
        } else if(id == R.id.chat) {
            Intent intent = new Intent(UserAlbum.this, Chat.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
