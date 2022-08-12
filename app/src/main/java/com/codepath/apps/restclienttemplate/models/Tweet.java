package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public Tweet(){}

    public  String body;
    public long id;
    public  String createAt;
    public User user;

    public  static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet=new Tweet();
        tweet.body= jsonObject.getString("text");
        tweet.createAt= jsonObject.getString("created_at");
        tweet.id= jsonObject.getLong("id");
        tweet.user=User.fromJson(jsonObject.getJSONObject("user")) ;

        return tweet;
    }
    public  static List<Tweet>fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets= new ArrayList<>();

        for(int i =0; i< jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));

        }
        return tweets;
    }

    public String getBody() {

        return body;
    }

    public long getId() {

        return id;
    }

    public String getCreateAt() {

        return createAt;
    }

    public User getUser() {

        return user;
    }
}
