package com.stu.doantinhoc.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private boolean type;

    public User(String username, String password, boolean type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(JSONObject object) throws JSONException {
        this.username = object.getString("username");
        this.password = object.getString("pass");
        if (object.getInt("accounttype") == 0)
            this.type = true;
        else this.type = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
