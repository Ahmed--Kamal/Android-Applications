package com.ahmed.fast_search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private String[] searchList = {"Select", "Google", "Bing", "DuckDuckGo", "YouTube", "Dailymotion",
            "facebook", "twitter", "Wikipedia"};
    private int[] images = {R.drawable.down, R.drawable.google, R.drawable.bing, R.drawable.duckduckgo, R.drawable.youtube,
    R.drawable.dailymotion, R.drawable.facebook, R.drawable.twitter, R.drawable.wiki};
    private Button submitBtn,clearBtn;
    private EditText searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ArrayAdapter<String> searchListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, searchList);
        spinner = (Spinner)findViewById(R.id.searchOptions);
        spinner.setPrompt("Select One");
        spinner.setAdapter(new SpinnerAdapter(MainActivity.this, R.layout.row, searchList));

        submitBtn = (Button)findViewById(R.id.submitBtn);
        clearBtn = (Button)findViewById(R.id.clearBtn);
        searchText = (EditText)findViewById(R.id.searchText);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4846410650053023~5573899394");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchLine = searchText.getText().toString();
                if(searchLine.length() == 0) {
                    Toast.makeText(getBaseContext(), "Enter Text to search for", Toast.LENGTH_LONG).show();
                    return;
                }
                String selectedItem = spinner.getSelectedItem().toString();
                String url="", line="";
                try{
                    line = URLEncoder.encode(searchLine, "utf-8");}
                catch (Exception e){}
                if(selectedItem.equals("Google")) {
                    url = "http://www.google.com/search?q=" + line;
                }
                else if(selectedItem.equals("Bing")) {
                    url = "http://www.bing.com/search?q=" + line;
                }
                else if(selectedItem.equals("Select")) {
                    Toast.makeText(getBaseContext(), "Select a search engine", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(selectedItem.equals("facebook")){
                    url = "https://m.facebook.com/search/?search=people&search_source=search_bar&query=" + line;
                }
                else if(selectedItem.equals("twitter")){
                    url = "https://twitter.com/search?q=" + line;
                }
                else if(selectedItem.equals("YouTube")){
                    url = "http://youtube.com/results?q=" + line;
                }
                else if(selectedItem.equals("Dailymotion")){
                    url = "http://www.dailymotion.com/relevance/search/" + line;
                }
                else if(selectedItem.equals("DuckDuckGo"))
                {
                    url = "https://duckduckgo.com/?q=" + line;
                }
                else if(selectedItem.equals("Wikipedia"))
                {
                    url = "https://en.wikipedia.org/wiki/Special:Search/" + line;
                }
                Intent openBrowser = new Intent (Intent.ACTION_VIEW);
                openBrowser.setData(Uri.parse(url));
                startActivity(openBrowser);
            }
        });
    }
    public class SpinnerAdapter extends ArrayAdapter<String> {

        public SpinnerAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.searchSite);
            label.setText(searchList[position]);

            ImageView icon=(ImageView)row.findViewById(R.id.image);
            icon.setImageResource(images[position]);

            return row;
        }
    }
}
