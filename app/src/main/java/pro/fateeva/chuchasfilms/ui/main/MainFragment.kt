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

    companion object { // Статическая часть класса. Та, которой не нужен объект для работы.
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
        inflater.inflate(R.menu.main_menu, menu) // Добавить меню из рамзетки в меню в аргументах
        menu.findItem(R.id.switchAdultFilms).isChecked = viewModel.getAdultFilmsPreference(requireContext()) // Проставить (или убрать) галочку из Preferences насчёт Фильмов 18+
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Клики по пунктам меню
        if (item.itemId == R.id.switchAdultFilms){ // Убеждаемся, что пункт меню именно тот, что нужен (чек бокс про Фильмы 18+)
            if (item.isChecked){ // Если галочка уже была, то убираем её
                item.isChecked = false
            } else {
                item.isChecked = true // Если галочки нет, то ставим её
            }

            viewModel.saveAdultFilmsPreference(requireContext(), item.isChecked) // Сохраняем настройки галочки в Preferences
            refresh() // Обновляем списки фильмов
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() { // Заполнение или перезаполнение всех recyclerView фильмами
        fillRecycleView(MovieTopList.POPULAR, recyclerPopularFilmList)
        fillRecycleView(MovieTopList.NOW_PLAYING, recyclerNowPlayingFilmList)
        fillRecycleView(MovieTopList.UPCOMING, recyclerUpcomingFilmList)
    }

    private fun fillRecycleView(movieTopList: MovieTopList, recyclerView: RecyclerView) {
        viewModel.getMoviesData(movieTopList).observe(viewLifecycleOwner, Observer { // Подписка на изменение состояния AppState в результате скачивания фильмов
            renderData( // Выполнится, когда придёт какой-то Appstate. Это отрисовака списков
                it, // Состояние Appstate, которое пришло по подписке
                movieTopList, // Какой лист
                recyclerView // Какой ресайклвью
            )
        })
        viewModel.getFilmsFromRemoteSource(movieTopList, requireContext()) // Запуск скачивания фильмов - ЭТО ВЫПОЛНИТСЯ ПЕРВЫМ В ЭТОЙ ФУНКЦИИ!
    }

    private fun renderData(
        appState: AppState,
        movieTopList: MovieTopList,
        recyclerView: RecyclerView
    ) {
        when (appState) {
            is AppState.Success -> { // Если успешно, то отрисовываем список фильмов, который лежит в Appstate
                binding.loadingLayout.visibility = View.GONE // Убираем кружок-загрузки
                recyclerView.adapter =
                    FilmListAdapter(appState.filmsList) { film -> // Тут скаченный список фильмов попадает в отрисовку в ресайклвью
                        openFilmDetails( // Клик по фильму - открытие фрагмента с "О фильме"
                            film
                        )
                    }
            }
            is AppState.Error -> { // Если ошибка
                Log.e(null, "Ошибка при скачке фильмов", appState.error)
                binding.loadingLayout.visibility = View.GONE // Убираем кружок-загрузки
                binding.root.showSnackbar( // Показываем ошибку
                    R.string.error,
                    R.string.reload // С кнопкой "Попробовать скачать фильмы снова"
                ) { viewModel.getFilmsFromRemoteSource(movieTopList, requireContext()) } // Пробуем снова скачать
            }
            AppState.Loading -> { // Если загрузка, то показываем кружок-загрузки
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