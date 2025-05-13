package com.example.healthybotfront.di

import com.example.healthybotfront.data.repository.AuthRepository
import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.api.UserApi
import com.example.healthybotfront.domain.usecase.CreateGoalUseCase
import com.example.healthybotfront.domain.usecase.CreateHabitUseCase
import com.example.healthybotfront.domain.usecase.DeleteGoalUseCase
import com.example.healthybotfront.domain.usecase.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.GetProgressByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.GetUserUseCase
import com.example.healthybotfront.domain.usecase.LoginUseCase
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import com.example.healthybotfront.domain.usecase.SaveProgressUseCase
import com.example.healthybotfront.domain.usecase.UpdateGoalUseCase
import com.example.healthybotfront.domain.usecase.UpdateUserUseCase
import com.example.healthybotfront.presentacion.viewmodel.GetProgressViewModel
import com.example.healthybotfront.presentacion.viewmodel.GoalViewModel
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(get()) }
    single { UserRepository(get()) }
    single { HabitRepository(get()) }
    single { GoalRepository(get()) }
    single {ProgressRepository(get())}


    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
    factory { CreateHabitUseCase(get()) }
    factory { GetHabitsByUserIdUseCase(get()) }

    factory { GetGoalByHabitIdUseCase(get()) }
    factory { CreateGoalUseCase(get()) }
    factory { UpdateGoalUseCase(get()) }
    factory { DeleteGoalUseCase(get()) }
    factory { GetProgressByHabitIdUseCase(get()) }


    factory { SaveProgressUseCase(get()) }

    // ViewModels
    viewModel{ RegisterViewModel(get())}
    viewModel{ LoginViewModel(get())}
    viewModel{ ProfileViewModel(get(),get(),get())}
    viewModel{ HabitViewModel(get(),get())}
    viewModel { GoalViewModel(get(),get(),get(),get()) }

    viewModel{ProgressViewModel(get(),get())}
    viewModel{GetProgressViewModel(get(),get())}
}