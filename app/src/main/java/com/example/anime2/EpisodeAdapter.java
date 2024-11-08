package com.example.anime2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodeList;

    public EpisodeAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout para cada episodio
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.title.setText(episode.getTitle());
        holder.malId.setText("ID: " + episode.getMal_id());
        holder.aired.setText("Fecha: " + episode.getAired());  // Establecer fecha de emisión
        holder.score.setText("Puntuación: " + episode.getScore());  // Establecer puntuación
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView malId, title, aired, score;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            malId = itemView.findViewById(R.id.episode_mal_id);  // Asegúrate de que el ID coincida con el XML
            title = itemView.findViewById(R.id.episode_title);    // Asegúrate de que el ID coincida con el XML
            aired = itemView.findViewById(R.id.episode_aired);    // Asegúrate de que el ID coincida con el XML
            score = itemView.findViewById(R.id.episode_score);    // Asegúrate de que el ID coincida con el XML
        }
    }
}
