package pro.fateeva.chuchasfilms.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.FilmCellBinding
import java.util.*

class FilmListAdapter(
    var filmsList: List<Film>,
    private val onItemClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmListAdapter.FilmListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        return FilmListViewHolder(
            FilmCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.bind(filmsList[position])
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    inner class FilmListViewHolder(private val binding: FilmCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Film) {
            with(binding) {
                movieTitleTextView.text = film.title
                yearTextView.text = film.year
                ratingTextView.text = film.rating
            }
            itemView.setOnClickListener { v: View? ->
                onItemClick.invoke(
                    film
                )
            }
        }
    }
}