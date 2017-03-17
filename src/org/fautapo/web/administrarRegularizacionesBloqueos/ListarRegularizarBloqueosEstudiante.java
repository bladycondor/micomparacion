package org.fautapo.web.administrarRegularizacionesBloqueos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarRegularizarBloqueosEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    
    //Votamos los datos
    modelo.put("id_estudiante", sId_estudiante);

    if (("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi)))
        || (sId_estudiante == null) && (sNombres == null) && (sCi == null)) {
      return new ModelAndView("administrarRegularizacionesBloqueos/BuscarEstudiantes", modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
	   datosEstudiante.setIns_sede(cliente.getId_almacen());
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante = this.mi.getEstBuscarEstudiantePrsSede(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("administrarRegularizacionesBloqueos/Error","mensaje", "El estudiante no esta registrado en su Area y/o sede. De acuerdo a Reglamento no puede acceder a la Regularizacion de los Bloqueos. Verifique");
      }
    
      //Listamos las regularizaciones del estudiante
      List lListarRegularizacionesEtudiante = this.mi.getMiListarRegularizacionesEstudiante(datosEstudiante);
      modelo.put("lListarRegularizacionesEtudiante", lListarRegularizacionesEtudiante);
  
      return new ModelAndView("administrarRegularizacionesBloqueos/ListarRegularizarBloqueosEstudiante", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesDipAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      List lEstudiantes = this.mi.getEstListarEstudiantesNombresAccesos(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    return new ModelAndView("administrarRegularizacionesBloqueos/ListarEstudiantes", modelo);
  }
}