package io.keepcoding.tareas.presentation.detail_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.presentation.add_task.AddTaskFragment
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        if (savedInstanceState == null) {

            /*val fragment = DetailFragment()
            val id = intent.getStringExtra("id")

            val args = Bundle()
            args.putString("id", id)

            fragment.arguments = args*/

            setUpToolbar()
            setUpFragment(savedInstanceState)

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

    private fun setUpFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, DetailFragment())
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
