package dementyev.anton.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.InputStreamReader;

import dementyev.anton.myapplication.api.MyAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    TextView textView1, cordiX, cordiY, cordiZ;
    String xx, yy, zz;
    File internalStorageDir;
    File data;
    FileOutputStream fos;

    private MyAPI api = NetworkProvider.api();


    CordiData cordiData;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);

        textView1 = (TextView) findViewById(R.id.textView);
        cordiX = (TextView) findViewById(R.id.cordiX);
        cordiY = (TextView) findViewById(R.id.cordiY);
        cordiZ = (TextView) findViewById(R.id.cordiZ);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

        /*if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            getOrientation(event);
        }*/
    }


    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        cordiX.setText("" + x);
        cordiY.setText("" + y);
        cordiZ.setText("" + z);


        xx = cordiX.getText().toString();
        yy = cordiY.getText().toString();
        zz = cordiZ.getText().toString();

        // call AsynTask to perform network operation on separate thread


        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        //System.out.println("value: ------------>         "+accelationSquareRoot);
        //long actualTime = event.timestamp;

        api.postData(accelationSquareRoot).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failure!", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
        textView1.setText("x: " + xx + "\ny: " + yy + "\nz: " + zz + "\nAccelation Square Root: " + accelationSquareRoot);
        /*if (accelationSquareRoot >= 2) //
        {
            // Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT).show();
            //new HttpAsyncTask().execute("10.80.5.108:8080/hello");


            // Close the file output stream


            cordiData = new CordiData(xx, yy, zz);



            //POST("10.80.5.108:8080/hello", cordiData);


            if (color) {
                view.setBackgroundColor(Color.GREEN);
            } else {
                view.setBackgroundColor(Color.WHITE);
            }
            color = !color;
        }*/

    }


   /** private void getOrientation(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        textView1.setText("Angel: " + x );
        api.postDataAngle(x).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failure!", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);


        /*sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);*/
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}