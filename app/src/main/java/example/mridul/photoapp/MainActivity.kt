package example.mridul.photoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.mridul.photoapp.api.PhotoRetriever
import example.mridul.photoapp.models.Photo
import example.mridul.photoapp.models.PhotoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var photos: List<Photo>? = null
    var mainAdapter: MainAdapter? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var retriever = PhotoRetriever()

        val callback = object : Callback<PhotoList> {
            override fun onResponse(
                call: Call<PhotoList>?,
                response: Response<PhotoList>
            ) {
                response?.isSuccessful.let {
                    this@MainActivity.photos = response?.body()?.hits
                    mainAdapter = MainAdapter(this@MainActivity.photos!!, this@MainActivity)
                    recyclerView.adapter = mainAdapter
                }
            }

            override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                Log.e("MainActivity", "Problems calling api: " + t.localizedMessage)
            }

        }

        retriever.getPhotos(callback)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, DetailActivity::class.java)
        val holder = view?.tag as MainAdapter.PhotoViewHolder
        intent.putExtra(DetailActivity.PHOTO, mainAdapter?.getPhoto(holder.adapterPosition))
        startActivity(intent)
    }
}
