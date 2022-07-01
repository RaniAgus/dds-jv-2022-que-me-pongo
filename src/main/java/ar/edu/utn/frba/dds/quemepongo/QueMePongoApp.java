package ar.edu.utn.frba.dds.quemepongo;

import ar.edu.utn.frba.dds.quemepongo.jobs.PlanificadorQuartz;
import ar.edu.utn.frba.dds.quemepongo.jobs.GenerarAlertas;
import ar.edu.utn.frba.dds.quemepongo.jobs.GenerarSugerencias;
import ar.edu.utn.frba.dds.quemepongo.model.clima.OpenWeather;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ServicioMeteorologico;
import ar.edu.utn.frba.dds.quemepongo.model.clima.ProxyWeather;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioAlertas;
import ar.edu.utn.frba.dds.quemepongo.repository.RepositorioUsuarios;
import io.github.cdimascio.dotenv.Dotenv;
import org.quartz.*;
import java.time.Duration;

public final class QueMePongoApp {
  private static Dotenv env = Dotenv.load();
  private static RepositorioAlertas repositorioAlertas = RepositorioAlertas.getInstance();
  private static RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
  private static ServicioMeteorologico servicioMeteorologico = new ProxyWeather(
      new OpenWeather(env.get("OPEN_WEATHER_API_KEY")),
      Duration.ofMinutes(1)
  );

  public static void main(String[] args) throws SchedulerException {
    PlanificadorQuartz planificador = new PlanificadorQuartz()
        .agregarTarea(new GenerarAlertas(repositorioAlertas, repositorioUsuarios, servicioMeteorologico), "* 57 * * * ?")
        .agregarTarea(new GenerarSugerencias(repositorioUsuarios),"* 56 * * * ?");

    planificador.iniciar();
  }
}
