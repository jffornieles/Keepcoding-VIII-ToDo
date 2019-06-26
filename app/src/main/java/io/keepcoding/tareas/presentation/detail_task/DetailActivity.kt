package io.keepcoding.tareas.presentation.detail_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.domain.model.Task
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        if (savedInstanceState == null) {

            val fragment = DetailFragment()
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
        setTitle(R.string.detail_task_title)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    private fun setUpFragment(savedInstanceState: Bundle?, fragment: DetailFragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerDetail, fragment)
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
