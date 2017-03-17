package org.fautapo.web.reportesAcademicos.verEstudiantesPorNivelesAcademicos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListarNivelesAcademicos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    int iId_programa = cliente.getInt(request, "id_programa");
    String periodo = request.getParameter("periodo");
    String gestion = request.getParameter("gestion");
    String sId_plan = request.getParameter("id_plan");
    
    modelo.put("gestion", gestion);
    modelo.put("periodo", periodo);
    modelo.put("id_plan", sId_plan);

    Estudiantes estudiante= new Estudiantes();    
    estudiante.setGestion(Integer.parseInt(gestion));
    estudiante.setPeriodo(Integer.parseInt(periodo));
    estudiante.setId_programa(Integer.parseInt(sId_programa));    
    estudiante.setId_plan(sId_plan);
    
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1);
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(datosPrograma.getId_facultad());
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);


/*
    Programas prog = new Programas();
    prog.setId_programa(estudiante.getId_programa());
    prog = this.mi.getPrgBuscarPrograma(prog);
    modelo.put("programa",prog);

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(Integer.parseInt(sId_facultad));
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);
*/

    List listaNiveles=this.mi.getListarNiveles(estudiante);
    modelo.put("listaNiveles", listaNiveles);

    return new ModelAndView("reportesAcademicos/verEstudiantesPorNivelesAcademicos/ListarNivelesAcademicos", modelo);
  }
}