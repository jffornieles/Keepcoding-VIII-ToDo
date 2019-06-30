package io.keepcoding.tareas.presentation.edit_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailFragment
import io.keepcoding.tareas.presentation.edit_task.ui.edittask.EditTaskFragment
import kotlinx.android.synthetic.main.activity_main.*

class EditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task_activity)

        if (savedInstanceState == null) {
            val fragment = EditTaskFragment()
            val task = intent.getParcelableExtra<Task>("task")
            val args = Bundle()
            args.putParcelable("task", task)

            fragment.arguments = args

            setUpToolbar()
            setUpFragment(savedInstanceState, fragment)
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar as Toolbar)
        setTitle(R.string.edit_task_title)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    private fun setUpFragment(savedInstanceState: Bundle?, fragment: EditTaskFragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerEdit, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
