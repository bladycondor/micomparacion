package org.fautapo.web.cambioPlanEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iId_estudiante = 0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sId_estudiante = request.getParameter("id_estudiante");
    try {
      iId_estudiante = Integer.parseInt(sId_estudiante);
    }
    catch(Exception e) {
      return new ModelAndView("cambioPlanEstudiante/Entrada", "cliente", cliente);      
    }

    // Comprobamos es quien debe, de acuerdo a su clave
    Usuarios datosUsuario = new Usuarios();
    datosUsuario.setId_usuario(cliente.getId_usuario());
    datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));

    if (null == this.mi.getComprobarUsuario(datosUsuario)) {
      return new ModelAndView("cambioPlanEstudiante/Entrada", "cliente", cliente);
    }
    
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos los planes de estudio
    Planes datosPlan = new Planes();
    datosPlan.setId_programa(datosEstudiante.getId_programa());
    List lPlanesEstudio = this.mi.getListarPrgPlanesActual(datosPlan);
    modelo.put("lPlanesEstudio", lPlanesEstudio);
    
    //Sacamos el nombre del Estudiante
    datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
    modelo.put("datosEstudiante2", datosEstudiante);

    return new ModelAndView("cambioPlanEstudiante/ListarPlanes", modelo);
  }
}