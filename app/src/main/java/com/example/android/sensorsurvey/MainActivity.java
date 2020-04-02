package com.example.android.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    // Individual light and proximity sensors.
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorProximity = (TextView) findViewById(R.id.label_proximity);

        mSensorProximity =
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }

    }

        @Override
        protected void onStart() {
            super.onStart();

            if (mSensorProximity != null) {
                mSensorManager.registerListener(this, mSensorProximity,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (mSensorLight != null) {
                mSensorManager.registerListener(this, mSensorLight,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        @Override
        protected void onStop() {
            super.onStop();
            mSensorManager.unregisterListener(this);
        }
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            // The sensor type (as defined in the Sensor class).
            int sensorType = sensorEvent.sensor.getType();

            // The new data value of the sensor.  Both the light and proximity
            // sensors report one value at a time, which is always the first
            // element in the values array.
            float currentValue = sensorEvent.values[0];

            switch (sensorType) {
                // Event came from the light sensor.
                case Sensor.TYPE_LIGHT:
                    // Set the light sensor text view to the light sensor string
                    // from the resources, with the placeholder filled in.
                    mTextSensorLight.setText(getResources().getString(
                            R.string.label_light, currentValue));
                    break;
                case Sensor.TYPE_PROXIMITY:
                    // Set the proximity sensor text view to the light sensor
                    // string from the resources, with the placeholder filled in.
                    mTextSensorProximity.setText(getResources().getString(
                            R.string.label_proximity, currentValue));
                    break;
                default:
                    // do nothing
            }
        }

        /**
         * Abstract method in SensorEventListener.  It must be implemented, but is
         * unused in this app.
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }


}
