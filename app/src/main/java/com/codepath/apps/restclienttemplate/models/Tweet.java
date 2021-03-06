package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public long id;
    public User user;
    public String mediaUrl;
    public int favoriteCount;
    public int retweetCount;


    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.favoriteCount = jsonObject.getInt("favorite_count");
        tweet.retweetCount = jsonObject.getInt("retweet_count");

        if (jsonObject.has("retweeted_status")) {
            JSONObject retweetedStatus = jsonObject.getJSONObject("retweeted_status");
            if (retweetedStatus != null) {
                if (retweetedStatus.has("favorite_count")) {
                    tweet.favoriteCount = retweetedStatus.getInt("favorite_count");
                }
            }
        }
        Log.i("Tweet", "fromJson: " + jsonObject.getJSONObject("entities").toString());
        if (jsonObject.getJSONObject("entities").has("media")) {
            tweet.mediaUrl = jsonObject.getJSONObject("entities")
                    .getJSONArray("media")
                    .getJSONObject(0)
                    .getString("media_url_https");
        }
        else {
            tweet.mediaUrl = "";
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
