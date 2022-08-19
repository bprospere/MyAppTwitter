package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public Tweet(){}

    public Media media;
    public Url url;

    public  String body;
    public long id;
    public  String createAt;
    public User user;
    public String favorite_count;
    public String retweet_count;



    public  static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet=new Tweet();
        tweet.body= jsonObject.getString("text");
        tweet.createAt= jsonObject.getString("created_at");
        tweet.id= jsonObject.getLong("id");
        tweet.user=User.fromJson(jsonObject.getJSONObject("user")) ;
        tweet.media=Media.fromJson(jsonObject.getJSONObject("entities")) ;
//        tweet.url=Url.fromJson(jsonObject.getJSONObject("extended_entities"));
        tweet.favorite_count= jsonObject.getString("favorite_count");
        tweet.retweet_count= jsonObject.getString("retweet_count");


        return tweet;



    }




    public  static List<Tweet>fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets= new ArrayList<>();

        for(int i =0; i< jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));

        }
        return tweets;

    }



    public static String getFormattedTime(String createAt) {
        return TimeFormatter.getTimeDifference(createAt);
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

    public String getFavorite_count() {

        return favorite_count + "     Favorites";
    }

    public String getRetweet_count() {

        return   retweet_count + "      Retweets";
    }

    public Media getMedia() {
        return media;
    }

    public Url getUrl() {
        return url;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
    public static String getFormattedTime1(String createAt) {
        return TimeFormatter.getTimeStamp(createAt);
    }
}
