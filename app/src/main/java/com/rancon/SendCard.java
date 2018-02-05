package com.rancon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SendCard extends AppCompatActivity {
    RecyclerView friendRecycler;
    List<String> friendNameList = new ArrayList<>();
    List<String> friendIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_card);
        friendRecycler = (RecyclerView) findViewById(R.id.friend_recycler);
        friendRecycler.setLayoutManager(new LinearLayoutManager(this));
        DwnFriendList dwn = new DwnFriendList();
        dwn.execute();

    }

    public class friendAdapter extends RecyclerView.Adapter<friendHolder> {
        LayoutInflater lf;
        List<String> nameList;
        List<String> idList;

        public friendAdapter(Context c, List<String> nameList, List<String> idList) {
            this.lf = LayoutInflater.from(c);
            this.nameList = nameList;
            this.idList = idList;
        }

        @Override
        public friendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = lf.inflate(R.layout.card_element, parent, false);
            friendHolder fiendHolder = new friendHolder(v);
            return fiendHolder;
        }

        @Override
        public void onBindViewHolder(final friendHolder holder, final int position) {
            holder.tV.setText(nameList.get(position));
            @SuppressLint("StaticFieldLeak")
            AsyncTask dwmPic = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        URL imgEnc = new URL("http://graph.facebook.com/" + idList.get(position) + "/picture?type=small");
                        Bitmap picture = BitmapFactory.decodeStream(imgEnc.openConnection().getInputStream());
                        holder.iV.setImageBitmap(picture);
                    } catch (Exception e) {

                    }
                    return null;
                }
            };
            dwmPic.execute();

        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    public class friendHolder extends RecyclerView.ViewHolder {
        TextView tV;
        ImageView iV;

        public friendHolder(View itemView) {
            super(itemView);
            tV = (TextView) itemView.findViewById(R.id.friend_name);
            iV = (ImageView) itemView.findViewById(R.id.profile_pic);
        }
    }

    private class DwnFriendList extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            String uId = AccessToken.getCurrentAccessToken().getUserId();
            final GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/"+uId+"/friends",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            Log.e("result", response.getJSONObject().toString());
                            try {
                                JSONArray data = response.getJSONObject().getJSONArray("name");
                                for (int i = 0 ; i < data.length() ; i++) {
                                    JSONObject friendDetail = data.getJSONObject(i);
                                    String name = friendDetail.getString("name");
                                    String id = friendDetail.getString("id");
                                    friendNameList.add(name);
                                    friendIdList.add(id);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            request.executeAsync();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

    }

}
