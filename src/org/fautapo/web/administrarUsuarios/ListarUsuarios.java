package org.fautapo.web.administrarUsuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class ListarUsuarios implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    
    Usuarios usuario = new Usuarios();
    int iPagina = cliente.getInt(request, "pagina");
    String sId_usuario = request.getParameter("id_usuario");
    String sPagina = request.getParameter("pagina");
    System.out.println("La pagina para verificar-->"+ sPagina); 
    
    if (sPagina == null) {
      // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
      usuario.setId_usuario(cliente.getId_usuario());
      usuario.setClave(request.getParameter("clave" + request.getParameter("hora")));
      if (null == this.mi.getComprobarUsuario(usuario)) {
        //modelo.put("mensaje", "Clave incorrecta, por favor intente nuevamente");
	return new ModelAndView("Error","mensaje","Clave incorrecta, por favor intente nuevamente");
        //return new ModelAndView("administrarUsuarios/Aviso", modelo);
      }
    }
    
    //Listamos los Usuarios
    if (iPagina == 0)
      usuario.setPagina(1);
    else
      usuario.setPagina(iPagina);
    modelo.put("pagina", Integer.toString(iPagina));
      
    List lUsuarios = this.mi.getListarUsuarios(usuario);
    System.out.println("ENTRA AQUI CON L PERSONAS -->" +Integer.toString(lUsuarios.size()));
    modelo.put("lUsuarios", lUsuarios);
    modelo.put("size", Integer.toString(lUsuarios.size()));
    modelo.put("direccion", "listarUsuarios.fautapo");

    return new ModelAndView("administrarUsuarios/ListarUsuarios", modelo);
  }
}