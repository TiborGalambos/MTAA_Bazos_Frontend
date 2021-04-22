import android.content.Context
import com.example.myapplication.loginLogic.APIservice
import com.example.myapplication.loginLogic.AuthInterceptor
import com.example.myapplication.loginLogic.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class APIclient {
    private lateinit var apiService: APIservice

    fun getApiService(context: Context): APIservice {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
//                .client(okhttpClient(context))


            apiService = retrofit.create(APIservice::class.java)
        }

        return apiService
    }

//    private fun okhttpClient(context: Context): OkHttpClient {
////        return OkHttpClient.Builder()
////            .addInterceptor(AuthInterceptor(context))
////            .build()
////    }

}