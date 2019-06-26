package io.keepcoding.tareas.presentation.tasks

import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory
import io.keepcoding.util.Event
import io.keepcoding.util.extensions.call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(
    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
): BaseViewModel(dispatcherFactory) {

    val tasksState = MutableLiveData<List<Task>>()
    val isLoadingState = MutableLiveData<Boolean>()
    val deleteState = MutableLiveData<List<Task>>()

    fun loadTasks() {
        launch {
            showLoading(true)

            val result = withContext(dispatcherFactory.getIO()) { taskRepository.getAll() }
            tasksState.value = result

            showLoading(false)
        }
    }

    fun toggleFinished(task: Task) {
        val newTask = task.copy(isFinished = !task.isFinished)

        launch(dispatcherFactory.getIO()) {
            taskRepository.updateTask(newTask)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        isLoadingState.value = isLoading
    }

    fun deleteTask(task: Task) {
        launch {


            withContext(dispatcherFactory.getIO()) { taskRepository.deleteTask(task) }
            val result = withContext(dispatcherFactory.getIO()) { taskRepository.getAll() }
            deleteState.value = result
        }

    }

}