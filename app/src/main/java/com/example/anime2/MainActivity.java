package com.example.anime2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimeAdapter animeAdapter;
    private List<Anime> animeList = new ArrayList<>();
    private int currentPage = 1;  // Página inicial
    private static final int LIMIT = 7;  // Número de animes por página

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configuramos el adaptador
        animeAdapter = new AnimeAdapter(animeList, malId -> {
            // Crear la intención para abrir la nueva actividad
            Intent intent = new Intent(MainActivity.this, EpisodeListActivity.class);
            intent.putExtra("MAL_ID", malId); // Pasar el mal_id
            startActivity(intent);
        });

        recyclerView.setAdapter(animeAdapter);

        // Cargar los datos de los animes
        fetchAnimeData(currentPage, LIMIT);

        // Detectar cuando se llega al final del RecyclerView para cargar más
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)) { // Si llegamos al final
                    currentPage++;  // Aumentar la página
                    fetchAnimeData(currentPage, LIMIT);  // Cargar más animes
                }
            }
        });
    }

    private void fetchAnimeData(int page, int limit) {
        Moshi moshi = new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/") // Base URL de la API
                .addConverterFactory(MoshiConverterFactory.create(moshi)) // Conversor para Moshi
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Realizar la llamada a la API para obtener la lista de animes con paginación
        Call<ApiResponse> call = apiService.getAnimeList(page, limit);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa, obtenemos la lista de animes
                    List<Anime> newAnimeList = response.body().getData();
                    animeList.addAll(newAnimeList);  // Agregar los nuevos animes a la lista existente

                    // Notificar al adaptador para que actualice la lista
                    animeAdapter.notifyDataSetChanged();
                } else {
                    Log.e("MainActivity", "Error: Unable to fetch data. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MainActivity", "Network error: " + t.getMessage());
            }
        });
    }
}