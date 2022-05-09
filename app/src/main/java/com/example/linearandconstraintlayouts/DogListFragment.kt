package com.example.linearandconstraintlayouts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.linearandconstraintlayouts.adapter.BreedListRecyclerViewAdapter
import com.example.linearandconstraintlayouts.adapter.DogListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = DogListFragment()
    }

    private lateinit var viewModel: DogListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dog_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DogListViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view_dog_list)

        val onDismissCallback = { item: DogListRecyclerViewAdapter.DogItem ->
           viewModel.dismissDogById(item.id)
        }

        val onDetailCallback = { item: DogListRecyclerViewAdapter.DogItem ->
            viewModel.getDogDetailsById(item.id)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = DogListRecyclerViewAdapter(
            data = viewModel.listItems.value ?: emptyList(),
            onDismissClickListener = onDismissCallback,
            onDetailsClickListener = onDetailCallback
        )

        viewModel.listItems.observe(this.viewLifecycleOwner, Observer { items ->
            (recyclerView.adapter as DogListRecyclerViewAdapter).updateData(items)
            (recyclerView.adapter as DogListRecyclerViewAdapter).notifyDataSetChanged()
        })

        viewModel.selectedDogDetail.observe(this.viewLifecycleOwner, Observer { selectedBreed ->
            selectedBreed?.let {
                DogDetailFragment(
                    selectedBreed,
                    { viewModel.closeDetailDialog() }
                ).show(childFragmentManager, DogDetailFragment.TAG)
            }
        })

        viewModel.fetchDogs()
    }

}