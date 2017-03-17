package org.fautapo.web.administrarProgramasEspecializados.impresionCertificadoDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarAsignacionDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iId_estudiante = 0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    int iId_docente = 0;
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    
    if ((sId_tramite != null) && (!"".equals(sId_tramite))) {
      //Sacamos los datos del Campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setEtiqueta("id_docente");
      datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
      iId_docente = Integer.parseInt(datosTramite.getValores());
    }
    
    //Sacamos los datos del Docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(iId_docente);
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente", datosDocente);
    if(datosDocente == null)
     return new ModelAndView("Aviso","mensaje", "No existe el R.D.  "+Integer.toString(iId_docente));
    //Sacamos sus asignaciones por la gestion y periodo actual
    Asignaciones datosAsignacion= new Asignaciones();
    datosAsignacion.setId_docente(iId_docente);
    List lAsignacionDocente = this.mi.getListarAsignacionDocenteTodas(datosAsignacion);
    modelo.put("lAsignacionDocente", lAsignacionDocente);
    

    //Sacamos el formato de la hora
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }

    return new ModelAndView("administrarProgramasEspecializados/impresionCertificadoDocente/ListarAsignacionDocente", modelo);
  }
}