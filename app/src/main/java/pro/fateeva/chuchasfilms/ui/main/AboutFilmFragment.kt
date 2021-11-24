package pro.fateeva.chuchasfilms.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import pro.fateeva.chuchasfilms.databinding.AboutFilmFragmentBinding

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
                genreTextView.text = it.genres?.joinToString(", ")
                ratingTextViewTextView.text = it.rating
                descriptionTextViewTextView.text = it.description
                castTextView.text = it.cast
                coverImageView.load(it.posterPath)
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