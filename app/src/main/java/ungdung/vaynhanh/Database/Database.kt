package ungdung.vaynhanh.Database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import android.util.Log
import com.squareup.picasso.Picasso
import ungdung.vaynhanh.Models.ThongTin

class Database(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    private val TABLE_NAME: String = "Thong_Tin"

    private val KEY_ID: String = "id"
    private val KEY_TIEU_DE: String = "Tieu_De"
    private val KEY_MO_TA: String = "Mo_Ta"
    private val KEY_CHI_TIET: String = "Chi_Tiet"
    private val KEY_DANH_GIA: String = "Danh_Gia"
    private val KEY_LIEN_KET: String = "Lien_Ket"
    private val KEY_HINH_ANH: String = "Hinh_Anh"

    private val CREATE_TALBE: String =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_TIEU_DE TEXT, $KEY_MO_TA TEXT, $KEY_CHI_TIET TEXT, $KEY_DANH_GIA TEXT, $KEY_LIEN_KET TEXT, $KEY_HINH_ANH BLOG)"

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(CREATE_TALBE)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }

    fun themDanhSachThongTin(listThongTin: List<ThongTin>, danhSachByteHinhAnh: ArrayList<ByteArray>) {
        val database: SQLiteDatabase = writableDatabase
        for (i in 0 until listThongTin.size) {
            val querySql = "INSERT INTO $TABLE_NAME VALUES(null, ?, ?, ?, ?, ?, ?)"
            val statement: SQLiteStatement = database.compileStatement(querySql)
            statement.clearBindings()

            statement.bindString(1, listThongTin[i].getTieuDe())
            statement.bindString(2, listThongTin[i].getMota())
            statement.bindString(3, listThongTin[i].getChiTiet())
            statement.bindString(4, listThongTin[i].getDanhGia())
            statement.bindString(5, listThongTin[i].getLienKetChuyenHuong())
            statement.bindBlob(6, danhSachByteHinhAnh[i])

            statement.executeInsert()
        }
    }

    fun getDanhSachThongTin(): ArrayList<ThongTin> {
        var danhSach: ArrayList<ThongTin> = arrayListOf()
        val db = readableDatabase
        val checkTable = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '$TABLE_NAME'"
        var cursor: Cursor? = db.rawQuery(checkTable, null)
        if (cursor != null) {
            if (cursor.count > 0) {
                val getAllQuery = "SELECT * FROM $TABLE_NAME"
                cursor = db.rawQuery(getAllQuery, null)
                if (cursor.moveToFirst()) {
                    do {
                        var thongTin: ThongTin = ThongTin(
                            "null",
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(4),
                            cursor.getString(3),
                            cursor.getString(5)
                        )
                        danhSach.add(thongTin)
                    } while (cursor.moveToNext())
                }
            } else {
                val createAgain =
                    "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_TIEU_DE TEXT, $KEY_MO_TA TEXT, $KEY_CHI_TIET TEXT, $KEY_DANH_GIA TEXT, $KEY_LIEN_KET TEXT, $KEY_HINH_ANH BLOG)"
                db.execSQL(createAgain)
                db.close()
            }
        }
        cursor!!.close()
        return danhSach
    }

    fun getDanhSachByteHinhAnh(): ArrayList<ByteArray> {
        var danhSach: ArrayList<ByteArray> = arrayListOf()
        var db = readableDatabase
        val checkTable = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '$TABLE_NAME'"
        var cursor: Cursor? = db.rawQuery(checkTable, null)
        if (cursor != null) {
            if (cursor.count > 0) {
                val getAllQuery = "SELECT * FROM $TABLE_NAME"
                cursor = db.rawQuery(getAllQuery, null)
                if (cursor.moveToFirst()) {
                    do {
                        danhSach.add(cursor.getBlob(6))
                    } while (cursor.moveToNext())
                }
            } else {
                val createAgain =
                    "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_TIEU_DE TEXT, $KEY_MO_TA TEXT, $KEY_CHI_TIET TEXT, $KEY_DANH_GIA TEXT, $KEY_LIEN_KET TEXT, $KEY_HINH_ANH BLOG)"
                db.execSQL(createAgain)
                db.close()
            }
        }
        cursor!!.close()
        return danhSach
    }

    fun deleteData() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }
}