package org.fautapo.web.administrarEstudiantes;

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
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-01-07
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-01-07
 */


public class RegistrarModificarEstPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    modelo.put("usuario", cliente.getNombres());  
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
    String sId_tipo_clasificacion = cliente.getString(request, "id_tipo_clasificacion");
    int iId_tipo_graduacion = cliente.getInt(request, "id_tipo_graduacion");
    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
    int iAnio_titulacion = cliente.getInt(request, "anio_titulacion");
    String sTitulo = cliente.getString(request, "titulo");
    String sTipo_sanguineo = cliente.getString(request, "tipo_sanguineo");
    int iNro_hijos = cliente.getInt(request, "nro_hijos");
    int iNro_dependientes = cliente.getInt(request, "nro_dependientes");
    String sNro_seguro_medico = cliente.getString(request, "nro_seguro_medico");
    String sCelular = cliente.getString(request, "celular");
    String sCorreo = cliente.getString(request, "correo");
    
    String sId_tipo_estudiante = cliente.getString(request, "id_tipo_estudiante"); //Nuevo-Antiguo
    String sFec_egreso = request.getParameter("fec_egreso");
    String sFec_inscripcion = request.getParameter("fec_inscripcion");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");
    int iId_persona = cliente.getInt(request,"id_persona");
    int iId_estudiante = cliente.getInt(request,"id_estudiante");
    
    int iResultadoT =0;
    if (iId_estudiante > 0  &&  iId_persona > 0) {
      if ((!"".equals(sId_tipo_estudiante)) && (!"".equals(sId_tipo_clasificacion))  && 
	  (!"".equals(sId_pais)) && (!"".equals(sId_departamento)) && (!"".equals(sId_provincia)) && (!"".equals(sId_localidad)) && (!"".equals(sId_tipo_institucion)) && (!"".equals(sId_colegio)) && (!"".equals(sId_tipo_turno)) &&
	  (!"".equals(sNombres)) && (sNombres != null) && (!"".equals(sDip)) && (sDip != null) && (!"".equals(sFec_nacimiento)) && (sFec_nacimiento != null) &&
	  (!"".equals(sDireccion)) && (!"".equals(sId_tipo_estado_civil)) && (!"".equals(sId_tipo_sexo))  && (!"".equals(sId_tipo_empresa_telefonica)) && (!"".equals(sFec_inscripcion)))
      {
        Personas datosP = new Personas();
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
        int iId_persona_resultado = this.mi.setRegistrarPersona(datosP);
        if (iId_persona_resultado != 0) {
          datosP.setId_colegio(Integer.parseInt(sId_colegio)); 
          datosP.setId_tipo_turno(Integer.parseInt(sId_tipo_turno)); 
          datosP.setAnio_egreso(iAnio_titulacion); 
          //Registrando prs_colegios
          int iResultadoCole = this.mi.setRegistrarPrsColegio(datosP);
          if(iResultadoCole == 0)
            return new ModelAndView("Error","mensaje", "Los datos de colegios no se registraron");
          //Registrando en prs_clasificaciones
	  Estudiantes datosClasificacion = new Estudiantes();
	  datosClasificacion.setId_estudiante(iId_estudiante);
          datosClasificacion.setId_tipo_clasificacion(Integer.parseInt(sId_tipo_clasificacion));
          datosClasificacion.setUlt_usuario(cliente.getId_usuario());
          int iResultadoClasif = this.mi.setRegistrarEstClasificacion(datosClasificacion); //Registra el tipo_clasificacion si no existe, de lo contrario modifica
        }  
        else {
          return new ModelAndView("Error","mensaje","Ocurrio un error. Los datos no se registraron");
        } 
      
        //Cambiando solo el tipo de estudiante= Nuevo-Antiguo
        Estudiantes datosEst = new Estudiantes(); 
	datosEst.setId_estudiante(iId_estudiante);
	datosEst.setId_tipo_estudiante(Integer.parseInt(sId_tipo_estudiante));
	datosEst.setUlt_usuario(cliente.getId_usuario());
	int iResultadoTipoEst = this.mi.setModificarTipoEstudiante(datosEst); 
	
        //System.out.println("EL ID estudiante RESULTADO -->"+Integer.toString(iId_estudiante));
        int iResultado;
	//Si registro el tipo_estudiante => vemos sus documentos
	if (iResultadoTipoEst > 0) {
          //Listados para mostrar en la siguiente pantalla	
	  //Sacando los datos del estudiante 
          Estudiantes buscarEst = new Estudiantes();
          buscarEst.setId_estudiante(iId_estudiante);
          buscarEst= this.mi.getEstBuscarEstudiantePrsPre(buscarEst);
          modelo.put("buscarEst",buscarEst);
	  //Buscar Tipo clasificacion persona
	  Estudiantes datosClas = new Estudiantes();
	  datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosEst);
	  modelo.put("datosClas", datosClas);
	  
	  //Sacamos el formato de la fecha
	  Abm formatoParametro = new Abm();
          formatoParametro.setCampo("formato_fecha");
          formatoParametro.setCodigo("dibrap");
          modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoParametro));
    
	}
	
      }
      else {
         return new ModelAndView("Error","mensaje", "LLene todos los datos obligatorios");
      }
    } 
    else {
         return new ModelAndView("Error","mensaje", "No existe el estudiante");
    }

    return new ModelAndView("administrarEstudiantes/EntradaBuscarEstudiantes", modelo);
  }
}
