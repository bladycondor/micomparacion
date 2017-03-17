package org.fautapo.web.anularTramites;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Adjuntos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class ListarTramitesAnulados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sGestion = Integer.toString(cliente.getGestion());
    modelo.put("gestion", sGestion);

    //Listar tramites anulados
    List lTramitesAnulados = this.mi.getListarTramitesAnulados();
    for (int i = 0; i < lTramitesAnulados.size(); i++) {
      Tramites auxiliar = (Tramites) lTramitesAnulados.get(i);
      auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
      lTramitesAnulados.set(i, auxiliar);
    }
    modelo.put("lTramitesAnulados", lTramitesAnulados);
    //Fin - Listar tramites anulados

    return new ModelAndView("anularTramites/ListarTramitesAnulados", modelo);
  }    
}    
