package com.example.elijah.team8_2a_finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserAlbumAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    String albumnID;

    public UserAlbumAdapter(Context context, final String albumnID) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("albumn");
                query.whereEqualTo("userID", albumnID);
                query.orderByAscending("name");
                return query;
            }
        });
        this.context = context;
        this.albumnID = albumnID;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.row_user_albumn, null);
        }

        super.getItemView(object, v, parent);


        // Add the title view
        TextView title = (TextView) v.findViewById(R.id.row_user_albumn_title);
        title.setText(object.getString("name"));

        TextView date = (TextView) v.findViewById(R.id.row_user_albumn_date);
        date.setText(String.valueOf(object.getCreatedAt()));

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.row_albumn);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Pictures.class);
                intent.putExtra("albumnID", object.getObjectId().toString());

                context.startActivity(intent);
            }
        });


        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ProgressDialog progress;
                progress = ProgressDialog.show(context, "Deleting",
                        "Deleting Albumn", true);
                progress.show();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("albumn");
                query.whereEqualTo("userID", albumnID);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> messages, ParseException e) {
                        if (e == null) {
                            // remove all messages at once
                            try {
                                ParseObject.deleteAll(messages);
                                Toast.makeText(context, "Albumn deleted", Toast.LENGTH_SHORT).show();
                                loadObjects();
                            } catch (ParseException pe) {
                                pe.printStackTrace();
                            }
                        }
                    }
                });

                progress.dismiss();
                return false;
            }

        });

        return v;
    }

}