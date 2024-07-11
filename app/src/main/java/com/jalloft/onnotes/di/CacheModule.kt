package com.jalloft.onnotes.di

import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.common.SessionPrefsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val cacheModule = module {
    single <SessionPrefs>{ SessionPrefsImpl(androidContext()) }
}