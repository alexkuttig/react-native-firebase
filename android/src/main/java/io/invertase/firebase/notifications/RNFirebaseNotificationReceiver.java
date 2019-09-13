package io.invertase.firebase.notifications;

import android.os.Bundle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * This is invoked by the Alarm Manager when it is time to display a scheduled notification.
 */
public class RNFirebaseNotificationReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    // display notification
    new RNFirebaseNotificationManager(context).displayScheduledNotification(intent.getExtras());

    // check bundle for logging request
    Bundle bundle = intent.getExtras().getBundle("android");
    if (bundle != null) {
        Bundle loggingRequest = bundle.getBundle("loggingRequest");
        if(loggingRequest != null){
            new LoggingTask(loggingRequest).execute();
        }
    }
  }
}
