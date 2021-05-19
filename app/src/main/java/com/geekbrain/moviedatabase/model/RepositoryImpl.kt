package com.geekbrain.moviedatabase.model

class RepositoryImpl : Repository {
    override fun getMovieListFromServer() = getMovieListMostPopular()
    override fun getMovieListMostPopularLocal() = getMovieListMostPopular()
    override fun getMovieListLatestReleasedLocal() = getMovieListLatestRelease()

}