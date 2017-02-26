/**
 * Copyright 2015 Stuart Kent
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.github.stkent.amplify.feedback;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import static android.content.Intent.ACTION_VIEW;

public final class GooglePlayStoreFeedbackCollector implements IFeedbackCollector {

    private static final String ANDROID_MARKET_URI_PREFIX = "market://details?id=";

    private static final String GOOGLE_PLAY_STORE_URI_PREFIX = "https://play.google.com/store/apps/details?id=";

    @NonNull
    private final String packageName;

    public GooglePlayStoreFeedbackCollector(@NonNull final Context context) {
        this(context.getPackageName());
    }

    public GooglePlayStoreFeedbackCollector(@NonNull final String packageName) {
        this.packageName = packageName;
    }

    @Override
    public boolean tryCollectingFeedback(@NonNull final Activity currentActivity) {
        try {
            currentActivity.startActivity(new Intent(ACTION_VIEW, getAndroidMarketUri()));
            currentActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        } catch (final ActivityNotFoundException ignored) {
            try {
                currentActivity.startActivity(new Intent(ACTION_VIEW, getGooglePlayStoreUri()));
                currentActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } catch (final ActivityNotFoundException ignored2) {
                return false;
            }
        }
    }

    @NonNull
    private Uri getAndroidMarketUri() {
        return Uri.parse(ANDROID_MARKET_URI_PREFIX + packageName);
    }

    @NonNull
    private Uri getGooglePlayStoreUri() {
        return Uri.parse(GOOGLE_PLAY_STORE_URI_PREFIX + packageName);
    }

}
