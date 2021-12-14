package piotr.example.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {
    @GET("search/repositories")
    suspend fun getProjects(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): ProjectsResponse
}