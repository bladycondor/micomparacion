package org.fautapo.web.administrarProgramasEspecializados.cambioPrograma.postulante;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class PstRegistrarPrograma implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    int iId_tramite = cliente.getInt(request, "id_tramite");
    int iId_proceso = cliente.getInt(request, "id_proceso");
    int iId_programa = cliente.getInt(request, "id_programa");
    String sNombres = cliente.getNombres();
    String sId_plan = cliente.getString(request, "id_plan");
    modelo.put("programa", cliente.getString(request, "programa"));
    modelo.put("plan", cliente.getString(request, "plan"));
    modelo.put("usuario", sNombres);

    Tramites tramite = new Tramites();
    tramite.setId_tramite(iId_tramite);
    tramite.setEtiqueta("id_postulante");
    tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
    int iId_postulante = Integer.parseInt(tramite.getValores());

    Postulantes postulante = new Postulantes();
    postulante.setId_postulante(iId_postulante);
    postulante = this.mi.getPstBuscarPostulanteNombres(postulante);
    modelo.put("postulante", postulante);
    
    Programas programa = new Programas();
    programa.setId_programa(postulante.getId_programa());
    programa = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa_ant", programa);
    postulante.setId_programa(iId_programa);
    postulante.setId_plan(sId_plan);
	postulante.setId_estado("B");
    postulante.setId_rol(cliente.getId_rol());
    postulante.setUlt_usuario(cliente.getId_usuario());
	
	//desde aqui se agrega
	
	
    this.mi.setPstRegistrarPrograma(postulante);
	
    this.mi.setMiRegistrarPostulanteC(postulante);

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    //Sacamos el formato de la hora
    formatoFecha.setCampo("formato_hora");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));

    tramite.setUlt_usuario(cliente.getId_usuario());
    this.mi.setRegistrarTrPrFrLogLimbo(tramite);
    
    return new ModelAndView("administrarProgramasEspecializados/cambioPrograma/postulante/PstImpresionRecibo", modelo);
  }
}