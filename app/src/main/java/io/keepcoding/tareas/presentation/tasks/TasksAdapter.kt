package io.keepcoding.tareas.presentation.tasks

import android.animation.ValueAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import kotlinx.android.synthetic.main.item_task.view.*



class TasksAdapter(
    private val onFinished: (task: Task) -> Unit
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
            with (itemView) {
                cardContentText.text = task.content

                taskFinishedCheck.isChecked = task.isFinished

                /*val dateCreateAt = Date.from(task.createdAt)
                val formatter = SimpleDateFormat("EEE, MMM d, ''yy")
                val formattedDate = formatter.format(dateCreatAt)
                "EEE, MMM d, ''yy" -> Wed, Jul 4, '01*/
                cardDateCreated.text = "Created on: ${task.createdAt.toString()}"

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

            if(animate) {
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