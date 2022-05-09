package com.example.linearandconstraintlayouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.linearandconstraintlayouts.data.entities.Breed

class SummaryFragment(private val breed: Breed) : Fragment() {

    private lateinit var breedNameTextView: TextView
    private lateinit var lifeSpanTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var breedForTextView: TextView
    private lateinit var breedGroupTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary_fragment, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        view?. let {
            breedNameTextView = it.findViewById(R.id.text_view_detail_breed_name)
            lifeSpanTextView = it.findViewById(R.id.text_view_detail_life_span)
            weightTextView = it.findViewById(R.id.text_view_detail_weight)
            heightTextView = it.findViewById(R.id.text_view_detail_height)
            breedForTextView = it.findViewById(R.id.text_view_detail_breed_for)
            breedGroupTextView = it.findViewById(R.id.text_view_detail_breed_group)

            breedNameTextView.text = breed.name
            lifeSpanTextView.text = breed.lifeSpan
            weightTextView.text = breed.weight
            heightTextView.text = breed.height
            breedForTextView.text = breed.bredFor
            breedGroupTextView.text = breed.breedGroup
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(breed: Breed) = SummaryFragment(breed)
    }
}