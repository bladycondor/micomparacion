package org.fautapo.web.waykaDibRep;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;

import org.fautapo.domain.Dibwayka;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class DibRep implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    //CONTROL DE SESION
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    String _nombres = cliente.getNombres();
    List lCamposProceso;
    String descripcion = request.getParameter("descripcion");
    modelo.put("descripcion", descripcion);

    List lProcesos = this.mi.getListarProcesosAccesoTramites(cliente);
    modelo.put("lProcesos", lProcesos);

    String sId_proceso = request.getParameter("id_proceso");
    if ((sId_proceso!=null)&&(!sId_proceso.equals("0"))) {
      int iId_proceso = Integer.parseInt(sId_proceso);
      Actividades oProceso = new Actividades();
      oProceso.setId_proceso(iId_proceso);
      oProceso = this.mi.getBuscarProceso(oProceso);
      modelo.put("proceso",oProceso);

      Dibwayka oCamposProceso = new Dibwayka();
      oCamposProceso.setId_proceso(oProceso.getId_proceso());
      lCamposProceso = this.mi.getListarCamposProcesoWK(oCamposProceso);
      for (int i=0; i<lCamposProceso.size(); i++) {
        Dibwayka oCampo = (Dibwayka)lCamposProceso.get(i);
        List lTuplasCombo = this.mi.getListarComboWK(oCampo);
        if(lTuplasCombo.size()>0) {
          oCampo.setLista_combo(lTuplasCombo);
        }
        lCamposProceso.set(i,oCampo);
      }
      modelo.put("lCamposProceso",lCamposProceso);
    }
    return new ModelAndView("waykaDibRep/Generador", modelo);
  }
}