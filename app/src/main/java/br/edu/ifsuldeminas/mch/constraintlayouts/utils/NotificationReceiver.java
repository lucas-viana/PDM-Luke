package br.edu.ifsuldeminas.mch.constraintlayouts.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = new NotificationManager(context);
        notificationManager.showInstantNotification(
            "💙 Luke te lembra!",
            "Que tal registrar como você está se sentindo hoje?"
        );
    }
}
