package com.rancon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class SendCard extends AppCompatActivity {
    RecyclerView friendRecycler;
    List<String> friendNameList = new ArrayList<>();
    List<String> friendIdList = new ArrayList<>();
    SendButton sendButton;
    friendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_card);
        friendRecycler = (RecyclerView) findViewById(R.id.friend_recycler);
        friendRecycler.setLayoutManager(new LinearLayoutManager(this));
        friendAdapter = new friendAdapter(this);
        dwnFriendList();


        sendButton = (SendButton) findViewById(R.id.sendButton);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        sendButton.setShareContent(content);
        sendButton.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(SendCard.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SendCard.this, "Cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SendCard.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public class friendAdapter extends RecyclerView.Adapter<friendHolder> {
        LayoutInflater lf;

        public friendAdapter(Context c) {
            this.lf = LayoutInflater.from(c);
        }

        @Override
        public friendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = lf.inflate(R.layout.friends_element, parent, false);
            friendHolder fiendHolder = new friendHolder(v);
            return fiendHolder;
        }

        @Override
        public void onBindViewHolder(final friendHolder holder, final int position) {
            Log.e("TEST", "" + position);
            holder.tV.setText(friendNameList.get(position));
            holder.iV.setProfileId(friendIdList.get(position));
        }

        @Override
        public int getItemCount() {
            Log.e("TEST12321", "" + friendNameList.size());
            return friendNameList.size();
        }
    }

    public class friendHolder extends RecyclerView.ViewHolder {
        TextView tV;
        ProfilePictureView iV;

        public friendHolder(View itemView) {
            super(itemView);
            tV = (TextView) itemView.findViewById(R.id.friend_name);
            iV = (ProfilePictureView) itemView.findViewById(R.id.profile_pic);
        }
    }

    private void dwnFriendList() {

        String uId = AccessToken.getCurrentAccessToken().getUserId();
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + uId + "/friends",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.e("result", response.getJSONObject().toString());
                        try {
                            JSONArray data = response.getJSONObject().getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject aa = data.getJSONObject(i);
                                String name = aa.getString("name");
                                String id = aa.getString("id");
                                friendNameList.add(name);
                                friendIdList.add(id);
                                Log.e("TESTqq", name);
                                Log.e("TESTqq", id);
                            }
                            friendRecycler.setAdapter(friendAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();
    }


}
