package io.keepcoding.tareas.di

import androidx.room.Room
import io.keepcoding.tareas.data.repository.FakeRepository
import io.keepcoding.tareas.data.repository.TaskRepositoryImpl
import io.keepcoding.tareas.data.repository.local.TaskDatabase
import io.keepcoding.tareas.data.repository.mapper.TaskEntityMapper
import io.keepcoding.tareas.data.repository.mapper.TaskMapper
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.presentation.add_task.AddTaskViewModel
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailViewModel
import io.keepcoding.tareas.presentation.edit_task.ui.edittask.EditTaskViewModel
import io.keepcoding.tareas.presentation.tasks.TasksViewModel
import io.keepcoding.util.AppDispatcherFactory
import io.keepcoding.util.DispatcherFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java,
            "tasks.db"
        ).build()
    }

    factory {
        get<TaskDatabase>().getTaskDao()
    }

    single<TaskRepository> {
        TaskRepositoryImpl(get(), get(), get())
    }

    single {
        TaskMapper()
    }

    single {
        TaskEntityMapper()
    }

    single<DispatcherFactory> {
        AppDispatcherFactory()
    }

    viewModel {
        TasksViewModel(get(), get())
    }

    viewModel {
        AddTaskViewModel(get(), get())
    }

    viewModel {
        DetailViewModel(get(), get())
    }

    viewModel {
        EditTaskViewModel(get(), get())
    }

}