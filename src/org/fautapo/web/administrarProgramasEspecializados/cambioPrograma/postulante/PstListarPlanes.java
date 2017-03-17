package org.fautapo.web.administrarProgramasEspecializados.cambioPrograma.postulante;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Literales;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class PstListarPlanes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iId_tramite = cliente.getInt(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");

    //Buscamos los datos del proceso
    Actividades proceso = new Actividades();
    proceso.setId_proceso(iId_proceso);
    modelo.put("proceso", this.mi.getBuscarProceso(proceso));
    modelo.put("id_tramite", Integer.toString(iId_tramite));
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_postulante = Integer.parseInt(tramite.getValores());
    //Sacamos los datos del postulante
    Postulantes postulante = new Postulantes();
    postulante.setId_postulante(iId_postulante);
    postulante = this.mi.getPstBuscarPostulanteNombres(postulante);
    modelo.put("postulante", postulante);
    //Lista de Programas (carreras)
    Programas programa = new Programas();
    programa.setId_programa(postulante.getId_programa());
    programa = this.mi.getPrgBuscarPrograma(programa);
    Facultades facultad = new Facultades();
    facultad.setId_facultad(programa.getId_facultad());
    facultad = this.mi.getFclBuscarFacultad(facultad);
    Universidades universidad = new Universidades();
    universidad.setId_universidad(facultad.getId_universidad());
    modelo.put("lFacultades", this.mi.getUnvListarFacultades(universidad));
    modelo.put("lProgramas", this.mi.getUnvListarProgramas(universidad));
    Planes plan = new Planes();
    plan.setId_universidad(universidad.getId_universidad());
    plan.setId_tipo_grado(postulante.getId_tipo_grado());
    modelo.put("lPlanes", this.mi.getUnvGrdListarPlanes(plan));
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarProgramasEspecializados/cambioPrograma/postulante/PstListarConceptos", modelo);
  }
}