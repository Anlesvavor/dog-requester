package com.example.linearandconstraintlayouts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.linearandconstraintlayouts.data.entities.Breed
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class BreedDetailFragment(
    val breed: Breed
) : DialogFragment() {

    private lateinit var viewModel: BreedDetailViewModel
    private lateinit var requestDogButton: Button

    companion object {
        fun newInstance(breed: Breed) = BreedDetailFragment(breed)
        const val TAG = "BreedDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.breed_detail_fragment, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        view?. let {
            requestDogButton = it.findViewById(R.id.button_detail_request_dog)

            requestDogButton.setOnClickListener {
                viewModel.requestDog(breed)
                dismiss()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreedDetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(childFragmentManager.beginTransaction()) {
            replace(R.id.frame_layout_summary_container, SummaryFragment(breed))
            commit()
        }
    }
}