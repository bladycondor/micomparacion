package org.fautapo.web.administrarProgramasEspecializados.administrarPostulantesPost.Cajas;

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

public class RegistrarTransaccion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
      
    int iResultado=0;
    int iId_tramite = cliente.getInt(request, "id_tramite");
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    
    
    //Aqui la verificacion de cupos
    //FIN verificacion de cupos
    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_postulante = Integer.parseInt(tramite.getValores());

    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_perfil");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_perfil = Integer.parseInt(tramite.getValores());

    Perfiles transaccion = new Perfiles();
    transaccion.setId_perfil(iId_perfil);
    transaccion.setId_estudiante(iId_postulante);
    transaccion.setDeposito(0);
    transaccion.setEfectivo(Float.parseFloat(cliente.getString(request, "total")));
    transaccion.setTotal(Float.parseFloat(cliente.getString(request, "total")));
    transaccion.setUlt_usuario(cliente.getId_usuario());
    iResultado = this.mi.setPstRegistrarTransaccion(transaccion);
    
    //Registro correctamente volver mis pendientes
    if(iResultado >0){
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      return new ModelAndView("administrarProgramasEspecializados/administrarPostulantesPost/SalidaPostulante", modelo);
    }
    
    //Despues de que grabe hace la verificacion
    //Fin grabar
    
    return new ModelAndView("administrarProgramasEspecializados/administrarPostulantesPost/Cajas/ListarConceptos", modelo);
  }
}