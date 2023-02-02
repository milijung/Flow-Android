package com.example.client.ui.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class BankNotificationListenerService : NotificationListenerService() {
    // 서비스가 실행되었을 때. 앱이 최초에 실행되거나 알림 접근 권한을 획득하게 되면 호출됨
    override fun onListenerConnected() {
        super.onListenerConnected()
    }
    // 서비스가 연결 해제되었을 때.
    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.e("kobbi","MyNotificationListener.onListenerDisconnected()")
    }
    // 알림이 왔을 때. StatusBarNotification 객체에 해당 알림에 대한 정보가 들어있으며, 객체 내부 getNotification() 메서드로 알림에 대한 정보를 획득할 수 있음
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.packageName?.run {
            Log.e("kobbi","MyNotificationListener.onNotificationPosted() --> packageName: $this")
        }
    }
}