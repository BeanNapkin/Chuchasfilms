package pro.fateeva.chuchasfilms.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.AboutFilmFragmentBinding
import pro.fateeva.chuchasfilms.databinding.MainFragmentBinding

class AboutFilmFragment : Fragment() {

    private var _binding: AboutFilmFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = AboutFilmFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let {
            with(binding) {
                titleTextView.text = it.title
                yearTextView.text = it.year
                genreTextView.text = it.genre
                ratingTextViewTextView.text = it.rating
                descriptionTextViewTextView.text = it.description
                castTextView.text = it.cast
            }
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): AboutFilmFragment {
            val fragment = AboutFilmFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}