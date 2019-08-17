package ungdung.vaynhanh

import io.reactivex.Observable
import retrofit2.http.GET
import ungdung.vaynhanh.Models.ThongTin

interface RequestApi {

    @GET("/apiThongTin.php")
    fun getDanhSachThongTin1(): Observable<List<ThongTin>>

    @GET("/apiThongTin.php")
    fun getDanhSachThongTin2(): Observable<List<ThongTin>>
}