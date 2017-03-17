package org.fautapo.web.asignacionAuxiliarMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Modelos_ahorros;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Asignaciones;
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

public class RegistrarAsignacionAuxiliar implements Controller {

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
    
    //Estatico id_docente
    //String sId_docente ="1";
     int iResultadoAsignacion =0;
    int iId_docente = cliente.getInt(request, "id_docente");  //id_docente
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iId_asignacion = cliente.getInt(request, "id_asignacion");
    int iId_dpto_grupo = cliente.getInt(request, "id_dpto_grupo");
    int iGestion     = Integer.parseInt(request.getParameter("gestion"));
    int iPeriodo     = Integer.parseInt(request.getParameter("periodo"));
    int iId_programa = Integer.parseInt(request.getParameter("id_programa"));
    String sId_plan  = request.getParameter("id_plan");
    String sCargaHoraria  = request.getParameter("carga_horaria");
    //int iId_materia  = Integer.parseInt(request.getParameter("id_materia"));
    String sId_materia  = request.getParameter("id_materia");
    String sId_grupo = request.getParameter("id_grupo");
    //int iId_tipo_docente = Integer.parseInt(request.getParameter("id_tipo_docente"));
    //int iId_tipo_evaluacion = Integer.parseInt(request.getParameter("id_tipo_evaluacion"));
    //int iId_tipo_asignacion = Integer.parseInt(request.getParameter("id_tipo_asignacion"));
    String sId_tipo_docente = request.getParameter("id_tipo_docente");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    String sId_tipo_asignacion = request.getParameter("id_tipo_asignacion");
    
    String sFec_inicio = request.getParameter("fec_inicio");
    String sFec_fin = request.getParameter("fec_fin");
    String sObservacion = request.getParameter("observacion");
    String sNro_resolucion = request.getParameter("nro_resolucion");
    String sFec_resolucion = request.getParameter("fec_resolucion");
    String sAccion = request.getParameter("accion");
    
    modelo.put("gestion", request.getParameter("gestion"));
    modelo.put("periodo", request.getParameter("periodo"));
    modelo.put("id_programa", request.getParameter("id_programa"));    
    modelo.put("programa", request.getParameter("programa"));        
    modelo.put("id_materia", request.getParameter("id_materia"));        
    modelo.put("materia", request.getParameter("materia"));        
    modelo.put("id_plan", request.getParameter("id_plan"));
    modelo.put("id_grupo", request.getParameter("id_grupo"));
    modelo.put("accion", request.getParameter("accion"));
    
    
    if(("".equals(sAccion)) || (sAccion == null))
      return new ModelAndView("Error","mensaje", "No selecciono una Accion para Asignacion Docente-Materia");
    
    //Buscamos la asignacion docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(iId_asignacion);
    datosAsignacion = this.mi.getDctBuscarAsignacionAuxiliar(datosAsignacion);
    modelo.put("datosAsignacion", datosAsignacion);
    
    //Buscamos los datos de prg_planes
    Planes datosPrgPlan = new Planes();
    datosPrgPlan.setId_prg_plan(iId_prg_plan);
    datosPrgPlan = this.mi.getBuscarPrgPlan2(datosPrgPlan);
    modelo.put("datosPrgPlan", datosPrgPlan);
    if(datosPrgPlan == null)
      return new ModelAndView("Error","mensaje", "No existe el Programa-Plan");
      
    //Buscamos dpto_grupo
    Grupos datosDptoGrupo = new Grupos();
    datosDptoGrupo.setId_dpto_grupo(iId_dpto_grupo);
    datosDptoGrupo = this.mi.getDptoBuscarGrupo(datosDptoGrupo);
    modelo.put("datosDptoGrupo", datosDptoGrupo);
    modelo.put("id_tipo_evaluacion", Integer.toString(datosDptoGrupo.getId_tipo_evaluacion()));
    if(datosDptoGrupo == null)
      return new ModelAndView("Error","mensaje", "No existen datos en Dpto-Grupos");
    
    //Sacamos los datos del programa
    Programas programa = new Programas();
    programa.setId_programa(iId_programa);
    Programas datosPrograma = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", datosPrograma);
    modelo.put("id_plan", sId_plan);
    
    if(!"Eliminar".equals(sAccion)) {
      //Sacamos los datos de la Materia
      Materias materia = new Materias();
      materia.setId_materia(Integer.parseInt(sId_materia));
      materia = (Materias) this.mi.getMtrBuscarMateria(materia);
      modelo.put("materia", materia);
      
      //Registramos la asignacion docente
      Asignaciones datosDoc = new Asignaciones();
      datosDoc.setId_docente(iId_docente);
      datosDoc.setId_departamento(materia.getId_departamento());  //de dpto_grupo
      datosDoc.setId_materia(materia.getId_materia());
      datosDoc.setId_grupo(datosDptoGrupo.getId_grupo());
      datosDoc.setId_modelo_ahorro(datosDptoGrupo.getId_modelo_ahorro());
      datosDoc.setId_fase(0);  //Empieza en fase //Cambiar
      datosDoc.setId_tipo_evaluacion(datosDptoGrupo.getId_tipo_evaluacion());
      datosDoc.setId_tipo_docente(Integer.parseInt(sId_tipo_docente));
      datosDoc.setId_tipo_asignacion(Integer.parseInt(sId_tipo_asignacion));
      datosDoc.setGestion(datosDptoGrupo.getGestion());
      datosDoc.setPeriodo(datosDptoGrupo.getPeriodo());
      datosDoc.setObservacion(sObservacion);
      datosDoc.setFec_inicio(sFec_inicio);
      datosDoc.setFec_fin(sFec_fin);
      datosDoc.setSnro_resolucion(sNro_resolucion);
      datosDoc.setFec_resolucion2(request.getParameter("fec_resolucion"));
      datosDoc.setId_asignacion(iId_asignacion);
      datosDoc.setId_rol(cliente.getId_rol());
      datosDoc.setUlt_usuario(cliente.getId_usuario());
      datosDoc.setCarga_horaria(sCargaHoraria);
      System.out.println("ID PLAN = "+sId_plan );
      datosDoc.setId_plan(sId_plan);
      iResultadoAsignacion = this.mi.setRegistrarAsignacionAuxiliar(datosDoc);
    }
    else {
      //Eliminar asignacion docente-materia
      if(datosAsignacion != null) {
        System.out.println("BORRAR ID_ASIGNACION = "+datosAsignacion.getId_asignacion());
        System.out.println("BORRAR ID_ROL = "+cliente.getId_rol());	
	System.out.println("BORRAR ID_USUARIO = "+cliente.getId_usuario());	
	
        Asignaciones eliminar = new Asignaciones();
        eliminar.setId_asignacion(datosAsignacion.getId_asignacion());
	eliminar.setId_rol(cliente.getId_rol());;
	eliminar.setUlt_usuario(cliente.getId_usuario());
	iResultadoAsignacion = this.mi.setEliminarAsignacionAuxiliarMateria(eliminar);
      }	
    } 
    if(iResultadoAsignacion >0) {
      //Listamos las materias del plan mas sus grupos
      Modelos_ahorros aux = new Modelos_ahorros();
      aux.setId_programa(iId_programa);
      //System.out.println("El id_programa dct -->"+Integer.toString(aux.getId_programa()));
      aux.setId_plan(datosPrgPlan.getId_plan());  //Sacando el plan de prg_planes
      //System.out.println("El id_plan dct -->"+aux.getId_plan());
      aux.setGestion(iGestion);
      //System.out.println("La gestion dct -->"+Integer.toString(aux.getGestion()));
      aux.setPeriodo(iPeriodo);
      //System.out.println("El periodo dct -->"+Integer.toString(aux.getPeriodo()));
      aux.setId_tipo_grado(datosPrgPlan.getId_tipo_grado()); //Sacando el id_tipo_grado Universitario-Vestibular
      //System.out.println("El id_tipo_grado dct -->"+Integer.toString(aux.getId_tipo_grado()));
      List listaPlanEstudio = this.mi.getListarPlanProgramaModeloAhorro(aux);
      for (int i = 0; i < listaPlanEstudio.size(); i++) {
        Modelos_ahorros materias = (Modelos_ahorros) listaPlanEstudio.get(i);
        aux.setId_materia(materias.getId_materia());
        //System.out.println("El id_materia dct -->"+Integer.toString(materias.getId_materia()));
        aux.setId_modelo_ahorro(materias.getId_modelo_ahorro());
        //System.out.println("El id_modelo_ahorro dct -->"+Integer.toString(materias.getId_modelo_ahorro()));
        aux.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
        //System.out.println("El id_tipo_evaluacion dct -->"+Integer.toString(aux.getId_tipo_evaluacion()));
        materias.setGrupos(this.mi.getDptoListarGruposMateriaTipoEvaluacionAuxiliares(aux));
        materias.setNro_grupos(materias.getGrupos().size());
        listaPlanEstudio.set(i, materias);
      }
      PagedListHolder listarplanestudios = new PagedListHolder(listaPlanEstudio);
      listarplanestudios.setPageSize(listarplanestudios.getNrOfElements());
      modelo.put("listarplanestudios", listarplanestudios);
    
      modelo.put("gestion", Integer.toString(datosDptoGrupo.getGestion()));
      modelo.put("periodo", Integer.toString(datosDptoGrupo.getPeriodo()));
      modelo.put("programa", datosPrograma);
      //modelo.put("id_plan", sId_plan);
      
      //return new ModelAndView("asignacionDocenteMaterias/ListarMaterias", modelo);
      return new ModelAndView("asignacionAuxiliarMaterias/ListarMaterias", modelo);
    }
    else {
        return new ModelAndView("Error","mensaje","No se realizo la accion de asignacion del auxiliar");
    }
    //return new ModelAndView("asignacionDocenteMaterias/Entrada", "cliente",cliente);
  }
}