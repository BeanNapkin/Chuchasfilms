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

        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        if (film != null) {
            binding.titleTextView.text = film.title
            binding.yearTextView.text = film.year
            binding.genreTextView.text = film.genre
            binding.ratingTextViewTextView.text = film.rating
            binding.descriptionTextViewTextView.text = film.description
            binding.castTextView.text = film.cast
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