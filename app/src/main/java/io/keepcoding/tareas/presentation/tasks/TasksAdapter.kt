package io.keepcoding.tareas.presentation.tasks

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.add_task.AddTaskActivity
import io.keepcoding.tareas.presentation.detail_task.DetailActivity
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_task.*
import kotlinx.android.synthetic.main.item_task.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat


class TasksAdapter(
    private val onFinished: (task: Task) -> Unit,
    private val listener: (task: Task) -> Unit,
    private val deleteTask: (task: Task) -> Unit,
    private val listenerEdit: (task: Task) -> Unit
) : ListAdapter<Task, TasksAdapter.TaskViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            with(itemView) {

                cardContentText.text = task.content

                taskFinishedCheck.isChecked = task.isFinished

                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm a")
                val createAt = formatter.format(parser.parse(task.createdAt.toString()))
                cardDateCreated.text = createAt

                if (task.isHighPriority) {
                    textViewPriority.text = "High priority"
                    textViewPriority.setTextColor(Color.RED)
                } else {
                    textViewPriority.text = "Normal priority"
                    textViewPriority.setTextColor(Color.GREEN)
                }

                if (task.isFinished) {
                    applyStrikeThrough(cardContentText, task.content)
                } else {
                    removeStrikeThrough(cardContentText, task.content)
                }

                taskFinishedCheck.setOnClickListener {
                    onFinished(task)

                    if (taskFinishedCheck.isChecked) {
                        applyStrikeThrough(cardContentText, task.content, animate = true)
                    } else {
                        removeStrikeThrough(cardContentText, task.content, animate = true)
                    }
                }

                setOnClickListener {
                    listener(task)
                }

                buttonEdit.setOnClickListener {
                    listenerEdit(task)
                }

                buttonDelete.setOnClickListener {
                    AlertDialog.Builder(it.context)
                            .setTitle("Delete")
                            .setMessage("Delete task? ")
                            .setPositiveButton("Yes") { _, _ ->
                                deleteTask(task)
                            }
                            .setNegativeButton("No", null)
                            .create()
                            .show()

                }

            }
        }

        private fun applyStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animate) {
                ValueAnimator.ofInt(content.length).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                span.setSpan(spanStrike, 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                view.text = span
            }
        }

        private fun removeStrikeThrough(view: TextView, content: String, animate: Boolean = false) {
            val span = SpannableString(content)
            val spanStrike = StrikethroughSpan()

            if (animate) {
                ValueAnimator.ofInt(content.length, 0).apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
            } else {
                view.text = content
            }
        }

    }

}