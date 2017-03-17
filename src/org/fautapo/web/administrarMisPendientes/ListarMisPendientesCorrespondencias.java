package org.fautapo.web.administrarMisPendientes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarMisPendientesCorrespondencias implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Character caracter;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");    
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");    
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");    
    String sActuacion = request.getParameter("actuacion");    
    String sPara = request.getParameter("para");    
    String sAccion = request.getParameter("accion");
    modelo.put("id_tipo_proceso", sId_tipo_proceso);

    Tramites tramite = new Tramites();
    //Para registrar como recibido
    if ("Recibir".equals(sAccion)) {
      tramite.setId_tramite(Integer.parseInt(request.getParameter("id_tramite")));
      tramite.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRecibirTramite(tramite);
      if (iResultado == 0) {
        return new ModelAndView("Error", "mensaje", "No se pudo recibir el proceso");
      }
    }

    //Para avanzar correspondencias y la copia
    if ("Avanzar".equals(sAccion) && "2".equals(sId_tipo_proceso)){
        tramite.setId_tipo_proveido(1); //Formulario
        if ((request.getParameter("actuacion") != null) && (!"".equals(request.getParameter("actuacion")))) {
          tramite.setId_tipo_actuacion(Integer.parseInt(request.getParameter("id_tipo_actuacion")));
          tramite.setOrden(Integer.parseInt(request.getParameter("actuacion")));
        }
        else {
          tramite.setId_tipo_actuacion(1);
          tramite.setOrden(0);
        }
        String sPara_s = request.getParameter("para_s");
        if (sPara_s != null) {
	   String xvalores[];
           xvalores = request.getParameterValues("para_s");
	   String xvalores1[];
	   xvalores1 = request.getParameterValues("id_tramite");
           for (int i=0; i < xvalores.length; i++) {
             if (xvalores[i] != null) {
	       String sId_tramite_c = xvalores1[i];
	       tramite.setId_tramite(Integer.parseInt(sId_tramite_c));
               String sPara_c = xvalores[i];
  	       tramite.setPara(Integer.parseInt(sPara_c));
	       tramite.setId_tipo_proveido(1); //Formulario
	       this.mi.setAvanzarTramite(tramite);
             }
           }
	}		    
	else {
          Tramites tra = new Tramites();
           String xvalores[];
           xvalores = request.getParameterValues("id_tramite");
	   if(xvalores != null){
             for (int i=0; i < xvalores.length; i++) {
               if (xvalores[i] != null) {
	         String sId_tramite_c = xvalores[i];
	         tramite.setId_tramite(Integer.parseInt(sId_tramite_c));
	         tramite.setId_tipo_proveido(1); //Formulario
	         this.mi.setAvanzarTramite(tramite);
               }
             }
	  }
	  else{
	    String mensaje = "Seleccione el o los tràmites a ser despachados";
            modelo.put("mensaje", mensaje);
            return new ModelAndView("Error",modelo);
	  }     
       }
    }

    //Listar correspondencia recibida
    Tramites tramitec = new Tramites();
    tramitec.setPara(cliente.getId_usuario());
    List listaTramites1 = this.mi.getListarTramitesMiosCorrespondenciaPara(tramitec);
    for (int j = 0; j < listaTramites1.size(); j++) {
      Tramites tramitin1 = (Tramites) listaTramites1.get(j);
      tramitin1.setLista(this.mi.getListarCamposReferencia(tramitin1));
      listaTramites1.set(j, tramitin1);
    }
    modelo.put("lCorresRecibida", listaTramites1);

    //Colocamos el nombre de FAUTAPO
    String sInstitucion = (caracter = new Character((char)70)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)85)).toString();
    sInstitucion = sInstitucion+(caracter = new Character((char)84)).toString()+(caracter = new Character((char)65)).toString()+(caracter = new Character((char)80)).toString()+(caracter = new Character((char)79)).toString();
    modelo.put("institucion", sInstitucion);
    //FIN Colocamos el nombre de FAUTAPO

    return new ModelAndView("administrarMisPendientes/ListarMisPendientesCorrespondencias", modelo);
  }
}