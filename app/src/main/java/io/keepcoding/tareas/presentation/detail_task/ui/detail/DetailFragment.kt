package io.keepcoding.tareas.presentation.detail_task.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailFragment : DialogFragment() {

//    companion object {
//        const val PARAM_TASK = "task"
//
//        fun newInstance(task: Task): DetailFragment =
//            DetailFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(PARAM_TASK, task)
//                }
//            }
//    }

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
            // dismiss()
            Log.d("ToDo","Task is Null!!")
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
        }
    }

    private fun bindActions() {
        imageEdit.setOnClickListener {


            checkPriorityDetail.isEnabled = true
            contentTaskText.isEnabled = true
            checkTaskCompleted.isEnabled = true
            buttonSave.isEnabled = true

            detailViewModel.updateTask(task!!)
        }

        imageDelete.setOnClickListener {
            detailViewModel.deleteTask(task!!)
        }
    }

    private fun showDetails(task: Task?) {

        task?.let {
            contentTaskText.hint = it.content!!

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
        // val id = arguments?.getLong("id", 0)
        detailViewModel.loadTask(task?.id!!)
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksDetailLoading.setVisible(isLoading)
    }
}
