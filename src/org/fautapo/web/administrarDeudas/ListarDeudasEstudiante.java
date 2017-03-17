package org.fautapo.web.administrarDeudas;

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

public class ListarDeudasEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    String sMensaje="";
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_programa = request.getParameter("id_programa");
    String sCi = request.getParameter("dip");
    String sNombres = request.getParameter("nombres");
    //Votamos los datos
    modelo.put("id_estudiante", sId_estudiante);
    modelo.put("id_programa", sId_programa);
    modelo.put("ci", sCi);
    modelo.put("nombres", sNombres);

    if (("".equals(sId_estudiante)) && ("".equals(sId_programa))
        || (sId_estudiante == null) && (sId_programa == null)) {
      return new ModelAndView("administrarDeudas/BuscarEstudiantes", modelo);
    }
    
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      
      //datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
      if (datosEstudiante == null) {
	sMensaje="El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.";
	modelo.put("mensaje",sMensaje);
	return new ModelAndView("administrarDeudas/Aviso",modelo);
      }
      //Buscamos otros datos
      datosEstudiante.setIns_sede(cliente.getId_almacen());
	  datosEstudiante = this.mi.getEstBuscarEstudiantePrsSede(datosEstudiante);   
if(datosEstudiante == null){
 return new ModelAndView("Error", "mensaje", "El Estudiante no pertenece a tu sede y/o Area, por lo cual segun Reglamento Academico no puede realizar Modificaciones");
}	  
      modelo.put("datosEstudiante", datosEstudiante);
      //Listamos las regularizaciones del estudiante
      List lListarDeudasEstudiantes = this.mi.getListarDeudasEstudiante(datosEstudiante);
      modelo.put("lListarDeudasEstudiantes", lListarDeudasEstudiantes);
  
      return new ModelAndView("administrarDeudas/ListarDeudasEstudiante", modelo);
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
    return new ModelAndView("administrarDeudas/ListarEstudiantes", modelo);
  }
}