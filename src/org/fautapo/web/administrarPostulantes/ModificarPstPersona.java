package org.fautapo.web.administrarPostulantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
//import org.fautapo.domain.Programas;
import org.fautapo.domain.Universidades;
//import org.fautapo.domain.Planes;
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


public class ModificarPstPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    int iId_facultad = cliente.getId_facultad();
    int iId_programa = cliente.getId_programa();
 
    String sId_persona = request.getParameter("id_persona");
    String sId_postulante = request.getParameter("id_postulante");

    //Planes datoPlan = new Planes();
    //Programas datoPrograma = new Programas();
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
    
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("cliente", cliente);
    modelo.put("id_persona", sId_persona);
    modelo.put("id_postulante", sId_postulante);
    
    return new ModelAndView("administrarPostulantes/ModificarPstPersona", modelo);
    
  }
}
