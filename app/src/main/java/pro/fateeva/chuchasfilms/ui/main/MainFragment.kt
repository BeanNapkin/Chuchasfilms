package pro.fateeva.chuchasfilms.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.view.*
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.MainFragmentBinding


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
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFilmsFromLocalSource()

        val popularFilmslayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val popularFilmList = binding.recyclerPopularFilmList
        popularFilmList.layoutManager = popularFilmslayoutManager

//        val watchedNowFilmslayoutManager = LinearLayoutManager(
//            requireContext(), LinearLayoutManager.HORIZONTAL, false
//        )
//        val watchedNowFilmList = binding.recyclerWatchedNowFilmList
//        watchedNowFilmList.layoutManager = watchedNowFilmslayoutManager
//        watchedNowFilmList.adapter = FilmListAdapter{film -> openFilmDetails(film)}
//
//        val waitedFilmslayoutManager = LinearLayoutManager(
//            requireContext(), LinearLayoutManager.HORIZONTAL, false
//        )
//        val waitedFilmList = binding.recyclerWaitedFilmList
//        waitedFilmList.layoutManager = waitedFilmslayoutManager
//        waitedFilmList.adapter = FilmListAdapter{film -> openFilmDetails(film) }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.recyclerPopularFilmList.adapter = FilmListAdapter(appState.filmsList) { film ->
                    openFilmDetails(
                        film
                    )
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getFilmsFromLocalSource() }
                    .show()
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