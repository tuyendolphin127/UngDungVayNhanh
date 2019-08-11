package ungdung.vaynhanh

import retrofit2.Call
import retrofit2.http.GET
import ungdung.vaynhanh.Models.ThongTin

interface RequestApi {
    @get: GET("apiThongTin.php")
    val danhSachThongTin: Call<ArrayList<ThongTin>>
}