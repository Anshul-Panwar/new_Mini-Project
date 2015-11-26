package com.example.anshul.findmetrains1;
  import android.os.AsyncTask;
  import android.os.Bundle;
  import android.view.View;
  import android.webkit.WebView;
import android.app.*;
  import android.widget.Button;
  import android.widget.TextView;

  import org.json.JSONArray;
  import org.json.JSONException;
  import org.json.JSONObject;

  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.net.HttpURLConnection;
  import java.net.MalformedURLException;
  import java.net.URL;
  import java.util.ArrayList;
  import java.util.List;

public class RailwayActivity extends MapsActivity {
    TextView tv;String source,destination;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.railwaylayout);
        Bundle b = this.getIntent().getExtras();
        String array[] = b.getStringArray("key");
        source=array[0];
        destination=array[1];
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONtask().execute(" http://api.railwayapi.com/name_to_code/station/"+source+"/apikey/btxhj9442/");
                new JSONtask().execute(" http://api.railwayapi.com/name_to_code/station/"+destination+"/apikey/btxhj9442/");
            }
        });


    }


    public class JSONtask extends AsyncTask<String, String, String> {

       // ArrayList<RailwayActivity> traincodelist= new ArrayList<>();
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                 String finalJson= buffer.toString();

                JSONObject parentobject= new JSONObject(finalJson);
                JSONArray parentarray= new JSONArray("stations");
                JSONObject finalobject=parentarray.getJSONObject(0);
                String fullname=finalobject.getString("fullname");
                String code=finalobject.getString("code");


              return fullname +" - "+ code;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }

    }

}
