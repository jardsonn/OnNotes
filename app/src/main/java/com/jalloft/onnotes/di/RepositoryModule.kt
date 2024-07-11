package com.jalloft.onnotes.di

import com.jalloft.onnotes.repositories.AuthRepository
import com.jalloft.onnotes.repositories.AuthRepositoryImpl
import com.jalloft.onnotes.repositories.NotesRepository
import com.jalloft.onnotes.repositories.NotesRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}