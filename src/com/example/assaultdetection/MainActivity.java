package com.example.assaultdetection;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity implements SensorEventListener,LocationListener {
private float mLastX, mLastY, mLastZ;
private boolean mInitialized; private SensorManager mSensorManager; private Sensor mAccelerometer; private final float NOISE = (float) 5.0;
/** Called when the activity is first created. */
int i=0;
double latitude=0.0;
double longitude=0.0;
Location currentLocation;
@Override

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
mInitialized = false;
mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
}

@Override
public void onSensorChanged(SensorEvent event) {
TextView tvX= (TextView)findViewById(R.id.x_axis);
TextView tvY= (TextView)findViewById(R.id.y_axis);
TextView tvZ= (TextView)findViewById(R.id.z_axis);
ImageView iv = (ImageView)findViewById(R.id.image);
float x = event.values[0];
float y = event.values[1];
float z = event.values[2];
if (!mInitialized) {
mLastX = x;
mLastY = y;
mLastZ = z;
tvX.setText("0.0");
tvY.setText("0.0");
tvZ.setText("0.0");
mInitialized = true;
} else {
float deltaX = Math.abs(mLastX - x);
float deltaY = Math.abs(mLastY - y);
float deltaZ = Math.abs(mLastZ - z);
if (deltaX < NOISE) deltaX = (float)0.0;
if (deltaY < NOISE) deltaY = (float)0.0;
if (deltaZ < NOISE) deltaZ = (float)0.0;
mLastX = x;
mLastY = y;
mLastZ = z;
tvX.setText(Float.toString(deltaX));
tvY.setText(Float.toString(deltaY));
tvZ.setText(Float.toString(deltaZ));
iv.setVisibility(View.VISIBLE);
/*if (deltaX > deltaY) {
iv.setImageResource(R.drawable.horizontal);
} else if (deltaY > deltaX) {
iv.setImageResource(R.drawable.vertical);
} else {
iv.setVisibility(View.INVISIBLE);
}*/
EditText phone1No=(EditText) findViewById(R.id.editText1);
System.out.println(phone1No.getText()+"     jgjgjfhfghfgh");
String phoneNo=phone1No.getText().toString();
String sms=null;
LocationManager mlocManager=null;  
LocationListener mlocListener;  
mlocManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);  
mlocListener = new LocationListener(){
    public void onLocationChanged(Location location) {
        updateLocation(location);
        }

    public void onStatusChanged(String provider, int status,
            Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
};
mlocManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);  
if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))

if(deltaX>NOISE&&deltaY>NOISE&&deltaY>NOISE&&phoneNo.length()==11&&i<1)
try {
	
    	
    sms = "Help I am in Trouble Latitude:"+latitude+" Longitude:"+longitude;
              
        
	SmsManager smsManager = SmsManager.getDefault();
	smsManager.sendTextMessage(phoneNo, null, sms, null, null);
	Toast.makeText(getApplicationContext(), "SMS Sent!",
				Toast.LENGTH_LONG).show();
	if(i<1){
	MediaPlayer mp1 = MediaPlayer.create(this, R.raw.alarm);
	mp1.start();}
	i++;
} catch (Exception e) {
	Toast.makeText(getApplicationContext(),
		"SMS faild, please try again later!",
		Toast.LENGTH_LONG).show();
	e.printStackTrace();
}
}
}

@Override
protected void onResume() {
	super.onResume();
	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	protected void onPause() {
	super.onPause();
	mSensorManager.unregisterListener(this);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	// can be safely ignored for this demo
	}

	 void updateLocation(Location location) {
         currentLocation = location;
         latitude = currentLocation.getLatitude();
         longitude = currentLocation.getLongitude();}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}