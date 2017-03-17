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

public class AnularTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sGestion = Integer.toString(cliente.getGestion());
    modelo.put("gestion", sGestion);

    String sId_tramite =request.getParameter("id_tramite");
    String sBoton = request.getParameter("boton");

    //Sacamos los datos del tramite
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);
    modelo.put("datosTramite", datosTramite);
    
    if ("X".equals(datosTramite.getId_estado())) {
      return new ModelAndView("Error", "mensaje", "Este proceso ya se encuentra anulado");
    }
        
    if ("C".equals(datosTramite.getId_estado())) {
      return new ModelAndView("Error", "mensaje", "Este proceso no se puede anular ya que está concluido");
    }

    if ("P".equals(datosTramite.getId_estado())) {
      return new ModelAndView("Error", "mensaje", "Este proceso no se puede anular ya que está en tránsito");
    }

    if ("B".equals(datosTramite.getId_estado())) {
       return new ModelAndView("Error", "mensaje", "Este proceso no se puede anular porque esta bloqueado");
    }
    
    if ("Si".equals(sBoton)) {
      try {
        Tramites tramite = new Tramites();
        tramite.setId_tramite(Integer.parseInt(sId_tramite));
        int iValor = this.mi.setAnularTramite(tramite);
        if (iValor == 0) {
          return new ModelAndView("Aviso","mensaje","Uno o mas Campos estan usando este tipo de proceso usted no puede anularlo");	
        }
	else {
	  List lTramitesAnulados = this.mi.getListarTramitesAnulados();
	  for (int i = 0; i < lTramitesAnulados.size(); i++) {
            Tramites auxiliar = (Tramites) lTramitesAnulados.get(i);
            auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
            lTramitesAnulados.set(i, auxiliar);
          }
          modelo.put("lTramitesAnulados", lTramitesAnulados);
	  return new ModelAndView("anularTramites/ListarTramitesAnulados", modelo);
	} 
      } 
      catch (Exception e) {
        modelo.put("mensaje","Uno o mas Campos estan usando este tipo de Area usted no puede Eliminarlo");
        return new ModelAndView("Aviso", modelo);   
      }
    } 

    modelo.put("id_tramite", sId_tramite);
    modelo.put("boton", sBoton);
    return new ModelAndView("anularTramites/AnularTramite", modelo);
  }    
}    
