package io.keepcoding.tareas.presentation.detail_task.ui.detail

import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory
import io.keepcoding.util.extensions.call
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
): BaseViewModel(dispatcherFactory) {

    val isLoadingState = MutableLiveData<Boolean>()
    val tasksState = MutableLiveData<Task>()

    fun loadTask(id: Long) {
        launch {
            showLoading(true)

            val result = withContext(dispatcherFactory.getIO()) { taskRepository.getTaskById(id) }
            tasksState.value = result

            showLoading(false)
        }
    }

    fun deleteTask(task: Task) {
        launch {
            withContext(dispatcherFactory.getIO()) { taskRepository.deleteTask(task)}
        }
    }

    fun updateTask(task: Task) {
        launch {
            withContext(dispatcherFactory.getIO()) { taskRepository.updateTask(task)}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        isLoadingState.value = isLoading
    }

}
