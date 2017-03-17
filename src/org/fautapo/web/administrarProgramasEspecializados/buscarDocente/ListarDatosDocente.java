package org.fautapo.web.administrarProgramasEspecializados.buscarDocente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Perfiles;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarDatosDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iId_docente = 0;
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sId_docente = request.getParameter("id_docente");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_docente", sId_docente);
    modelo.put("id_tramite", sId_tramite);

    try {
      iId_docente = Integer.parseInt(sId_docente);
    }
    catch(Exception e) {
      return new ModelAndView("administrarProgramasEspecializados/buscarDocente/Entrada", modelo);
    }

    //Sacamos los datos del Docente
    Docentes datosDocente = new Docentes();
    datosDocente.setId_docente(iId_docente);
    datosDocente = this.mi.getBuscarDocente(datosDocente);
    modelo.put("datosDocente", datosDocente);
    if(datosDocente == null)
     return new ModelAndView("Aviso","mensaje", "No existe el registro unico de docente"+sId_docente);
    //Sacamos sus asignaciones por la gestion y periodo actual
    Asignaciones datosAsignacion= new Asignaciones();
    datosAsignacion.setId_docente(iId_docente);
    datosAsignacion.setGestion(cliente.getGestion());
    datosAsignacion.setPeriodo(cliente.getPeriodo());
    List lAsignacionDocente = this.mi.getDctListarAsignacionDocente(datosAsignacion);
    modelo.put("lAsignacionDocente", lAsignacionDocente);
    
    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);

    try {
      //Sacamos el id_campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
      datosTramite.setEtiqueta("requisitos");
      int iId_campo = this.mi.getBuscarIdCampoLimbo(datosTramite);

      //Buscamos los datos del campo
      Campos datosCampo = new Campos();
      datosCampo.setId_campo(iId_campo);
      datosCampo = this.mi.getBuscarCampoForm(datosCampo);

      //Sacamos los datos del dominio
      Dominios datosDominio =  new Dominios();
      datosDominio.setId_dominio(datosCampo.getId_dominio());
      datosDominio = this.mi.getBuscarDominio(datosDominio);
    
      //Sacamos los datos de los requisitos
      datosTramite = new Tramites();
      datosTramite.setId_dominio(datosDominio.getId_dominio());
      datosTramite.setId_tupla_padre(datosDominio.getId_tupla_padre());
      datosTramite.setId_tipo_dominio(datosDominio.getId_tipo_dominio());
      datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
      List lTuplas = this.mi.getListarCombos2(datosTramite);
      modelo.put("lTuplas", lTuplas);
    } catch(Exception e) {
      System.out.println("El proceso no tiene requisitos");
    }

    try {
      //Verificamos si el perfil puede tener descuento
      Perfiles datosPerfil = new Perfiles();
      datosPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfiles = this.mi.getTrnMiListarPerfilesProceso(datosPerfil);
      datosPerfil = (Perfiles) lPerfiles.get(0);
      int iTieneDescuento = this.mi.getTrnPerfilTieneDescuento(datosPerfil);
      modelo.put("tieneDescuento", Integer.toString(iTieneDescuento));
      modelo.put("descuento", "0");
      //Listamos tipos descuentos
      List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
      modelo.put("lTiposDescuentos", lTiposDescuentos);    
    } catch(Exception e) {
      return new ModelAndView("Error", "mensaje", "El proceso no tiene asignado un perfil");
    }
    return new ModelAndView("administrarProgramasEspecializados/buscarDocente/ListarDatosDocente", modelo);
  }
}