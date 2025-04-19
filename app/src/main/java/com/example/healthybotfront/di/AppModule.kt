package com.example.healthybotfront.di

import com.example.healthybotfront.data.repository.AuthRepository
import com.example.healthybotfront.domain.usecase.LoginUseCase
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }

    // ViewModels
    viewModel{ RegisterViewModel(get())}
    viewModel{ LoginViewModel(get())}


}