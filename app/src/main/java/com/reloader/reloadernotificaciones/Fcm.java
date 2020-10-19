package com.reloader.reloadernotificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {

        Log.e("token", "mi token es: " + s);

        guardarToken(s);


    }

    private void guardarToken(String s) {

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("matias").setValue(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String  from =  remoteMessage.getFrom();
        Log.e("TAG", "Mensaje Recibido de: " + from);

      /*
      Valores por defecto de Firebase
      if(remoteMessage.getNotification() != null){
            Log.v("TAG", "el titulo es:"+ remoteMessage.getNotification().getTitle());
            Log.v("TAG", "el contenido es:"+ remoteMessage.getNotification().getBody());
        }*/

//Valores Personalizados
        if(remoteMessage.getData().size() > 0){

//            Log.e("TAG", "mi titulo es"+ remoteMessage.getData().get("titulo"));
//            Log.e("TAG", "mi detalle es"+ remoteMessage.getData().get("detalle"));
//            Log.e("TAG", "el color es"+ remoteMessage.getData().get("color"));

            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");

            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                mayorQueOreo(titulo, detalle);
            }if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                menorQueOreo(titulo, detalle);
            }


        }
    }

    private void menorQueOreo(String titulo,  String detalle) {
        String  id= "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
       //sin canales
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentInfo("nuevo");

        Random random = new Random();

        int idNotify = random.nextInt(8000);
        assert nm != null;
        nm.notify(idNotify, builder.build());
    }

    private void mayorQueOreo(String titulo,  String detalle) {

        String  id= "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentInfo("nuevo");

        Random random = new Random();

        int idNotify = random.nextInt(8000);
        assert nm != null;
        nm.notify(idNotify, builder.build());
    }


    public PendingIntent clickNotif(){
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.putExtra("color","rojo");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0, nf, 0);
    }

}
