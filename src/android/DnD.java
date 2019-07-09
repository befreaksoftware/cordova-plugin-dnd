package io.electrosoft.dnd;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import android.support.annotation.RequiresApi;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class DnD extends CordovaPlugin {
    private NotificationManager notificationManager;
    /**
     * zero meaning API level is below 23
     * 1 permission required
     * 2 off
     * 3 alarm only
     * 4 priority
     * 5 fully turned on
     *
     */

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        notificationManager= (NotificationManager) webView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        super.execute(action, args, callbackContext);

        /*
        for API 23 upwards
         */
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(action.equals("on")){
                on(callbackContext);
            }else if(action.equals("off")){
                off(callbackContext);
            }else if(action.equals("alarm")){
                partial(callbackContext);
            }else if(action.equals("priority")){
                priority(callbackContext);
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void on(CallbackContext callbackContext){
        changeFilter(NotificationManager.INTERRUPTION_FILTER_NONE,callbackContext,5);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void off(CallbackContext callbackContext){
        changeFilter(NotificationManager.INTERRUPTION_FILTER_ALL,callbackContext,2);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void partial(CallbackContext callbackContext){
        changeFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS,callbackContext,3);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void priority(CallbackContext callbackContext){
        changeFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY,callbackContext,4);
    }
    private void changeFilter(int filter, CallbackContext callbackContext,int status) {


        /*
        for API 23 upwards
         */
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (notificationManager.isNotificationPolicyAccessGranted()){
                notificationManager.setInterruptionFilter(filter);
                callbackContext.success(status);
            } else {
                Toast.makeText(webView.getContext(), "Access policy required", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                callbackContext.error(1);
                webView.getContext().startActivity(intent);
            }
        }else{
            callbackContext.error(0);
        }

    }
}
