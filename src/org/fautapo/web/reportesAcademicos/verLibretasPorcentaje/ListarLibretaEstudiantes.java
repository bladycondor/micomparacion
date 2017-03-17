package org.fautapo.web.reportesAcademicos.verLibretasPorcentaje;

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
import org.fautapo.domain.FormatosNum;   
import org.fautapo.domain.Instituciones;

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

public class ListarLibretaEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_tipo_nota_s     = "";  
    String sId_materia         = request.getParameter("id_materia");
    String sId_grupo           = request.getParameter("id_grupo");  
    String sGrupo              = request.getParameter("grupo");  
    String sId_programa        = request.getParameter("id_programa");
    String sId_fase            = request.getParameter("id_fase");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sTipo_evaluacion    = request.getParameter("tipo_evaluacion");
    String sId_modelo_ahorro   = request.getParameter("id_modelo_ahorro");
    sId_tipo_nota_s            = request.getParameter("id_tipo_nota_s");
    String sNro_nota_s         = request.getParameter("nro_nota_s");
    String sMateria            = request.getParameter("materia");
    String sPrograma           = request.getParameter("programa");    
    String sBandera            = request.getParameter("bandera");
    String sGestion            = request.getParameter("gestion");
    String sPeriodo            = request.getParameter("periodo"); 
    String sId_tipo_grado      = request.getParameter("id_tipo_grado");
    String sAvanzado           = request.getParameter("avanzado");
    String sId_departamento    = request.getParameter("id_departamento");    
    String sNombres_docente    = request.getParameter("nombres");    
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_docente",Integer.toString(cliente.getId_usuario()));
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_grupo", sId_grupo);
    modelo.put("grupo", sGrupo);
    modelo.put("id_fase", sId_fase);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("tipo_evaluacion", sTipo_evaluacion);
    modelo.put("id_modelo_ahorro", sId_modelo_ahorro);
    modelo.put("nro_nota", sNro_nota_s);
    modelo.put("id_tipo_nota_s", sId_tipo_nota_s);
    modelo.put("nro_nota_s", sNro_nota_s);
    modelo.put("id_materia", sId_materia);
    modelo.put("materia", sMateria);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", sPrograma);
    modelo.put("id_tipo_grado", sId_tipo_grado);
    modelo.put("avanzado", sAvanzado);
    modelo.put("id_departamento", sId_departamento);    
    modelo.put("docente", sNombres_docente);    
    
    //Convertimos a entero los datos necesarios
    int iGestion = Integer.parseInt(sGestion);
    int iPeriodo = Integer.parseInt(sPeriodo);
    
    int iId_materia = Integer.parseInt(sId_materia);
    int iId_fase = Integer.parseInt(sId_fase);    
    int iId_tipo_evaluacion = Integer.parseInt(sId_tipo_evaluacion);    
    int iId_grupo = Integer.parseInt(sId_grupo);    
    int iId_modelo_ahorro = Integer.parseInt(sId_modelo_ahorro);
    int iId_departamento = Integer.parseInt(sId_departamento);
    //listamos los estudiantes con  notas por tipos notas 
    Libretas datosEst = new Libretas();
    datosEst.setId_materia(iId_materia);
    datosEst.setId_grupo(iId_grupo);
    datosEst.setId_modelo_ahorro(iId_modelo_ahorro); 
    datosEst.setGestion(iGestion);
    datosEst.setPeriodo(iPeriodo);
    datosEst.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosEst.setId_fase(iId_fase);
    datosEst.setId_departamento(iId_departamento);
    List lnotasFases = this.mi.getListarNotasFaseEstudiantes(datosEst);
    System.out.println("lista de postulantes----->" + lnotasFases.size());	
    modelo.put("lnotasFases", lnotasFases);

    //Sacamos el programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", buscarPrograma);
    //modelo.put("programa", buscarPrograma.getPrograma());
    
    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(iId_materia);
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("materia", buscarMateria.getMateria());
    modelo.put("sigla", buscarMateria.getSigla());
    modelo.put("id_departamento", Integer.toString(buscarMateria.getId_departamento()));
    modelo.put("id_materia", sId_materia);
    modelo.put("id_tipo_nota_s", sId_tipo_nota_s);

    //Sacamos la definicion de items de la materia
    Libretas datosEvaluacion = new Libretas();
    datosEvaluacion.setId_materia(iId_materia);
    datosEvaluacion.setId_grupo(iId_grupo);
    datosEvaluacion.setGestion(iGestion);
    datosEvaluacion.setPeriodo(iPeriodo);
    datosEvaluacion.setId_departamento(buscarMateria.getId_departamento());
    datosEvaluacion.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosEvaluacion.setId_fase(iId_fase);
    datosEvaluacion.setId_modelo_ahorro(iId_modelo_ahorro);
    List lEvaluacion = this.mi.getGrpListarEvaluacionDefinida(datosEvaluacion);
    modelo.put("listaItems", lEvaluacion);

    //Sacando la lista de estudiantes programados a la materia, evaluación regualar
    Libretas datosEstProg = new Libretas();
    datosEstProg.setId_materia(iId_materia);
    datosEstProg.setId_grupo(iId_grupo);
    datosEstProg.setId_modelo_ahorro(iId_modelo_ahorro); 
    datosEstProg.setGestion(iGestion);
    datosEstProg.setPeriodo(iPeriodo);
    datosEstProg.setId_fase(iId_fase);
    datosEstProg.setId_tipo_evaluacion(iId_tipo_evaluacion);
    List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
    modelo.put("listaEstudiantes", lEstudiantes);
      
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

    return new ModelAndView("reportesAcademicos/verLibretasPorcentaje/ListarLibretaEstudiantes", modelo);
  }
}