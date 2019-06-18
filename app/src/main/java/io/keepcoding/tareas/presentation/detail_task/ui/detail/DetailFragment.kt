package io.keepcoding.tareas.presentation.detail_task.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.util.extensions.observe
import io.keepcoding.util.extensions.setVisible
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.item_task.*
import kotlinx.android.synthetic.main.item_task.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindState()
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

    private fun showDetails(task: Task?) {

        contentTaskText.text = task?.content!!

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm a")
        val createAt = formatter.format(parser.parse(task.createdAt.toString()))
        dateTaskText.text = createAt

        if (task.isHighPriority) {
            priorityTex.text = "Priority: High"
            priorityTex.setTextColor(Color.parseColor("#ff0000"))
        } else {
            priorityTex.text = "Priority: Normal"
            priorityTex.setTextColor(Color.parseColor("#04993f"))
        }

        if (task.isFinished) {
            checkBox.isChecked = true
        }

    }

    override fun onResume() {
        super.onResume()
        val id = arguments?.getLong("id", 0)
        detailViewModel.loadTask(id!!)
    }

    private fun onLoadingState(isLoading: Boolean) {
        tasksDetailLoading.setVisible(isLoading)
    }
}
