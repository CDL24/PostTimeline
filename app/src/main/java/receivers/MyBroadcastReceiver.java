package receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.example.climbachiya.timelineexample.PostLocation;

import java.util.ArrayList;

import interfaces.TimelineUpdateListener;
import modal.Timeline;
import utility.ConstantData;

/**
 * Created by C.limbachiya on 4/22/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    int mId = 101;
    public static final String DATA_LIST = "DATA_LIST";
    private TimelineUpdateListener mDataUpdateListener = null;


    public MyBroadcastReceiver(TimelineUpdateListener dataUpdateListener) {
        mDataUpdateListener = dataUpdateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver called", Toast.LENGTH_SHORT).show();

        if (null != intent) {
            // assuming data is available in the delivered intent
            /*ArrayList<Timeline> dataList = intent.getSerializableExtra(DATA_LIST);*/
            if (null != mDataUpdateListener) {
                mDataUpdateListener.onDataAvailable(ConstantData.timelinePost);
            }
        }
    }

    private void sendNotification(Context context, String time, String type) {


    }
}
