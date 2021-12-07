package pro.fateeva.chuchasfilms.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favourite_films_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.FavouriteFilmsFragmentBinding
import pro.fateeva.chuchasfilms.databinding.MainFragmentBinding
import pro.fateeva.chuchasfilms.ui.main.SnackbarExtensions.showSnackbar


class FavouriteFilmsFragment : Fragment() {

    private var _binding: FavouriteFilmsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FavouriteFilmsFragment()
    }

    private lateinit var viewModel: FavouriteFilmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavouriteFilmsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouriteFilmsViewModel::class.java)

        val favouriteFilmsLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val favouriteFilmList = binding.recyclerFavouriteFilmList
        favouriteFilmList.layoutManager = favouriteFilmsLayoutManager

        fillRecycleView()
    }

    private fun fillRecycleView() {
        viewModel.getfavouriteMoviesLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getFavouriteFilms()
    }

    private fun renderData(
        appState: AppState
    ) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.recyclerFavouriteFilmList.adapter =
                    FilmListAdapter(appState.filmsList) { film ->
                        openFilmDetails(
                            film
                        )
                    }
            }
            is AppState.Error -> {
                Log.e("chucha", "Ошибка при скачке фильмов", appState.error)
                binding.loadingLayout.visibility = View.GONE
                binding.root.showSnackbar(
                    R.string.error,
                    R.string.reload
                ) { viewModel.getFavouriteFilms() }
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    fun openFilmDetails(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(AboutFilmFragment.BUNDLE_EXTRA, film)
        parentFragmentManager.beginTransaction()
            .add(R.id.container, AboutFilmFragment.newInstance(bundle))
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}