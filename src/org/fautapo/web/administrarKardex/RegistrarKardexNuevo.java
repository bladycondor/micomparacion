package org.fautapo.web.administrarKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-01
*/

public class RegistrarKardexNuevo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { 
    this.mi = mi; 
  }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Dominios datosDominio = new Dominios();
    int iId_actividad_actual=0; int iCodigo=0;
    String sDato=""; List listita = new ArrayList(); 
    Tramites auxiliar; Tramites auxiliar1;
    String sDatox[]; String sDatoy[]; String sChequeados[]; 

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sAplicacion = request.getParameter("aplicacion");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_proceso = request.getParameter("id_proceso");
    String sProveido = request.getParameter("proveido");
    String sRecargado = request.getParameter("recargado");
    String sCombito = request.getParameter("combito");
    String iId_usuario = request.getParameter("id_usuario");

    modelo.put("id_proceso", sId_proceso);
    modelo.put("proveido", sProveido);
    modelo.put("id_usuario", iId_usuario);
    modelo.put("aplicacion", sAplicacion);
    
    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAccesoKardex();
    modelo.put("lProcesos", lProcesos);

    //Buscar tipo proceso
    if (sId_proceso != null) { 
      Tramites datosTipoProceso = new Tramites();
      datosTipoProceso.setId_proceso(Integer.parseInt(sId_proceso));
      Tramites datosTipoP = (Tramites) this.mi.getBuscarTipoProceso2(datosTipoProceso);
      modelo.put("buscarTipoProceso", datosTipoP);
      modelo.put("id_tipo_proceso", Integer.toString(datosTipoP.getId_tipo_proceso()));
    }

    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      //Sacamos la fecha actual
      Date dFecha_actual = new Date();
      String sDia = Integer.toString(dFecha_actual.getDate());
      String sMes = Integer.toString(dFecha_actual.getMonth() + 1);
      String sAnio = Integer.toString(dFecha_actual.getYear() + 1900);
      String sFecha = sAnio+"-"+sMes+"-"+sDia;
      
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
	  abm.setTabla(datosDominio.getTabla());
          abm = this.mi.getBuscarTabla1(abm);
          Abm abm1 = (Abm) this.mi.getListarCamposTabla2(abm);
	  datosTramite.setCampos(abm1.getCampo());
	  datosTramite.setEtiqueta(abm1.getEtiqueta());
    
          //Sacamos los campos que son primarios de la tabla
          abm = new Abm();
	  abm.setTabla(datosDominio.getTabla());
	  datosTramite.setPrimarios(this.mi.getListarDatosPrimarios(abm));
   	  
	  //Sacamos datos del campo foraneo
          abm = new Abm();
	  abm.setTabla(datosDominio.getTabla());
	  abm.setCampo(datosDominio.getCampo());
          abm = this.mi.getBuscarForanea(abm);
          if (abm != null) {
	    datosTramite.setTabla_foranea(abm.getTabla_foranea());
	    datosTramite.setCampo_foraneo(abm.getCampo_foraneo());
	    datosTramite.setId_campo_foraneo(abm.getId_campo_foraneo());
          }
        }
        else {
          datosTramite.setValor("");
        }
        //Para el caso de un acl de tipo fecha
        if ("D".equals(datosTramite.getId_tipo_permiso())) {
          datosTramite.setValor(sFecha);
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
	        auxiliar1 = (Tramites) lTuplas.get(l);
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
	        auxiliar1 = (Tramites) lTuplas.get(l);
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
    Usuarios datosUsuario = new Usuarios();
    datosUsuario.setId_usuario(cliente.getId_usuario());    
    List lUsuarios = this.mi.getListarUsuariosUbicacionOrganica(datosUsuario);
    modelo.put("listaMultiplesUsuarios", lUsuarios);
    modelo.put("banderakardex", "0");
    return new ModelAndView("administrarKardex/RegistrarKardexNuevo", modelo);
  }
}