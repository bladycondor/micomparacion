package org.fautapo.web.programarPostulantesPsa;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarProgramas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
//Funciones f = new Funciones(request, modelo, mi);
    //Si dio volver recuperamos los datos
    String sFecha = request.getParameter("fecha");
    String sHora = request.getParameter("hora");
	String sLugar = request.getParameter("lugar");
	String sNromaquinas = request.getParameter("nro_maquinas");
    String sGestion = cliente.getString(request, "gestion");
    String sPeriodo = cliente.getString(request, "periodo");
String sNroPostulantes="";   List listaPostulantesNro;
    //modelo.put("id_facultad", sId_facultad);
   // modelo.put("id_programa", sId_programa);

 //   if (("".equals(sId_facultad)) && ("".equals(sId_programa)) ) {
      // Comprobamos es quien debe, de acuerdo a su clave
      Usuarios datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(cliente.getId_usuario());
      datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
 
      if (null == this.mi.getComprobarUsuario(datosUsuario)) {
	    return new ModelAndView("Error","mensaje","Clave incorrecta, por favor intente nuevamente");
      //  return new ModelAndView("habilitarPostulantes/Entrada", "cliente", cliente);
      }
   // }
 Postulantes postulante = new Postulantes();
    postulante.setGestion(Integer.parseInt(sGestion));
    postulante.setPeriodo(Integer.parseInt(sPeriodo));
	 listaPostulantesNro = this.mi.getNroPostulantesPsa(postulante);
      modelo.put("listaPostulantesNro", listaPostulantesNro);
  
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    
    //Sacamos el formato de la fecha	
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
	  modelo.put("fecha", sFecha);
	

    return new ModelAndView("programarPostulantesPsa/ListarProgramas", modelo);
  }
}