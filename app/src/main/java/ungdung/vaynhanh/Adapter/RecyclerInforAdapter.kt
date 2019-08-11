package ungdung.vaynhanh.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_list_infor.view.*
import ungdung.vaynhanh.Activity.ActivityInfor
import ungdung.vaynhanh.Models.ThongTin
import ungdung.vaynhanh.R
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class RecyclerInforAdapter(val context: Context, val listThongTin: List<ThongTin>) :
    RecyclerView.Adapter<RecyclerInforAdapter.ViewHolder>() {
    private var danhSachByteHinhAnh: ArrayList<ByteArray> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_list_infor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listThongTin.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thongTin = listThongTin[position]
        Picasso.get().load(thongTin.getLienKetAnh()).fit().into(holder.imageViewInfor)
        Timer("LoadImage", false).schedule(5000) {
            val bitmapDrawable: BitmapDrawable = holder.imageViewInfor.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            chuyenSangByte(bitmap)
        }
        holder.textViewInfor.text = thongTin.getTieuDe()
        holder.textViewDescribe.text = listThongTin[position].getMota()
        holder.textViewRating.text = listThongTin[position].getDanhGia()
        val temp = position + 1;
        holder.textViewVay.text = "Hình thức vay $temp"
        holder.itemView.setOnClickListener {
            toastClick(thongTin)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewInfor = view.imageViewInfor!!
        val textViewInfor = view.textViewInfor!!
        val textViewDescribe = view.textViewDescibe!!
        val textViewRating = view.textViewRating!!
        val textViewVay = view.textViewVay!!
    }

    private fun toastClick(thongTin: ThongTin) {
        val intent = Intent(context, ActivityInfor::class.java)
        intent.putExtra("thongTin", thongTin)
        intent.putExtra("kiemTra", true)
        context.startActivity(intent)
    }

    private fun chuyenSangByte(bitmap: Bitmap) {
        var byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        var hinhAnh = byteArray.toByteArray()
        danhSachByteHinhAnh.add(hinhAnh)
    }

    fun getDanhSachByteHinhAnh(): ArrayList<ByteArray> {
        return this.danhSachByteHinhAnh
    }
}