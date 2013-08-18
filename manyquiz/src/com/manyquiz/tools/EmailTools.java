package com.manyquiz.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.manyquiz.R;

public abstract class EmailTools {

    private static final String TAG = EmailTools.class.getSimpleName();

    public static void send(Context context, String subject, String message) {
        String packageName = context.getPackageName();
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info;
            if (manager != null) {
                info = manager.getPackageInfo(packageName, 0);
                if (info != null) {
                    message += String.format("\n\n--\n[App: %s Version: %d/%s]",
                            packageName, info.versionCode, info.versionName);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not get package info", e);
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ context.getString(R.string.email_address) });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.email_client_chooser)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    context.getString(R.string.no_email_client), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public static void send(Context context, int subjectID, String message) {
        send(context, context.getString(subjectID), message);
    }

    public static void send(Context context, int subjectID) {
        send(context, context.getString(subjectID), "");
    }
}
