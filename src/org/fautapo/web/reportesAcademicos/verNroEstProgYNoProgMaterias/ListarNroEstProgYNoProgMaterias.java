package org.fautapo.web.reportesAcademicos.verNroEstProgYNoProgMaterias;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Libretas;   
import org.fautapo.domain.Notas;   
import org.fautapo.domain.FormatosNum;   
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Estudiantes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class ListarNroEstProgYNoProgMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    int id_programa = cliente.getId_programa();

    String sGestion = request.getParameter("gestion"); 
    String sPeriodo = request.getParameter("periodo"); 
    modelo.put("gestion", sGestion);    
    modelo.put("periodo", sPeriodo);
    String sPlan = request.getParameter("plan");
    String sDatos[] = sPlan.split("/");
    String splan = sDatos[0];
    String sid_tipo_grado = sDatos[1];
    
    //Convertimos a entero los datos necesarios
    int plan = Integer.parseInt(splan);
    int iPeriodo = Integer.parseInt(sPeriodo);
    int iGestion = Integer.parseInt(sGestion);
    int id_tipo_grado = Integer.parseInt(sid_tipo_grado);

    //Sacando la lista de estudiantes matriculados
    Estudiantes datosEst = new Estudiantes();
    datosEst.setPlan(plan);
    datosEst.setId_tipo_grado(id_tipo_grado);
    datosEst.setId_programa(id_programa);
    datosEst.setPeriodo(iPeriodo);
    if (id_tipo_grado==1) {
      datosEst.setGestion(iGestion);
      List lEstudiantes = this.mi.getListarNroEstProgramadosMaterias(datosEst);
      datosEst.setGestion(iGestion-1);
      List lEstudiantes1 = this.mi.getListarNroEstProgramadosMaterias(datosEst);
      modelo.put("listaEstudiantes", lEstudiantes);
      modelo.put("listaEstudiantes1", lEstudiantes1);
    }
    else {
      datosEst.setGestion(iGestion);
      List lEstudiantes = this.mi.getListarNroPostProgramadosMaterias(datosEst);
      datosEst.setGestion(iGestion-1);
      List lEstudiantes1 = this.mi.getListarNroPostProgramadosMaterias(datosEst);
      modelo.put("listaEstudiantes", lEstudiantes);
      modelo.put("listaEstudiantes1", lEstudiantes1);
    }
    
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
    //sacamos el programa
    Programas programa = new Programas();
    programa.setId_programa(id_programa); 
    programa = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", programa.getPrograma());
    
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

    return new ModelAndView("reportesAcademicos/verNroEstProgYNoProgMaterias/ListarNroEstProgYNoProgMaterias", modelo);
  }
}