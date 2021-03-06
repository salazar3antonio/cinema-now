package com.example.moviefinder.api

import com.example.moviefinder.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface TheaterSearchAPI {

    @GET(Constants.THEATER_RADIUS_SEARCH)
    suspend fun getTheaterRadiusSearch(
        @Query(Constants.ORIGIN_PARAM) origin: String,
        @Query(Constants.RADIUS_PARAM) radius: Int,
        @Query(Constants.MAX_MATCHES_PARAM) maxMatches: Int,
        @Query(Constants.AMBIGUITIES_PARAM) ambiguities: String,
        @Query(Constants.HOSTED_DATA_PARAM) hostedData: String,
        @Query(Constants.OUT_FORMAT_PARAM) outFormat: String,
        @Query(Constants.KEY_PARAM) key: String
    ) : String

}