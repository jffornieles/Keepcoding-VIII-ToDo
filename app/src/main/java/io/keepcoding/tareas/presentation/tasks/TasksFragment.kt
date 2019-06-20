package io.keepcoding.tareas.presentation.tasks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.detail_task.DetailActivity
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailFragment
import io.keepcoding.util.EqualSpacingItemDecoration
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {

    val adapter: TasksAdapter by lazy {
        TasksAdapter (
            {
                tasksViewModel.toggleFinished(it)
            },
            {
                launchTaskDetail(it)
            }
        )
    }

    val tasksViewModel: TasksViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        bindState()
    }

    private fun setUpRecycler() {
        with (tasksRecycler) {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = this@TasksFragment.adapter
            addItemDecoration(EqualSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.card_margin)))
        }
    }

    private fun bindState() {
        with (tasksViewModel) {
            observe(isLoadingState) {
                onLoadingState(it)
            }
            observe(tasksState) {
                onTasksLoaded(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.loadTasks()
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksLoading.setVisible(isLoading)
    }

    private fun onTasksLoaded(tasks: List<Task>) {
        adapter.submitList(tasks)
    }

    fun launchTaskDetail(task: Task) {
        val intent = Intent(context, DetailActivity()::class.java)
        intent.putExtra("task", task)
        //intent.getParcelableExtra<Task>("task")
        //val fragment = DetailFragment.newInstance(task)
        //fragment.show(childFragmentManager, null)
        startActivity(intent)
    }

}