package com.example.anshul.findmetrains1;
  import android.os.Bundle;
import android.webkit.WebView;
import android.app.*;

import java.io.IOException;

   public class RailwayActivity extends MapsActivity  {

       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           WebView webview = new WebView(this);
           setContentView(webview);
           Bundle b=this.getIntent().getExtras();
           String[] array=b.getStringArray("key");
           webview.loadUrl("https://maps.googleapis.com/maps/api/directions/json?origin=\"+array[0]+\"&destination=\"+array[1]+\"&transit_mode=train&key= \n");

       }
   }