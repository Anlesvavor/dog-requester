package com.example.linearandconstraintlayouts

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.linearandconstraintlayouts.data.entities.Breed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailFragment(
    val breed: Breed,
    val onDismiss: () -> Unit
) : DialogFragment() {

    companion object {
        fun newInstance(breed: Breed, onDismiss: () -> Unit) = DogDetailFragment(breed, onDismiss)
        const val TAG = "DogDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dog_detail_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(childFragmentManager.beginTransaction()) {
            replace(R.id.dog_detail_frame_layout_summary_container, SummaryFragment(breed))
            commit()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        onDismiss()
    }
}