package gautambverma.com.game.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/* Here the variables used are not public as we are building whole game into 1 package */
public class AccelerometerHandler implements SensorEventListener {
	float accelX;
	float accelY;
	float accelZ;
	
	public AccelerometerHandler(Context context) {
		Log.i("Accelerometer", "Accelerometer Handle Cons");
		SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			Sensor accelerometer = manager.getSensorList(
			Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer,
			SensorManager.SENSOR_DELAY_GAME);
			}
		}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
	}

	public float getAccelX() {
		return accelX;
	}

	public float getAccelY() {
		return accelY;
	}

	public float getAccelZ() {
		return accelZ;
	}
}
