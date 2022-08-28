package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    @Query("SELECT Tweet.body As tweet_body, Tweet.createAt As tweet_createAt, Tweet.id As tweet_id,Tweet.favorite_count As tweet_favorite_count,Tweet.retweet_count As tweet_retweet_count" +
            ",Tweet.favorites_count As tweet_favorites_count,Tweet.retweeted As tweet_retweeted,Tweet.favorited As tweet_favorited,User.*,Media.*" +
            " FROM Tweet,Media INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createAt DESC LIMIT 10")
    List<TweetWithUser> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User...users);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Media...medias);
}
