package org.fautapo.web.administrarProgramasEspecializados.reporteEstudianteCertificado;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Notas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarDatosEstudiante implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);
    modelo.put("id_tramite", sId_tramite);
    
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);

      //Sacamos los datos del Campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setEtiqueta("id_estudiante");
      datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
      String sId_estudiante = datosTramite.getValores();
      modelo.put("id_estudiante", sId_estudiante);

      //Sacamos los datos del Estudiante
      Estudiantes datosEstudiante = new Estudiantes();
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
 
      try {
        //Sacamos los datos de los requisitos
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("requisitos");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        modelo.put("requisitos", datosTramite.getValores());
      } catch(Exception e) {}

      try {
        //Sacamos el descuento del perfil
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("descuento");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        modelo.put("descuento", datosTramite.getValores());
      } catch(Exception e) {}
      
      try {
        //Sacamos los gestiones y periodos 
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("gestiones_periodos");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        String sGestiones = datosTramite.getValores();
	
        List lGestiones = new ArrayList();
	String campos[] = sGestiones.split(", ");// Separador de campos
        for (int i = 0; i < campos.length; i++) {
	  System.out.println("i-----"+i);
          System.out.println("gestion--------"+campos[i]);
	  String camp_recortado[] = campos[i].split("-");	  
	  Notas notas = new Notas();
	  notas.setPeriodo(Integer.parseInt(camp_recortado[0]));
	  notas.setGestion(Integer.parseInt(camp_recortado[1]));
	  notas.setId_estudiante(Integer.parseInt(sId_estudiante));
	  lGestiones.add(notas);
        }
	
	modelo.put("lGestiones", lGestiones);
	//modelo.put("lgestiones", notas.getGestion());      
        modelo.put("gestiones", sGestiones);      
      } catch(Exception e) {}
      
      return new ModelAndView("administrarProgramasEspecializados/reporteEstudianteCertificado/ListarDatosEstudiante", modelo);
    /*}
    return new ModelAndView("administrarProgramasEspecializados/reporteEstudianteCertificado/Entrada", modelo);*/
  }

}