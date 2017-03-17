package org.fautapo.web.administrarUsuarios;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.fautapo.domain.Personas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
*  FUNDACION AUTAPO
*  @autor           : Luis A. Jordan P.
*  @fec_registro    : 11/04/2006
*  @ult_usuario     : Carmen Rosa Calle
*  @fec_modificacion: 02/05/2008
*/

public class ListarPersonas implements Controller {
  private MiFacade mi;
   
  public void setMi(MiFacade mi){ this.mi = mi; }
   
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    //Session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    
    //Recuperamos las variables del jsp
    int iPagina = cliente.getInt(request, "pagina");
    String sPatron = cliente.getString(request, "patron");

    Personas persona = new Personas();
    if (iPagina == 0)
      persona.setPagina(1);
    else
      persona.setPagina(iPagina);
    modelo.put("pagina", Integer.toString(iPagina));
    persona.setPatron(sPatron);
    modelo.put("patron", sPatron);
      
    List lPersonas = this.mi.getListarPersonas(persona);
    System.out.println("ENTRA AQUI CON L PERSONAS -->" +Integer.toString(lPersonas.size()));
    modelo.put("lPersona", lPersonas);
    modelo.put("size", Integer.toString(lPersonas.size()));
    modelo.put("direccion", "listarPersonas.fautapo");
    //Return
    return new ModelAndView("administrarUsuarios/ListarPersonas", modelo);
  }
}