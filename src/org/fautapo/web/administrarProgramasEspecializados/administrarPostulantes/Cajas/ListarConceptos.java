package org.fautapo.web.administrarProgramasEspecializados.administrarPostulantes.Cajas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarConceptos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iId_tramite = cliente.getInt(request, "id_tramite");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_postulante = Integer.parseInt(tramite.getValores());

    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_perfil");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_perfil = Integer.parseInt(tramite.getValores());

    Perfiles perfil = new Perfiles();
    perfil.setId_perfil(iId_perfil);
    perfil = this.mi.getPrfBuscarPerfil(perfil);
    modelo.put("perfil", perfil);
    
    List listaConceptos = this.mi.getPrfListarConceptos(perfil);
    modelo.put("listaConceptos", listaConceptos);
    float total = 0;
    for (int i = 0; i < listaConceptos.size(); i++){
      Perfiles cajita = (Perfiles) listaConceptos.get(i);
      total += cajita.getCosto();
    }
    modelo.put("total", String.valueOf(total));
    //return new ModelAndView("administrarProgramasEspecializados/matriculacionPst/ListarConceptos", modelo);
    return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/Cajas/ListarConceptos", modelo);
  }
}