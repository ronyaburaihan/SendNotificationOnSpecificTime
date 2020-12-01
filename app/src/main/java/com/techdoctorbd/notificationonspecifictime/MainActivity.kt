package com.techdoctorbd.notificationonspecifictime

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var btnSetNotification: MaterialButton
    lateinit var btnCancelNotification: MaterialButton
    lateinit var tvNotificationStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSetNotification = findViewById(R.id.btn_setNotification)
        btnCancelNotification = findViewById(R.id.btn_cancel_notification)
        tvNotificationStatus = findViewById(R.id.tv_notification_status)

        btnSetNotification.setOnClickListener {
            val timePickerFragment = TimePickerFragment() as DialogFragment
            timePickerFragment.show(supportFragmentManager, "time picker")
        }

        btnCancelNotification.setOnClickListener {
            cancelNotification()
        }
    }

    private fun cancelNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        alarmManager.cancel(pendingIntent)
        tvNotificationStatus.text = getString(R.string.notification_cancelled)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        updateStatusText(calendar)
        setNotification(calendar)
    }

    private fun setNotification(calendar: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun updateStatusText(calendar: Calendar) {
        val statusText = "Notification set for : ${DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)}"
        tvNotificationStatus.text = statusText
    }
}