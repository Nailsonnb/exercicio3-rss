package br.ufpe.cin.if1001.rss.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import br.ufpe.cin.if1001.rss.R;
import br.ufpe.cin.if1001.rss.ui.MainActivity;

public class NovaNoticia extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext());
        builder
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("RSS APP")
                .setContentText("Noticia nova!");
        Intent resultIntent = new Intent(context.getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),0,resultIntent,0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManagerCompat.from(context.getApplicationContext()).notify(0,notification);
        //NotificationManagerCompat.from(context.getApplicationContext()).cancel(0);
    }
}
