package com.example.tinkoffapp.ui.hot_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tinkoffapp.databinding.FragmentHotBinding
import com.example.tinkoffapp.ui.ViewModelFactory
import com.example.tinkoffapp.ui.common.RecyclerAdapter
import com.example.tinkoffapp.ui.view_states.ViewState
import com.google.android.material.tabs.TabLayout


class MainFragment : Fragment() {
    private val adapter = RecyclerAdapter()
    private lateinit var viewModel: FragmentViewModel
    private var category: String = "latest"

    private lateinit var _binding: FragmentHotBinding
    private val binding get() = _binding!!

    private val stateObserver = Observer<ViewState> { state -> state?.let { render(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory).get(FragmentViewModel::class.java)
        binding.recyclerView.adapter = adapter
        observeViewModel()
        viewModel.start()
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> category = "latest"
                    1 -> category = "top"
                    2 -> category = "hot"
                }
                viewModel.resetPage()
                binding.btnPrev.isVisible = false
                adapter.clearList()
                viewModel.getPosts(category)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })

        viewModel.getPosts(category)

        binding.btnNext.setOnClickListener {
            viewModel.nextItem()
        }
        binding.btnPrev.setOnClickListener {
            viewModel.prevItem()
        }
        binding.btnReload.setOnClickListener {
            viewModel.reloadItem()
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    private fun render(state: ViewState) {
        var position: Int

        if (state.isReload) {
            binding.pbLoading.isVisible = true
            viewModel.getPosts(category)
        }

        with(binding.recyclerView) {
            layoutManager?.let { manager ->
                if (manager is LinearLayoutManager) {
                    position = manager.findLastVisibleItemPosition()

                    if (position == adapter!!.itemCount - 2) {
                        viewModel.getPosts(category)
                    }

                    if (state.isNext) {
                        position = position.inc()
                        if (!binding.btnPrev.isVisible)
                            binding.btnPrev.isVisible = true
                    }

                    if (state.isPrev) {
                        position = position.dec()
                        if (position == 0)
                            binding.btnPrev.isVisible = false
                    }
                    this.post { scrollToPosition(position) }
                }
            }
        }

        if (state.isLoading) {
            binding.tvEmpty.isVisible = false
            binding.tvError.isVisible = false
            binding.btnNext.isVisible = false
            binding.pbLoading.isVisible = true
        }

        if (state.isSuccess) {
            binding.tvError.isVisible = false
            binding.btnReload.isVisible = false
            binding.btnNext.isVisible = true
            binding.recyclerView.isVisible = true
            binding.pbLoading.isVisible = false
        }

        if (state.isError) {
            binding.btnNext.isVisible = false
            binding.btnPrev.isVisible = false
            binding.recyclerView.isVisible = false
            binding.pbLoading.isVisible = false
            binding.tvError.isVisible = true
            binding.btnReload.isVisible = true
        }

        if (state.items.isNotEmpty()) {
            adapter.addToLst(state.items)
        }

        if (state.isEmpty) {
            binding.tvEmpty.isVisible = true
            binding.pbLoading.isVisible = false
            binding.recyclerView.isVisible=false
        }
    }
}