package com.example.smsreader;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Telephony;

public class SmsReader extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getSMS")) {
            getSMS(callbackContext);
            return true;
        }
        return false;
    }

    private void getSMS(CallbackContext callbackContext) {
        ContentResolver contentResolver = cordova.getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder smsData = new StringBuilder();
            do {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                smsData.append("From: ").append(address).append("\n");
                smsData.append("Message: ").append(body).append("\n\n");
            } while (cursor.moveToNext());

            callbackContext.success(smsData.toString());
            cursor.close();
        } else {
            callbackContext.error("No SMS found");
        }
    }
}
