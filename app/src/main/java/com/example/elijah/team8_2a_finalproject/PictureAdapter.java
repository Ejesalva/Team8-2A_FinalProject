package com.example.elijah.team8_2a_finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Family on 12/14/2015.
 */
public class PictureAdapter extends ParseQueryAdapter<ParseObject> {

    Context context;
    String ID;

    public PictureAdapter(Context context, final String albumnID) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("pictures");
                query.whereEqualTo("pictureID", albumnID);
                return query;
            }
        });
        this.context = context;
        this.ID = albumnID;
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.picture_layout, null);
        }
        super.getItemView(object, v, parent);

        ImageView image = (ImageView) v.findViewById(R.id.picture_imageView);

        String url = object.getParseFile("ImageFile").getUrl();
        Picasso.with(context).load(url).resize(300,300).into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });
                String hold = object.getParseFile("ImageFile").getUrl();
                Uri uri = Uri.parse(hold);
                ImageView imageView = new ImageView(context);
                Picasso.with(context).load(uri).into(imageView);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();
            }
        });



        return v;
    }

}