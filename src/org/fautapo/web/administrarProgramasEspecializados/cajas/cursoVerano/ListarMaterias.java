package org.fautapo.web.administrarProgramasEspecializados.cajas.cursoVerano;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Tramites;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class ListarMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);

    int iId_proceso = cliente.getInt(request, "id_proceso");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    int iId_estudiante = cliente.getInt(request, "id_estudiante");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    
    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(iId_proceso);
    modelo.put("proceso", this.mi.getBuscarProceso(datosProceso));

    //Verificamos si tiene matricula para la gestion y periodo
    Estudiantes datosMatricula = new Estudiantes();
    datosMatricula.setId_estudiante(iId_estudiante);
    datosMatricula.setGestion(iGestion);
    datosMatricula.setPeriodo(iPeriodo);
    datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
    if (datosMatricula == null) {
      return new ModelAndView("Aviso", "mensaje", "El estudiante con R.U. "+iId_estudiante+" no esta matriculado para la gestion "+iGestion+" y periodo "+iPeriodo);
    }
    if ("B".equals(datosMatricula.getId_estado())) {
      return new ModelAndView("Aviso", "mensaje", "La matricula del estudiante con R.U. "+iId_estudiante+" esta bloqueada");
    }
      
    //Buscamos el id_perfil_proceso
    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    String sId_perfil_proceso="0";
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
    } else {
      Tramites tramite = new Tramites();
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }
    perfil.setId_perfil_proceso(sId_perfil_proceso);
    List lPerfiles = this.mi.getTrnPrcListarPerfiles(perfil);
    perfil = (Perfiles) lPerfiles.get(0);

    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
    estudiante = this.mi.getEstBuscarEstudianteNombres(estudiante);
    modelo.put("estudiante", estudiante);
    estudiante.setGestion(iGestion);
    estudiante.setPeriodo(iPeriodo);
    estudiante.setId_perfil(perfil.getId_perfil());
    modelo.put("lMaterias", this.mi.getTrnListarMateriasVerano(estudiante));
    modelo.put("lTiposEvaluaciones", this.mi.getTrnListarEvaluacionesVerano());
    return new ModelAndView("administrarProgramasEspecializados/cajas/cursoVerano/ListarMaterias", modelo);
  }
}