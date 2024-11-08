package com.example.anime2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Método para obtener la lista de animes con paginación
    @GET("v4/anime")
    Call<ApiResponse> getAnimeList(
            @Query("page") int page,   // Número de página
            @Query("limit") int limit  // Número de elementos por página
    );

    // Método para obtener los episodios de un anime por su mal_id
    @GET("anime/{id}/episodes")
    Call<EpisodeResponse> getAnimeEpisodes(
            @Path("id") int malId,
            @Query("page") int page,    // Página actual
            @Query("limit") int limit   // Número de episodios por página
    );
}
