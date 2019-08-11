package ungdung.vaynhanh.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ThongTin : Serializable {
    @SerializedName("LienKetAnh")
    private var lienKetAnh: String = ""
    @SerializedName("TieuDe")
    private var tieuDe: String = ""
    @SerializedName("MoTa")
    private var moTa: String = ""
    @SerializedName("ChiTiet")
    private var chiTiet: String = ""
    @SerializedName("DanhGia")
    private var danhGia: String = ""
    @SerializedName("LienKetChuyenHuong")
    private var lienKetChuyenHuong: String = ""

    constructor(
        lienKetAnh: String,
        tieuDe: String,
        moTa: String,
        danhGia: String,
        chiTiet: String,
        lienKetChuyenHuong: String
    ) {
        this.lienKetAnh = lienKetAnh
        this.tieuDe = tieuDe
        this.moTa = moTa
        this.danhGia = danhGia
        this.chiTiet = chiTiet
        this.lienKetChuyenHuong = lienKetChuyenHuong
    }


    fun setLienKetChuyenHuong(lienKetChuyenHuong: String) {
        this.lienKetChuyenHuong = lienKetChuyenHuong
    }

    fun getLienKetChuyenHuong(): String {
        return this.lienKetChuyenHuong
    }

    fun setTieuDe(tieuDe: String) {
        this.tieuDe = tieuDe
    }

    fun setDescribe(moTa: String) {
        this.moTa = moTa
    }

    fun setDanhGia(rating: String) {
        this.danhGia = rating
    }

    fun setLienKetAnh(lienKetAnh: String) {
        this.lienKetAnh = lienKetAnh
    }

    fun getTieuDe(): String {
        return this.tieuDe
    }

    fun getMota(): String {
        return this.moTa
    }

    fun getDanhGia(): String {
        return this.danhGia
    }

    fun getLienKetAnh(): String {
        return this.lienKetAnh
    }

    fun setChiTiet(chiTiet: String) {
        this.chiTiet = chiTiet
    }

    fun getChiTiet(): String {
        return this.chiTiet
    }
}