package io.keepcoding.tareas.presentation.detail_task.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    val detailViewModel: DetailViewModel by viewModel()
    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = arguments?.let {
            it.getParcelable("task")
        }
        if (task == null) {
             // TODO
        }
        bindState()
        bindActions()
    }

    private fun bindState() {
        with (detailViewModel) {
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
        imageEdit.setOnClickListener {

            checkPriorityDetail.isEnabled = true
            contentTaskText.isEnabled = true
            checkTaskCompleted.isEnabled = true
            buttonSave.isEnabled = true
            contentTaskText.selectAll()

        }

        imageDelete.setOnClickListener {
            AlertDialog.Builder(activity!!)
                    .setTitle("Delete")
                    .setMessage("Delete task? ")
                    .setPositiveButton("Yes") { _, _ ->
                        detailViewModel.deleteTask(task!!)
                    }
                    .setNegativeButton("No", null)
                    .create()
                    .show()

        }

        buttonSave.setOnClickListener {
            AlertDialog.Builder(activity!!)
                    .setTitle("Edit")
                    .setMessage("Edit task? ")
                    .setPositiveButton("Yes") { _, _ ->
                        val taskContent = contentTaskText.text.toString()
                        val taskIsPriority =  checkPriorityDetail.isChecked
                        val taskIsCompleted = checkTaskCompleted.isChecked
                        detailViewModel.updateTask(task!!.id, taskContent, task!!.createdAt, taskIsPriority, taskIsCompleted)
                    }
                    .setNegativeButton("No", null)
                    .create()
                    .show()


        }
    }

    private fun showDetails(task: Task?) {

        task?.let {
            //contentTaskText.hint = it.content!!
            contentTaskText.setText(it.content)

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
        detailViewModel.loadTask(task?.id!!)
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksDetailLoading.setVisible(isLoading)
    }

    private fun onClose() {
        requireActivity().finish()
    }
}
