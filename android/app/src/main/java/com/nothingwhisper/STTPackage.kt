package com.nothingwhisper

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.*

class STTPackage : ReactPackage {
    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return listOf(STTModule(reactContext))
    }

    override fun createViewManagers(reactContext: ReactApplicationContext)
            = emptyList<ViewManager<*, *>>()
}