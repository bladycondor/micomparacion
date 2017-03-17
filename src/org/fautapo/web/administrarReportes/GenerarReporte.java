package org.fautapo.web.administrarReportes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-29
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public class GenerarReporte implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    List lCampos1 = new ArrayList();
    String sCampos=""; String sCampos_a=""; String sCampos_m = ""; String sCadena_a = ""; String sCadena_ag = ""; String sCadena_gb = ""; String sCampos_n="";
    String sExiste_sumita="0"; String sCadena_1; String sDatoz; String sAux_1 = "0"; String sCampos_contar = ""; String sColumnas = "";
    Campos datosCampo;
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sChequeo = request.getParameter("chequeo");
    String sChequeo_o = request.getParameter("chequeo_o");
    String sId_campo_contar = request.getParameter("id_campo_contar");
    String sListar = request.getParameter("listar");

    String sCadena="SELECT t.id_tramite, id_campo, trim(valor) as valor FROM tr_datos td JOIN tr_tramites t USING(id_tramite) WHERE t.id_estado<>\'X\' AND t.id_proceso="+sId_proceso+" AND (";
    String sCadena_sum="SELECT '-' ";
    if (sChequeo != null) {
      String sCodigo[] = request.getParameterValues("chequeo");
      for (int i=0; i < sCodigo.length; i++) {
        datosCampo = new Campos();
	datosCampo.setId_campo(Integer.parseInt(sCodigo[i]));
        datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
	 
        //Para los datos que son numericos
        if ("9".equals(datosCampo.getId_tipo_validacion())) {
	  sCadena_sum = sCadena_sum+" , sum(((campo"+sCodigo[i]+")::text)::integer)";
	  sExiste_sumita = "1";
	}
	else{
	  sCadena_sum = sCadena_sum+" , '-'";
	}
	//Fin datos numericos
	  
        if (i == 0) {
   	  sCampos = "coalesce(id_tramite, ' ') ||'-'|| coalesce("+"campo"+sCodigo[i]+", ' ')";
          sCampos_m = "id_tramite varchar, "+"campo"+sCodigo[i]+" varchar";	    	    
	}
        else {
          sCampos = sCampos+"||'-'|| coalesce("+"campo"+sCodigo[i]+", ' ')";
          sCampos_m = sCampos_m+","+"campo"+sCodigo[i]+" varchar"; 
	}
	if (i == (sCodigo.length-1)) {
          sCadena = sCadena+"id_campo="+sCodigo[i];
	}
	else {
	  sCadena = sCadena+"id_campo="+sCodigo[i]+" OR ";
	}
      }
      sCadena = sCadena+") ORDER BY t.id_tramite, id_campo";
      sCadena_sum = sCadena_sum+" FROM _"+cliente.getId_usuario();
    }
    
    Campos campos = new Campos();
    campos.setCadena(sCadena);
    campos.setCampos(sCampos_m);
    campos.setCampos_suma(sCadena_sum);

    Campos campos2 = new Campos();
    campos2.setCadena(sCadena);
    campos2.setCampos(sCampos_m);

    //Recuperamos la lista de los campos por los que tenemos que ordenar      
    sCadena_1 =" SELECT ("+sCampos+")::varchar as valor FROM _"+cliente.getId_usuario()+" ORDER BY ";
    String sCamposOrdenar[] = request.getParameterValues("id_campo_o");
    for (int i=0; i < sCamposOrdenar.length; i++) {
      if (i == 0) {
        sCadena_1 = sCadena_1+"campo"+sCamposOrdenar[i];
      }
      else {
        sCadena_1 = sCadena_1+ ", campo"+sCamposOrdenar[i];
      }
    }
    //Fin order by
    
    //Recuperamos la lista de los campos por los que tenemos que agrupar
    String sAgrupar[] = request.getParameterValues("chequeo_o");
    if (sAgrupar != null) {
      for (int l=0; l < sAgrupar.length; l++) {
        if (l == 0) {
   	  sCampos_a = "coalesce("+"campo"+sAgrupar[l]+", ' ')";
	}
        else {
          sCampos_a = sCampos_a+"||'-'|| coalesce("+"campo"+sAgrupar[l]+", ' ')";
	}
        if (l == ((sAgrupar.length) -1)) {
          sCadena_gb = sCadena_gb+"campo"+sAgrupar[l];
	  sCampos_contar = sCampos_contar+sAgrupar[l];
          sColumnas = sColumnas+sAgrupar[l]+":";
        }
        else {
          sCadena_gb = sCadena_gb+ "campo"+sAgrupar[l]+", ";
	  sCampos_contar = sCampos_contar+sAgrupar[l]+":";
          sColumnas = sColumnas+sAgrupar[l]+":";
        }
      }
      sCadena_ag =" SELECT ("+sCampos_a+" ||'-'|| count(*))::varchar as valor FROM __"+cliente.getId_usuario()+" GROUP BY "+sCadena_gb+" ORDER BY "+sCadena_gb;
    }
    System.out.println("cadena_groupby----"+sCadena_gb);
    campos2.setCadena_1(sCadena_ag);
    campos2.setTablita("__"+cliente.getId_usuario());
    campos2.setValor(sColumnas); //Para los nombres de los campos
    //Fin group by

    campos.setCadena_1(sCadena_1);
    campos.setTablita("_"+cliente.getId_usuario());

    //Para la lista del order by
    if ("Si".equals(sListar)) {
    System.out.println("listar------"+sListar);
      List lDatos = new ArrayList();
//      try {
        List lCamposOrden = this.mi.getListarCamposReporte(campos);
	System.out.println("tamanio de order by--------"+lCamposOrden.size());
        modelo.put("cantidad_order_by", Integer.toString(lCamposOrden.size()));
        List lTemp = new ArrayList();
        Campos datosTemp = new Campos();
        for (int j=0; j < lCamposOrden.size(); j++) {
          List listita = new ArrayList();
          Campos auxiliar = new Campos();
          Campos aux = (Campos) lCamposOrden.get(j);
          String sCad[] = (aux.getValor()).split("#~~#");
          for (int k=0; k < sCad.length; k++) {
            Tramites datosValores = new Tramites();
 	    datosValores.setValor(sCad[k]);
            listita.add(datosValores);
          }
          auxiliar.setLista(listita);
          lDatos.add(auxiliar);
        }
        if ("1".equals(sAux_1)) {
          datosTemp.setLista(lTemp);
          lDatos.add(datosTemp);
        }
/*      }
      catch(Exception e) {}*/
      modelo.put("lDatos", lDatos);
    }
    
    //Para la lista del group by
    List lDatos2 = new ArrayList();
    try {
      List lCamposAgrupados = this.mi.getListarCamposReporte2(campos2);
      System.out.println("tamanio de group by--------"+lCamposAgrupados.size());
      modelo.put("cantidad_group_by", Integer.toString(lCamposAgrupados.size()));
      for (int j=0; j < lCamposAgrupados.size(); j++) {
        List listita = new ArrayList();
        Campos auxiliar = new Campos();
        Campos aux = (Campos) lCamposAgrupados.get(j);
        String sCad[] = (aux.getValor()).split("#~~#");
        for (int k=0; k < sCad.length; k++) {
          Tramites datosValores = new Tramites();
 	  datosValores.setValor(sCad[k]);
          listita.add(datosValores);
        }
        auxiliar.setLista(listita);
        lDatos2.add(auxiliar);
      }
      modelo.put("lCamposGB", lCampos1);
    }
    catch(Exception e){}
    modelo.put("lDatosGB", lDatos2);
    modelo.put("existe_sumita", sExiste_sumita);

    //Para los conteos
    try {
      if (!"".equals(sId_campo_contar) && (sId_campo_contar != null)) {
        datosCampo = new Campos();
        datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
        datosCampo.setId_campo(Integer.parseInt(sId_campo_contar));
        datosCampo.setCampos(sCampos_contar);
        String sValores = this.mi.getListarTotalesDatos(datosCampo);
        String sCad[] = sValores.split("####");
        List listita = new ArrayList();
        for (int k=0; k < sCad.length; k++) {
          Tramites auxiliar = new Tramites();
  	  String sValor[] = (sCad[k]).split("#~~#");
          List listita2 = new ArrayList();
	  for (int w=0; w < sValor.length; w++) {
  	    Tramites auxiliar2 = new Tramites();
	    auxiliar2.setValor(sValor[w]);
	    listita2.add(auxiliar2);
	  }
          auxiliar.setLista(listita2);
          listita.add(auxiliar);
        }
        modelo.put("lCamposContados", listita);
        datosCampo = (Campos) this.mi.getBuscarCampoForm(datosCampo);
        modelo.put("datosCampo", datosCampo);
      }
    }
    catch (Exception e) {
    }
    modelo.put("id_campo_contar", sId_campo_contar);
    modelo.put("id_proceso", sId_proceso);
    
    return new ModelAndView("administrarReportes/ListarReporte", modelo);   
  }    
}    

