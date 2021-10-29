package pro.fateeva.chuchasfilms.ui.main

import android.app.ProgressDialog.show
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.services.FilmService
import pro.fateeva.chuchasfilms.services.FilmService.Companion.BROADCAST_ACTION
import pro.fateeva.chuchasfilms.ui.main.SnackbarExtensions.showSnackbar


class MainServiceFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getParcelableArrayExtra(FilmService.EXTRA_DATA)?.map{it as Film}
            renderData(AppState.Success(data ?: emptyList()))
        }
    }

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

        val popularFilmslayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val popularFilmList = binding.recyclerPopularFilmList
        popularFilmList.layoutManager = popularFilmslayoutManager
    }

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(receiver, IntentFilter(BROADCAST_ACTION));
        getFilmsFromService()
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(receiver);
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.recyclerPopularFilmList.adapter =
                    FilmListAdapter(appState.filmsList) { film ->
                        openFilmDetails(
                            film
                        )
                    }
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.root.showSnackbar(
                    R.string.error,
                    R.string.reload
                ) { getFilmsFromService() }
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun getFilmsFromService(){
        renderData(AppState.Loading)
        FilmService.startActionLoadFilms(requireContext())
    }

    fun openFilmDetails(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(AboutFilmFragment.BUNDLE_EXTRA, film)
        parentFragmentManager.beginTransaction()
            .add(R.id.container, AboutFilmFragment.newInstance(bundle))
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    companion object {
        fun newInstance() = MainServiceFragment()
    }
}