package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
@Parcel
public class Tweet {

    public Tweet(){}
    @Ignore
    public Media media;
    @PrimaryKey
    @ColumnInfo
    public long id;
    @ColumnInfo
    public  String body;
    @ColumnInfo
    public  String createAt;
    @Ignore
    public User user;
    @ColumnInfo
    public long userId;
    @ColumnInfo
    public int favorite_count;
    @ColumnInfo
    public int retweet_count;
    @ColumnInfo
    public String favorites_count;
    @ColumnInfo
    public boolean retweeted;
    @ColumnInfo
    public boolean favorited;







    public  static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet=new Tweet();
        tweet.body= jsonObject.getString("text");
        tweet.createAt= jsonObject.getString("created_at");
        tweet.id= jsonObject.getLong("id");
        User user=User.fromJson(jsonObject.getJSONObject("user")) ;
        tweet.userId= user.id;
        tweet.user=user;
        tweet.media=Media.fromJson(jsonObject.getJSONObject("entities")) ;
        tweet.favorite_count= jsonObject.getInt("retweet_count");
        tweet.retweet_count= jsonObject.getInt("retweet_count");
        tweet.favorites_count= jsonObject.getString("favorite_count");
        tweet.retweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");



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
        return String.valueOf(favorite_count);
    }

    public String getRetweet_count() {
        return retweet_count + "  Retweets";
    }

    public Media getMedia() {
        return media;
    }



    public boolean isRetweeted() {
        return retweeted;
    }

    public boolean isFavorited() {
        return favorited;
    }


    public String getFavorites_count() {
        return favorites_count + "  Likes";
    }



    public void setMedia(Media media) {
        this.media = media;
    }
    public static String getFormattedTime1(String createAt) {
        return TimeFormatter.getTimeStamp(createAt);
    }


    public String getUrl() {
        return "https://twitter.com/" + user.screenName + "/status/" + id;
    }
}
