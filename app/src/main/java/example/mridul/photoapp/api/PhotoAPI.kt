package example.mridul.photoapp.api

import example.mridul.photoapp.BuildConfig.PixbayAPIKey
import example.mridul.photoapp.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET

interface PhotoAPI {
    @GET("?key=${PixbayAPIKey}&q=nature&image_type=photo")
    fun getPhotos(): Call<PhotoList>
}