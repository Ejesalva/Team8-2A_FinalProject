package com.example.elijah.team8_2a_finalproject;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Family on 12/16/2015.
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }
}
