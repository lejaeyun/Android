package org.pnu.dietapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import org.pnu.dietapp.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class Fragment3 extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    TextView tvStepCount;
    private int myStepCount;

    // Step Counter 센서가 없는 경우 알람 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myStepCount = 0;



        tvStepCount = (TextView) tvStepCount.findViewById(R.id.tvStepCount);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCountSensor == null) {
            Toast.makeText(this, "STEP COUNTER 센서가 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    private Object getSystemService(String sensorService) {
    }

    private void setContentView(int activity_main) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void myAlarm(String tmpTitle, String tmpText) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel mChannel = new NotificationChannel("myPush", "myPush", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(mChannel);

        NotificationCompat.Builder myAlarmBuilder =
                new  NotificationCompat.Builder(this, mChannel.getId())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(tmpTitle)
                        .setContentText(tmpText)
                        .setAutoCancel(true);

        notificationManager.notify(0, myAlarmBuilder.build());
    }

    // 텍스트 변경
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 스텝카운터가 변경될 때 값도 변경
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            myStepCount++;
            tvStepCount.setText("Step Count : " + String.valueOf(myStepCount));
            //100보 마다 만보기 알람
            if (myStepCount % 100 == 0) {
                myAlarm("만보기 알람", "걸음 " + String.valueOf(myStepCount) + "회 달성!");
            }
        }
    }

    // 가상화 함수 오버라이딩 추가
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
