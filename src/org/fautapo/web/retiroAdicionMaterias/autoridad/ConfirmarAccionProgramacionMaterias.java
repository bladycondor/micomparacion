package org.fautapo.web.retiroAdicionMaterias.autoridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class ConfirmarAccionProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo         = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    int iNro_teoricas = 0, iNro_laboratorios = 0, iNro_normales = 0;
    String sNombre_materia = "", sSigla = "", sCadena="", sMensaje="";
    List lMaterias = new ArrayList();
    //Recuperamos datos del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_periodo = request.getParameter("id_periodo");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sAccion=request.getParameter("accion");
    String sId_tipo_evaluacion=request.getParameter("id_tipo_evaluacion");
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
    //Buscamos el tipo Evaluacion para programar
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval",datosTipoEval);
    
    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante",datosEstudiante);    
    modelo.put("id_programa",Integer.toString(datosEstudiante.getId_programa()));    
    
    //Sacando los datos personales del Estudiante encontrado
    Personas datosPersona = new Personas();
    datosPersona.setId_persona(datosEstudiante.getId_persona());
    datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
    modelo.put("datosPersona", datosPersona);

    //Sacando el programa en que esta el estudiante
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);
    modelo.put("id_periodo", Integer.toString(buscarPeriodo.getId_periodo()));    
    
    //Sacando los parametros de programacion de prg_detalles
    Programas parametro = new Programas();
    parametro.setId_programa(datosEstudiante.getId_programa());
    parametro.setId_plan(datosEstudiante.getId_plan());
    parametro.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
    parametro.setId_tipo_programacion(1); //COMO AUTORIDAD
    parametro.setGestion(Integer.parseInt(sGestion));
    parametro.setPeriodo(Integer.parseInt(sPeriodo));
    List lParametros = this.mi.getPrgBuscarDetalles(parametro);
    if (lParametros.size() == 0) {
      //return new ModelAndView("Aviso","mensaje","No existe parametros de programación");
      sMensaje="No existe parametros de programación";
      modelo.put("mensaje",sMensaje);
      return new ModelAndView("retiroAdicionMaterias/autoridad/Salida", modelo);
    }
    modelo.put("lParametros", lParametros);
    int iMax_materias_teoricas = parametro.getMax_materias_teoricas();
    int iMax_materias_laboratorios = parametro.getMax_materias_laboratorios();
    float fCosto_materia_teorica = parametro.getCosto_materia_teorica();
    float fCosto_materia_laboratorio = parametro.getCosto_materia_laboratorio();
    
    //Recuperamos las materias seleccionadas
    String sValores[] = request.getParameterValues("materia");
    if (sValores == null) {
      sMensaje="No selecciono nínguna materia";
      modelo.put("mensaje",sMensaje);
      return new ModelAndView("retiroAdicionMaterias/autoridad/Salida", modelo);
      //return new ModelAndView("Aviso","mensaje","No selecciono nínguna materia");    
    }
    
    if("Retiro".equals(sAccion)){
      for (int i=0; i < sValores.length; i++) {
        if (sValores[i] != null) {
          Programas aux = new Programas();
          Materias materia_obj = new Materias();
          String sDatos[] = sValores[i].split(":");
          int iId_programacion = Integer.parseInt(sDatos[0]);
	  int iId_materia = Integer.parseInt(sDatos[1]);
          int iId_modelo_ahorro = Integer.parseInt(sDatos[2]);
	  if ((iId_modelo_ahorro  < 0) ||(iId_modelo_ahorro == 0)){                        
	    iNro_teoricas = iNro_teoricas + 1;
	    materia_obj.setId_materia(iId_materia);
            materia_obj = this.mi.getMtrBuscarMateria(materia_obj);
	    sNombre_materia = materia_obj.getMateria();
	    sSigla = materia_obj.getSigla();
	  }
	  else {
  	    aux.setId_modelo_ahorro(iId_modelo_ahorro);
	    aux = this.mi.getMdlBuscarMateriaAhorro(aux);
	    sNombre_materia = aux.getModelo_ahorro();
	    sSigla = "--";
	    iNro_laboratorios = iNro_laboratorios + 1;
	  }
          int iId_grupo = Integer.parseInt(sDatos[3]);
	  Grupos grupo_obj = new Grupos();
	  grupo_obj.setId_grupo(iId_grupo);
	  grupo_obj = this.mi.getGrpBuscarGrupo(grupo_obj);
          aux.setId_materia(iId_materia);
          aux.setMateria(sNombre_materia);
          aux.setSigla(sSigla);
	  aux.setId_modelo_ahorro(iId_modelo_ahorro);
	  aux.setGrupo(grupo_obj.getGrupo());
	  aux.setId_grupo(iId_grupo);
	  aux.setId_programacion(iId_programacion);
          lMaterias.add(aux);
        }
      }
      //Como todo esta bien, sumamos el total de materias
      int iTotal_materias = iNro_teoricas +iNro_laboratorios;
      modelo.put("total_materias", Integer.toString(iTotal_materias));
      modelo.put("lMaterias", lMaterias);
      modelo.put("accion",sAccion);
      //modelo.put("gestion",sGestion);
      //modelo.put("periodo",sPeriodo);
      return new ModelAndView("retiroAdicionMaterias/autoridad/ConfirmarAccionProgramacionMaterias", modelo);
    }
    
    if ("Adicion".equals(sAccion) || ("Cambio de grupo".equals(sAccion))) {
      //Listamos las materias que tienen choque de horario
      for (int i=0; i < sValores.length; i++){
        String id_grupo = request.getParameter("id_grupo:"+sValores[i]);
	try{
	  int iId_grupo = Integer.parseInt(id_grupo);
	}
	catch(Exception e) {
	  return new ModelAndView("Error","mensaje","No selecciono el grupo de  la materia. Revise por favor");
	}
        sCadena = sCadena + sValores[i] + ":" + id_grupo + "|";
        System.out.println(" ARMANDO LA CADENA -->" +sCadena);
      }
      Programas choque = new Programas();
      choque.setId_programa(datosEstudiante.getId_programa());
      choque.setId_plan(datosEstudiante.getId_plan());
      choque.setGestion(cliente.getGestion());
      choque.setPeriodo(cliente.getPeriodo());
      choque.setMaterias(sCadena);
      choque.setId_estudiante(datosEstudiante.getId_estudiante());
/*
      List lChoqueMaterias = this.mi.getZchListarChoqueMaterias(choque); // QUE RARO NO APARECE ID_ESTUDIANTE
      modelo.put("lChoqueMaterias", lChoqueMaterias);    
      modelo.put("tamanio_choque", Integer.toString(lChoqueMaterias.size()));
      List lChoquePeriodos = this.mi.getZchListarChoquePeriodos(choque);
      modelo.put("lChoquePeriodos", lChoquePeriodos);
      modelo.put("tamanio_choque_periodo", Integer.toString(lChoquePeriodos.size()));
*/
      for (int i=0; i < sValores.length; i++) {
        if (sValores[i] != null) {
          Programas aux = new Programas();
          Materias materia_obj = new Materias();
          String sDatos[] = sValores[i].split(":");
          int iId_materia = Integer.parseInt(sDatos[0]);
          int iId_modelo_ahorro = Integer.parseInt(sDatos[1]);
	  if ((iId_modelo_ahorro  < 0) ||(iId_modelo_ahorro == 0)){                        
	    iNro_teoricas = iNro_teoricas + 1;
	    materia_obj.setId_materia(iId_materia);
            materia_obj = this.mi.getMtrBuscarMateria(materia_obj);
	    sNombre_materia = materia_obj.getMateria();
	    sSigla = materia_obj.getSigla();
	  }
	  else {
  	    aux.setId_modelo_ahorro(iId_modelo_ahorro);
	    aux = this.mi.getMdlBuscarMateriaAhorro(aux);
	    sNombre_materia = aux.getModelo_ahorro();
	    sSigla = "--";
	    iNro_laboratorios = iNro_laboratorios + 1;
	  }
          String sId_grupo = request.getParameter("id_grupo:"+sValores[i]);
	  Grupos grupo_obj = new Grupos();
	  grupo_obj.setId_grupo(Integer.parseInt(sId_grupo));
	  grupo_obj = this.mi.getGrpBuscarGrupo(grupo_obj);
          aux.setId_materia(iId_materia);
          aux.setMateria(sNombre_materia);
          aux.setSigla(sSigla);
	  aux.setId_modelo_ahorro(iId_modelo_ahorro);
	  aux.setGrupo(grupo_obj.getGrupo());
	  aux.setId_grupo(Integer.parseInt(sId_grupo));
          lMaterias.add(aux);
        }
      }
      //Como todo esta bien, sumamos el total de materias
      int iTotal_materias = iNro_teoricas +iNro_laboratorios;
      modelo.put("total_materias", Integer.toString(iTotal_materias));
      modelo.put("lMaterias", lMaterias);
      modelo.put("accion",sAccion);
      //modelo.put("gestion",sGestion);
      //modelo.put("periodo",sPeriodo);
      return new ModelAndView("retiroAdicionMaterias/autoridad/ConfirmarAccionProgramacionMaterias", modelo);
    }
    return new ModelAndView("retiroAdicionMaterias/autoridad/ConfirmarAccionProgramacionMaterias", modelo);
  }
}