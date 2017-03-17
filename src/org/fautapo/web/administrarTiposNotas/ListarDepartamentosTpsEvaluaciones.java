package org.fautapo.web.administrarTiposNotas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class ListarDepartamentosTpsEvaluaciones implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
  
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    //int id_facultad = cliente.getId_facultad();
    int iId_facultad = cliente.getInt(request, "id_facultad");
    System.out.println("El id_Facultada de tipo nota -->"+ Integer.toString(iId_facultad));
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    //Buscamos FAcultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(iId_facultad);
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("facultad",datosFacultad);
    if(datosFacultad ==null)
      return new ModelAndView("Error", "mensaje", "No existe la facultad");
    //Listando los tipos_evaluaciones
    List lListarTiposEvaluaciones = this.mi.getTpsListarTiposEvaluaciones();
    modelo.put("lListarTiposEvaluaciones", lListarTiposEvaluaciones);
    //Listando los fcl_departamentos
    Facultades dato = new Facultades();
    dato.setId_facultad(iId_facultad);
    List  lListarFclDepartamentos = this.mi.getFclListarDepartamentos(dato);
    modelo.put("lListarFclDepartamentos", lListarFclDepartamentos);
    
    modelo.put("cliente",cliente);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
    return new ModelAndView("administrarTiposNotas/ListarDepartamentosTpsEvaluaciones", modelo);
  }
}
