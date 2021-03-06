package ar.edu.utn.frba.dds.quemepongo.model.clima;

import ar.edu.utn.frba.dds.quemepongo.exception.ServicioMeteorologicoException;
import com.google.common.collect.ImmutableMap;
import edu.utn.frba.dds.accuweather.AccuWeatherAPI;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccuWeather implements ServicioMeteorologico {
  private AccuWeatherAPI api = new AccuWeatherAPI();

  @SuppressWarnings("unchecked")
  public Temperatura getTemperatura() {
    try {
      Map<String, Object> data = (Map<String, Object>) api
          .getWeather("Buenos Aires, Argentina")
          .get(0)
          .get("Temperature");

      Map<String, Function<Double, Temperatura>> converters = ImmutableMap.of(
          "F", Temperatura::ofFahrenheit,
          "C", Temperatura::ofCelsius
      );

      return Objects.requireNonNull(converters.get((String) data.get("Unit")))
          .apply(Double.valueOf((Integer) data.get("Value")));

    } catch (NullPointerException e) {
      throw new ServicioMeteorologicoException(e);
    }
  }

  @Override
  public Set<Alerta> getAlertas() {
    try {
      Map<String, Alerta> converters = ImmutableMap.of(
          "STORM", Alerta.TORMENTA,
          "HAIL", Alerta.GRANIZO
      );

      return api.getAlerts("Buenos Aires").get("CurrentAlerts")
          .stream()
          .map(converters::get)
          .collect(Collectors.toSet());

    } catch (NullPointerException e) {
      throw new ServicioMeteorologicoException(e);
    }
  }
}
