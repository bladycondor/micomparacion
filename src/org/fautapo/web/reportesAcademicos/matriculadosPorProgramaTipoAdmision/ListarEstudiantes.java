package org.fautapo.web.reportesAcademicos.matriculadosPorProgramaTipoAdmision;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Personas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Recuperamos las variables del jsp
    int iId_tipo_estudiante = cliente.getInt(request, "id_tipo_estudiante");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    String sFecha_ini = cliente.getString(request, "valor_1");
    String sFecha_fin = cliente.getString(request, "valor_2");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));
    modelo.put("fecha_ini", sFecha_ini);
    modelo.put("fecha_fin", sFecha_fin);
    
    //Sacamos el tipo de estudiante
    Personas datosTipoEstudiante = new Personas();
    datosTipoEstudiante.setId_tipo_estudiante(iId_tipo_estudiante);
    datosTipoEstudiante = this.mi.getBuscarTipoEstudiante(datosTipoEstudiante);
    modelo.put("datosTipoEstudiante", datosTipoEstudiante);
    
    //Sacamos el listado de los estudiantes
    Estudiantes datosEstudiantes = new Estudiantes();
    datosEstudiantes.setId_tipo_estudiante(iId_tipo_estudiante);
    datosEstudiantes.setGestion(iGestion);
    datosEstudiantes.setPeriodo(iPeriodo);
    datosEstudiantes.setFecha_ini(sFecha_ini);
    datosEstudiantes.setFecha_fin(sFecha_fin);
    List lEstudiantes = this.mi.getEstListarMatriculadosPorProgramaTipoAdmision(datosEstudiantes);
    modelo.put("lEstudiantes", lEstudiantes);

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }

    Instituciones datosInstitucionSede = new Instituciones();
    datosInstitucionSede.setId_institucion(cliente.getId_almacen()); //--------------------------ESTATICO
    datosInstitucionSede = this.mi.getBuscarInstitucionSede(datosInstitucionSede);
    if (datosInstitucionSede !=null) {
      modelo.put("datosInstitucionsede", datosInstitucionSede);
    }
    
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("reportesAcademicos/matriculadosPorProgramaTipoAdmision/ListarEstudiantes", modelo);
  }
}