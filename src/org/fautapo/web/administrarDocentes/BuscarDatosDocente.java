package org.fautapo.web.administrarDocentes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BuscarDatosDocente implements Controller {
  
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Su session ha terminado. Por favor, ingrese nuevamente."); }

    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    String sAccion     = cliente.getString(request,"accion");
    String sId_docente =cliente.getString(request,"id_docente");
    List lDocentes;
    Docentes datosDocente = new Docentes();
    Personas datosPersona = new Personas();
    //Votamos los datos
    modelo.put("nombre", sNombre);
    modelo.put("dip", sDip);
    modelo.put("accion", sAccion);
    modelo.put("id_docente", sId_docente);    
    
    if("".equals(sAccion))
      return new ModelAndView("Error", "mensaje", "Seleccione un accion");
    
    //Listando Paises
    Personas dPaises = new Personas();
    List lPaises = this.mi.getListarPaises();
    modelo.put("lPaises", lPaises);              
    List lDepartamentos = this.mi.getListarDepartamentos(dPaises);
    modelo.put("lDepartamentos", lDepartamentos);
    List lProvincias = this.mi.getListarProvincias(dPaises);
    modelo.put("lProvincias", lProvincias);
    List lLocalidades = this.mi.getListarLocalidades(dPaises);
    modelo.put("lLocalidades", lLocalidades);
    //Listar Tipos
    List lTiposSexos = this.mi.getListarTiposSexos();
    modelo.put("lTiposSexos", lTiposSexos);
    List lTiposEstadosCiviles = this.mi.getListarTiposEstadosCiviles();
    modelo.put("lTiposEstadosCiviles", lTiposEstadosCiviles);
    List lTiposEmpresasTelefonicas = this.mi.getListarTiposEmpresasTelef();
    modelo.put("lTiposEmpresasTelefonicas", lTiposEmpresasTelefonicas);
    List lTiposInstituciones = this.mi.getListarTiposInstituciones();
    modelo.put("lTiposInstituciones", lTiposInstituciones);
     
    //Sacamos el formato de la fecha
    Abm formatoParametro = new Abm();
    formatoParametro.setCampo("formato_fecha");
    formatoParametro.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoParametro));
    
    if("NuevoPersonaDocente".equals(sAccion)) {
      return new ModelAndView("administrarDocentes/NuevoPersonaDocente", modelo);
    }
    
    if("Modificar".equals(sAccion) && (!"".equals(sId_docente))&&(sId_docente != null)) {
      //Buscar Datos Docente y Persona
      datosDocente.setId_docente(Integer.parseInt(sId_docente));
      datosDocente = this.mi.getBuscarDocente(datosDocente);
      if(datosDocente != null) {
        datosPersona.setId_persona(datosDocente.getId_persona());
	datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      }
      modelo.put("datosDocente", datosDocente);
      modelo.put("datosPersona", datosPersona);
      return new ModelAndView("administrarDocentes/ModificarDatosDocente", modelo);  
    }
    
    if("Eliminar".equals(sAccion) && (!"".equals(sId_docente))&&(sId_docente != null)) {
      //Buscar Datos Docente y Persona
      datosDocente.setId_docente(Integer.parseInt(sId_docente));
      datosDocente = this.mi.getBuscarDocente(datosDocente);
      if(datosDocente != null) {
        datosPersona.setId_persona(datosDocente.getId_persona());
	datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      }
      modelo.put("datosDocente", datosDocente);
      modelo.put("datosPersona", datosPersona);
      return new ModelAndView("administrarDocentes/ConfirmarDatosDocente", modelo);  
    }
    
    modelo.put("direccion", "listarDocentes.fautapo");
    return new ModelAndView("administrarDocentes/NuevoDocente", modelo);
  }
}