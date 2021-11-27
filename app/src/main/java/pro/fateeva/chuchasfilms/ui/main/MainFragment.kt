package pro.fateeva.chuchasfilms.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.MainFragmentBinding
import pro.fateeva.chuchasfilms.ui.main.SnackbarExtensions.showSnackbar


class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val popularFilmslayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val popularFilmList = binding.recyclerPopularFilmList
        popularFilmList.layoutManager = popularFilmslayoutManager

        val nowPlayingFilmslayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val nowPlayingFilmList = binding.recyclerNowPlayingFilmList
        nowPlayingFilmList.layoutManager = nowPlayingFilmslayoutManager

        val upcomingFilmslayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val upcomingFilmList = binding.recyclerUpcomingFilmList
        upcomingFilmList.layoutManager = upcomingFilmslayoutManager

        refresh()

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.switchAdultFilms).isChecked = viewModel.getAdultFilmsPreference(requireContext())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.switchAdultFilms){
            if (item.isChecked){
                item.isChecked = false
            } else {
                item.isChecked = true
            }

            viewModel.saveAdultFilmsPreference(requireContext(), item.isChecked)
            refresh()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        fillRecycleView(MovieTopList.POPULAR, recyclerPopularFilmList)
        fillRecycleView(MovieTopList.NOW_PLAYING, recyclerNowPlayingFilmList)
        fillRecycleView(MovieTopList.UPCOMING, recyclerUpcomingFilmList)
    }

    private fun fillRecycleView(movieTopList: MovieTopList, recyclerView: RecyclerView) {
        viewModel.getMoviesData(movieTopList).observe(viewLifecycleOwner, Observer {
            renderData(
                it,
                movieTopList,
                recyclerView
            )
        })
        viewModel.getFilmsFromRemoteSource(movieTopList, requireContext())
    }

    private fun renderData(
        appState: AppState,
        movieTopList: MovieTopList,
        recyclerView: RecyclerView
    ) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                recyclerView.adapter =
                    FilmListAdapter(appState.filmsList) { film ->
                        openFilmDetails(
                            film
                        )
                    }
            }
            is AppState.Error -> {
                Log.e(null, "Ошибка при скачке фильмов", appState.error)
                binding.loadingLayout.visibility = View.GONE
                binding.root.showSnackbar(
                    R.string.error,
                    R.string.reload
                ) { viewModel.getFilmsFromRemoteSource(movieTopList, requireContext()) }
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