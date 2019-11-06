package com.clark.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.NonNull;

public class ActivityUtils {

    /**
     * Return the activity by view.
     *
     * @param view The view.
     * @return the activity by view.
     */
    public static Activity getActivityByView(@NonNull View view) {
        return getActivityByContext(view.getContext());
    }

    /**
     * Return the activity by context.
     *
     * @param context The context.
     * @return the activity by context.
     */
    public static Activity getActivityByContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


}
