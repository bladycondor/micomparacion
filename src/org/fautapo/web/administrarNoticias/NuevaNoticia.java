package org.fautapo.web.administrarNoticias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tableros;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04_03
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-03
*/

public class NuevaNoticia implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();
    String sNombres = cliente.getNombres();

    Tableros tableros;
    String sId_tablero = request.getParameter("id_tablero");
    String sBoton = request.getParameter("boton");
    
    //Listar tipos avisos
    List lTiposAvisos = this.mi.getListarTiposAvisos();
    modelo.put("lTiposAvisos", lTiposAvisos);

    //Listar tipos tableros
    List lTiposTableros =this.mi.getListarTiposTableros();
    modelo.put("lTiposTableros", lTiposTableros);
    
    //Listar roles
    List lRoles =this.mi.getListarRoles();
    modelo.put("lRoles", lRoles);
    
    if ("Modificar".equals(sBoton)) {
      Tableros datosTablero = new Tableros();
      datosTablero.setId_tablero(Integer.parseInt(sId_tablero));
      datosTablero = this.mi.getBuscarTablero(datosTablero);
      datosTablero.setMensaje(datosTablero.getMensaje().replace("\r\n",""));
      modelo.put("datosTablero",datosTablero);
      modelo.put("id_tablero", sId_tablero);
    }

    modelo.put("boton", sBoton);
    modelo.put("nombres", sNombres);
    
    return new ModelAndView("administrarNoticias/NuevaNoticia",modelo);
  }
}