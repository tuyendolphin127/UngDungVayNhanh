package ungdung.vaynhanh.Activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ungdung.vaynhanh.*
import ungdung.vaynhanh.Adapter.RecyclerInforAdapter
import ungdung.vaynhanh.Adapter.RecyclerInforAdapterOffline
import ungdung.vaynhanh.Database.Database
import ungdung.vaynhanh.Models.ThongTin
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private final val URL1 = "https://ungdungvaytien.000webhostapp.com/"
    private final val URL2 = "https://ungdungvaytien.000webhostapp.com/"

    private lateinit var dialogLoad: Dialog
    private lateinit var composite: CompositeDisposable
    private val DATABASE_NAME: String = "DanhSachThongTin"

    private val database: Database = Database(this, DATABASE_NAME, null, 1)
    private var danhSachThongTin: MutableList<ThongTin> = mutableListOf()
    private var adapter: RecyclerInforAdapter = RecyclerInforAdapter(this, danhSachThongTin)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var connectionReceiver: ConnectionReceiver = ConnectionReceiver()
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (connectionReceiver.isConnected(this)) {
            initData1()
        } else {
            recyclerView.adapter =
                RecyclerInforAdapterOffline(this, database.getDanhSachThongTin(), database.getDanhSachByteHinhAnh())
        }
    }

    private fun showDialogLoad() {
        dialogLoad = Dialog(this)
        dialogLoad.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoad.setCancelable(false)
        dialogLoad.setContentView(R.layout.dialog_load_api)
        dialogLoad.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lq = WindowManager.LayoutParams()
        lq.copyFrom(dialogLoad.window!!.attributes)
        lq.width = WindowManager.LayoutParams.MATCH_PARENT
        lq.height = WindowManager.LayoutParams.WRAP_CONTENT
        lq.gravity = Gravity.CENTER
        dialogLoad.window!!.attributes = lq
        dialogLoad.show()
    }


    private fun initData1() {
        showDialogLoad()
        composite = CompositeDisposable()
        val requestApi = Retrofit.Builder().baseUrl(URL1)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestApi::class.java)

        val disposable = requestApi.getDanhSachThongTin1()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                handleSuccessData1(result)
            }, { error ->
                handleErrorData1(error)
            })
        composite.add(disposable)
    }

    private fun handleErrorData1(error: Throwable) {
        initData2()
    }

    private fun handleSuccessData1(result: List<ThongTin>?) {
        dialogLoad.dismiss()
        danhSachThongTin.addAll(result!!)
        recyclerView.adapter = adapter
        Timer("ThemData", false).schedule(7000) {
            database.deleteData()
            database.themDanhSachThongTin(danhSachThongTin, adapter.getDanhSachByteHinhAnh())
        }
    }

    private fun initData2() {
        composite = CompositeDisposable()
        val requestApi = Retrofit.Builder().baseUrl(URL2)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestApi::class.java)

        val disposable = requestApi.getDanhSachThongTin2()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                handleSuccessData12(result)
            }, { error ->
                handleErrorData12(error)
            })
        composite.add(disposable)
    }

    private fun handleErrorData12(error: Throwable?) {
        dialogLoad.dismiss()
        Toast.makeText(this, "Please checked internet!", Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccessData12(result: List<ThongTin>?) {
        dialogLoad.dismiss()
        danhSachThongTin.addAll(result!!)
        recyclerView.adapter = adapter
        Timer("ThemData", false).schedule(7000) {
            database.deleteData()
            database.themDanhSachThongTin(danhSachThongTin, adapter.getDanhSachByteHinhAnh())
        }
    }
}
