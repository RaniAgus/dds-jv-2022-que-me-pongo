package ar.edu.utn.frba.dds.quemepongo.model.clima;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
  @GET("weather")
  Call<Weather> getWeather(@Query("q") String city,
                           @Query("units") String units,
                           @Query("appid") String key);
}