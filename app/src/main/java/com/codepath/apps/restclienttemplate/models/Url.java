package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Url {


    public String displayUrl;



    public String getDisplayUrl() {

        return displayUrl;
    }

    public Url() {}
    public static Url fromJson(JSONObject jsonObject) throws JSONException {
        Url url = new Url();

        if(!jsonObject.has("url")){
            url.displayUrl = "";

        }
        else if(jsonObject.has("url")){
            JSONArray url_display = jsonObject.getJSONArray("url");
            url.displayUrl = url_display.getJSONObject(0).getString("display_url");
        }

        return url;
    }
}
