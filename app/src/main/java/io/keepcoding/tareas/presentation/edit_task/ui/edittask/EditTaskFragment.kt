package io.keepcoding.tareas.presentation.edit_task.ui.edittask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class EditTaskFragment : Fragment() {

    val editViewModel: EditTaskViewModel by viewModel()
    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = arguments?.let {
            it.getParcelable("task")
        }

        bindState()
        bindActions()
    }

    private fun bindState() {
        with (editViewModel) {
            observe(isLoadingState) {
                onLoadingState(it)
            }
            observe(tasksState) {
                showDetails(it)
            }
            observe(closeAction) {
                it.consume {
                    onClose()
                }
            }
        }
    }

    private fun bindActions() {

        buttonSave.setOnClickListener {
            AlertDialog.Builder(activity!!)
                    .setTitle("Edit")
                    .setMessage("Edit task? ")
                    .setPositiveButton("Yes") { _, _ ->
                        val taskContent = contentTaskText.text.toString()
                        val taskIsPriority =  checkPriorityDetail.isChecked
                        val taskIsCompleted = checkTaskCompleted.isChecked
                        editViewModel.updateTask(task!!.id, taskContent, task!!.createdAt, taskIsPriority, taskIsCompleted)
                    }
                    .setNegativeButton("No", null)
                    .create()
                    .show()


        }
    }

    private fun showDetails(task: Task?) {

        task?.let {
            contentTaskText.setText(it.content)
            contentTaskText.selectAll()

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm a")
            val createAt = formatter.format(parser.parse(it.createdAt.toString()))
            dateTaskText.text = createAt

            if (it.isHighPriority) {
                checkPriorityDetail.isChecked = true
            }

            if (it.isFinished) {
                checkTaskCompleted.isChecked = true
            }
        }

    }

    override fun onResume() {
        super.onResume()
        editViewModel.loadTask(task?.id!!)
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksDetailLoading.setVisible(isLoading)
    }

    private fun onClose() {
        requireActivity().finish()
    }
}

