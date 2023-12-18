package com.swaraajsports.technician.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InternetConnection extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {


        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {

            ConnectivityListner.getInstance().changeState(status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED);
        }
    }

}
