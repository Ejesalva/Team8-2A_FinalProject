package com.example.elijah.team8_2a_finalproject;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, "o6dVmdHNDKCJGydskHr0e8eu75blzkmxVMQhcNbh", "aZx2bRwpRv41NSljbib23Avs2DUQSNPISolZcrBt");

        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}