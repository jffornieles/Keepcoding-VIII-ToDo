package io.keepcoding.tareas.presentation.add_task

import androidx.lifecycle.MutableLiveData
import io.keepcoding.tareas.domain.TaskRepository
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.BaseViewModel
import io.keepcoding.util.DispatcherFactory
import io.keepcoding.util.Event
import io.keepcoding.util.extensions.call
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant

class AddTaskViewModel(
    private val taskRepository: TaskRepository,
    private val dispatcherFactory: DispatcherFactory
) : BaseViewModel(dispatcherFactory) {

    val closeAction = MutableLiveData<Event<Unit>>()

    fun save(content: String, isPriority: Boolean) {
        if (!validateContent(content)) {
            return
        }

        launch {
            withContext(dispatcherFactory.getIO()) {
                taskRepository.addTask(Task(0, content, Instant.now(), isPriority, false))
            }
            closeAction.call()
        }
    }

    private fun validateContent(content: String): Boolean =
        content.isNotEmpty()

}