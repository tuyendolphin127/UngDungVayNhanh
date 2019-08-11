package ungdung.vaynhanh.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_list_infor.view.*
import ungdung.vaynhanh.Activity.ActivityInfor
import ungdung.vaynhanh.Models.ThongTin
import ungdung.vaynhanh.R

class RecyclerInforAdapterOffline(
    val context: Context,
    val listThongTin: List<ThongTin>,
    val danhSachByte: ArrayList<ByteArray>
) :
    RecyclerView.Adapter<RecyclerInforAdapterOffline.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_list_infor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listThongTin.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var thongTin = listThongTin[position]
        var byteAnh = danhSachByte[position]
        var bitmap = BitmapFactory.decodeByteArray(byteAnh, 0, byteAnh.size)
        holder.imageViewInfor.setImageBitmap(bitmap)
        holder.textViewInfor.text = thongTin.getTieuDe()
        holder.textViewDescribe.text = listThongTin[position].getMota()
        holder.textViewRating.text = listThongTin[position].getDanhGia()
        val temp = position + 1;
        holder.textViewVay.text = "Hình thức vay $temp"
        holder.itemView.setOnClickListener {
            toastClick(thongTin, byteAnh)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewInfor = view.imageViewInfor!!
        val textViewInfor = view.textViewInfor!!
        val textViewDescribe = view.textViewDescibe!!
        val textViewRating = view.textViewRating!!
        val textViewVay = view.textViewVay!!
    }

    private fun toastClick(thongTin: ThongTin, byteAnh: ByteArray) {
        val intent = Intent(context, ActivityInfor::class.java)
        intent.putExtra("thongTin", thongTin)
        intent.putExtra("Anh", byteAnh)
        intent.putExtra("kiemTra", false)
        context.startActivity(intent)
    }
}