package com.techdoctorbd.notificationonspecifictime

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val intent1 = Intent(context,MainActivity::class.java)
        NotificationHelper.sendNotification(context,intent1,"Test notification","This is the Body")
    }
}