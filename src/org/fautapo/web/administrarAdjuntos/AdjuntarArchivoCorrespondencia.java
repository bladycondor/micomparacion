package org.fautapo.web.administrarAdjuntos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class AdjuntarArchivoCorrespondencia implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iContador = 0; int iNro_registros; int iCodigo=0; int sw = 0; int iResultado = 0; int iForm = 0;
    String sDato=""; String sChequeados[]; String sDatox[]; String sDatoy[]; String sArchivos="";
    List listita = new ArrayList();  
    Tramites auxiliar1; Tramites datosTramite; Dominios datosTupla; Dominios datosDominio;

    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_tramite = request.getParameter("id_tramite");   
    String sId_proceso = request.getParameter("id_proceso");    
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");        
    String sProveido = request.getParameter("proveido_0");        
    String sId_actividad_actual = request.getParameter("id_actividad_actual");    
    String sId_form = request.getParameter("id_form");    
    String sAuxiliar = request.getParameter("auxiliar");
    String sNro_registros =request.getParameter("nu_registros");
    String sRecargado = request.getParameter("recargado");
    String sCombito = request.getParameter("combito");
    String sId_usuario_a = request.getParameter("id_usuario_a");
    String sUsuarios2 = request.getParameter("usuarios2");
    String sCodigos = request.getParameter("codigos");

    try {
      iNro_registros = Integer.parseInt(sNro_registros);
    }
    catch (Exception h) {
      iNro_registros = 0;
    }
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tipo_proceso", sId_tipo_proceso);    
    modelo.put("id_actividad_actual", sId_actividad_actual);
    modelo.put("id_actividad", sId_actividad_actual);
    modelo.put("id_form", sId_form);
    modelo.put("proveido", sProveido);
    modelo.put("id_tramite", sId_tramite);
    modelo.put("aplicacion", sAplicacion);
    modelo.put("id_usuario_a" , sId_usuario_a);
    modelo.put("usuarios2", sUsuarios2);
    modelo.put("codigos", sCodigos);
    
    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite = this.mi.getBuscarTramite(datosTramite);
    modelo.put("datosTramite", datosTramite);

    List lFormulario = this.mi.getListarFormulario(datosTramite);
    for (int i = 0; i < lFormulario.size(); i++) {
      iCodigo = 0;
      datosTramite = (Tramites) lFormulario.get(i);
      String sValorx = datosTramite.getValor();
      if ("R".equals(datosTramite.getId_tipo_permiso())) {
        //Para sacar los valores de los checks
        if (sValorx.indexOf("###") > 0) {
          try {
            String sDatoz[] = sValorx.split("###");
            String sValorcito = "";
            for (int c=0; c < sDatoz.length; c++) {
              sDatoy = (sDatoz[c]).split(":");
              if ("id_codigo".equals(sDatoy[0])) {
              	datosTupla = new Dominios();
	        datosTupla.setId_tupla(Integer.parseInt(sDatoy[1]));
	        datosTupla.setId_tipo_dominio(datosTramite.getId_tipo_dominio());
	        datosTupla.setId_campo(datosTramite.getId_campo());
  	        datosTupla = (Dominios) this.mi.getBuscarTupla2(datosTupla);
	        sValorcito = datosTupla.getTupla()+", "+sValorcito;
	      }
	    }
            datosTramite.setValor(sValorcito);
	  }
	  catch(Exception e) {
	  }
	}
      }
      //Para sacar los valores combos...
      try {
        sDatox = sValorx.split(":");
        if ("id_codigo".equals(sDatox[0])) {
          if (Integer.parseInt(sDatox[1]) == 0) {
	    datosTramite.setValor("");
	  }
	  else {
	    datosTupla = new Dominios();
	    datosTupla.setId_tupla(Integer.parseInt(sDatox[1]));
	    datosTupla.setId_tipo_dominio(datosTramite.getId_tipo_dominio());
	    datosTupla.setId_campo(datosTramite.getId_campo());
  	    datosTupla = (Dominios) this.mi.getBuscarTupla2(datosTupla);
	    datosTramite.setValor(datosTupla.getTupla());
            iCodigo = Integer.parseInt(sDatox[1]);
	  }
        }
      }
      catch(Exception e) {
        iCodigo=0;
      }
      if ("si".equals(sRecargado)) {
        sDato = request.getParameter("valor_"+Integer.toString(i+1)+"_"+iForm);
	datosTramite.setValor(sDato);
      }
      if ("si".equals(sRecargado)) {
	try {
          if ("C".equals(datosTramite.getId_tipo_permiso())) {
	    datosTramite.setSeleccionado(Integer.parseInt(request.getParameter("combo_"+datosTramite.getId_dominio()+"_"+iForm)));
	    //Buscamos los datos del dominio
	    datosDominio = new Dominios();
	    datosDominio.setId_dominio(datosTramite.getId_dominio());
            datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
	    datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
            if (datosDominio.getId_dominio_padre() > 0) {
 	      datosTramite.setId_tupla_padre(Integer.parseInt(request.getParameter("combo_"+datosDominio.getId_dominio_padre()+"_"+iForm)));
            }
	  }
	}
	catch(Exception e) {
	  datosTramite.setSeleccionado(0);
	}
  	datosTramite.setId_actividad(Integer.parseInt(sId_actividad_actual));
        if ("K".equals(datosTramite.getId_tipo_permiso())) {
  	  List lTuplas = this.mi.getListarCombos2(datosTramite);
          for (int l=0; l < lTuplas.size(); l++) {
	    datosTupla = (Dominios) lTuplas.get(l);
  	    //PARA RECUPERAR VALORES DE LOS CHECKBOXs
            try {
              sChequeados = request.getParameterValues("check"+Integer.toString(i+1)+"_"+iForm);
              for (int k=0; k < sChequeados.length; k++) {
                if (Integer.parseInt(sChequeados[k]) == datosTupla.getId_tupla()) {
                  datosTupla.setSeleccionado(Integer.parseInt(sChequeados[k]));
    	        }
              }
	    }
	    catch(Exception e) {}
	  }
	  datosTramite.setTuplas(lTuplas);
        }
	else {
          datosTramite.setTuplas(this.mi.getListarCombos2(datosTramite));
	}
	lFormulario.set(i, datosTramite);
      }
    }
    modelo.put("lFormulario", lFormulario);
    return new ModelAndView("administrarAdjuntos/AdjuntarArchivoCorrespondencia", modelo);
  }
}
