package com.example.healthybotfront.di

import com.example.healthybotfront.data.repository.AuthRepository
import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.scheduler.DailyNotificationScheduler
import com.example.healthybotfront.data.source.local.NotificationTimeRepositoryImpl
import com.example.healthybotfront.domain.repository.NotificationTimeRepository
import com.example.healthybotfront.domain.usecase.goalUseCases.CreateGoalUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.CreateHabitUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.DeleteGoalUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.DeleteHabitUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.DeleteProgressUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.GetProgressByUserAndDateUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.GetProgressPercentageByUserUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.GetUserUseCase
import com.example.healthybotfront.domain.usecase.AuthUseCases.LoginUseCase
import com.example.healthybotfront.domain.usecase.AuthUseCases.RegisterUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.SaveProgressUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.UpdateGoalUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.GetHabitByIdUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.UpdateHabitUseCase
import com.example.healthybotfront.domain.usecase.notificationsUseCases.GetNotificationTimeUseCase
import com.example.healthybotfront.domain.usecase.notificationsUseCases.ScheduleNotificationUseCase
import com.example.healthybotfront.domain.usecase.notificationsUseCases.SetNotificationTimeUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.ResetPasswordUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.UpdateUserUseCase
import com.example.healthybotfront.presentacion.viewmodel.ForgotPasswordViewModel
import com.example.healthybotfront.presentacion.viewmodel.GetProgressPercentageViewModel
import com.example.healthybotfront.presentacion.viewmodel.GoalViewModel
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import com.example.healthybotfront.presentacion.viewmodel.NotificationViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.android.ext.koin.androidContext
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
    factory { ResetPasswordUseCase(get())}

    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ForgotPasswordViewModel ( get())}

    // --- Habit ---
    single { HabitRepository(get()) }

    factory { CreateHabitUseCase(get()) }
    factory { GetHabitsByUserIdUseCase(get()) }
    factory { DeleteHabitUseCase(get()) }
    factory { UpdateHabitUseCase(get()) }
    factory { GetHabitByIdUseCase(get()) }


    viewModel { HabitViewModel(get(), get(), get(), get(),get()) }


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
    factory { DeleteProgressUseCase (get()) }
    factory{ GetProgressByUserAndDateUseCase ( get()) }


    viewModel { ProgressViewModel(get(), get(),get(),get(),get()) }
    viewModel { GetProgressPercentageViewModel(get()) }

// --- Notifications ---
    single<NotificationTimeRepository> { NotificationTimeRepositoryImpl(androidContext()) }
    single { DailyNotificationScheduler() }

    factory { SetNotificationTimeUseCase(get(), get()) }
    factory { GetNotificationTimeUseCase(get()) }
    factory { ScheduleNotificationUseCase(get()) }

    viewModel { NotificationViewModel(get(), get()) }

}