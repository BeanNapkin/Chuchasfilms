package pro.fateeva.chuchasfilms.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import kotlinx.android.synthetic.main.about_film_fragment.*
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.AboutFilmFragmentBinding
import pro.fateeva.chuchasfilms.ui.main.SnackbarExtensions.showSnackbar
import java.util.concurrent.ExecutionException

class AboutFilmFragment : Fragment() {

    private var _binding: AboutFilmFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AboutFilmViewModel
    private lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AboutFilmFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutFilmViewModel::class.java)

        arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let {
            film = it
            with(binding) {
                titleTextView.text = it.title
                yearTextView.text = it.year
                genreTextView.text = it.genres?.joinToString(", ")
                ratingTextViewTextView.text = it.rating
                descriptionTextViewTextView.text = it.description
                coverImageView.load(it.posterPath)
            }
        }

        viewModel.getHistoryLiveData().observe(viewLifecycleOwner, Observer {
            when(it){
                is AboutState.Success ->
                    noteEditText.setText(it.historyEntity?.noteAboutFilm)
                is AboutState.Error -> {
                    Log.e(null, "Ошибка при загрузке истории", it.error)
                    binding.root.showSnackbar(
                        R.string.error,
                        R.string.reload
                    ) { viewModel.getHistory(film.id) }
                }
            }
        })

        viewModel.getHistory(film.id)

        viewModel.saveFilmToViewed(film.id)

        noteEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                viewModel.saveNote(film, s.toString())
            }
        })
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