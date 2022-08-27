package com.codepath.apps.restclienttemplate;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.Parcels;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    ImageView image;
    TextView name;
    TextView userName;
    TextView description;
    TextView date2;
    TextView tvFavorite;
    TextView tvRetweet;
    ImageView image1;
    TextView ic_com1;
    TextView ic_rep;
    TextView ic_stars;
    TextView star_color;
    TextView  ic_rep_change;
    TextView ic_sha;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar =findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);
        getSupportActionBar().setIcon(R.drawable.logo_twitter);
        getSupportActionBar().setTitle("  Tweet");



        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        userName=findViewById(R.id.userName);
        description=findViewById(R.id.description);
        date2 = findViewById(R.id.date2);
        tvFavorite=findViewById(R.id.tvFavorite);
        tvRetweet=findViewById(R.id.tvRetweet);
        image1=findViewById(R.id.image1);
        ic_com1=findViewById(R.id.ic_com1);
        ic_rep=findViewById(R.id.ic_rep);
        ic_stars=findViewById(R.id.ic_stars);
        star_color=findViewById(R.id.star_color);
        ic_rep_change=findViewById(R.id.ic_rep_change);
        ic_sha=findViewById(R.id.ic_sha);







        Tweet tweet=Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        name.setText(tweet.getUser().getName());
        userName.setText(tweet.getUser().getScreenName());
        description.setText(tweet.getBody());
        date2.setText(Tweet.getFormattedTime1(tweet.createAt));
        tvFavorite.setText(tweet.getFavorites_count());
        tvRetweet.setText(tweet.getRetweet_count());

        ic_com1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog2();
            }

            private void showEditDialog2() {
                FragmentManager fm = getSupportFragmentManager();
                ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Some Title");
                Bundle bundle=new Bundle();
                bundle.putParcelable("userInfo",Parcels.wrap(TimelineActivity.user));
                composeDialogFragment.setArguments(bundle);
                composeDialogFragment.show(fm, "activity_comment_frament");
            }
        });

        ic_sha.setOnClickListener((View v) -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, tweet.getUrl());
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
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





        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .transform(new CircleCrop())
                .into(image);


        if(!tweet.media.getMediaUrl().isEmpty()) {
            image1.setVisibility(VISIBLE);
            Glide.with(this)
                    .load(tweet.media.getMediaUrl())
                    .transform(new RoundedCorners(50))
                    .into(image1);
        }

        else {
            image1.setVisibility(GONE);
        }
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


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int back = R.id.homeAsUp;
        Intent intent = new Intent(DetailActivity.this,TimelineActivity.class);
        intent.putExtra("back", Parcels.wrap(back));
        DetailActivity.this.startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
   
}