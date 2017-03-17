package org.fautapo.web.reportesTramites.imprimirReporteTramites;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-25
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-25
*/

public class ListarTramitesImpresionPDF implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_tramite = request.getParameter("id_tramite");

    Tramites datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    List lTramites = this.mi.getListarTramitesImpresion(datosTramite);
    for (int i = 0; i < lTramites.size(); i++) {
      Tramites auxiliar = (Tramites) lTramites.get(i);
      auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
      lTramites.set(i, auxiliar);
    }
    modelo.put("lTramites", lTramites);
    
    return new ModelAndView(new ListarConsultaPDF(), modelo);
  }
}