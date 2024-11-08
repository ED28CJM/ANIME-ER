package com.example.anime2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class EpisodeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EpisodeAdapter episodeAdapter;
    private int malId;
    private int currentPage = 1;  // Página inicial
    private static final int LIMIT = 10;  // Número de episodios por página
    private List<Episode> episodeList = new ArrayList<>();
    private boolean isLoading = false; // Para evitar múltiples solicitudes simultáneas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

        recyclerView = findViewById(R.id.recyclerViewEpisodes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener el mal_id del intent
        malId = getIntent().getIntExtra("MAL_ID", -1);

        // Verificar que mal_id esté presente
        if (malId != -1) {
            fetchEpisodesData(malId, currentPage, LIMIT); // Cargar los primeros 10 episodios
        } else {
            Log.e("EpisodeListActivity", "Invalid mal_id");
        }

        // Agregar un listener para la paginación al final del RecyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Detecta si hemos llegado al final del RecyclerView
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastVisibleItemPosition() == episodeList.size() - 1) {
                    // Si no estamos cargando y hemos llegado al final, cargamos más episodios
                    currentPage++;
                    fetchEpisodesData(malId, currentPage, LIMIT); // Cargar más episodios
                }
            }
        });
    }

    private void fetchEpisodesData(int malId, int page, int limit) {
        isLoading = true;  // Marcar como cargando
        Moshi moshi = new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/") // Base URL de la API
                .addConverterFactory(MoshiConverterFactory.create(moshi)) // Conversor para Moshi
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Llamada a la API con los parámetros de paginación
        Call<EpisodeResponse> call = apiService.getAnimeEpisodes(malId, page, limit); // Llamada con paginación
        call.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call, Response<EpisodeResponse> response) {
                isLoading = false;  // Desmarcar como cargando
                if (response.isSuccessful() && response.body() != null) {
                    List<Episode> newEpisodes = response.body().getData();
                    Log.d("EpisodeListActivity", "Received episodes: " + newEpisodes.size()); // Verificar cantidad de episodios recibidos

                    if (newEpisodes != null && !newEpisodes.isEmpty()) {
                        episodeList.addAll(newEpisodes);  // Agregar los nuevos episodios a la lista existente
                        if (episodeAdapter == null) {
                            episodeAdapter = new EpisodeAdapter(episodeList);
                            recyclerView.setAdapter(episodeAdapter);
                        } else {
                            episodeAdapter.notifyDataSetChanged(); // Notificar que los datos se actualizaron
                        }
                    } else {
                        Log.e("EpisodeListActivity", "No more episodes to load.");
                    }
                } else {
                    Log.e("EpisodeListActivity", "Error: Unable to fetch episodes. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {
                isLoading = false;  // Desmarcar como cargando
                Log.e("EpisodeListActivity", "Network error: " + t.getMessage());
            }
        });
    }
}



