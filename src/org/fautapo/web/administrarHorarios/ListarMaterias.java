package org.fautapo.web.administrarHorarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Modelos_ahorros;
import org.fautapo.domain.Programas;
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

public class ListarMaterias implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //int iId_programa=0; String sId_plan="";
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    if ("".equals(Integer.toString(cliente.getId_rol()))) {
      modelo.put("mensaje", "No se le permite entrar a este m�dulo");
      return new ModelAndView("Error", modelo);
    }
 
    //Recuperamos las variables
    int iGestion = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo = Integer.parseInt(request.getParameter("periodo"));
    String sId_facultad = request.getParameter("id_facultad");
    
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    
    
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    
    if(datosPrgPlan == null)
      return new ModelAndView("Aviso","mensaje","No existe el plan seleccionado en Programas - Planes");
    
    if(iId_tipo_evaluacion == 0)
      return new ModelAndView("Aviso","mensaje","Seleccione el Tipo de Evalucion");  
    
    //Buscamos el programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscar Tipo evaluacion
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(iId_tipo_evaluacion);
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval", datosTipoEval);
    
    
    //Listamos las materias del plan m�s sus grupos
    Modelos_ahorros aux = new Modelos_ahorros();
    aux.setId_programa(iId_programa);
    System.out.println("El id_programa grp -->"+Integer.toString(aux.getId_programa()));
    aux.setId_plan(datosPrgPlan.getId_plan());  //Sacando el plan de prg_planes
    System.out.println("El id_plan grp -->"+aux.getId_plan());
    aux.setGestion(iGestion);
    System.out.println("La gestion grp -->"+Integer.toString(aux.getGestion()));
    aux.setPeriodo(iPeriodo);
    System.out.println("El periodo grp -->"+Integer.toString(aux.getPeriodo()));
    aux.setId_tipo_grado(datosPrgPlan.getId_tipo_grado()); //Sacando el id_tipo_grado Universitario-Vestibular
    System.out.println("El id_tipo_grado grp -->"+Integer.toString(aux.getId_tipo_grado()));
    List listaPlanEstudio = this.mi.getListarPlanProgramaModeloAhorro(aux);
    
    for (int i = 0; i < listaPlanEstudio.size(); i++) {
      Modelos_ahorros materias = (Modelos_ahorros) listaPlanEstudio.get(i);
      aux.setId_materia(materias.getId_materia());
      aux.setId_modelo_ahorro(materias.getId_modelo_ahorro());
      aux.setId_tipo_evaluacion(iId_tipo_evaluacion);
      materias.setGrupos(this.mi.getDptoListarGruposMateria(aux));
      materias.setNro_grupos(materias.getGrupos().size());
      listaPlanEstudio.set(i, materias);
    }
    
    List lPlanEstudios = listaPlanEstudio;
    //listarplanestudios.setPageSize(listarplanestudios.getNrOfElements());
    //modelo.put("listarplanestudios", listarplanestudios);
    modelo.put("lPlanEstudios", lPlanEstudios);
    
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("id_programa", request.getParameter("id_programa"));
    
    return new ModelAndView("administrarHorarios/ListarMaterias", modelo);
  }
}