package org.fautapo.web.busquedasAcademicas.verDatosEstudiante;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2007-10-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2007-10-18
*/

public class MostrarDatosEstudiante implements Controller 
{
  private MiFacade mi;
  public void setMi(MiFacade mi)  {this.mi = mi;}
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
  {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");

    if (("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi)))
        || (sId_estudiante == null) && (sNombres == null) && (sCi == null)) {
      return new ModelAndView("busquedasAcademicas/verDatosEstudiante/BuscarEstudiantes", modelo);
    }
        
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("busquedasAcademicas/verDatosEstudiante/Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_universidad(cliente.getId_universidad());
      datosEstudiante.setId_facultad(cliente.getId_facultad());
      datosEstudiante.setId_programa(cliente.getId_programa());
      datosEstudiante = this.mi.getEstBuscarEstudianteAccesos(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("busquedasAcademicas/verDatosEstudiante/Aviso", "mensaje", "El estudiante no es de su Area Verifique");
      }

      //Buscar al estudiante
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      
      //Listamos sus matriculas
      List lMatriculasEstudiante = this.mi.getMtrListarMatriculasEstudiante(datosEstudiante);
      modelo.put("lMatriculasEstudiante", lMatriculasEstudiante);

      //Mostramos la imagen del estudiante, siempre sera uno por imagen cargada
      Estudiantes imagenEst = new Estudiantes();
      imagenEst.setId_estudiante(datosEstudiante.getId_estudiante());
      imagenEst.setId_estado("I");
      List lImagenes = this.mi.getListarAdjuntosEstudiante(imagenEst);
      modelo.put("lImagenes", lImagenes);

      //Buscar Persona Colegio
      Personas datosPrs = new Personas();
      datosPrs.setId_persona(datosEstudiante.getId_persona());
      datosPrs = this.mi.getPrsBuscarPersona(datosPrs);
      Personas datosCol = this.mi.getBuscarPersonaColegio(datosPrs);
      modelo.put("datosPrs",datosPrs);
      modelo.put("datosCol",datosCol);

      
      //Sacamos el tipo clasificacion estudiante
      Estudiantes datosClasificacion = new Estudiantes();
      datosClasificacion.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosClasificacion = this.mi.getBuscarTipoClasificacionEstudiante(datosClasificacion);
      modelo.put("datosClasificacion", datosClasificacion);

      //Listar PrsDocumentos y Prs_compromisos
      Personas documento = new Personas();
      documento.setId_persona(datosEstudiante.getId_persona());
      List lPrsCompromisosTodo= this.mi.getListarPrsCompromisosPersona(documento);
      modelo.put("lPrsCompromisosTodo",lPrsCompromisosTodo);
      
	  
	  //Desde aqui
	  Estudiantes datosClas = new Estudiantes();
      datosClas.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);      
      if(datosClas == null) {
       return new ModelAndView("Aviso","mensaje","El estudiante con R.U.: "+ sId_estudiante +" no tiene registrado el tipo de clasificación por lo que no ver su documentación. actualice sus datos como estudiante.");
      }    
 	         
     datosClas.setId_persona(datosEstudiante.getId_persona());
	 
      List lPrsDocumentosClasificacion= this.mi.getListarPrsDocumentosClasificacion(datosClas);
	  //List lPrsDocumentosTodo= this.mi.getListarPrsDocumentosPersona(documento);
      modelo.put("lPrsDocumentosTodo",lPrsDocumentosClasificacion);
      
	  //Hasta aqui 
      //Listar el ultimo est_regularizacion
      Estudiantes lUltimoEstRegularizacion= this.mi.getMiBuscarUltimoEstRegularizacion(datosEstudiante);
      modelo.put("lUltimoEstRegularizacion",lUltimoEstRegularizacion);
      
      
      //Listar Est Deudas
      List lDeudasEstudiante = this.mi.getListarDeudasEstudiante(datosEstudiante);
      modelo.put("lDeudasEstudiante", lDeudasEstudiante);
      
	  //Listar Detalle de ventas
	  Perfiles datosPerfiles = new Perfiles();
	  datosPerfiles.setId_estudiante(Integer.parseInt(sId_estudiante));
      List lDetalleVentas = this.mi.getPstListarPerfiles(datosPerfiles);
      modelo.put("lDetalleVentas", lDetalleVentas);
	  
	  List lDetalleVentasEntidades = this.mi.getPstListarPerfilesEntidad(datosPerfiles);
      modelo.put("lDetalleVentasEntidad", lDetalleVentasEntidades);
	  
	  
	  //Listar Detalle de Auxiliarias de Docencia
	  Estudiantes datosAuxiliares = new Estudiantes();
	  datosAuxiliares.setId_estudiante(Integer.parseInt(sId_estudiante));
      List lDetalleAuxiliares = this.mi.getBuscarEstudianteAuxiliarTodas(datosAuxiliares);
      modelo.put("lDetalleAuxiliares", lDetalleAuxiliares);


      return new ModelAndView("busquedasAcademicas/verDatosEstudiante/MostrarDatosEstudiante", modelo); 
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
    return new ModelAndView("busquedasAcademicas/verDatosEstudiante/ListarDatosEstudiantes", modelo);
  }
}