package com.swaraajsports.technician.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.swaraajsports.technician.R;
import com.swaraajsports.technician.activity.CustomNotificationActivity;
import com.swaraajsports.technician.dashboard.DashBoardActivity;
import com.swaraajsports.technician.util.Delegate;
import com.swaraajsports.technician.util.PreferenceManager;
import com.swaraajsports.technician.util.VariableBag;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;

public class FirebaseDataReceiver extends BroadcastReceiver {

    static int numMessages = 0;
    NotificationManager notificationManager;
    Bitmap bitmap = null;
    Bitmap bitmapSmall = null;
    String TAG = "FirebaseDataReceiver";
    private PreferenceManager preferenceManager;
    boolean isSocietyChanged = false;
    Vibrator vibe;

    public void onReceive(final Context context, Intent var1) {
        String var2 = var1.getAction();

        Paper.init(context);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        preferenceManager = new PreferenceManager(context);
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        System.gc();

        if ("com.google.android.c2dm.intent.RECEIVE".equals(var2)) {

            Bundle var20;

            if ((var20 = var1.getExtras()) == null) {
                var20 = new Bundle();
            }

            var20.remove("android.support.content.wakelockid");
            RemoteMessage remoteMessage = new RemoteMessage(var20);

            if (Delegate.mainActivity!=null && !Delegate.mainActivity.isDestroyed()){
                Delegate.mainActivity.getDashBoardData();
            }

            if (preferenceManager.getLoginSession()) {
                if (remoteMessage.getData().containsKey("msg_id") && remoteMessage.getData().get("msg_id") != null) {
                    try {

                        Long msgId = Long.parseLong(remoteMessage.getData().get("msg_id"));
                        Long lastIntId = Long.valueOf(preferenceManager.getKeyValueStringWithZero(VariableBag.LAST_NOTIFICATION_ID));

                        Log.e(TAG, "timeNew: " + msgId);
                        Log.e(TAG, "timeLast: " + lastIntId);


                        if (lastIntId < msgId) {

                            preferenceManager.setKeyValueString(VariableBag.LAST_NOTIFICATION_ID, remoteMessage.getData().get("msg_id"));
                            nextStep(context, remoteMessage);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onReceive: " + e.getLocalizedMessage());
                        nextStep(context, remoteMessage);
                    }

                } else {
                    nextStep(context, remoteMessage);
                }
            }

        } else {
            Log.e(TAG, "onReceive: ");

        }


    }

    private void nextStep(Context context, RemoteMessage remoteMessage) {

        final String title = remoteMessage.getData().get("title");
        final String message = remoteMessage.getData().get("body");
        final String clickAction = remoteMessage.getData().get("click_action");
        final String society_id = remoteMessage.getData().get("society_id");
        final String menuClick = remoteMessage.getData().get("menuClick");



        String image = remoteMessage.getData().get("image");
        String small_icon = remoteMessage.getData().get("small_icon");
        if (image != null && image.length() > 5) {
            if (image.contains("logo.png")) {
                bitmap = null;
                image = null;

                sendNotification(context, title, message, clickAction, society_id, image, menuClick);
            } else {
                getBitmapFromUrl(context, title, message, clickAction, society_id, image, menuClick, remoteMessage, true);
            }
        } else if (small_icon != null && small_icon.length() > 5) {
            getBitmapFromUrl(context, title, message, clickAction, society_id, small_icon, menuClick, remoteMessage, false);
        } else {
            sendNotification(context, title, message, clickAction, society_id, image, menuClick);
        }
    }

    private void sendNotification(Context context, String title, String message, String clickAction, String society_id, String image, String menuClick) {
        JSONObject mainObject = null;

        if (title != null && title.length() > 0 && !title.equalsIgnoreCase("sos")) {

            if (title.equalsIgnoreCase("logout")){

                preferenceManager.clearPreferences();
                if (Delegate.mainActivity!=null && !Delegate.mainActivity.isDestroyed()){

                    Delegate.mainActivity.initCode();

                }


            }else {
                try {

                    int NOTIFICATION_COLOR = context.getResources().getColor(R.color.colorPrimary);
                    long[] VIBRATE_PATTERN = {0, 500};


                    String channelId = "channel-" + 01;
                    String channelName = "Channel Name";
                    int importance = 0;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        importance = NotificationManager.IMPORTANCE_HIGH;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(
                                channelId, channelName, importance);

                        mChannel.setShowBadge(true);
                        mChannel.setSound(null, null);
                        mChannel.setDescription(title);
                        mChannel.setLightColor(NOTIFICATION_COLOR);
                        mChannel.setVibrationPattern(VIBRATE_PATTERN);
                        mChannel.enableVibration(true);

                        if (notificationManager != null) {
                            notificationManager.createNotificationChannel(mChannel);
                        }
                    }
                    Intent intent;

                    if (menuClick != null && menuClick.equalsIgnoreCase("custom_notification")) {
                        intent = new Intent(context, CustomNotificationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (clickAction != null && clickAction.length() > 0) {
                            mainObject = new JSONObject(clickAction);
                            intent.putExtra("img", mainObject.getString("img_url"));
                            intent.putExtra("title", mainObject.getString("title"));
                            intent.putExtra("desc", mainObject.getString("description"));
                            intent.putExtra("time", mainObject.getString("notification_time"));
                        }
                    } else {
                        intent = new Intent(context, DashBoardActivity.class);
                        intent.putExtra("isFromFCM", true);
                        intent.putExtra("click_action", clickAction);

                    }

                    PendingIntent pendingIntent;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                        pendingIntent = PendingIntent.getActivity(context, getRandomNumber(1, 10000), intent,
                                PendingIntent.FLAG_MUTABLE);
                    }else {
                        pendingIntent = PendingIntent.getActivity(context, getRandomNumber(1, 10000), intent,
                                PendingIntent.FLAG_ONE_SHOT);
                    }

                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

                    //pendingIntent.

                    if (bitmap != null) {

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setSound(null)
                                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                                .setLights(Color.RED, 1000, 1000)
                                .setVibrate(new long[]{0, 400, 250, 400})
                                .setAutoCancel(true)
                                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                .setContentIntent(pendingIntent);


                        if (notificationManager != null) {

                            notificationManager.notify(getRandomNumber(1, 10000), mBuilder.build());

                        }

                    } else if (bitmapSmall != null) {

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                                .setLargeIcon(bitmapSmall)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(title)
                                .setSound(null)
                                .setContentText(message)
                                .setLights(Color.RED, 1000, 1000)
                                .setVibrate(new long[]{0, 400, 250, 400})
                                .setAutoCancel(true)
                                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                .setContentIntent(pendingIntent);

                        if (message.length() > 40) {
                            mBuilder.setStyle(bigTextStyle);
                        }


                        if (notificationManager != null) {

                            notificationManager.notify(getRandomNumber(1, 10000), mBuilder.build());

                        }

                    } else {

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setSound(null)
                                .setLights(Color.RED, 1000, 1000)
                                .setVibrate(new long[]{0, 400, 250, 400})
                                .setAutoCancel(true)
                                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                .setContentIntent(pendingIntent);

                        if (message.length() > 40) {
                            mBuilder.setStyle(bigTextStyle);
                        }


                        if (notificationManager != null) {

                            notificationManager.notify(getRandomNumber(1, 10000), mBuilder.build());


                        }

                    }
                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                    if (am != null && getNotificationPermission(context)) {

                        switch (am.getRingerMode()) {
                            case AudioManager.RINGER_MODE_SILENT:
                                Log.i("MyApp", "Silent mode");
                                break;
                            case AudioManager.RINGER_MODE_VIBRATE:
                                Log.i("MyApp", "Vibrate mode");
                                vibe.vibrate(30);

                                break;
                            case AudioManager.RINGER_MODE_NORMAL:
                                Log.i("MyApp", "Normal mode");

                                try {
                                    playNotificationSound(context, Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                                            + "://" + context.getPackageName() + "/raw/just_saying"));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public void getBitmapFromUrl(Context context, String title, String message, String clickAction,
                                 String society_id, String imageUrl, String menuClick, RemoteMessage remoteMessage, boolean isBigImg) {


        new Thread(() -> {
            try {

                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmapNew = BitmapFactory.decodeStream(input);
                    if (isBigImg) {
                        bitmap = bitmapNew;
                    } else {
                        bitmapSmall = bitmapNew;
                    }

                    sendNotification(context, title, message, clickAction, society_id, imageUrl, menuClick);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (isBigImg) {
                        bitmap = null;
                    } else {
                        bitmapSmall = null;
                    }

                    sendNotification(context, title, message, clickAction, society_id, imageUrl, menuClick);

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public int getRandomNumber(int min, int max) {
        // min (inclusive) and max (exclusive)
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    static
    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean getNotificationPermission(Context context) {
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    public void playNotificationSound(Context context, Uri alarmSound) {
        try {
            Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
