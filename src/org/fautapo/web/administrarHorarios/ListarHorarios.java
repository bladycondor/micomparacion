package org.fautapo.web.administrarHorarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Horarios;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-18
 */

public class ListarHorarios implements Controller {

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

    int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    //int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    String sId_plan  = request.getParameter("id_plan");
    int iId_materia  = Integer.parseInt(request.getParameter("id_materia"));
    int iId_dpto_grupo = Integer.parseInt( request.getParameter("id_dpto_grupo"));
    int iId_modelo_ahorro = Integer.parseInt(request.getParameter("id_modelo_ahorro"));
    
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");

    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("id_programa", request.getParameter("id_programa"));    
    modelo.put("programa", request.getParameter("programa"));        
    modelo.put("id_materia", request.getParameter("id_materia"));        
    modelo.put("materia", request.getParameter("materia"));        
    modelo.put("id_plan", request.getParameter("id_plan"));
    
    
    
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
    Grupos grupo = new Grupos();
    grupo.setId_dpto_grupo(iId_dpto_grupo);
    grupo = this.mi.getDptoBuscarGrupo(grupo);
    modelo.put("grupo", grupo);
    //modelo.put("id_plan", sId_plan);
    
    //Sacamos los datos de los horarios
    Horarios horario = new Horarios();
    horario.setId_programa(iId_programa);
    //horario.setId_plan(iId_plan);
    horario.setId_materia(iId_materia);
    horario.setId_grupo(grupo.getId_grupo());
    horario.setId_modelo_ahorro(iId_modelo_ahorro);
    horario.setGestion(iGestion);
    horario.setPeriodo(iPeriodo);
    horario.setId_tipo_evaluacion(iId_tipo_evaluacion);  //Verano -Mesa-Regular
    List listaHorario = this.mi.getListarHorario(horario);
    for (int i = 0; i < listaHorario.size(); i++) {
      Horarios horas = (Horarios) listaHorario.get(i);
        horas.setId_programa(iId_programa);
	horas.setGestion(iGestion);
	horas.setPeriodo(iPeriodo);
	horas.setId_tipo_evaluacion(iId_tipo_evaluacion);  //Verano-Mesa-Regular
        horas.setAulas(this.mi.getListarAulasDisponibles(horas));
	horas.setNro_aulas(horas.getAulas().size());
	listaHorario.set(i, horas);
    }
    
    //Sacamos los datos de la Materia
    Materias materia = new Materias();
    materia.setId_materia(iId_materia);
    materia = (Materias) this.mi.getMtrBuscarMateria(materia);
    modelo.put("datosMateria", materia);

    //Listamos los horarios
    List lDeHorarios = listaHorario;
    modelo.put("lDeHorarios", lDeHorarios);
    
    //Listamos los dias
    List lDeDias = this.mi.getListarDias();
    modelo.put("lDeDias", lDeDias);

    modelo.put("id_modelo_ahorro", Integer.toString(iId_modelo_ahorro));

    return new ModelAndView("administrarHorarios/ListarHorarios", modelo);
  }
}