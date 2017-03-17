package org.fautapo.web.administrarTramites;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Proveidos;
import org.fautapo.domain.Adjuntos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.fileupload.*;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-25
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-25
*/

public class RegistrarTramite implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; } 

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iNro_registros; int iCodigo=0; int iSw=0; int iId_dominio=0; int iContador=0; int iResultado;
    String sDato="";  String sChequeados[]; String sDatox[]; String sDatoy[];
    List listita = new ArrayList();  
    Tramites datosTramite; Dominios datosTupla; Dominios datosDominio; Proveidos datosProveido;
       
    //Sacamos los datos de la session
    Clientes cliente   = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sAccion = request.getParameter("accion");
    String sForm = request.getParameter("form");
    String sId_actividad_actual = request.getParameter("id_actividad_actual");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_proceso = request.getParameter("id_proceso");
    String sProceso = request.getParameter("proceso");
    String sId_form = request.getParameter("id_form");
    String sProveido = request.getParameter("proveido");
    String sRecargado = request.getParameter("recargado");
    String sNro_registros=request.getParameter("nu_registros");
    String sNro_pagina = request.getParameter("nro_pagina");
    modelo.put("nro_pagina", sNro_pagina);

    //RECUPERANDO EL ESTADO Y FECHAS   
    String sFecha_ini = request.getParameter("fechainicio");  //AQUI 4
    String sFecha_fin = request.getParameter("fechafin");
    String sFechadellunes = request.getParameter("fechadellunes");
    String sId_estado = request.getParameter("id_estado");
    //FIN RECUPERA

    modelo.put("aplicacion", sAplicacion);

    try {
      iNro_registros = Integer.parseInt(sNro_registros);
    }
    catch(Exception h) {
      iNro_registros = 0;
    }

    datosTramite = new Tramites();
    datosTramite.setPara(cliente.getId_usuario());
    
    if ("Formulario".equals(sAccion)) {
      //insertamos los datos en la tabla tr_fr_log para verficar que el usuario reviso el formulario
      Tramites datosFrLog = new Tramites();
      datosFrLog.setId_tramite(Integer.parseInt(sId_tramite)); 
      datosFrLog.setId_proceso(Integer.parseInt(sId_proceso));
      datosFrLog.setId_form(Integer.parseInt(sId_form));
      datosFrLog.setId_actividad(Integer.parseInt(sId_actividad_actual));
      datosFrLog.setId_estado("R");
      datosFrLog.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setInsertarFrLog(datosFrLog);
      
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite = this.mi.getBuscarTramite(datosTramite);
      modelo.put("datosTramite", datosTramite);

      List lFormulario = this.mi.getListarFormulario(datosTramite);
      for (int i = 0; i < lFormulario.size(); i++) {
        iCodigo = 0;
        datosTramite = (Tramites) lFormulario.get(i);
	//Para los valores que son de otras tablas
        if ("T".equals(datosTramite.getId_tipo_permiso()) && (datosTramite.getId_tipo_dominio() == 3)) {
          Abm abm = new Abm();
          //Buscamos los datos del dominio
	  datosDominio = new Dominios();
	  datosDominio.setId_dominio(datosTramite.getId_dominio());
          datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);

  	  //Listamos los datos de la tabla
   	  abm.setTabla(datosDominio.getTabla());
          datosTramite.setValores(this.mi.getListarDatosTabla(abm));
          datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());

	  //Sacamos los datos de la tabla
	  abm.setTabla(datosDominio.getTabla());
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

	  try {
  	    String sValor1[] = (datosTramite.getValor()).split("##~##");
	    if (sValor1.length > 1) {
  	      datosTramite.setValor(sValor1[1]);
	    }
	  }
	  catch(Exception e) {}
        }
	String sValor = datosTramite.getValor();
        if ("R".equals(datosTramite.getId_tipo_permiso())) {
	  //Para sacar los valores de los checks
	  if (sValor.indexOf("###") > 0) {
	    try {
      	      String sDatoz[] = sValor.split("###");
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
                  datosTramite.setSeleccionado(Integer.parseInt(sDatoy[1]));
	        }
	      }
              datosTramite.setValor(sValorcito);
	    }
	    catch(Exception e) {}
	  }
        }
        //Para sacar los valores combos...
        try {
	  sDatox = sValor.split(":");
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
	      if (this.mi.getBuscarTieneHijos(datosTramite) == 1) {
	        Tramites temp = new Tramites();
		temp.setId_tramite(Integer.parseInt(sId_tramite));
		temp.setId_tupla(iCodigo);
		temp.setId_dominio(datosTramite.getId_dominio());
		iResultado = this.mi.setRegistrarTempTupla(temp);
	      }
	      datosTramite.setSeleccionado(Integer.parseInt(sDatox[1]));
	    }
	  }
	}
	catch(Exception e) {
	  iCodigo=0;
	}
	//Para el caso de campos en lectura pero que sean de tabla
	try {
  	  String sValor1[] = (datosTramite.getValor()).split("##~##");
	  if (sValor1.length > 1) {
  	    datosTramite.setValor(sValor1[1]);
	  }
	}
	catch(Exception e) {}
        if ("si".equals(sRecargado)) {
          sDato = request.getParameter("valor_"+Integer.toString(i+1));
	  datosTramite.setValor(sDato);
	}
        if (("C".equals(datosTramite.getId_tipo_permiso())) || ("K".equals(datosTramite.getId_tipo_permiso()))) {
	  //Verificamos si tienes hijos
	  datosTramite.setId_dato(this.mi.getBuscarTieneHijos(datosTramite));
	  datosTramite.setId_tupla_padre(0);
          //Para la primera vez que se lista el formulario
          if (!"si".equals(sRecargado)) {
	    if ("K".equals(datosTramite.getId_tipo_permiso())) {
  	      List lTuplas = this.mi.getListarCombos2(datosTramite);
	      listita = new ArrayList();
              for (int l=0; l < lTuplas.size(); l++) {
	        Dominios auxiliar1 = (Dominios) lTuplas.get(l);
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
	      datosTramite.setId_tupla_padre(this.mi.getBuscarTuplaPadre(datosTramite));
	      //Buscamos los datos del dominio
	      datosDominio = new Dominios();
	      datosDominio.setId_dominio(datosTramite.getId_dominio());
              datosDominio = (Dominios) this.mi.getBuscarDominio(datosDominio);
	      datosTramite.setId_dominio_padre(datosDominio.getId_dominio_padre());
              if ((datosDominio.getId_dominio_padre() > 0) && (iCodigo > 0)) {
  	        datosTupla = new Dominios();
	        datosTupla.setId_tupla(iCodigo);
	        datosTupla.setId_tipo_dominio(datosTramite.getId_tipo_dominio());
	        datosTupla.setId_campo(datosTramite.getId_campo());
  	        datosTupla = (Dominios) this.mi.getBuscarTupla2(datosTupla);
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
  	    datosTramite.setId_actividad(Integer.parseInt(sId_actividad_actual));
            if ("K".equals(datosTramite.getId_tipo_permiso())) {
  	      List lTuplas = this.mi.getListarCombos2(datosTramite);
              for (int l=0; l < lTuplas.size(); l++) {
	        datosTupla = (Dominios) lTuplas.get(l);
  	        //PARA RECUPERAR VALORES DE LOS CHECKBOXs
                try {
                  sChequeados = request.getParameterValues("check"+Integer.toString(i+1));
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
	      datosTramite.setResultado((this.mi.getListarCombos2(datosTramite)).size());
	    }
	    lFormulario.set(i, datosTramite);
          }
	}
      }
      modelo.put("lformulario", lFormulario);

      // Listar Proveidos Historicos
      datosProveido = new Proveidos();
      datosProveido.setId_tramite(Integer.parseInt(sId_tramite));
      List lProveidos = this.mi.getListarProveidosHistoricos(datosProveido);
      modelo.put("lProveidos", lProveidos);
      
      // Proveido
      modelo.put("proveido", sProveido);
    }

    if ("Grabar".equals(sAccion)) {
      datosTramite = new Tramites();
      for (int i = 1; i < iNro_registros+1; i++) {
        if ("true".equals(request.getParameter("habilitado_"+Integer.toString(i)))) {
          datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
          datosTramite.setId_campo(Integer.parseInt(request.getParameter("id_campo_"+Integer.toString(i))));
          datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
          datosTramite.setId_form(Integer.parseInt(sId_form));
          datosTramite.setValor(request.getParameter("valor_"+Integer.toString(i)));
          datosTramite.setUlt_usuario(cliente.getId_usuario());
	  datosTramite.setCampos("");
	  String _valor_recuperado = request.getParameter("combo_"+request.getParameter("id_dominio_"+Integer.toString(i)));
	  //INICIO - Sacamos los datos del campo
	  Campos campoAuxiliar = new Campos();
	  campoAuxiliar.setId_campo(datosTramite.getId_campo());
	  campoAuxiliar = this.mi.getBuscarCampoForm(campoAuxiliar);
	  //FIN - Sacamos los datos del campo

          //si es un combo concatenamos con la cadena "id_codigo:" para saber que el valor es un identificador de una tupla
          if ("C".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
            //Verificamos si el campo es obligatorio
	    if ((campoAuxiliar.getObligatorio()) && ("0".equals(_valor_recuperado))) {
  	      return new ModelAndView("administrarTramites/RegistrarTramiteError","mensaje","Ingrese todos los campos obligatorios");
	    }
            //Fin - Verificamos si el campo es obligatorio
            if ((_valor_recuperado != null) && (!"".equals(_valor_recuperado))) {
              datosTramite.setValor("id_codigo:"+_valor_recuperado);
	    }
	    else {
              datosTramite.setValor("id_codigo:0");
	    }
          }
          else {
            //si es un check concatenamos con la cadena "id_codigo:" para saber que el valor es un identificador de una tupla
            if ("K".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
 	      try {
	        String sCadena="";
                sChequeados = request.getParameterValues("check"+Integer.toString(i));
                //Verificamos si el campo es obligatorio
                if ((campoAuxiliar.getObligatorio()) && (sChequeados == null)) {
  	          return new ModelAndView("administrarTramites/RegistrarTramiteError","mensaje","Ingrese todos los campos obligatorios");
	        }
		//FIN - Verificamos si el campo es obligatorio
	        for (int k=0; k < sChequeados.length; k++) {
                  if (sChequeados[k] != null ) {
                    sCadena = sCadena+"id_codigo:"+sChequeados[k]+"###";
    	          }
                }
                datosTramite.setValor(sCadena);
	        iResultado = this.mi.setRegistrarValor(datosTramite);
	        if (iResultado == 0) {
	          return new ModelAndView("Error","mensaje","El dato no se inserto");
	        }
	      }
	      catch(Exception e) {
	        datosTramite.setValor("");
	        iResultado = this.mi.setRegistrarValor(datosTramite);
	        if (iResultado == 0) {
	          return new ModelAndView("Error","mensaje","El dato no se inserto");
	        }
	      }
	      if ((_valor_recuperado != null) && (!"".equals(_valor_recuperado))) {
                datosTramite.setValor("id_codigo:"+_valor_recuperado);
	      }
	      else {
	        datosTramite.setValor("id_codigo:0");
	      }
            }
	    else {
              datosTramite.setValor(request.getParameter("valor_"+Integer.toString(i)));
	    }
          }
	  
	  //Verificamos si el campo es obligatorio
          if ((campoAuxiliar.getObligatorio()) && ("".equals(datosTramite.getValor()))) {
            return new ModelAndView("administrarTramites/RegistrarTramiteError","mensaje","Ingrese todos los campos obligatorios");
          }
	  //FIN - Verificamos si el campo es obligatorio

          if ("D".equals(request.getParameter("id_tipo_permiso_"+i))) {
            /*INICIO - VALIDACION FECHA*/
            try {
	      String sAuxFecha = datosTramite.getValor();
	      String formato = "dd/MM/yyyy"; 
	      String[] bits = sAuxFecha.split("/");     
              SimpleDateFormat sdf = new SimpleDateFormat(formato);
     
              Calendar cal = Calendar.getInstance();
              cal.set(Integer.parseInt(bits[2]),Integer.parseInt(bits[1])-1,Integer.parseInt(bits[0]));
     
              if (!sdf.format(cal.getTime()).equals(sAuxFecha)) { 
  	        return new ModelAndView("administrarTramites/RegistrarTramiteError", "mensaje", "Fecha no vÃ¡lida");
	      }
            }
            catch(Exception e) {
  	      return new ModelAndView("administrarTramites/RegistrarTramiteError", "mensaje", "El formato de la fecha es INCORRECTO");
            }   
	    /*FIN - VALIDACION*/
          }

          //Se graba solo si tiene permisos de escritura o es un combo
          if ("W".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i))) || "C".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))|| "D".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
            iResultado = this.mi.setRegistrarValor(datosTramite);
  	    if (iResultado == 0) {
	      return new ModelAndView("Error","mensaje","El dato no se inserto");
	    }
          }
	  //Cuando son datos de otras tablas
	  if ("T".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
            if (("".equals(request.getParameter("valor_"+Integer.toString(i)))) || (request.getParameter("valor_"+Integer.toString(i)) == null)) {
              datosTramite.setValor("");
              datosTramite.setCampos("");
            }
            else {
              datosTramite.setValor(request.getParameter("primarios_"+Integer.toString(i))+"##~##"+request.getParameter("valor_"+Integer.toString(i)));
              datosTramite.setCampos(request.getParameter("campos_"+Integer.toString(i)));
  	    }
            iResultado = this.mi.setRegistrarValor(datosTramite);
	    if (iResultado == 0) {
	      return new ModelAndView("Error","mensaje","El dato no se inserto");
	    }
          }
        }
      }
      // Proveido
      datosProveido = new Proveidos();
      datosProveido.setId_tramite(Integer.parseInt(sId_tramite));
      datosProveido.setId_actividad(Integer.parseInt(sId_actividad_actual));
      datosProveido.setProveido(sProveido);
      datosProveido.setId_tipo_proveido(1); //Proveido de un formulario
      datosProveido.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarProveido(datosProveido);
      if (iResultado == 0) {
	return new ModelAndView("Error","mensaje","El proveido no se registró");
      }
      modelo.put("accion", "Aviso");

      //Sacamos la lista de informes de esta actividad
      List lInformes = this.mi.getListarInformesActividad(datosTramite);
      modelo.put("lInformes", lInformes);
      int iCantInformes = lInformes.size();
      modelo.put("cantInformes", Integer.toString(iCantInformes));
      modelo.put("id_proceso", sId_proceso);
      modelo.put("id_actividad", sId_actividad_actual);
      modelo.put("id_tramite", sId_tramite);
      
      modelo.put("fechainicio", sFecha_ini);  //AQUI 4
      modelo.put("fechafin", sFecha_fin);
      modelo.put("fechadellunes", sFechadellunes);
      modelo.put("id_estado", sId_estado);
      String sNombreInforme = sId_tramite+"_"+cliente.getId_usuario();
      modelo.put("nombre_informe", sNombreInforme);
      return new ModelAndView("administrarTramites/RegistrarTramiteNuevo1", modelo);
    }

    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("proceso", sProceso);
    modelo.put("id_actividad", sId_actividad_actual);
    modelo.put("id_form", sId_form);
    modelo.put("accion", sAccion);
    
    modelo.put("fechainicio", sFecha_ini);  //AQUI 4
    modelo.put("fechafin", sFecha_fin);
    modelo.put("fechadellunes", sFechadellunes);
    modelo.put("id_estado", sId_estado);
    
    iResultado = this.mi.setLimpiarTempTuplas();
    
    //Sacamos el formato de la fecha definida en parametros
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    //FIN - Sacamos el formato de la fecha definida en parametros

    //Sacamos el formato de la hora definida en parametros
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));
    //FIN - Sacamos el formato de la hora definida en parametros

    return new ModelAndView("administrarTramites/RegistrarTramite", modelo);
  }
  
}