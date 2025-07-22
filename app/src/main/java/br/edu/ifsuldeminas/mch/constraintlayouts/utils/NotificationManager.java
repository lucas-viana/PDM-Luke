package br.edu.ifsuldeminas.mch.constraintlayouts.utils;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.activities.MainActivity;

public class NotificationManager {
    private static final String CHANNEL_ID = "luke_diary_channel";
    private static final String CHANNEL_NAME = "Luke Diário Notificações";
    private static final String CHANNEL_DESCRIPTION = "Notificações de lembrete do diário";
    private static final int NOTIFICATION_ID = 1001;
    private static final int ALARM_REQUEST_CODE = 1002;
    
    private Context context;
    private android.app.NotificationManager systemNotificationManager;
    
    public NotificationManager(Context context) {
        this.context = context;
        this.systemNotificationManager = (android.app.NotificationManager) 
            context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            systemNotificationManager.createNotificationChannel(channel);
        }
    }
    
    public void scheduleDaily19hNotification() {
        // Configurar o alarme para disparar todos os dias às 19:00
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context, 
            ALARM_REQUEST_CODE, 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // Configurar para 19:00 (7 PM)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        // Se já passou das 19:00 hoje, agendar para amanhã
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        if (alarmManager != null) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            );
        }
    }
    
    public void cancelDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            context, 
            ALARM_REQUEST_CODE, 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
    
    public void showInstantNotification(String title, String message) {
        if (ActivityCompat.checkSelfPermission(context, 
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    
    // Classe interna para receber as notificações
    public static class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            enviarNotificacao(context);
        }

        private static void enviarNotificacao(Context context) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("💙 Luke te lembra!")
                .setContentText("Que tal registrar como você está se sentindo hoje?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText("Olá! Como foi seu dia? Registre suas emoções e vamos descobrir juntos como você está se sentindo. Luke está aqui para te ajudar! 😊"));

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            
            // Verificar permissão de notificação (Android 13+)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
