package org.fautapo.web.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Categorias;
import org.fautapo.domain.Enlaces;

public class Menu implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    Enlaces enlace = new Enlaces();
    enlace.setId_rol(cliente.getId_rol());
    
    System.out.println("rolcito "+enlace.getId_rol());
    List listaEnlaces = this.mi.getListarEnlaces(enlace);
    int fil = listaEnlaces.size();
    String enlaces[][] = new String[fil][10];
    for (int i = 0; i < listaEnlaces.size(); i++) {
      Enlaces aux = (Enlaces) listaEnlaces.get(i);
      int id_enlace = aux.getId_enlace();
      int id_enlace_padre = aux.getId_enlace_padre();
      int nivel = aux.getNivel();
      String enlace1 = aux.getEnlace();
      String ruta = aux.getRuta();
      String imagen = aux.getImagen();
      
      enlaces[i][0] = String.valueOf(id_enlace);
      enlaces[i][1] = String.valueOf(id_enlace_padre);
      enlaces[i][2] = String.valueOf(nivel);
      
      enlaces[i][3] = enlace1;
      enlaces[i][4] = ruta;
      enlaces[i][5] = imagen;
    }

      modelo.put("fil", String.valueOf(fil-1));
      modelo.put("listaEnlaces", enlaces);
      modelo.put("cliente", (Clientes) request.getSession().getAttribute("__sess_cliente"));
    return new ModelAndView("menu/Menu", modelo);
  }
}