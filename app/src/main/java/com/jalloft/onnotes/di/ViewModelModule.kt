package com.jalloft.onnotes.di

import com.jalloft.onnotes.ui.screens.addnotes.AddNoteViewModel
import com.jalloft.onnotes.ui.screens.auth.siginup.SiginUpViewModel
import com.jalloft.onnotes.ui.screens.auth.signin.SiginInViewModel
import com.jalloft.onnotes.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { SiginInViewModel(get(), get()) }
    viewModel { SiginUpViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { AddNoteViewModel(get(), get()) }

}