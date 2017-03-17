package org.fautapo.web.administrarProgramasEspecializados.administrarPostulantesPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Universidades;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Perfiles;
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


public class NuevoPostulantePersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    int iId_facultad = cliente.getId_facultad();
    int iId_programa = cliente.getId_programa();
 
    //Para wayka
    String sId_persona = request.getParameter("id_persona");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sTitulo = request.getParameter("titulo");
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);

    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    Programas datoPrograma = new Programas();
    Personas dPaises = new Personas();
    
    if("".equals(sId_persona) && (sId_persona == null)){
      return new ModelAndView("Error","mensaje","Faltan datos");
    }
    
    //Buscar Datos Pst_persona
    Postulantes datosPstPersona = new Postulantes();
    datosPstPersona.setId_persona(Integer.parseInt(sId_persona));
    datosPstPersona = this.mi.getPstBuscarPersona(datosPstPersona);
    modelo.put("datosPstPersona",datosPstPersona);
    Postulantes datosColegio = new Postulantes();
    datosColegio.setId_persona(Integer.parseInt(sId_persona));
    datosColegio = this.mi.getBuscarPstPersonaColegio(datosColegio);
    modelo.put("datosColegio",datosColegio);
    
    
    //Listando Paises
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
    List lColegiosTipoInst = this.mi.getListarColegiosTipoIns(dPaises);
    modelo.put("lColegiosTipoInst", lColegiosTipoInst);
    List lTiposTurnos = this.mi.getListarTiposTurnos();
    modelo.put("lTiposTurnos", lTiposTurnos);
    //Fin listar tipos
    
    
    //Listando Facultades
    Universidades datosUniversidad = new Universidades();
    datosUniversidad.setId_universidad(cliente.getId_universidad());
    List lFacultades = this.mi.getUnvListarFacultadesPost(datosUniversidad);
    modelo.put("lFacultades", lFacultades);

    //Sacamos el listado de los programas
    List lProgramas = this.mi.getUnvListarProgramasPost(datosUniversidad);    
    modelo.put("lProgramas", lProgramas);
    //Listar Plan del programa actual
    List lPlanesActual= this.mi.getListarPrgPlanesVestibulares();
    modelo.put("lPlanesActual",lPlanesActual);
    
    //Listando los tipos
    Programas datosTipoAdmision = new Programas();
    datosTipoAdmision.setGestion(cliente.getGestion());
    datosTipoAdmision.setPeriodo(cliente.getPeriodo());
    List lTiposAdmisiones = this.mi.getListarTiposAdmisionesPrograma(datosTipoAdmision);
    modelo.put("lTiposAdmisiones",lTiposAdmisiones);

    List lTiposGrados = this.mi.getListarTiposGrados();
    modelo.put("lTiposGrados", lTiposGrados);

    //Listar TiposDocumentos*tipoclasificacion
    Postulantes tiposDoc = new Postulantes();
    tiposDoc.setId_tipo_clasificacion(3); //Por ser Vestibular
    List lTiposDocumentosClasf = this.mi.getListarTiposDocumentosClasificacionVigente(tiposDoc);
    modelo.put("lTiposDocumentosClasf", lTiposDocumentosClasf);
    
    //Buscamos el id_tipo_perfil, id_perfil
    if(!"".equals(sId_proceso) && (sId_proceso != null)){
      Perfiles datoPerfil = new Perfiles();
      datoPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfilesProcesos = this.mi.getTrnMiListarPerfilesProceso(datoPerfil);
      modelo.put("lPerfilesProcesos", lPerfilesProcesos);
    }
    else {
      return new ModelAndView("Error", "mensaje", "No existe el proceso. Verifique");
    }
    //Listamos tipos descuentos
    List lTiposDescuentos = this.mi.getTrnListarTiposDescuentos();
    modelo.put("lTiposDescuentos", lTiposDescuentos);    
    
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("cliente", cliente);
    modelo.put("id_persona", sId_persona);
    
    return new ModelAndView("administrarProgramasEspecializados/administrarPostulantesPost/NuevoPostulantePersona", modelo);
  }
}
