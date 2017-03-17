package org.fautapo.web.administrarPostulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarPstPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    

    int iId_postulante_resultado = 0;    
    String sId_persona = request.getParameter("id_persona");
    String sId_postulante = request.getParameter("id_postulante");
    modelo.put("id_postulante", sId_postulante);
    
    //Nuevo request
    String sPaterno = (request.getParameter("paterno")).trim();
    String sMaterno = (request.getParameter("materno")).trim();
    String sNombres = (request.getParameter("nombres")).trim();
    String sFec_nacimiento = request.getParameter("fec_nacimiento");
    String sDireccion = (request.getParameter("direccion")).trim();
    String sTelefono = (request.getParameter("telefono")).trim();
    String sDip = (request.getParameter("dip")).trim();
    String sId_tipo_sexo = cliente.getString(request, "id_tipo_sexo");
    String sId_tipo_estado_civil = cliente.getString(request, "id_tipo_estado_civil");
    String sId_pais = cliente.getString(request, "id_pais_n");
    String sId_departamento = cliente.getString(request, "id_departamento_n");
    String sId_provincia = cliente.getString(request, "id_provincia_n");
    String sId_localidad = cliente.getString(request, "id_localidad_n");
    String sId_tipo_institucion = cliente.getString(request, "id_tipo_institucion");
    String sId_colegio = cliente.getString(request, "id_colegio");
    String sId_tipo_turno = cliente.getString(request, "id_tipo_turno");
    int iId_tipo_graduacion = cliente.getInt(request, "id_tipo_graduacion");
    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
    String sAnio_egreso = cliente.getString(request, "anio_egreso");
    String sCelular = cliente.getString(request, "celular");
    String sCorreo = cliente.getString(request, "correo");
    //String sId_tipo_documento_p[] = request.getParameterValues("id_tipo_documento_p");
    //Fin Nuevo
    
    int iResultadoT =0; int iResultado; String sBandera; int iId_persona=0;
    
    if("".equals(sId_persona) || (sId_persona == null)) {
      return new ModelAndView("Error", "mensaje", "Faltan Datos");
    }
    
    //Registramos a Pst Personas
    Postulantes datosP =new Postulantes();
    datosP.setId_persona(Integer.parseInt(sId_persona));
    datosP.setNombres(sNombres);
    datosP.setPaterno(sPaterno);
    datosP.setMaterno(sMaterno);
    datosP.setDip(sDip);
    datosP.setDireccion(sDireccion);
    datosP.setTelefono(sTelefono);
    datosP.setCorreo(sCorreo);
    datosP.setCelular(sCelular);
    datosP.setFec_nacimiento(sFec_nacimiento);
    datosP.setId_pais(Integer.parseInt(sId_pais));
    datosP.setId_departamento(Integer.parseInt(sId_departamento));
    datosP.setId_provincia(Integer.parseInt(sId_provincia));
    datosP.setId_localidad(Integer.parseInt(sId_localidad));
    datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
    datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
    datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
    datosP.setUlt_usuario(cliente.getId_usuario());
    datosP.setId_rol(cliente.getId_rol());
    iId_persona = this.mi.setMiRegistrarPstPersona(datosP);
    
    if (iId_persona > 0) {
      //Registramos los cambios de colegios
      datosP.setId_colegio(Integer.parseInt(sId_colegio));
      datosP.setId_tipo_turno(Integer.parseInt(sId_tipo_turno));
      datosP.setAnio_egreso(Integer.parseInt(sAnio_egreso));
      int iResultadoColegio = this.mi.setRegistrarPstPrsColegio(datosP);
     
      //Buscar Datos Pst_persona
      Postulantes datosPostulante = new Postulantes();
      datosPostulante.setId_persona(Integer.parseInt(sId_persona));
      datosPostulante = this.mi. getPstBuscarPersona(datosPostulante);
      
      //Registrando documentos      
      /*if (sId_tipo_documento_p != null) {
        for (int i = 0; i< sId_tipo_documento_p.length; i++) {
    	  String sId_tipo_documento = sId_tipo_documento_p[i];
	  datosP.setId_tipo_documento(Integer.parseInt(sId_tipo_documento));
	  datosP.setNumero(request.getParameter("numero"+sId_tipo_documento));
	  datosP.setObservacion(request.getParameter("observacion"+sId_tipo_documento));
          iResultadoT  = this.mi.setPstRegistrarDocumentos(datosP);
        }
    
        
      }
      */
      
      //Buscamos los datos del postulante
      Postulantes datosPst = new Postulantes();
      datosPst.setId_postulante(Integer.parseInt(sId_postulante));
      datosPst = this.mi.getPstBuscarPostulanteNombres(datosPst);
      modelo.put("datosPst", datosPst);
      Postulantes datosColegio = new Postulantes();
      datosColegio.setId_persona(datosPst.getId_persona());
      datosColegio = this.mi.getBuscarPstPersonaColegio(datosColegio);
      modelo.put("datosColegio",datosColegio);
  
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    
      //Sacamos el formato de la hora
      Abm formatoHora = new Abm();
      formatoHora.setCampo("formato_hora");
      formatoHora.setCodigo("dibrap");
      modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));

      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
        modelo.put("datosInstitucion", datosInstitucion);
      }
      return new ModelAndView("administrarPostulantes/ImprimirDatosPostulante", modelo);
    }  
    else {
      return new ModelAndView("Error","mensaje","No se registro los datos de la persona postulante");
    }
    
    
  }
}
