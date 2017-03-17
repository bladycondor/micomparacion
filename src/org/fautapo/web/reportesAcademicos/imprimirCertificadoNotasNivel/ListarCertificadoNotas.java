package org.fautapo.web.reportesAcademicos.imprimirCertificadoNotasNivel;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
//import org.fautapo.domain.PLanes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Literales;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ListarCertificadoNotas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante; List lNotas = new ArrayList();
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sCi = request.getParameter("ci");
    String sNombres = request.getParameter("nombres");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_programa = request.getParameter("id_programa");
    String sTodas = request.getParameter("todas");
    
	String sNroCertificado = request.getParameter("nrocertificado");
	String sObservacion = request.getParameter("observacion");
	String sNivel = request.getParameter("nivel");
	
    
    //Votamos los datos
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);
    modelo.put("todas", sTodas);
    modelo.put("cliente",cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
 
    modelo.put("nrocertificado",sNroCertificado);
	modelo.put("observacion",sObservacion);
	modelo.put("nivel",sNivel);
	
 
    //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    if ("".equals(sId_estudiante) && ("".equals(sNombres)) && ("".equals(sCi))){
      return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasNivel/BuscarEstudiantes", modelo);
    }
    
    if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      } catch(Exception e) {
        return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
      }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
      if (datosEstudiante == null) {
        return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasNivel/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
      }
      datosEstudiante.setGestion(Integer.parseInt(sGestion));
      datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
	  datosEstudiante.setCantidad(Integer.parseInt(sNivel));
	  
	  //Sacamos los datos del certificado
      
	  //if ("Si".equals(sTodas)) {
        lNotas = this.mi.getListarCertificadoNotasNivel(datosEstudiante);
      //}
      //if ("No".equals(sTodas)) {
      //  lNotas = this.mi.getListarCertificadoNotasAprobadas(datosEstudiante);
     // }
	  
      List lMateriasNotas = new ArrayList();
      for (int i=0; i<lNotas.size();i++) {
        Libretas datosLibreta = (Libretas) lNotas.get(i);
        Literales literal = new Literales();
	    datosLibreta.setLiteral(literal.convertNumber(datosLibreta.getNota()));
        lMateriasNotas.add(i, datosLibreta);
      }
      modelo.put("lMateriasNotas", lMateriasNotas);

      //Buscamos el grado_academico por programa e id_plan
      Libretas datosGrados = new Libretas();
      datosGrados.setId_programa(datosEstudiante.getId_programa());
      datosGrados.setId_plan(datosEstudiante.getId_plan());
      datosGrados = this.mi.getBuscarGradoAcademicoPrograma(datosGrados);
      modelo.put("datosGrados",datosGrados);

      //Sacamos los datos de la Facultad
      Facultades datosFacultad = new Facultades();
      datosFacultad.setId_facultad(datosPrograma.getId_facultad());
      datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
      modelo.put("datosFacultad", datosFacultad);

      //Sacamos los datos del Estudiante
      datosEstudiante = new Estudiantes();
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
      modelo.put("datosEstudiante2", datosEstudiante);
    
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
        modelo.put("datosInstitucion", datosInstitucion);
      }
    
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));


      return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasNivel/ListarCertificadoNotas", modelo);
    }
    
    //Si la busqueda es por CI
    if (!"".equals(sCi)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setDip(sCi);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesDip(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    //Si la busqueda es por nombre
    if (!"".equals(sNombres)) {
      datosEstudiante = new Estudiantes();
      datosEstudiante.setNombres(sNombres);
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      List lEstudiantes = this.mi.getEstListarEstudiantesNombres(datosEstudiante);
      modelo.put("lEstudiantes", lEstudiantes);
    }
    return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasNivel/ListarDatosEstudiantes", modelo);
  }
}
