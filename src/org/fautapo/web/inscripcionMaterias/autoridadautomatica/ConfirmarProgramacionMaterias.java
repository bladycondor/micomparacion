package org.fautapo.web.inscripcionMaterias.autoridadautomatica;

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

public class ConfirmarProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo         = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    int iNro_teoricas = 0;
    int iNro_laboratorios = 0;
    int iNro_normales = 0;
    String sNombre_materia = "";
    String sSigla = "";
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_periodo = request.getParameter("id_periodo");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
    
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

    //Sacando los parametros de progrmacion de prg_detalles
    Programas parametro = new Programas();
    parametro.setId_programa(datosEstudiante.getId_programa());
    parametro.setId_plan(datosEstudiante.getId_plan());
    parametro.setId_tipo_grado(datosEstudiante.getId_tipo_grado());
    parametro.setId_tipo_programacion(1); //COMO AUTORIDAD
    parametro.setGestion(Integer.parseInt(sGestion));
    parametro.setPeriodo(Integer.parseInt(sPeriodo));
    List lParametros = this.mi.getPrgBuscarDetalles(parametro);
    
    if(lParametros.size() == 0){
      return new ModelAndView("Aviso","mensaje","No existe parametros de programación");
    }
    modelo.put("lParametros", lParametros);

    int iMax_materias_teoricas = parametro.getMax_materias_teoricas();
    
    int iMax_materias_laboratorios = parametro.getMax_materias_laboratorios();
    float fCosto_materia_teorica = parametro.getCosto_materia_teorica();
    float fCosto_materia_laboratorio = parametro.getCosto_materia_laboratorio();

    //Recuperamos las materias seleccionadas
    String sValores[] = request.getParameterValues("materia");
    if (sValores == null) {
      return new ModelAndView("Aviso","mensaje","No selecciono nínguna materia");    
    }

    List lMaterias = new ArrayList();

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
	try{
	  grupo_obj.setId_grupo(Integer.parseInt(sId_grupo));
	}
	catch(Exception e) {
	  return new ModelAndView("Error","mensaje","No selecciono el grupo de  la materia. Revise por favor");
	}  
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
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);  
    return new ModelAndView("inscripcionMaterias/autoridadautomatica/ConfirmarProgramacionMaterias", modelo);
  }
}