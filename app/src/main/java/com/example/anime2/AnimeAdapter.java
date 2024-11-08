package com.example.anime2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList;
    private OnItemClickListener listener;

    public AnimeAdapter(List<Anime> animeList, OnItemClickListener listener) {
        this.animeList = animeList;
        this.listener = listener;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.title.setText(anime.getTitle());
        holder.malId.setText("ID: " + anime.getMal_id());
        holder.episodes.setText("EPISODIOS: " + anime.getEpisodes());

        // Obtener la URL de la imagen
        String imageUrl = anime.getImages().getJpg().getImage_url();
        Picasso.get().load(imageUrl).into(holder.imageView);

        // Manejar clic en la imagen
        holder.imageView.setOnClickListener(v -> listener.onItemClick(anime.getMal_id()));
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView malId, title, episodes;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.anime_image);
            malId = itemView.findViewById(R.id.mal_id);
            title = itemView.findViewById(R.id.title);
            episodes = itemView.findViewById(R.id.episodes);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int malId);
    }
}
