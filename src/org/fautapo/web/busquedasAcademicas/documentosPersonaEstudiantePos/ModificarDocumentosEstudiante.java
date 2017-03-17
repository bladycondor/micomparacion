package org.fautapo.web.busquedasAcademicas.documentosPersonasEstudiantesPos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ModificarDocumentosEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes buscarEst;
    //Personas datosClas;
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    
    if ("".equals(sId_estudiante)) {
      return new ModelAndView("Error","mensaje", "No ingreso el R.U.");
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      buscarEst = new Estudiantes();
      try {
       buscarEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      }
      catch(Exception e) {
        return new ModelAndView("Error","mensaje","Para el R.U. inserte un dato entero ");
      }	
      buscarEst = this.mi.getEstBuscarEstudiantePrsPos(buscarEst);
      if (buscarEst == null) {
        return new ModelAndView("Aviso","mensaje","El R.U.: "+ sId_estudiante +"no es de su Área");
      }
      
      //Buscar Tipo clasificacion estudiante
      Estudiantes datosClas = new Estudiantes();
      datosClas.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
      modelo.put("datosClas",datosClas);
      if(datosClas == null) {
       return new ModelAndView("Aviso","mensaje","El estudiante con R.U.: "+ sId_estudiante +" no tiene registrado el tipo de clasificación por lo que no podra modificar su documentación. Matriculese primero o actualice sus datos como estudiante.");
      }
      
  	
      
      
      //Listar TiposDocumentos*tipoclasificacion
      Postulantes tiposDoc = new Postulantes();
      tiposDoc.setId_tipo_clasificacion(datosClas.getId_tipo_clasificacion());
      List lTiposDocumentosClasf = this.mi.getListarTiposDocumentosClasificacionVigente(tiposDoc);
      modelo.put("lTiposDocumentosClasf", lTiposDocumentosClasf);
      
      //Listando Tipos Compromisos y Documentos Presentados
      List lTiposCompromisos = this.mi.getListarTiposCompromisos();
      modelo.put("lTiposCompromisos",lTiposCompromisos);
      List lPrsDocumentosTodo= this.mi.getListarPrsDocumentosPersona(datosClas);
      modelo.put("lPrsDocumentosTodo",lPrsDocumentosTodo);
      List lPrsCompromisosTodo= this.mi.getListarPrsCompromisosPersona(datosClas);
      modelo.put("lPrsCompromisosTodo",lPrsCompromisosTodo);
      
       datosClas.setId_persona(buscarEst.getId_persona());
	 
      List lPrsDocumentosClasificacion= this.mi.getListarPrsDocumentosClasificacion(datosClas);
      modelo.put("lPrsDocumentosClasificacion",lPrsDocumentosClasificacion);
      
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
      //Sacamos el formata de la gestion siguiente
      formatoFecha.setCampo("gestion_siguiente");
      formatoFecha.setCodigo("mi");
      modelo.put("gestion_siguiente", this.mi.getDibBuscarParametro(formatoFecha));
      //Sacamos el formata de la periodo siguiente
      formatoFecha.setCampo("periodo_siguiente");
      formatoFecha.setCodigo("mi");
      modelo.put("periodo_siguiente", this.mi.getDibBuscarParametro(formatoFecha));
      
      modelo.put("buscarEst", buscarEst);
      modelo.put("gestion", Integer.toString(cliente.getGestion()));
      modelo.put("periodo",  Integer.toString(cliente.getPeriodo()));
      //return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/ListarEstudiantes", modelo);
    }
    
    return new ModelAndView("busquedasAcademicas/documentosPersonasEstudiantesPos/ModificarDocumentosEstudiante",modelo);
  }
}
