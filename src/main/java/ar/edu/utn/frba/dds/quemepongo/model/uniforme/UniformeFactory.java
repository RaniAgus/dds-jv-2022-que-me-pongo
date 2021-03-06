package ar.edu.utn.frba.dds.quemepongo.model.uniforme;

import ar.edu.utn.frba.dds.quemepongo.model.usuario.Atuendo;
import ar.edu.utn.frba.dds.quemepongo.model.prenda.Borrador;

public abstract class UniformeFactory {
  public Atuendo crearUniforme() {
    return new Atuendo(
        getBorradorSuperior().crearPrenda(),
        getBorradorInferior().crearPrenda(),
        getBorradorCalzado().crearPrenda(),
        null
    );
  }

  protected abstract Borrador getBorradorSuperior();
  protected abstract Borrador getBorradorInferior();
  protected abstract Borrador getBorradorCalzado();
}
