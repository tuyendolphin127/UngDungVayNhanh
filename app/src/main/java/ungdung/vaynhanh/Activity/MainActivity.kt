package ungdung.vaynhanh.Activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ungdung.vaynhanh.API
import ungdung.vaynhanh.Adapter.RecyclerInforAdapter
import ungdung.vaynhanh.Adapter.RecyclerInforAdapterOffline
import ungdung.vaynhanh.ConnectionReceiver
import ungdung.vaynhanh.Database.Database
import ungdung.vaynhanh.Models.ThongTin
import ungdung.vaynhanh.R
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), Callback<ArrayList<ThongTin>> {
    private val DATABASE_NAME: String = "DanhSachThongTin"
    val database: Database = Database(this, DATABASE_NAME, null, 1)

    var danhSachThongTin: MutableList<ThongTin> = mutableListOf()
    var adapter: RecyclerInforAdapter = RecyclerInforAdapter(this, danhSachThongTin)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var connectionReceiver: ConnectionReceiver = ConnectionReceiver()
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (connectionReceiver.isConnected(this)) {
            initData()
        } else {
            recyclerView.adapter =
                RecyclerInforAdapterOffline(this, database.getDanhSachThongTin(), database.getDanhSachByteHinhAnh())
        }
    }

    fun initData() {
        API.apiService.danhSachThongTin.enqueue(this)
    }


    override fun onResponse(call: Call<ArrayList<ThongTin>>, response: Response<ArrayList<ThongTin>>) {
        if (response == null || response.body() == null) {
            return
        }
        danhSachThongTin.addAll(response.body()!!)
        recyclerView.adapter = adapter
        Timer("ThemData", false).schedule(7000) {
            database.deleteData()
            database.themDanhSachThongTin(danhSachThongTin, adapter.getDanhSachByteHinhAnh())
        }
    }

    override fun onFailure(call: Call<ArrayList<ThongTin>>, t: Throwable) {
        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
    }


}
