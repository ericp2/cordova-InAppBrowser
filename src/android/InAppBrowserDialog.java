/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.inappbrowser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Oliver on 22/11/2013.
 */
public class InAppBrowserDialog extends Dialog {
    Context context;
    InAppBrowser inAppBrowser = null;
    protected Long LastTouch = 0L;  // holds last touch interaction time in s (timestamp)

    public InAppBrowserDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        LastTouch = System.currentTimeMillis(); // initial value is when app is loaded.
    }

    public void setInAppBroswer(InAppBrowser browser) {
        this.inAppBrowser = browser;
    }

    public void onBackPressed () {
        if (this.inAppBrowser == null) {
            this.dismiss();
        } else {
            // better to go through the in inAppBrowser
            // because it does a clean up
            if (this.inAppBrowser.hardwareBack() && this.inAppBrowser.canGoBack()) {
                this.inAppBrowser.goBack();
            }  else {
                this.inAppBrowser.closeDialog();
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event)
    {
        LastTouch = System.currentTimeMillis();    // this will be available in plugin.
        return super.dispatchTouchEvent(event);
    }
    /**
     * callable by plugin
     * @return last touch time is milliseconds
     **/
    public Long getLastTouchTime(){
        return LastTouch;
    }
    
    /**
     * set last touch time. this is a form of reset
     * @param long ltt timestamp in milliseconds wanted
     * */
   public void setLastTouchTime(Long ltt)
   {
	   LastTouch = ltt;
   }
}
