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

	Manager device;

	String ipAddress = "137.44.211.42";

	TextView eventOutput;
	ServoPhidget gateServo;
	ServoPhidget LRServo;
	ServoPhidget UDServo;

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
		//System.out.println("WHY YOU NO WORK YOU FUCKING CUNT");
		/*try {
			device = new Manager();

			//eventOutput = (TextView)findViewById(R.id.eventResultContainer);
			System.out.println("SHOULD BE ATTACHINGLISTENEREEEE");
			device.addAttachListener(new AttachListener() {
				public void attached(final AttachEvent attachEvent) {
					AttachEventHandler handler = new AttachEventHandler(attachEvent.getSource(), eventOutput);
					// This is synchronised in case more than one device is attached before one completes attaching
					synchronized(handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});

			device.addDetachListener(new DetachListener() {
				public void detached(final DetachEvent detachEvent) {
					DetachEventHandler handler = new DetachEventHandler(detachEvent.getSource(), eventOutput);
					// This is synchronised in case more than one device is attached before one completes attaching
					synchronized(handler) {
						runOnUiThread(handler);
						try {
							handler.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});

			// This will only open the first device it sees on the webservice
			// Make sure to change the IP address (above) and port to the one for your computer connected to the Phidget
			device.open(ipAddress, 5001);

		} catch (PhidgetException pe) {
			pe.printStackTrace();
			System.out.println(pe);
		}*/
		try {
			//Toast.makeText(getApplicationContext(), "Starting USB initialise", Toast.LENGTH_SHORT).show();
			System.out.println("WHY YOU NO WORK YOU FUCKING CUNT1");
			com.phidgets.usb.Manager.Initialize(this);
			System.out.println("WHY YOU NO WORK YOU FUCKING CUNT2");
			Toast.makeText(getApplicationContext(), "Starting gateServo process", Toast.LENGTH_SHORT).show();
			gateServo = new ServoPhidget();
			LRServo = new ServoPhidget();
			UDServo = new ServoPhidget();
			System.out.println("Are we here yet?");
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
			System.out.println("Attempting to attach?");
			Toast.makeText(this, "Attempting to attach gateServo" + gateServo, Toast.LENGTH_SHORT).show();
			gateServo.open(14304);
			//gateServo.waitForAttachment();
			Toast.makeText(getBaseContext(), "Got gateServo" + gateServo, Toast.LENGTH_SHORT).show();
			LRServo.open(14394);
			//LRServo.waitForAttachment();
			Toast.makeText(getApplicationContext(), "Got LRServo" + gateServo, Toast.LENGTH_SHORT).show();
			UDServo.open(19875);
			//UDServo.waitForAttachment();
			//Toast.makeText(getApplicationContext(), "Got UDServo" + gateServo, Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "DID IT MAKE IT PAST OPEN ANY?", Toast.LENGTH_SHORT).show();
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
					//getWindow().getDecorView().setBackgroundColor(Color.RED);
					try{
						if(gateServo.isAttached()){
							gateServo.setPosition(0, 100);
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				} else {
					// Nothing is nearby
					//getWindow().getDecorView().setBackgroundColor(Color.GREEN);
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
					//getWindow().getDecorView().setBackgroundColor(Color.BLUE);
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
					//getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
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
					//getWindow().getDecorView().setBackgroundColor(Color.GRAY);
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
					//getWindow().getDecorView().setBackgroundColor(Color.WHITE);
					try{
						if(UDServo.isAttached()){
							UDServo.setPosition(0, normaliseServoFromTilt(event.values[0]));
						}
					}catch (PhidgetException pe){
						pe.printStackTrace();
					}
				}
				//System.out.println("event = " + event.values[1]);
				//System.out.println("event.values = " + Arrays.toString(event.values));
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
			System.out.println("TOAST SHOULD BE APPEARING");
			Toast.makeText(getApplicationContext(),"Running AttachEventHandler", Toast.LENGTH_SHORT ).show();
			try {
				eventOutput.setText("Hello " + device.getDeviceName() + ", Serial " + Integer.toString(device.getSerialNumber()));
				Toast.makeText(getApplicationContext(),"Hello " + device.getDeviceName() + ", Serial "
						+ Integer.toString(device.getSerialNumber()), Toast.LENGTH_LONG ).show();
			} catch (PhidgetException e) {
				e.printStackTrace();
			}

	    	// Notify that we're done
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
		//System.out.println("alpha value = " + alphaValue);
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
			//TextView attachedTxt = (TextView) findViewById(R.id.attachedTxt);
			if(attach)
			{
				//attachedTxt.setText("Attached");
				try {
					/*TextView nameTxt = (TextView) findViewById(R.id.nameTxt);
					TextView serialTxt = (TextView) findViewById(R.id.serialTxt);
					TextView versionTxt = (TextView) findViewById(R.id.versionTxt);
					TextView labelTxt = (TextView) findViewById(R.id.labelTxt);

					nameTxt.setText(phidget.getDeviceName());
					serialTxt.setText(Integer.toString(phidget.getSerialNumber()));
					versionTxt.setText(Integer.toString(phidget.getDeviceVersion()));
					labelTxt.setText(phidget.getDeviceLabel());*/

					Toast.makeText(getApplicationContext(),"Hello " + phidget.getDeviceName() + ", Serial "
							+ Integer.toString(phidget.getSerialNumber()), Toast.LENGTH_LONG ).show();

				} catch (PhidgetException e) {
					e.printStackTrace();
				}
			}
			else
				//attachedTxt.setText("Detached");
			//notify that we're done
				Toast.makeText(getApplicationContext(),"DETACHED", Toast.LENGTH_LONG ).show();
			synchronized(this)
			{
				this.notify();
			}
		}
	}
}

