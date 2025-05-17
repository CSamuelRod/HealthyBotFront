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
import com.example.healthybotfront.domain.usecase.DeleteHabitUseCase
import com.example.healthybotfront.domain.usecase.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.GetProgressPercentageByUserUseCase
import com.example.healthybotfront.domain.usecase.GetUserUseCase
import com.example.healthybotfront.domain.usecase.LoginUseCase
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import com.example.healthybotfront.domain.usecase.SaveProgressUseCase
import com.example.healthybotfront.domain.usecase.UpdateGoalUseCase
import com.example.healthybotfront.domain.usecase.UpdateUserUseCase
import com.example.healthybotfront.presentacion.viewmodel.GetProgressPercentageViewModel
import com.example.healthybotfront.presentacion.viewmodel.GoalViewModel
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    // --- Register / Login ---
    single { AuthRepository(get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }

    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }


    // --- User ---
    single { UserRepository(get()) }

    factory { GetUserUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }

    viewModel { ProfileViewModel(get(), get(), get()) }


    // --- Habit ---
    single { HabitRepository(get()) }

    factory { CreateHabitUseCase(get()) }
    factory { GetHabitsByUserIdUseCase(get()) }
    factory { DeleteHabitUseCase(get()) }

    viewModel { HabitViewModel(get(), get(), get()) }


    // --- Goal ---
    single { GoalRepository(get()) }

    factory { GetGoalByHabitIdUseCase(get()) }
    factory { CreateGoalUseCase(get()) }
    factory { UpdateGoalUseCase(get()) }
    factory { DeleteGoalUseCase(get()) }

    viewModel { GoalViewModel(get(), get(), get(), get()) }


    // --- Progress ---
    single { ProgressRepository(get()) }

    factory { GetProgressPercentageByUserUseCase(get()) }
    factory { SaveProgressUseCase(get()) }

    viewModel { ProgressViewModel(get(), get()) }
    viewModel { GetProgressPercentageViewModel(get()) }
}