package org.fautapo.web.administrarHorarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Horarios;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-18
 */

public class RegistrarHorarios implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");

    if("".equals(Integer.toString(cliente.getId_rol()))){
      modelo.put("mensaje", "No se le permite entrar a este módulo");
      return new ModelAndView("Error", modelo);
    }
    
    //Recuperar variables
    String sMensaje  = "";
    int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    //int iId_materia  = Integer.parseInt(request.getParameter("id_materia"));
    //int iId_grupo    = Integer.parseInt( request.getParameter("id_grupo"));
    int iId_dpto_grupo = Integer.parseInt( request.getParameter("id_dpto_grupo"));
    int iId_modelo_ahorro = Integer.parseInt(request.getParameter("id_modelo_ahorro"));
    
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    
    
    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("id_programa", request.getParameter("id_programa"));    
    modelo.put("id_prg_plan", request.getParameter("id_prg_plan"));    
    modelo.put("id_tipo_evaluacion", request.getParameter("id_tipo_evaluacion"));
    
    
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    
    if(datosPrgPlan == null)
      return new ModelAndView("Aviso","mensaje","No existe el plan seleccionado en Programas - Planes");
    
    if(iId_tipo_evaluacion == 0)
      return new ModelAndView("Aviso","mensaje","-No ingeso el Tipo de Evalucion");  
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos de dpto_grupo
    Grupos datosDptoGrupo = new Grupos();
    datosDptoGrupo.setId_dpto_grupo(iId_dpto_grupo);
    datosDptoGrupo = this.mi.getDptoBuscarGrupo(datosDptoGrupo);
    modelo.put("datosDptoGrupo",datosDptoGrupo);
    if(datosDptoGrupo == null)
      return new ModelAndView("Aviso","mensaje","No existen datos en Dpto Grupos");
    
    //Buscar Materia
    Materias materia = new Materias();
    materia.setId_materia(datosDptoGrupo.getId_materia());
    materia = (Materias) this.mi.getMtrBuscarMateria(materia);
    modelo.put("datosMateria", materia);
    
    //Administrar Horario
    Horarios horario = new Horarios();
    horario.setId_programa(iId_programa);
    horario.setId_plan(datosPrgPlan.getId_plan());
    horario.setId_materia(datosDptoGrupo.getId_materia());
    horario.setId_grupo(datosDptoGrupo.getId_grupo());
    horario.setId_modelo_ahorro(datosDptoGrupo.getId_modelo_ahorro());
    horario.setGestion(iGestion);
    horario.setPeriodo(iPeriodo);
    horario.setId_tipo_grado(datosPrgPlan.getId_tipo_grado());  
    horario.setId_tipo_evaluacion(iId_tipo_evaluacion);  //Verano-Mesa-Regular
    horario.setId_rol(cliente.getId_rol());
    horario.setUlt_usuario(cliente.getId_usuario());
    //this.mi.setHrsLimpiarHorarioMateria(horario);
    String horarios[] = request.getParameterValues("horario");
    
    if (horarios == null) {
      return new ModelAndView("Error","mensaje","No selecciono ningún horario");
    }
    for (int i = 0; i < horarios.length; i++) {
      String valor[] = horarios[i].split(":");
      horario.setId_hora(Integer.parseInt(valor[0]));
      horario.setId_dia(Integer.parseInt(valor[1]));
      int iId_aula = Integer.parseInt(request.getParameter("id_aula" + horarios[i]));
      horario.setId_aula(iId_aula);
      int iResultado = this.mi.setHrsRegistrarHorarioAula(horario);
      if (iResultado == 0) {
        return new ModelAndView("Error","mensaje","El horario no se registro");
      }
      sMensaje ="El horario se registro correctamente";
    }
    modelo.put("mensaje", sMensaje);
    return new ModelAndView("administrarHorarios/Salida", modelo);
    //return new ModelAndView("Aviso","mensaje","El horario se registró correctamente");
  }
}