package org.fautapo.web.administrarProgramasEspecializados.cambioPrograma.estudiante;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
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

public class EstListarConceptos implements Controller {

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
    modelo.put("id_proceso", Integer.toString(iId_proceso));
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_estudiante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_estudiante = Integer.parseInt(tramite.getValores());

    Perfiles perfil = new Perfiles();
    perfil.setId_proceso(iId_proceso);
    List listaPerfiles = this.mi.getTrnMiListarPerfilesProceso(perfil);
    String sId_perfil_proceso="0";
    if (listaPerfiles.size() == 1) {
      perfil = (Perfiles) listaPerfiles.get(0);
      sId_perfil_proceso = perfil.getId_perfil_proceso();
    } else {
      tramite.setEtiqueta("id_perfil_proceso");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      sId_perfil_proceso = tramite.getValores();
    }
    //Sacamos los datos del estudiante
    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(iId_estudiante);
    modelo.put("estudiante", this.mi.getEstBuscarEstudianteNombres(estudiante));

    //Lista de Programas (carreras)
    Programas programa = new Programas();
    programa.setId_programa(estudiante.getId_programa());
    programa = this.mi.getPrgBuscarPrograma(programa);
    Facultades facultad = new Facultades();
    facultad.setId_facultad(programa.getId_facultad());
    facultad = this.mi.getFclBuscarFacultad(facultad);
    Universidades universidad = new Universidades();
    universidad.setId_universidad(facultad.getId_universidad());
    modelo.put("lFacultades", this.mi.getUnvListarFacultades(universidad));
    modelo.put("lProgramas", this.mi.getUnvListarProgramas(universidad));
    modelo.put("lPlanes", this.mi.getUnvListarPlanes(universidad));

    //Sacamos los datos del perfil
    perfil.setId_perfil_proceso(sId_perfil_proceso);
    perfil = this.mi.getPrcBuscarPerfil(perfil);
    perfil = this.mi.getPrfBuscarPerfil(perfil);
    modelo.put("perfil", perfil);

    int iDescuento = 0;
    try {
      tramite.setEtiqueta("descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      iDescuento = Integer.parseInt(tramite.getValores());

      tramite.setEtiqueta("id_tipo_descuento");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      //Averiguamos el nombre del tipo de descuento
      Perfiles descuento = new Perfiles();
      descuento.setId_tipo_descuento(Integer.parseInt(tramite.getValores()));
      modelo.put("descuento", this.mi.getTrnBuscarTipoDescuento(descuento));
    } catch(Exception e) {};

    perfil.setId_estudiante(iId_estudiante);
    perfil.setId_perfil_proceso(sId_perfil_proceso);
    perfil.setDescuento(iDescuento);
    List lConceptos = this.mi.getEstListarConceptos(perfil);
    modelo.put("lConceptos", lConceptos);
    double total = 0;
    for (int i = 0; i < lConceptos.size(); i++){
      Perfiles cajita = (Perfiles) lConceptos.get(i);
      total += cajita.getCosto() - cajita.getDescuento();
    }
    modelo.put("total", String.valueOf(total));
    Literales literal = new Literales();
    modelo.put("literal", literal.convert(total));

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarProgramasEspecializados/cambioPrograma/estudiante/EstListarConceptos", modelo);
  }
}