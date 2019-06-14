package io.keepcoding.tareas.presentation.detail_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import io.keepcoding.tareas.R
import io.keepcoding.tareas.presentation.detail_task.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance())
                    .commitNow()
        }
    }
}
