package com.bd.dana.hiltpractice.api.models.app_model

import android.graphics.drawable.Drawable

class AppModel(private var name:String, private var icon: Drawable, private var packages:String) {
    fun getName(): String {
        return name
    }

    fun getIcon(): Drawable {
        return icon
    }

    fun getPackages(): String {
        return packages
    }
}