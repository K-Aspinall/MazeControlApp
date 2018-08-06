package com.example.PhidgetHelloWorldRemote;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phidgets.*;
import com.phidgets.event.*;

import com.phidgets.Phidget;
import com.phidgets.PhidgetException;
import com.phidgets.ServoPhidget;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.InputChangeEvent;
import com.phidgets.event.InputChangeListener;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;
import com.phidgets.event.ServoPositionChangeEvent;
import com.phidgets.event.ServoPositionChangeListener;

public class HelloWorldRemoteActivity extends Activity {

	private Manager device;

	private ServoPhidget gateServo;
	private ServoPhidget LRServo;
	private ServoPhidget UDServo;

	private ImageView mSpotTop;
	private ImageView mSpotBottom;
	private ImageView mSpotLeft;
	private ImageView mSpotRight;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Toast.makeText(HelloWorldRemoteActivity.this, "Starting USB initialise", Toast.LENGTH_LONG).show();

		try {
			com.phidgets.usb.Manager.Initialize(this);
			Toast.makeText(getApplicationContext(), "Starting gateServo process", Toast.LENGTH_SHORT).show();
			gateServo = new ServoPhidget();
			LRServo = new ServoPhidget();
			UDServo = new ServoPhidget();
			gateServo.addAttachListener(new AttachListener() {
				public void attached(AttachEvent ae) {
					AttachDetachRunnable gateHandler = new AttachDetachRunnable(ae.getSource(), true);
					synchronized (gateHandler) {
						runOnUiThread(gateHandler);
						try {
							gateHandler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			gateServo.addDetachListener(new DetachListener() {
				public void detached(DetachEvent ae) {
					AttachDetachRunnable gateHandler = new AttachDetachRunnable(ae.getSource(), false);
					synchronized (gateHandler) {
						runOnUiThread(gateHandler);
						try {
							gateHandler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});

			LRServo.addAttachListener(new AttachListener() {
				public void attached(AttachEvent ae) {
					AttachDetachRunnable handler = new AttachDetachRunnable(ae.getSource(), true);
					synchronized (handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			LRServo.addDetachListener(new DetachListener() {
				public void detached(DetachEvent ae) {
					AttachDetachRunnable handler = new AttachDetachRunnable(ae.getSource(), false);
					synchronized (handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});

			UDServo.addAttachListener(new AttachListener() {
				public void attached(AttachEvent ae) {
					AttachDetachRunnable handler = new AttachDetachRunnable(ae.getSource(), true);
					synchronized (handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			UDServo.addDetachListener(new DetachListener() {
				public void detached(DetachEvent ae) {
					AttachDetachRunnable handler = new AttachDetachRunnable(ae.getSource(), false);
					synchronized (handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			Toast.makeText(this, "Attempting to attach gateServo" + gateServo, Toast.LENGTH_SHORT).show();
			gateServo.open(14304);

			Toast.makeText(getBaseContext(), "Got gateServo" + gateServo, Toast.LENGTH_SHORT).show();
			LRServo.open(14394);

			Toast.makeText(getApplicationContext(), "Got LRServo" + gateServo, Toast.LENGTH_SHORT).show();
			UDServo.open(19875);

			Toast.makeText(getApplicationContext(), "Servo's attached", Toast.LENGTH_SHORT).show();
		} catch (PhidgetException pe) {
			pe.printStackTrace();
			System.out.println(pe);
			Toast.makeText(getApplicationContext(), pe.toString(), Toast.LENGTH_SHORT).show();
		}

		mSpotTop = (ImageView) findViewById(R.id.spot_top);
		mSpotBottom = (ImageView) findViewById(R.id.spot_bottom);
		mSpotLeft = (ImageView) findViewById(R.id.spot_left);
		mSpotRight = (ImageView) findViewById(R.id.spot_right);

		SensorManager sensorManager =
				(SensorManager) getSystemService(SENSOR_SERVICE);

		final Sensor proximitySensor =
				sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

		// Create listener
		SensorEventListener proximitySensorListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent sensorEvent) {


				if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
					// Detected something nearby

					try{
						if(gateServo.isAttached()){
							gateServo.setPosition(0, 100);
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				} else {
					// Nothing is nearby

					try{
						if(gateServo.isAttached()){
							gateServo.setPosition(0, 0);
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int i) {
			}
		};

		// Register it, specifying the polling interval in
		// microseconds
		sensorManager.registerListener(proximitySensorListener,
				proximitySensor, 2 * 1000 * 1000);

		Sensor game_rotation_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

		SensorEventListener gameRotationSensorListener = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {

				mSpotTop.setAlpha(0f);
				mSpotBottom.setAlpha(0f);
				mSpotLeft.setAlpha(0f);
				mSpotRight.setAlpha(0f);

				//Left / Right tilt
				if(event.values[1] > 0.02f) { // anticlockwise

					mSpotRight.setAlpha(getAlphaValue(event.values[1]));
					try{
						if(LRServo.isAttached()){
							LRServo.setPosition(0, normaliseServoFromTilt(event.values[1]));
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				}
				if(event.values[1] < -0.02f) { // clockwise

					mSpotLeft.setAlpha(getAlphaValue(event.values[1]));
					try{
						if(LRServo.isAttached()){
							LRServo.setPosition(0, normaliseServoFromTilt(event.values[1]));
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				//Up / Down tilt
				}
                if(event.values[0] > 0.02f) { // anticlockwise
					mSpotBottom.setAlpha(getAlphaValue(event.values[0]));

					try{
						if(UDServo.isAttached()){
							UDServo.setPosition(0, normaliseServoFromTilt(event.values[0]));
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				}
				if(event.values[0] < -0.02f) { // clockwise
					mSpotTop.setAlpha(getAlphaValue(event.values[0]));

					try{
						if(UDServo.isAttached()){
							UDServo.setPosition(0, normaliseServoFromTilt(event.values[0]));
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				}

			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};

		sensorManager.registerListener(gameRotationSensorListener,
				game_rotation_sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    class AttachEventHandler implements Runnable {
    	Phidget device;
    	TextView eventOutput;

		public AttachEventHandler(Phidget device, TextView eventOutput) {
			this.device = device;
			this.eventOutput = eventOutput;
		}

		public void run() {

			Toast.makeText(getApplicationContext(),"Running AttachEventHandler", Toast.LENGTH_SHORT ).show();
			try {
				eventOutput.setText("Hello " + device.getDeviceName() + ", Serial " + Integer.toString(device.getSerialNumber()));
				Toast.makeText(getApplicationContext(),"Hello " + device.getDeviceName() + ", Serial "
						+ Integer.toString(device.getSerialNumber()), Toast.LENGTH_LONG ).show();
			} catch (PhidgetException e) {
				e.printStackTrace();
			}


	    	synchronized(this) {
	    		this.notify();
	    	}
		}
    }

    class DetachEventHandler implements Runnable {
    	Phidget device;
    	TextView eventOutput;

    	public DetachEventHandler(Phidget device, TextView eventOutput) {
    		this.device = device;
    		this.eventOutput = eventOutput;
    	}

		public void run() {
			try {
				eventOutput.setText("Goodbye " + device.getDeviceName() + ", Serial " + Integer.toString(device.getSerialNumber()));
			} catch (PhidgetException e) {
				e.printStackTrace();
			}

	    	// Notify that we're done
	    	synchronized(this) {
	    		this.notify();
	    	}
		}
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	try {
			device.close();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
    }

	public float getAlphaValue(float in){
		float alphaValue = Math.abs((in/0.5f)*2);
		if (alphaValue > 1.0f){
			alphaValue = 1.0f;
		}

		return alphaValue;
	}

	public float normaliseServoFromTilt(float in){
		return (float)(in*180)+90;
	}

	class OnServoClickListener implements CheckBox.OnClickListener {
		ServoPhidget phidget;
		public OnServoClickListener(ServoPhidget phidget) {
			this.phidget = phidget;
		}

		@Override
		public void onClick(View v) {
			try{
				if(phidget.isAttached()) {
					if(((CheckBox) v). isChecked()) {
						phidget.setPosition(0,10);
					} else {
						phidget.setPosition(0,100);
					}
				}
			} catch(PhidgetException e) {
				e.printStackTrace();
			}
		}
	}

	class AttachDetachRunnable implements Runnable {
		Phidget phidget;
		boolean attach;
		public AttachDetachRunnable(Phidget phidget, boolean attach)
		{
			this.phidget = phidget;
			this.attach = attach;
		}
		public void run() {
			if(attach)
			{

				try {

					Toast.makeText(getApplicationContext(),"Hello " + phidget.getDeviceName() + ", Serial "
							+ Integer.toString(phidget.getSerialNumber()), Toast.LENGTH_LONG ).show();

				} catch (PhidgetException e) {
					e.printStackTrace();
				}
			}
			else

			//notify that we're done
				Toast.makeText(getApplicationContext(),"DETACHED", Toast.LENGTH_LONG ).show();
			synchronized(this)
			{
				this.notify();
			}
		}
	}
}

