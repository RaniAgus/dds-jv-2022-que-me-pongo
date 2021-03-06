package ar.edu.utn.frba.dds.quemepongo.model.usuario;

import ar.edu.utn.frba.dds.quemepongo.model.accion.AccionTrasAlertas;
import ar.edu.utn.frba.dds.quemepongo.model.clima.Alerta;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Categoria;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Usuario {
  private String email;
  private String telefono;
  private List<Guardarropa> guardarropas;
  private List<AccionTrasAlertas> acciones;
  private List<Atuendo> sugerencias;

  public Usuario(String email,
                 String telefono,
                 List<Guardarropa> guardarropas,
                 List<AccionTrasAlertas> acciones,
                 List<Atuendo> sugerencias) {
    this.email = email;
    this.telefono = telefono;
    this.guardarropas = guardarropas;
    this.acciones = acciones;
    this.sugerencias = sugerencias;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void agregar(Guardarropa guardarropa) {
    guardarropas.add(guardarropa);
  }

  public void emitirAlertas(Set<Alerta> alertas) {
    acciones.forEach(it -> it.anteNuevasAlertas(this, alertas));
  }

  public void generarSugerencia() {
    sugerencias.add(new Atuendo(
        sugerirPrendaAleatoria(Categoria.PARTE_SUPERIOR),
        sugerirPrendaAleatoria(Categoria.PARTE_INFERIOR),
        sugerirPrendaAleatoria(Categoria.CALZADO),
        sugerirPrendaAleatoria(Categoria.ACCESORIO)
    ));
  }

  private Prenda sugerirPrendaAleatoria(Categoria categoria) {
    List<Prenda> prendasPosibles = guardarropas.stream()
        .flatMap(it -> it.getPrendasSugeribles(categoria))
        .collect(Collectors.toList());

    return prendasPosibles.get(
        ThreadLocalRandom.current().nextInt(prendasPosibles.size())
            % prendasPosibles.size()
    );
  }
}
