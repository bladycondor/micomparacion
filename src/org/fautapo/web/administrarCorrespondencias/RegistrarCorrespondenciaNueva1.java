package org.fautapo.web.administrarCorrespondencias;

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
import org.fautapo.domain.Proveidos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-01
*/

public class RegistrarCorrespondenciaNueva1 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iNro_registros; int iResultado; int iId_tramite;
    String sNro_registros =request.getParameter("nu_registros");

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");
    String sId_form = request.getParameter("id_form");
    String sProveido = request.getParameter("proveido");
    String iId_usuario = request.getParameter("id_usuario");
    String sAplicacion = request.getParameter("aplicacion");
    modelo.put("aplicacion", sAplicacion);
    
    String sArchivar_concluir = request.getParameter("archivar_concluir");
    System.out.println("El valor de Archivar Concluir es NUEVITO -->"+ sArchivar_concluir);
    
    if (("0".equals(iId_usuario)) || (iId_usuario == null)) {
       String mensaje = "Elija un destinatario";
       modelo.put("mensaje", mensaje);
       return new ModelAndView("Error",modelo);
     }

    try {
      iNro_registros = Integer.parseInt(sNro_registros);
    }
    catch (Exception e) {
      iNro_registros = 0;
    }

    Tramites tramite = new Tramites();
    tramite.setId_proceso(Integer.parseInt(sId_proceso));
    tramite.setId_tipo_proveido(1); //Formulario
    tramite.setPara(Integer.parseInt(iId_usuario));
    tramite.setDe(cliente.getId_usuario());
    iId_tramite = this.mi.setInsertarTramite(tramite); //CREA UN TRAMITE
    if (iId_tramite == 0) {
      return new ModelAndView("Error","mensaje","El trámite no se creó");
    }
    
    //Buscamos la actividad minima del proceso
    int iId_actividad = this.mi.getBuscarActividadMinima(tramite);
    
    Tramites datosFrLog = new Tramites();
    datosFrLog.setId_tramite(iId_tramite);
    datosFrLog.setId_proceso(Integer.parseInt(sId_proceso));
    datosFrLog.setId_form(Integer.parseInt(sId_form));
    datosFrLog.setId_actividad(iId_actividad);
    datosFrLog.setId_estado("R");
    datosFrLog.setUlt_usuario(cliente.getId_usuario());
    iResultado = this.mi.setInsertarFrLog(datosFrLog);
    // Fin del registro de la revision del formulario
    
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(iId_tramite);
    datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
    for (int i = 1; i < iNro_registros+1; i++) {
      datosTramite.setId_campo(Integer.parseInt(request.getParameter("id_campo_" + Integer.toString(i))));
      datosTramite.setId_form(Integer.parseInt(sId_form));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      datosTramite.setCampos("");
      String _valor_recuperado = request.getParameter("combo_"+request.getParameter("id_dominio_"+Integer.toString(i)));
      //si es un combo concatenamos con la cadena "id_codigo:" para saber que el valor es un identificador de una tupla
      if ("C".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
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
            String sChequeados[] = request.getParameterValues("check"+Integer.toString(i));
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
	  catch(Exception e) {}
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
      //Se graba solo si tiene permisos de escritura o es un combo
      if ("W".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i))) || "C".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))|| "D".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
	iResultado = this.mi.setRegistrarValor(datosTramite);
	if (iResultado == 0) {
	  return new ModelAndView("Error","mensaje","El dato no se inserto");
	}
      }
      if ("T".equals(request.getParameter("id_tipo_permiso_"+Integer.toString(i)))) {
        datosTramite.setValor(request.getParameter("primarios_"+Integer.toString(i))+"##~##"+request.getParameter("valor_"+Integer.toString(i)));
        datosTramite.setCampos(request.getParameter("campos_"+Integer.toString(i)));
	iResultado = this.mi.setRegistrarValor(datosTramite);
	if (iResultado == 0) {
	  return new ModelAndView("Error","mensaje","El dato no se inserto");
	}
      }
    }
    //Sacamos los datos del tramite
    datosTramite = new Tramites();
    datosTramite.setId_tramite(iId_tramite);
    datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);
    modelo.put("datosTramite", datosTramite);

    //Registramos el proveido
    Proveidos datosProveido = new Proveidos();
    datosProveido.setId_tramite(iId_tramite);
    datosProveido.setId_actividad(datosTramite.getId_actividad_actual());
    datosProveido.setProveido(sProveido);
    datosProveido.setId_actividad(iId_actividad);
    datosProveido.setId_tipo_proveido(1); //Para formularios
    datosProveido.setUlt_usuario(cliente.getId_usuario());
    iResultado = this.mi.setRegistrarProveido(datosProveido);

    modelo.put("id_tramite", Integer.toString(iId_tramite));
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tipo_proceso", sId_tipo_proceso);
    modelo.put("id_actividad", Integer.toString(iId_actividad));

    //Sacamos la lista de informes de esta actividad
    List lInformes = this.mi.getListarInformesActividad(datosTramite);
    modelo.put("lInformes", lInformes);
    int iCantInformes = lInformes.size();
    modelo.put("cantInformes", Integer.toString(iCantInformes));

    modelo.put("fechainicio", request.getParameter("fechainicio"));
    modelo.put("fechafin", request.getParameter("fechafin"));
    modelo.put("fechadellunes", request.getParameter("fechadellunes"));
    modelo.put("id_estado", request.getParameter("id_estado"));
    String sNombreInforme = Integer.toString(iId_tramite)+"_"+cliente.getId_usuario();
    modelo.put("nombre_informe", sNombreInforme);
    
    
    //Si decidio Archivar correspondencia = Concluir
    if("si".equals(sArchivar_concluir)) {
      System.out.println("Entro hasta aqui el registrar  y el valor de archivar NUEVO-->"+ sArchivar_concluir);
      Tramites concluir = new Tramites();
      concluir.setId_tramite(iId_tramite);
      System.out.println("El id_tramite que cambia de estado cONCLIDO NUEVO-->"+ Integer.toString(concluir.getId_tramite()));
      concluir.setId_estado("C");
      int iResultadoCon = this.mi.setCambiarEstadoTramite(concluir);
    }
    //Fin decidio Archivar correspondencia = Concluir
    
    return new ModelAndView("administrarCorrespondencias/RegistrarCorrespondenciaNueva1", modelo);
  }
}