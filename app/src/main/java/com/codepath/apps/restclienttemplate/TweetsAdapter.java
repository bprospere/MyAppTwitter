package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Media;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    TwitterClient client;
    public static final  String TAG="TweetsAdapter";
    

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        TweetsAdapter.ViewHolder viewHolder = new TweetsAdapter.ViewHolder(view,listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet=tweets.get(position);

        holder.bind(tweet);



    }

    @Override
    public int getItemCount() {

        return tweets.size();
    }


    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
    void OnItemClick(View itemView, int Position);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener=listener;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvSreenName;
        TextView usersName;
        TextView date;
        ImageView profileImage;
        TextView ic_com;
        TextView ic_rep;
        TextView  ic_stars;
        TextView star_color;
        TextView  ic_rep_change;
        TextView ic_sha;
        

        public ViewHolder(@NonNull View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            ivProfileImage=itemView.findViewById(R.id.ivProfileImage);
            tvBody=itemView.findViewById(R.id.tvBody);
            tvSreenName=itemView.findViewById(R.id.tvScreenName);
            usersName=itemView.findViewById(R.id.usersName);
            date=itemView.findViewById(R.id.date);
            profileImage=itemView.findViewById(R.id.profileImage);
            ic_com=itemView.findViewById(R.id.ic_com);
            ic_rep=itemView.findViewById(R.id.ic_rep);
            ic_stars=itemView.findViewById(R.id.ic_stars);
            star_color=itemView.findViewById(R.id.star_color);
            ic_rep_change=itemView.findViewById(R.id.ic_rep_change);
            ic_sha=itemView.findViewById(R.id.ic_sha);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnItemClick(itemView, getAdapterPosition());
                }
            });


        }


        public void bind(Tweet tweet) {
            tvBody.setText(tweet.getBody());
            tvSreenName.setText(tweet.user.getName());
            usersName.setText(tweet.user.getScreenName());
            date.setText(Tweet.getFormattedTime(tweet.getCreateAt()));

            ic_com.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEditDialog1();
                }

                private void showEditDialog1() {
                    FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Some Title");
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("userInfo",Parcels.wrap(TimelineActivity.user));
                    composeDialogFragment.setArguments(bundle);
                    composeDialogFragment.show(fm, "activity_comment_frament");
                }
            });







            if(tweet.favorited) {
                ic_stars.setVisibility(View.INVISIBLE);
                star_color.setVisibility(View.VISIBLE);

            }
            else {
                ic_stars.setVisibility(View.VISIBLE);
                star_color.setVisibility(View.INVISIBLE);
            }


            if(tweet.favorited) {
                ic_rep.setVisibility(View.INVISIBLE);
                ic_rep_change.setVisibility(View.VISIBLE);

            }
            else {
                ic_rep.setVisibility(View.VISIBLE);
                ic_rep_change.setVisibility(View.INVISIBLE);
            }


            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .transform(new CircleCrop())
                    .into(ivProfileImage);

            if (!tweet.media.getMediaUrl().isEmpty()) {
                profileImage.setVisibility(itemView.VISIBLE);
                Glide.with(context)
                        .load(tweet.media.getMediaUrl())
                        .transform(new RoundedCorners(50))
                        .into(profileImage);
            }
            else{
                profileImage.setVisibility(itemView.GONE);
            }


            ic_rep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.favorite_count++;
                    ic_rep.setVisibility(View.INVISIBLE);
                    ic_rep_change.setVisibility(View.VISIBLE);
                    ic_rep_change.setText(tweet.getFavorite_count());
                    tweet.favorited=true;
                }
            });



            ic_stars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.favorite_count++;
                    ic_stars.setVisibility(View.INVISIBLE);
                    star_color.setVisibility(View.VISIBLE);
                    star_color.setText(tweet.getFavorite_count());
                    tweet.favorited=true;
                }
            });



            star_color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.favorite_count--;
                    ic_stars.setVisibility(View.VISIBLE);
                    star_color.setVisibility(View.INVISIBLE);
                    ic_stars.setText(tweet.getFavorite_count());
                    tweet.favorited=false;
                }
            });
            ic_rep_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.favorite_count--;
                    ic_rep.setVisibility(View.VISIBLE);
                    ic_rep_change.setVisibility(View.INVISIBLE);
                    ic_rep.setText(tweet.getFavorite_count());
                    tweet.favorited=false;
                }
            });

            ic_sha.setOnClickListener((View v) -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, tweet.getUrl());
                context.startActivity(Intent.createChooser(shareIntent, "Share link using"));
            });
            }



    }

}













