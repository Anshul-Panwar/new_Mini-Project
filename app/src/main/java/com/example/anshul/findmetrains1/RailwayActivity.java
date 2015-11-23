package com.example.anshul.findmetrains1;
  import android.os.Bundle;
import android.webkit.WebView;
import android.app.*;
  import android.widget.TextView;

  import java.io.BufferedReader;
  import java.io.IOException;
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.net.HttpURLConnection;
  import java.net.MalformedURLException;
  import java.net.URL;

public class RailwayActivity extends MapsActivity  {
TextView tv;
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.railwaylayout);
           Bundle b=this.getIntent().getExtras();
           String array[]=b.getStringArray("key");
           HttpURLConnection connection = null;
           BufferedReader reader = null;
           try {
               URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=\"+array[0]+\"&destination=\"+array[1]+\"&transit_mode=train&key=");
               connection = (HttpURLConnection) url.openConnection();
               connection.connect();

               InputStream stream = connection.getInputStream();
               reader = new BufferedReader(new InputStreamReader(stream));
               StringBuffer buffer = new StringBuffer();
               String line = "";
               while ((line = reader.readLine()) != null) {
                   buffer.append(line);
               }

               tv.setText(buffer.toString());
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           finally {
               if(connection!=null) {
                   connection.disconnect();
               }
               try {
                   if(reader!=null) {
                       reader.close();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }


       }
   }