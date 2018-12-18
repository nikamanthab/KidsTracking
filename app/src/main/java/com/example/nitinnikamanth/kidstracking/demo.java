package com.example.nitinnikamanth.kidstracking;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class demo {
    //    private double calculateDistance(int rssi , int txpower) {
//
//        if (rssi == 0) {
//            return -1.0;
//        }
//
//        double ratio = rssi*1.0/txpower;
//        if (ratio < 1.0) {
//            return Math.pow(ratio,10);
//        }
//        else {
//            double distance =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
//            return distance;
//        }
//    }

   /* public void showNotification(String title, String message , int id) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/
//    private void viewAll() {
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor res = myDb.getAllData();
//                if(res.getCount() == 0) {
//                    // show message
//                    showMessage("Error","Nothing found");
//                    return;
//                }
//                Toast.makeText(TrackingActivity.this, "viewing...", Toast.LENGTH_SHORT).show();
//                StringBuffer buffer = new StringBuffer();
//                while (res.moveToNext()) {
////                    buffer.append("Id :"+ res.getString(0)+"\n");
////                    buffer.append("Parentname :"+ res.getString(1)+"\n");
////                    buffer.append("Kidname :"+ res.getString(2)+"\n");
////                    buffer.append("UUID :"+ res.getString(3)+"\n");
////                    buffer.append("Major :"+ res.getString(4)+"\n");
////                    buffer.append("Minor :"+ res.getString(5)+"\n\n");
//                    kids.add(res.getString(2));
//                }
//                // Show all data/**/
//                showMessage("Data",buffer.toString());
//            }
//        });
//    }

    //beaconRegion = new BeaconRegion("BeaconRegion" , UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d") , 1 ,2);

//        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
//
//            @Override
//            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
//                details.setText("In range");
//                showNotification("Alert!!!" , "Beacon Entered into Region!!!" , 3);
//                /*Toast.makeText(MainActivity.this, "hello world", Toast.LENGTH_SHORT).show();
//                am.setRingerMode(0);*/
//            }
//
//            @Override
//            public void onExitedRegion(BeaconRegion beaconRegion) {
//                details.setText("out of range");
//                showNotification("Alert!!!" , "Beacon Exited into Region!!!" , 3);
//               /* Toast.makeText(MainActivity.this, "Good bye", Toast.LENGTH_SHORT).show();
//                am.setRingerMode(2);*/
//            }
//        });
/*  private double getRange(int rssi, int txCalibratedPower) {
        double ratio_db = txCalibratedPower - rssi;
        double ratio_linear = Math.pow(10, ratio_db / 10);

        double r = Math.sqrt(ratio_linear);
        return r;
    }

*/

}
