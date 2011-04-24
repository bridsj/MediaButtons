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

import com.github.mediabuttons.R;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class Configure extends ListActivity
		implements AdapterView.OnItemClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		setResult(RESULT_CANCELED);
		  
		mInstanceId = getIntent().getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		if (mInstanceId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		
		String[] button_labels = getResources().getStringArray(
				R.array.button_labels);
		setListAdapter(new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, button_labels));
		
		getListView().setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SharedPreferences.Editor prefs =
			getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(ACTION_PREF_PREFIX + mInstanceId, position);
        prefs.commit();
		
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        Widget.updateWidget(this, manager, mInstanceId, position);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mInstanceId);
        setResult(RESULT_OK, resultValue);
        finish();
	}
	
	private int mInstanceId;
	
	public static String PREFS_NAME = "com.github.mediabuttons.prefs";
	public static String ACTION_PREF_PREFIX = "widget_action";
	
	public static final int PLAY_PAUSE_ACTION = 0;
	
	public static int[] sKeyCode = new int[] {
		KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
		KeyEvent.KEYCODE_MEDIA_FAST_FORWARD,
		KeyEvent.KEYCODE_MEDIA_REWIND,
		KeyEvent.KEYCODE_MEDIA_NEXT,
		KeyEvent.KEYCODE_MEDIA_PREVIOUS,
	};
	
	public static int[] sImageResource = new int[] {
		R.drawable.play,  // Will be updated by handler.
		R.drawable.fastforward,
		R.drawable.rewind,
		R.drawable.next,
		R.drawable.previous,
	};
}