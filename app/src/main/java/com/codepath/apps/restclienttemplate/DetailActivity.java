package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class DetailActivity extends AppCompatActivity {
    ImageView image;
    TextView name;
    TextView userName;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        userName=findViewById(R.id.userName);
        description=findViewById(R.id.description);

        Tweet tweet=Parcels.unwrap(getIntent().getParcelableExtra("tweets"));

        name.setText(tweet.getUser().getName());
        userName.setText(tweet.getUser().getScreenName());
        description.setText(tweet.getBody());

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .transform(new RoundedCorners(50))
                .into(image);


    }
}