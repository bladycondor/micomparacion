package org.fautapo.web.reportesAcademicos.reimprimirProgramacionEstudiante;

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
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarProgramacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iId_estudiante = 0; int iGestion = 0; int iPeriodo = 0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sTodas = request.getParameter("todas");

    try {
      iId_estudiante = Integer.parseInt(sId_estudiante);
      iGestion = Integer.parseInt(sGestion);
      iPeriodo = Integer.parseInt(sPeriodo);
    }
    catch(Exception e) {
      return new ModelAndView("reportesAcademicos/reimprimirProgramacionEstudiante/Entrada", "cliente", cliente);      
    }

    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    if (datosEstudiante == null) {
      return new ModelAndView("Error", "mensaje", "El RU que ingreso no existe");
    }
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos la programacion del estudiante
    datosEstudiante.setGestion(iGestion);
    datosEstudiante.setPeriodo(iPeriodo);
    
    if ("No".equals(sTodas)) {
  	     List lProgramacion = this.mi.getEstListarProgramacion(datosEstudiante);
         modelo.put("lProgramacion", lProgramacion);
	    if (lProgramacion.size()==0) {
               return new ModelAndView("reportesAcademicos/reimprimirProgramacionEstudiante/Aviso", "mensaje", "El estudiante no tiene materias programadas para la gestion "+sGestion+" y periodo"+sPeriodo);
         }
    
	}
      if ("Si".equals(sTodas)) {
			List lProgramacion = this.mi.getEstListarProgramacioncv(datosEstudiante);
		    modelo.put("lProgramacion", lProgramacion);			 
            if (lProgramacion.size()==0) {
                 return new ModelAndView("reportesAcademicos/reimprimirProgramacionEstudiante/Aviso", "mensaje", "El estudiante no tiene materias programadas para la gestion "+sGestion+" y periodo"+sPeriodo);
            }
    
      }

	
    
	
	
    //Sacamos los datos del Estudiante
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante2", datosEstudiante);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
	
	Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
	
    return new ModelAndView("reportesAcademicos/reimprimirProgramacionEstudiante/ListarProgramacion", modelo);
  }
}