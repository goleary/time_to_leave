package com.gabeoleary.trafficandroid;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TrafficMonitor extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_NOTIFY = "com.gabeoleary.trafficandroid.action.NOTIFY";
    private static final String ACTION_BAZ = "com.gabeoleary.trafficandroid.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.gabeoleary.trafficandroid.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.gabeoleary.trafficandroid.extra.PARAM2";

    public TrafficMonitor() {
        super("TrafficMonitor");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void sendNotification(Context context, String param1) {
        Intent intent = new Intent(context, TrafficMonitor.class);
        intent.setAction(ACTION_NOTIFY);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TrafficMonitor.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFY.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionNotify(param1);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNotify(String param1) {
        pushNotification();
    }

    public static final String NOTIFICATION_CHANNEL_ID = "4565";
    public static final String NOTIFICATION_CHANNEL_NAME = "TrafficNotifier";

    public void pushNotification() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //create notification channel
            //Notification Channel
            CharSequence channelName = NOTIFICATION_CHANNEL_NAME;
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
        }
        else {
            builder = new Notification.Builder(this);
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        //set
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.ic_android_black_24dp);
        builder.setContentText("Contents");
        builder.setContentTitle("title");
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
