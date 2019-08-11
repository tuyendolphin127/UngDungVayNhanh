package ungdung.vaynhanh

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private var retrofit: Retrofit? = null
    val apiService: RequestApi
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://ungdungvaytien.000webhostapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RequestApi::class.java)
        }
}