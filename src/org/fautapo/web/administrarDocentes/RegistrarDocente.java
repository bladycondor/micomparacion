package org.fautapo.web.administrarDocentes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-01-07
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-01-07
 */


public class RegistrarDocente implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    modelo.put("usuario", cliente.getNombres());
    //Rcuperando variables  
    String sPaterno = request.getParameter("paterno");
    String sMaterno = request.getParameter("materno");
    String sNombres = request.getParameter("nombres");
    String sFec_nacimiento = request.getParameter("fec_nacimiento");
    String sDireccion = request.getParameter("direccion");
    String sTelefono = request.getParameter("telefono");
    String sDip = request.getParameter("dip");
    String sId_tipo_sexo = cliente.getString(request, "id_tipo_sexo");
    String sId_tipo_estado_civil = cliente.getString(request, "id_tipo_estado_civil");
    String sId_pais = cliente.getString(request, "id_pais_n");
    String sId_departamento = cliente.getString(request, "id_departamento_n");
    String sId_provincia = cliente.getString(request, "id_provincia_n");
    String sId_localidad = cliente.getString(request, "id_localidad_n");
    String sId_tipo_clasificacion = cliente.getString(request, "id_tipo_clasificacion");
    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
    int iAnio_titulacion = cliente.getInt(request, "anio_titulacion");
    String sTitulo = cliente.getString(request, "titulo");
    String sTipo_sanguineo = cliente.getString(request, "tipo_sanguineo");
    int iNro_hijos = cliente.getInt(request, "nro_hijos");
    int iNro_dependientes = cliente.getInt(request, "nro_dependientes");
    String sNro_seguro_medico = cliente.getString(request, "nro_seguro_medico");
    String sCelular = cliente.getString(request, "celular");
    String sCorreo = cliente.getString(request, "correo");
    int iId_persona = cliente.getInt(request, "id_persona");
    String sId_docente = cliente.getString(request, "id_docente");
    String sCategoria = cliente.getString(request, "categoria");
    String sApodo = cliente.getString(request, "apodo");
    String sAccion = cliente.getString(request,"accion");
    String sBandera = cliente.getString(request,"bandera");
    Personas datosP = new Personas();
    Docentes datosDocente = new Docentes();
    List lDocentes;
    int iResultadoT =0;int iId_persona_resultado=0;int iResultadoDoc=0;
    
    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion !=null) {
      modelo.put("datosInstitucion", datosInstitucion);
    }
    
    //Sacamos el formato de la fecha
    Abm formatoParametro = new Abm();
    formatoParametro.setCampo("formato_fecha");
    formatoParametro.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoParametro));
    
    
    if ("Nuevo".equals(sAccion)) {
      if((!"0".equals(Integer.toString(iId_persona)))) {
       //Nuevo Docente
       datosDocente.setId_persona((iId_persona));
       datosDocente.setApodo("docente"+Integer.toString(iId_persona));
       datosDocente.setClave("123456"); //Por defecto
       datosDocente.setCategoria(sCategoria);
       datosDocente.setId_rol(cliente.getId_rol());
       datosDocente.setUlt_usuario(cliente.getId_usuario());
       iResultadoDoc = this.mi.setRegistrarDocente(datosDocente);
          if(iResultadoDoc == 1) {
            modelo.put("apodo","docente"+Integer.toString(iId_persona_resultado));
            modelo.put("clave","123456"); //Por defecto
            return new ModelAndView("administrarDocentes/SalidaImprimirDocenteRegistrado", modelo);  
          }
          else {
            return new ModelAndView("Error","mensaje", "No se realizo el Registro. Revise que la persona ya esta registrada/o como docente");    
          } 
         
      }
      else {
        return new ModelAndView("Error", "mensaje", "Faltan Datos");
      }
    }      
    
    if ("NuevoPersonaDocente".equals(sAccion)) {
      if ((!"".equals(sId_pais)) && (!"".equals(sId_departamento)) && (!"".equals(sId_provincia)) && (!"".equals(sId_localidad)) && 
	  (!"".equals(sNombres)) && (sNombres != null) && (!"".equals(sDip)) && (sDip != null) && (!"".equals(sFec_nacimiento)) && (sFec_nacimiento != null) &&
	  (!"".equals(sDireccion)) && (!"".equals(sId_tipo_estado_civil)) && (!"".equals(sId_tipo_sexo))  && (!"".equals(sId_tipo_empresa_telefonica)) )
      {
        datosP.setId_persona(iId_persona);
        datosP.setId_pais(Integer.parseInt(sId_pais));
        datosP.setId_departamento(Integer.parseInt(sId_departamento));
        datosP.setId_provincia(Integer.parseInt(sId_provincia));
        datosP.setId_localidad(Integer.parseInt(sId_localidad));
        datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
        datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
        datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
        datosP.setNombres(sNombres);
        datosP.setPaterno(sPaterno);
        datosP.setMaterno(sMaterno);
        datosP.setDip(sDip);
        datosP.setFec_nacimiento(sFec_nacimiento);
        datosP.setDireccion(sDireccion);
        datosP.setTelefono(sTelefono);
        datosP.setCelular(sCelular);
        datosP.setCorreo(sCorreo);
        datosP.setAnio_titulacion(iAnio_titulacion);
        datosP.setTitulo(sTitulo);
        datosP.setTipo_sanguineo(sTipo_sanguineo);
        datosP.setNro_hijos(iNro_hijos);
        datosP.setNro_dependientes(iNro_dependientes);
        datosP.setNro_seguro_medico(sNro_seguro_medico);
        datosP.setUlt_usuario(cliente.getId_usuario());
        iId_persona_resultado = this.mi.setRegistrarPersona(datosP);
        if (iId_persona_resultado != 0) {
	  //Registramos al docente
	  datosDocente.setId_persona(iId_persona_resultado);
	  datosDocente.setCategoria(sCategoria);
	  datosDocente.setApodo("docente"+Integer.toString(iId_persona_resultado));
          datosDocente.setClave("123456"); //Por defecto
          datosDocente.setId_rol(cliente.getId_rol());
	  datosDocente.setUlt_usuario(cliente.getId_usuario());
          iResultadoDoc = this.mi.setRegistrarDocente(datosDocente);
          if(iResultadoDoc == 1) {
            modelo.put("apodo","docente"+Integer.toString(iId_persona_resultado));
            modelo.put("clave","123456"); //Por defecto
	    modelo.put("nombre_docente", sNombres);
            modelo.put("paterno_docente", sPaterno);
	    modelo.put("materno_docente", sMaterno);	    
            return new ModelAndView("administrarDocentes/SalidaImprimirDocenteRegistrado", modelo);  
          }
          else {
            return new ModelAndView("Error","mensaje", "No se registro los datos de Docente");    
          }    
        }  
        else {
          return new ModelAndView("Error","mensaje","Ocurrio un error. Los datos  de Persona no se registraron. <br>Puede que esa persona ya exista registrado.<br> Verifique con el administrador.");
        } 
      } 
    }
    
    if (("Modificar".equals(sAccion)) && (!"".equals(sId_docente))) {
      if ((!"".equals(sId_pais)) && (!"".equals(sId_departamento)) && (!"".equals(sId_provincia)) && (!"".equals(sId_localidad)) && 
	  (!"".equals(sNombres)) && (sNombres != null) && (!"".equals(sDip)) && (sDip != null) && (!"".equals(sFec_nacimiento)) && (sFec_nacimiento != null) &&
	  (!"".equals(sDireccion)) && (!"".equals(sId_tipo_estado_civil)) && (!"".equals(sId_tipo_sexo))  && (!"".equals(sId_tipo_empresa_telefonica)) )
      {
        //Buscamos el docente
	datosDocente.setId_docente(Integer.parseInt(sId_docente));
	datosDocente = this.mi.getBuscarDocente(datosDocente);
	if(datosDocente == null)
	  return new ModelAndView("Error","mensaje", "No se encuentran datos del docente");
	//Modficamos datos de persona
        datosP.setId_persona(datosDocente.getId_persona());
        datosP.setId_pais(Integer.parseInt(sId_pais));
        datosP.setId_departamento(Integer.parseInt(sId_departamento));
        datosP.setId_provincia(Integer.parseInt(sId_provincia));
        datosP.setId_localidad(Integer.parseInt(sId_localidad));
        datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
        datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
        datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
        datosP.setNombres(sNombres);
        datosP.setPaterno(sPaterno);
        datosP.setMaterno(sMaterno);
        datosP.setDip(sDip);
        datosP.setFec_nacimiento(sFec_nacimiento);
        datosP.setDireccion(sDireccion);
        datosP.setTelefono(sTelefono);
        datosP.setCelular(sCelular);
        datosP.setCorreo(sCorreo);
        datosP.setAnio_titulacion(iAnio_titulacion);
        datosP.setTitulo(sTitulo);
        datosP.setTipo_sanguineo(sTipo_sanguineo);
        datosP.setNro_hijos(iNro_hijos);
        datosP.setNro_dependientes(iNro_dependientes);
        datosP.setNro_seguro_medico(sNro_seguro_medico);
        datosP.setUlt_usuario(cliente.getId_usuario());
        iId_persona_resultado = this.mi.setRegistrarPersona(datosP);
        
	//Modificar Datos Docente
	datosDocente.setId_docente(datosDocente.getId_docente());
	datosDocente.setCategoria(sCategoria);
	datosDocente.setApodo("docente"+Integer.toString(datosDocente.getId_persona()));
        datosDocente.setClave("123456");//Por defecto
        datosDocente.setId_rol(cliente.getId_rol());
	datosDocente.setUlt_usuario(cliente.getId_usuario());
	//int iResultadoM = this.mi.setRegistrarDocente(datosDocente);
        iResultadoDoc = this.mi.setRegistrarDocente(datosDocente);
          if(iResultadoDoc == 1) {
            modelo.put("apodo","docente"+Integer.toString(datosDocente.getId_persona()));
            modelo.put("clave","123456"); //Por defecto
	    modelo.put("nombre_docente", sNombres);
            modelo.put("paterno_docente", sPaterno);
	    modelo.put("materno_docente", sMaterno);	    
            return new ModelAndView("administrarDocentes/SalidaImprimirDocenteRegistrado", modelo);  
          }
          else {
            return new ModelAndView("Error","mensaje", "No se registro los datos de Docente");    
          }
      }
      else {
         return new ModelAndView("Error","mensaje", "LLene todos los datos obligatorios. Para modificar el registro");
      }
    } 
    
    if ("Eliminar".equals(sAccion) && (!"".equals(sId_docente))) {
       //Eliminar Docente
       datosDocente.setId_docente(Integer.parseInt(sId_docente));
       int iResultadoElim = this.mi.setEliminarDocente(datosDocente);
       if(iResultadoElim == 0)
         return new ModelAndView("Error", "mensaje", "No se realizo la eliminacion. Por que el Docente tiene materias asignadas");
    }
    
    
    
    modelo.put("accion", sAccion);
    return new ModelAndView("administrarDocentes/BuscarDocentePersona", modelo);  
  }
}
