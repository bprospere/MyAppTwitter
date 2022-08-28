package com.codepath.apps.restclienttemplate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeDialogFragment extends DialogFragment {
    public  static final String TAG="ComposeDialogFragment";
    public  static final  int MAX_TWEET_LENGTH=140;
    EditText etFragment;
    Button btnFragment;
    Context context;
    TwitterClient client;
    ImageButton btnCancel1;
    ImageView tvProfileImage;
    TextView names;
    TextView  userNames;



    public ComposeDialogFragment() {

    }

    public static ComposeDialogFragment newInstance(String title) {

        ComposeDialogFragment frag = new ComposeDialogFragment();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_compose_fragment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFragment = (EditText) view.findViewById(R.id.etFragment);
        btnFragment = (Button) view.findViewById(R.id.btnFragment);
        btnCancel1=(ImageButton) view.findViewById(R.id.btnCancel1);
        tvProfileImage= (ImageView) view.findViewById(R.id.tvProfileImage);
        names= (TextView) view.findViewById(R.id.names);
        userNames= (TextView) view.findViewById(R.id.userNames);
        client=TwitterApp.getRestClient(getContext());

        String title = getArguments().getString("title", "Enter your text");

        getDialog().setTitle(title);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().setLayout(700,2000);

        Bundle bundle=getArguments();
        User user= Parcels.unwrap(bundle.getParcelable("userInfo"));


        names.setText(user.name);
        userNames.setText(user.screenName);


        Glide.with(this)
                .load(user.profileImageUrl)
                .transform(new CircleCrop())
                .into(tvProfileImage);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = pref.getString("username", "");
        if (!username.isEmpty()) {
            etFragment.setText(username);
        }

        btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent=etFragment.getText().toString();
                if(tweetContent.isEmpty()){

                    Toast.makeText(context,"Sorry your tweet cannot be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(tweetContent.length() >MAX_TWEET_LENGTH){
                    Toast.makeText(context,"Sorry your tweet is too long",Toast.LENGTH_LONG).show();
                    return;

                }

//                Toast.makeText(context,tweetContent,Toast.LENGTH_LONG).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");
                        try {
                            Tweet tweet=Tweet.fromJson(json.jsonObject);
                            Log.i(TAG," published tweet says : " + tweet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"onFailure to publish tweet",throwable);

                    }
                });
                dismiss();
            }
        });
    }


    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Do you want to save or delete this message");
                alertDialogBuilder.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Save();
                            }
                        });

        alertDialogBuilder.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void Save(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("username",etFragment.getText().toString());
        edit.commit();
        dismiss();
    }


}
