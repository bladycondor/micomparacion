package org.fautapo.web.reportesAcademicos.imprimirCertificadoNotasCode;

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
    Estudiantes datosEstudiante; 
	List lNotas = new ArrayList();
    
    //Recuperando variables del jsp
	String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
	String sId_programa = request.getParameter("id_programa");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sNombres = request.getParameter("nombres");
	String sNro_recibo = cliente.getString(request, "nrocertificado");
	String sTodas = request.getParameter("todas");
    
    //Votamos los datos
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_estudiante",sId_estudiante);
	modelo.put("nombres",sNombres);
    modelo.put("nrocertificado",sNro_recibo);
	modelo.put("todas",sTodas);
	modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
	 	 
	System.out.println("1-> "+sGestion);
	System.out.println("2-> "+sPeriodo);
	System.out.println("3-> "+sId_programa);
    System.out.println("4-> "+sId_estudiante);
	System.out.println("4-> "+sNombres);
	System.out.println("5-> "+sNro_recibo);
    System.out.println("6-> "+sTodas); 	
    
     //Buscamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
  // if ("".equals(sId_estudiante) ){
    //  return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/", modelo);
    //}
    
   // if (!"".equals(sId_estudiante)) {
      //Sacando los datos del estudiante    
      datosEstudiante = new Estudiantes();
      //try {
       datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
     // } catch(Exception e) {
      //  return new ModelAndView("Error", "mensaje", "El R.U. no es valido, introduzca un numero");
     // }
      datosEstudiante.setId_programa(Integer.parseInt(sId_programa));
      datosEstudiante = this.mi.getEstBuscarEstudiantePrograma(datosEstudiante);
      modelo.put("datosEstudiante", datosEstudiante);
     // if (datosEstudiante == null) {
       // return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/Aviso","mensaje","El estudiante con R.U. : "+ sId_estudiante + "no esta registrado en el Programa : "+ datosPrograma.getPrograma() + ". Verifique.");
     // }
      datosEstudiante.setGestion(Integer.parseInt(sGestion));
      datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
	  

      //Sacamos los datos del certificado
      
	  if ("Si".equals(sTodas)) {
        lNotas = this.mi.getListarCertificadoNotasTodas(datosEstudiante);
      }
      if ("No".equals(sTodas)) {
        lNotas = this.mi.getListarCertificadoNotasAprobadas(datosEstudiante);
      }
	  
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

    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
	
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

     
     // return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/ListarCertificadoNotas", modelo);
    //}
   
    return new ModelAndView("reportesAcademicos/imprimirCertificadoNotasCode/ListarCertificadoNotas", modelo);
  }
}
