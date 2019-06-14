package io.keepcoding.tareas.presentation.detail_task.ui.detail

import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory

class DetailViewModel(
    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
): BaseViewModel(dispatcherFactory) {
    // TODO: Implement the ViewModel
}
