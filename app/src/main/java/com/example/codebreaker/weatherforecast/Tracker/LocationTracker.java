package com.example.codebreaker.weatherforecast.Tracker;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class LocationTracker extends Service implements LocationListener{

	private final Context context;

	public Location location;
	
	public double latitude;
	public double longitude;

	protected LocationManager locationManager;


	boolean GPS_state = false;
	boolean NETWORK_state = false;
	boolean canFetchLocation = false;

	public LocationTracker(Context context) {
		this.context = context;
		getLocation();
	}
	
	public Location getLocation() {


		try {
			locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
			
			GPS_state = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			NETWORK_state = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!GPS_state && !NETWORK_state)
			{

				Toast.makeText(context,"Either enable GPS or switch off the flight mode to get the location",Toast.LENGTH_SHORT).show();
				
			} else {
				this.canFetchLocation = true;
				
				if (NETWORK_state) {
					
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							1000 * 60 * 1,
							5, this);

					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

						if (location != null) {
							
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}

				}
				
				if(GPS_state) {
					if(location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								1000 * 60 * 1,
								5, this);
						
						if(locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							
							if(location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
 			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return location;
	}
	

	
	public double getLatitude() {
		if(location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}
	
	public double getLongitude() {
		if(location != null) {
			longitude = location.getLongitude();
		}
		
		return longitude;
	}
	
	public boolean canFetchLocation() {
		return this.canFetchLocation;
	}
	
	public void SettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		
		alertDialog.setTitle("GPS");
		
		alertDialog.setMessage("GPS is not enabled. Enable it in settings menu?");
		
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});
		
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		alertDialog.show();
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
