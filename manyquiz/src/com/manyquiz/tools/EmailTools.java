package com.manyquiz.tools;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.manyquiz.R;

public abstract class EmailTools {

    public static void send(Context context, String subject, String message) {
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
}
