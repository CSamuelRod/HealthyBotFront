package com.example.healthybotfront.di

import com.example.healthybotfront.data.repository.AuthRepository
import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.api.UserApi
import com.example.healthybotfront.domain.usecase.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.GetUserUseCase
import com.example.healthybotfront.domain.usecase.LoginUseCase
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import com.example.healthybotfront.domain.usecase.UpdateUserUseCase
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(get()) }
    single { UserRepository(get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }

    // ViewModels
    viewModel{ RegisterViewModel(get())}
    viewModel{ LoginViewModel(get())}
    viewModel{ ProfileViewModel(get(),get(),get())}


}