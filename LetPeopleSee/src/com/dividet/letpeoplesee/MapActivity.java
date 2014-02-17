package com.dividet.letpeoplesee;



import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.webkit.WebView;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapActivity extends android.support.v4.app.FragmentActivity
implements OnMapClickListener, OnMarkerClickListener{


	private GoogleMap mMap;
	private LocationClient mLocationClient;
	double lat,lon;
	Marker marker;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.harta);
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.harta))
             .getMap();
    mMap.setMyLocationEnabled(true);
    mMap.setOnMapClickListener(this);

    if (savedInstanceState == null) {
        CameraUpdate center=
            CameraUpdateFactory.newLatLng(new LatLng(44.4, 26.1));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
      }
  
  }
 
  
@Override
public void onMapClick(LatLng position) {
	
//	Location location = mLocationClient.getLastLocation();
//  LatLng mylocation = new LatLng( location.getLatitude(), location.getLongitude());
//  CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 14);
//  mMap.animateCamera(cameraUpdate);
	  Json evenimente = new Json();
	  ArrayList<String[]> list = evenimente.getJson();
	  for(String[] ev : list)
	  {
		LatLng pozitie = new LatLng(Double.parseDouble(ev[2]),Double.parseDouble(ev[3]));
		marker =  mMap.addMarker(new MarkerOptions().title(ev[4]).snippet(ev[5]).position(pozitie).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon)));
	   }
} 
public void events()
{	  lat = mMap.getMyLocation().getLatitude();
	  lon = mMap.getMyLocation().getLongitude();
	 }
public void dialog(String picture, String title, String informatii )
{	
}

@Override
public boolean onMarkerClick(Marker marker) {
	// TODO Auto-generated method stub
	  if (this.marker.equals(marker))
	  {
	  		WebView webView = new WebView(this);
		    webView.loadUrl("http://www.google.com/");
		    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		    dialog.setView(webView);
		    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        }
		    });
		    dialog.show();}
	return false;
}
}
