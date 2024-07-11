package com.jalloft.onnotes.di

import com.jalloft.onnotes.data.service.OnNotesDataSource
import com.jalloft.onnotes.data.service.OnNotesService
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {
    single<OnNotesService> { get<Retrofit>().create(OnNotesService::class.java) }
    single { OnNotesDataSource(get()) }
}