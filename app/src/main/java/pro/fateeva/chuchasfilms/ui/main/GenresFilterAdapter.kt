package pro.fateeva.chuchasfilms.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pro.fateeva.chuchasfilms.databinding.GenresFilterButtonBinding

class GenresFilterAdapter(
    var genres: List<GenreEnum>,
    private val onItemClick: (GenreEnum) -> Unit
) : RecyclerView.Adapter<GenresFilterAdapter.GenresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            GenresFilterButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    inner class GenresViewHolder(private val binding: GenresFilterButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: GenreEnum) {
            with(binding) {
                genresButton.text = genre.value
            }
            itemView.setOnClickListener { v: View? ->
                onItemClick.invoke(
                    genre
                )
            }
        }
    }
}