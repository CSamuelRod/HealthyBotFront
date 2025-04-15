package com.example.healthybotfront.di

import com.example.healthybotfront.data.repository.RegisterRestRepository
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RegisterRestRepository(get()) }


    factory { RegisterUseCase(get()) }


    // ViewModels
    //User VM
    viewModel{ RegisterViewModel(get())}


}