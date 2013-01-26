/*
 * Copyright (C) 2011 Cory Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mediabuttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Listens for a few broadcast intents that require registration and
 * forwards them to the Broadcast class.  Listens for ACTION_SCREEN_ON
 * and ACTION_HEADSET_PLUG.
 */
public class Repeater extends BroadcastReceiver {

    private static Repeater sRepeater = null;

    // Enforce singleton status with private constructor.
    private Repeater() {
        super();
        
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context, Broadcaster.class);
        context.startService(intent);
    }

    /**
     * Created and register the repeater.  Does nothing if already started.
     * 
     * @param context   The context to register the receiver in.  Should be an
     *      application context.
     */
    public static void start(Context context) {
        if (sRepeater != null) {
            return;
        }
        Log.d(Widget.TAG, "Starting repeater");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        
        //filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        
        //  Not for right now but can use this to get meta data about tracks, title , artist etc etc.
        /*
        filter.addAction("com.android.music.metachanged");
        filter.addAction("com.htc.music.metachanged");
        filter.addAction("fm.last.android.metachanged");
        filter.addAction("com.sec.android.app.music.metachanged");
        filter.addAction("com.nullsoft.winamp.metachanged");
        filter.addAction("com.amazon.mp3.metachanged");     
        filter.addAction("com.miui.player.metachanged");        
        filter.addAction("com.real.IMP.metachanged");
        filter.addAction("com.sonyericsson.music.metachanged");
        filter.addAction("com.rdio.android.metachanged");
        filter.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        filter.addAction("com.andrew.apollo.metachanged");
        */
        
        sRepeater = new Repeater();
        context.registerReceiver(sRepeater, filter);
    }

    /**
     * Stop and unregister the receiver if it is running.
     * 
     * @param context   The same context used when start() was called.
     */
    public static void stop(Context context) {
        if (sRepeater == null) {
            return;
        }
        Log.d(Widget.TAG, "Stopping repeater");
        context.unregisterReceiver(sRepeater);
        sRepeater = null;
    }
}
