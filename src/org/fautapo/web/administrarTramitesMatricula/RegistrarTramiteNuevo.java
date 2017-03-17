package org.fautapo.web.administrarTramitesMatricula;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-22
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class RegistrarTramiteNuevo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Dominios datosDominio = new Dominios();
    int iId_actividad_actual=0; int iCodigo=0;
    String sDato=""; List listita = new ArrayList();
    Tramites auxiliar; Dominios auxiliar1;
    String sDatox[]; String sDatoy[]; String sChequeados[];

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sId_tramite = request.getParameter("id_tramite");
    String sId_proceso = request.getParameter("id_proceso");
    String sProveido = request.getParameter("proveido");
    String sRecargado = request.getParameter("recargado");
    String sCombito = request.getParameter("combito");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("proveido", sProveido);
    
    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAccesoTramites2(cliente);
    modelo.put("lProcesos", lProcesos);

    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      Tramites datosTramite = new Tramites();
      datosTramite.setId_proceso(Integer.parseInt(sId_proceso));

      //Listamos los datos del formulario
      List lFormulario = this.mi.getListarFormularioNuevo(datosTramite);
      for (int i = 0; i < lFormulario.size(); i++) {
        datosTramite = (Tramites) lFormulario.get(i);
        //Para el caso de que un dominio referencie a una tabla
        if ("T".equals(datosTramite.getId_tipo_permiso()) && (datosTramite.getId_tipo_dominio() == 3)) {
          Abm abm = new Abm();
          //Buscamos los datos del dominio
	  datosDominio.setId_dominio(datosTramite.getId_dominio());
          datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);

  	  //Listamos los datos de la tabla
   	  abm.setTabla(datosDominio.getTabla());
          datosTramite.setValores(this.mi.getListarDatosTabla(abm));
          datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());

	  //Sacamos los datos de la tabla
          abm = this.mi.getBuscarTabla1(abm);
          Abm abm1 = (Abm) this.mi.getListarCamposTabla2(abm);
	  datosTramite.setCampos(abm1.getCampo());
	  datosTramite.setEtiqueta(abm1.getEtiqueta());
	  
	  //Sacamos los datos de los campos que deseamos que se listen
          datosTramite.setPrimarios(this.mi.getListarDatosPrimarios(abm));
	  datosTramite.setCadena(datosDominio.getCampo());

   	  //Sacamos el campo padre
	  if (datosTramite.getId_dominio_padre() != 0) {
	    Dominios datosDominioPadre = new Dominios();
	    datosDominioPadre.setId_dominio(datosTramite.getId_dominio_padre());
            datosDominioPadre = (Dominios) this.mi.getBuscarDominio(datosDominioPadre);
	    datosTramite.setId_campo_foraneo(datosDominioPadre.getPrimario());
          }
        }
        else {
          datosTramite.setValor("");
        }
        if ("si".equals(sRecargado)) {
          sDato = request.getParameter("valor_"+Integer.toString(i+1));
          datosTramite.setValor(sDato);
        }
        if (("C".equals(datosTramite.getId_tipo_permiso())) || ("K".equals(datosTramite.getId_tipo_permiso()))) {
	  //Verificamos si tienes hijos
	  datosTramite.setId_dato(this.mi.getBuscarTieneHijos(datosTramite));
	  datosTramite.setId_tupla_padre(0);
	  datosTramite.setSeleccionado(0);
          //Para la primera vez que se lista el formulario
          if (!"si".equals(sRecargado)) {
	    if ("K".equals(datosTramite.getId_tipo_permiso())) {
  	      List lTuplas = this.mi.getListarCombos2(datosTramite);
	      listita = new ArrayList();
              for (int l=0; l < lTuplas.size(); l++) {
	        auxiliar1 = (Dominios) lTuplas.get(l);
  	        //PARA RECUPERAR VALORES DE LOS CHECKBOXs
                try {
                  String sCadenay[] = (datosTramite.getValor()).split("###");
                  for (int c=0; c < sCadenay.length; c++) {
                    sDatoy = (sCadenay[c]).split(":");
                    if (("id_codigo".equals(sDatoy[0])) && (Integer.parseInt(sDatoy[1]) == auxiliar1.getId_tupla())) {
                      auxiliar1.setSeleccionado(Integer.parseInt(sDatoy[1]));
	              break;
	            }
	            else {
	              auxiliar1.setSeleccionado(0);
	 	    }
  	          }
	        }
	        catch(Exception e) {}
	        listita.add(auxiliar1);
	      }
              datosTramite.setTuplas(listita);
            }
	    else {
	      datosTramite.setSeleccionado(iCodigo);
              //Buscamos los datos del dominio
	      datosDominio = new Dominios();
	      datosDominio.setId_dominio(datosTramite.getId_dominio());
              datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
              datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
	      if ((datosDominio.getId_dominio_padre() > 0) && (iCodigo > 0)) {
		Dominios datosTupla = new Dominios();
		datosTupla.setId_tupla(iCodigo);
	        datosTupla = (Dominios) this.mi.getBuscarTupla(datosTupla);
                datosTramite.setId_tupla_padre(datosTupla.getId_tupla_padre());
              }
              datosTramite.setTuplas(this.mi.getListarCombos2(datosTramite));
              datosTramite.setResultado((this.mi.getListarCombos2(datosTramite)).size());
	    }
	  }
  	  //Cuando se recarga el formulario por el evento onchage de los combos con hijos
	  if ("si".equals(sRecargado)) {
	    try {
              if ("C".equals(datosTramite.getId_tipo_permiso())) {
	        datosTramite.setSeleccionado(Integer.parseInt(request.getParameter("combo_"+datosTramite.getId_dominio())));
	        //Buscamos los datos del dominio
		datosDominio = new Dominios();
	        datosDominio.setId_dominio(datosTramite.getId_dominio());
                datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
                datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
                if (datosDominio.getId_dominio_padre() > 0) {
  	          if ((request.getParameter("combo_"+datosDominio.getId_dominio_padre())) == null) {
	            datosTramite.setId_tupla_padre(this.mi.getBuscarTuplaPadre(datosTramite));
		  }
		  else {
 	            datosTramite.setId_tupla_padre(Integer.parseInt(request.getParameter("combo_"+datosDominio.getId_dominio_padre())));
		  }
	        }
	      }
	    }
	    catch(Exception e) {
	      datosTramite.setSeleccionado(0);
	    }
  	    datosTramite.setId_actividad(iId_actividad_actual);
            if ("K".equals(datosTramite.getId_tipo_permiso())) {
  	      List lTuplas = this.mi.getListarCombos2(datosTramite);
              for (int l=0; l < lTuplas.size(); l++) {
	        auxiliar1 = (Dominios) lTuplas.get(l);
  	        //PARA RECUPERAR VALORES DE LOS CHECKBOXs
                try {
                  sChequeados = request.getParameterValues("check"+Integer.toString(i+1));
                  for (int k=0; k < sChequeados.length; k++) {
                    if (Integer.parseInt(sChequeados[k]) == auxiliar1.getId_tupla()) {
                      auxiliar1.setSeleccionado(Integer.parseInt(sChequeados[k]));
    	            }
                  }
	        }
	        catch(Exception e) {}
	      }
	      datosTramite.setTuplas(lTuplas);
            }
	    else {
              datosTramite.setTuplas(this.mi.getListarCombos2(datosTramite));
              datosTramite.setResultado((this.mi.getListarCombos2(datosTramite)).size());
	    }
	    lFormulario.set(i, datosTramite);
          }
        }
      }
      modelo.put("lformulario", lFormulario);
    }
    //Sacamos el formato de la fecha definida en parametros
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    //FIN - Sacamos el formato de la fecha definida en parametros

    return new ModelAndView("administrarTramitesMatricula/RegistrarTramiteNuevo", modelo);
  }
}