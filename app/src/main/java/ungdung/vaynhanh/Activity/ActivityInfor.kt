package ungdung.vaynhanh.Activity

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_infor.*
import ungdung.vaynhanh.Models.ThongTin
import ungdung.vaynhanh.R

class ActivityInfor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infor)
        getData()
    }

    private fun getData() {
        val thongTin = intent.extras!!.getSerializable("thongTin") as? ThongTin
        if (intent.getBooleanExtra("kiemTra", true)) {
            Picasso.get().load(thongTin!!.getLienKetAnh()).into(imageViewThongTin)
        } else {
            val byte = intent.extras?.getByteArray("Anh")
            val bitmap = BitmapFactory.decodeByteArray(byte, 0, byte!!.size)
            imageViewThongTin.setImageBitmap(bitmap)
        }
        textViewTenThongTinNho.text = thongTin!!.getTieuDe()
        textViewMoTa.text = thongTin.getMota()
        textViewDanhGia.text = thongTin.getDanhGia()
        textViewTenThongTin.text = thongTin.getTieuDe()
        textViewChiTiet.text = thongTin.getChiTiet()
        buttonDangKy.setOnClickListener { runIntent(thongTin.getLienKetChuyenHuong()) }
    }

    private fun runIntent(lienKetChuyenHuong: String) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
        builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
        customTabsIntent.launchUrl(this, Uri.parse(lienKetChuyenHuong))
    }
}


