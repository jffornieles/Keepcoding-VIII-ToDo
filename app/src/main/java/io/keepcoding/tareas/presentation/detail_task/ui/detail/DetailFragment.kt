package io.keepcoding.tareas.presentation.detail_task.ui.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.keepcoding.tareas.R
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    val detailViewModel: DetailViewModel by viewModel()

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bindEvents()
//        bindActions()
    }

    /*private fun bindActions() {
        // TODO
    }

    private fun bindEvents() {
        // TODO
    }*/


}
