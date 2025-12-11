package com.neotreks.accuterra.mapboxdemo

import android.util.Log
import com.neotreks.accuterra.mobile.sdk.SdkInitListener
import com.neotreks.accuterra.mobile.sdk.SdkInitState
import com.neotreks.accuterra.mobile.sdk.SdkInitStateDetail

private const val TAG = "SdkInitLogger"

class LoggingListener : SdkInitListener {
    override fun onProgressChanged(progress: Float) {
        Log.d(TAG, progress.toString())
    }

    override fun onStateChanged(
        state: SdkInitState,
        detail: SdkInitStateDetail?
    ) {
        when (state) {
            // Show progress bar
            SdkInitState.IN_PROGRESS -> {
                Log.d(TAG, "SdkInitState.${state}")
            }
            SdkInitState.PAUSED,
            SdkInitState.WAITING,
            SdkInitState.WAITING_FOR_NETWORK,
            SdkInitState.COMPLETED -> {
                Log.i(TAG, "SdkInitState.${state}")
            }

            SdkInitState.UNKNOWN,
            SdkInitState.CANCELED,
            SdkInitState.FAILED -> {
                Log.w(TAG, "SdkInitState.${state}")
            }
        }
    }
}