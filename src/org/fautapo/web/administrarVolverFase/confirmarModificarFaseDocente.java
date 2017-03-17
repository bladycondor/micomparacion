package org.fautapo.web.administrarVolverFase;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
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

public class confirmarModificarFaseDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");    
    String sId_tipo_nota_s     = "";  
    
    String sId_asignacion      = request.getParameter("id_asignacion");
    String sId_materia         = request.getParameter("id_materia");
    String sId_grupo           = request.getParameter("id_grupo");  
    String sGrupo              = request.getParameter("grupo");  
    String sId_programa        = request.getParameter("id_programa");
    String sId_fase            = request.getParameter("id_fase");
    String sFase               = request.getParameter("fase");
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
    String sId_departamento    = request.getParameter("id_departamento");    
    String sDepartamento       = request.getParameter("departamento");    
    String sNombres_docente    = request.getParameter("nombres");    
    String sId_tipo_docente    = request.getParameter("id_tipo_docente");    
    String sId_docente         = request.getParameter("id_docente");    
    
    modelo.put("gestion", sGestion);   // TOMANDO EN CUENTA LA GESTION Y PERIODO;    
    modelo.put("periodo", sPeriodo);
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_docente",sId_docente);
    modelo.put("id_rol",Integer.toString(cliente.getId_rol()));    
    modelo.put("id_grupo", sId_grupo);
    modelo.put("grupo", sGrupo);
    modelo.put("id_fase", sId_fase);
    modelo.put("fase", sFase);
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
    modelo.put("id_departamento", sId_departamento);    
    modelo.put("departamento", sDepartamento);    
    modelo.put("docente", sNombres_docente);    
    modelo.put("id_tipo_docente", sId_tipo_docente);    
    modelo.put("id_asignacion", sId_asignacion);    
    
    return new ModelAndView("administrarVolverFase/confirmarModificarFaseDocente", modelo);
  }
}