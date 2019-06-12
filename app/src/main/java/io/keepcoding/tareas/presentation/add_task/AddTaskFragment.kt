package io.keepcoding.tareas.presentation.add_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.tareas.R
import io.keepcoding.util.extensions.consume
import io.keepcoding.util.extensions.observe
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddTaskFragment : Fragment() {

    val addTaskViewModel: AddTaskViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        bindActions()
    }

    private fun bindActions() {
        saveButton.setOnClickListener {
            val taskContent = taskContent.text.toString()
            addTaskViewModel.save(taskContent)
        }
    }

    private fun bindEvents() {
        with (addTaskViewModel) {
            observe(closeAction) {
                it.consume {
                    onClose()
                }
            }
        }
    }

    private fun onClose() {
        requireActivity().finish()
    }

}