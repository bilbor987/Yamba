package com.example.yamba;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

//import androidx.fragment.app.Fragment;

public class StatusFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "StatusFragment";
    private EditText editStatus;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        editStatus = view.findViewById(R.id.editStatus);
        Button buttonTweet = view.findViewById(R.id.buttonTweet);
        textCount = view.findViewById(R.id.textCount);

        buttonTweet.setOnClickListener(this);

        defaultTextColor = textCount.getTextColors().getDefaultColor();
        editStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s){
                int count = 140 - editStatus.length();
                textCount.setText(Integer.toString(count));
                textCount.setTextColor(Color.GREEN);
                if (count < 10) {
                    textCount.setTextColor(Color.RED);
                } else {
                    textCount.setTextColor(defaultTextColor);
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked with status: " + status);

        new PostTask().execute(status);
    }
    private final class PostTask extends AsyncTask<String, Void, String> {

        SharedPreferences prefs;

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            String username = prefs.getString("username", "");
            String password = prefs.getString("password", "");

            // Check that username and password are not empty
            // If empty, Toast a message to set login info and bounce to
            // SettingActivity
            // Hint: TextUtils.
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                getActivity().startActivity(
                        new Intent(getActivity(), SettingsActivity.class));
                return "Please update your username and password";
            }
            YambaClient cloud = new YambaClient("student", "password", "http://yamba.newcircle.com/api");
            try {
                cloud.postStatus(params[0]);
                return "Successfully posted";
            } catch (YambaClientException e) {
                e.printStackTrace();
                return "Failed to post to yamba service";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(StatusFragment.this.getActivity(), result, Toast.LENGTH_LONG).show();
        }
    }
}
