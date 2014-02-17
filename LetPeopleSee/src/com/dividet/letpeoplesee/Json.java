package com.dividet.letpeoplesee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class Json extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json);
	ArrayList<String[]> list = getJson();
	TextView text =(TextView)findViewById(R.id.textjson);
	for(String[] ev : list)
		text.append(ev[0]+" "+ev[1]+" "+ev[2]+" "+ev[3]+" "+ev[4]+" "+ev[5]+"\n");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
		
		
	}
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public String connect()
	{
		String result = "";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

	    HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet("http://licenta.dividet.com/evenimente.php"); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httpget);
	        // Examine the response status
	        //Log.i("Info",response.getStatusLine().toString());  Comes back with HTTP/1.1 200 OK

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            result= convertStreamToString(instream);
	            instream.close();
	        }


	    } catch (Exception e) {
	        Log.e("Error",e.toString());
	    }
	    
	    return "{ \"evenimente\" :"+result+"}";
	}
	
	 private static String convertStreamToString(InputStream is) {
		    /*
		     * To convert the InputStream to String we use the BufferedReader.readLine()
		     * method. We iterate until the BufferedReader return null which means
		     * there's no more data to read. Each line will appended to a StringBuilder
		     * and returned as String.
		     */
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    try {
		        while ((line = reader.readLine()) != null) {
		            sb.append(line + "\n");
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            is.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    return sb.toString();
		}
	 
	 public ArrayList<String[]> getJson(){
			String jText = connect();
			ArrayList<String[]> list = new ArrayList<String[]>();
			JSONObject jObj;
			JSONArray jArray;
		
	     // try parse the string to a JSON object
	     try {
	 
	    	 // add 
	         jObj = new JSONObject(jText);  
	         jArray = jObj.getJSONArray("evenimente");
	         for(int i=0; i<jArray.length(); i++)
	        	 list.add(new String[]{jArray.getJSONObject(i).getString("idEvenimente"),
	        			 			   jArray.getJSONObject(i).getString("idUser"),
	        						   jArray.getJSONObject(i).getString("lat"),
	        						   jArray.getJSONObject(i).getString("lon"),
	        						   jArray.getJSONObject(i).getString("poza"),
	        						   jArray.getJSONObject(i).getString("coment")});
	    
	              
	    	 
	     } catch (JSONException e) {}
	     
	     return list;
	 }
	 
	 
	 

}
