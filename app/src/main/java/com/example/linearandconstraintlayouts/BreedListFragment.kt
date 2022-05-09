package com.example.linearandconstraintlayouts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.linearandconstraintlayouts.adapter.BreedListRecyclerViewAdapter
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.factories.BreedFactory
import com.example.linearandconstraintlayouts.data.factories.DogFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var detailFragment: BreedDetailFragment

    companion object {
        fun newInstance() = BreedListFragment()
    }

    private lateinit var viewModel: BreedListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.breed_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BreedListViewModel::class.java)

        recyclerView = view.findViewById(R.id.breed_list_recycler_view)
        val onBreedClickListener = { breedItem: BreedListRecyclerViewAdapter.BreedItem ->
            // the fetchBreedById is warranted to retrieve the full Breed because BreedItem
            // is just a strip down version of an existing Breed record.
            val breed = viewModel.fetchBreedById(breedItem.id)!!
            BreedDetailFragment(breed).show(childFragmentManager, BreedDetailFragment.TAG)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = BreedListRecyclerViewAdapter(
            viewModel.listItems.value ?: listOf(BreedListRecyclerViewAdapter.BreedItem(name = "loading...", id = 1, imageUrl = null)),
            onBreedClickListener
        )

        viewModel.listItems.observe(this.viewLifecycleOwner, Observer { items ->
            (recyclerView.adapter as BreedListRecyclerViewAdapter).updateData(items)
            (recyclerView.adapter as BreedListRecyclerViewAdapter).notifyDataSetChanged()
        })

        viewModel.fetchBreeds()
    }

}