package com.rancon;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.widget.SendButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaishu on 4/2/18.
 */

public class FacebookApi {
    public FacebookApi() {

    }
    public void getFriendsList() {
        AccessToken aT = AccessToken.getCurrentAccessToken();


    }









/*

        final List<String> friendslist = new ArrayList<String>();
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/"+AccessToken.getCurrentAccessToken().getUserId()+"/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                *//* handle the result *//*
                Log.e("Friends List: 1", response.toString());
                *//*try {
                    JSONObject responseObject = response.getJSONObject();
                    JSONArray dataArray = responseObject.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String fbId = dataObject.getString("id");
                        String fbName = dataObject.getString("name");
                        Log.e("FbId", fbId);
                        Log.e("FbName", fbName);
                        friendslist.add(fbId);
                    }
                    Log.e("fbfriendList", friendslist.toString());
                    List<String> list = friendslist;
                    String friends = "";
                    if (list != null && list.size() > 0) {
                        friends = list.toString();
                        if (friends.contains("[")) {
                            friends = (friends.substring(1, friends.length() - 1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }*//*
            }
        }).executeAsync();
        return friendslist;*/

}
