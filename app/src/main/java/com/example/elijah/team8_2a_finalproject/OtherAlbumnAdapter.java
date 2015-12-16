package com.example.elijah.team8_2a_finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OtherAlbumnAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    String albumnID;

    public OtherAlbumnAdapter(Context context, final String albumnID) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("albumn");
                query.whereNotEqualTo("userID", albumnID);
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
        title.setText(object.getString("user_name"));

        TextView date = (TextView) v.findViewById(R.id.row_user_albumn_date);
        date.setText(object.getString("name"));

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.row_albumn);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Pictures.class);
                intent.putExtra("albumnID", object.getObjectId().toString());
                context.startActivity(intent);
            }
        });

        return v;
    }

}