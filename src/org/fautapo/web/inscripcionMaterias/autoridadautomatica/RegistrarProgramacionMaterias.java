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
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarProgramacionMaterias implements Controller {

  private MiFacade mi;;
     
  public void setMi(MiFacade mi) {this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");

    int iResultado;
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_periodo    = request.getParameter("id_periodo");
    int iTotal_materias   = Integer.parseInt(request.getParameter("total_materias"));
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");

    modelo.put("cliente", cliente);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    modelo.put("id_tipo_evaluacion", sId_tipo_evaluacion);
    
    //Buscamos el tipo Evaluacion para programar
    Libretas datosTipoEval = new Libretas();
    datosTipoEval.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion));
    datosTipoEval = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEval);
    modelo.put("datosTipoEval",datosTipoEval);
    

    //Sacando los datos del estudiante    
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    //Buscamos el periodo
    Programas buscarPeriodo= new Programas();
    buscarPeriodo.setId_programa(datosEstudiante.getId_programa());
    buscarPeriodo = this.mi.getPrdBuscarPrgPeriodo(buscarPeriodo);

    boolean bBandera = true;
    for (int i = 0; i < iTotal_materias; i++) {
      String sMateria = request.getParameter("materia" + i);
      String sDatos[] = sMateria.split(":"); 
      Grupos grupo = new Grupos();
      grupo.setId_materia(Integer.parseInt(sDatos[0]));
      grupo.setId_modelo_ahorro(Integer.parseInt(sDatos[1]));
      grupo.setGestion(Integer.parseInt(sGestion));
      grupo.setPeriodo(Integer.parseInt(sPeriodo));
      grupo.setId_grupo(Integer.parseInt(sDatos[2]));

      grupo = this.mi.getDptoBuscarCupoRestanteGrupo(grupo);
      if (grupo.getCupo_actual() > 0) {
        Programas programacion = new Programas();
	programacion.setId_estudiante(datosEstudiante.getId_estudiante());
	programacion.setId_materia(Integer.parseInt(sDatos[0]));
	programacion.setId_grupo(Integer.parseInt(sDatos[2]));
	programacion.setId_modelo_ahorro(Integer.parseInt(sDatos[1]));
	programacion.setGestion(Integer.parseInt(sGestion));
	programacion.setPeriodo(Integer.parseInt(sPeriodo));
	programacion.setId_tipo_evaluacion(Integer.parseInt(sId_tipo_evaluacion)); //Tipo_evaluacion de programacion Regular-Verano-Mesa
	programacion.setId_rol(cliente.getId_rol());         //CRCB
	programacion.setUlt_usuario(cliente.getId_usuario());
        this.mi.setEstProgramacionMateria(programacion);
      }
      else {
        bBandera = false;
      }
    }
    if (!bBandera) {
      return new ModelAndView("Aviso","mensaje","Alguna de sus materias no fueron programadas por falta de plazas");
    }
    // INICIO - DESDE AQUI LA IMPRESION DE LA PROGRAMACION
    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);
    
    //Listamos la programacion del estudiante
    datosEstudiante.setGestion(Integer.parseInt(sGestion));
    datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
    if(("1".equals(sId_tipo_evaluacion))||("4".equals(sId_tipo_evaluacion)))	
	{
	     List lProgramacion = this.mi.getEstListarProgramacion(datosEstudiante);
		 modelo.put("lProgramacion", lProgramacion);
	}
	 else
	 {
		 if ("3".equals(sId_tipo_evaluacion))
		 {
			List lProgramacion = this.mi.getEstListarProgramacioncv(datosEstudiante);
		    modelo.put("lProgramacion", lProgramacion);			 
		 }
	 }
		
	 
    
    
    //Sacamos los datos del Estudiante
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante2", datosEstudiante);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    // FIN - DESDE AQUI LA IMPRESION DE LA PROGRAMACION

    return new ModelAndView("inscripcionMaterias/autoridadautomatica/ImprimirProgramacion", modelo);
  }
}