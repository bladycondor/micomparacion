package org.fautapo.web.copiaSeguridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class ListarDescargas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    modelo.put("cliente", cliente);

/*
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(cliente.getString(request, "clave"));
    if(null == this.mi.getComprobarUsuario(usuario)) {
      return new ModelAndView("copiaSeguridad/Entrada", modelo);
    }
*/
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("copia_seguridad");
    formatoFecha.setCodigo("dibrap");
    String sCarpeta = this.mi.getDibBuscarParametro(formatoFecha) + "/";
    modelo.put("carpeta", sCarpeta);
    List listaArchivos = new ArrayList();
    try {
      // Execute command
      String sComando[] = {"/bin/sh", "-c", "ls " + sCarpeta + "*.bz2"};
      Process pr = Runtime.getRuntime().exec(sComando);
      InputStream is = pr.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String sArchivo = br.readLine();
      while (sArchivo != null) {
        File fTexto = new File(sArchivo);
        String pedazos[] = fTexto.getName().split("\\.");

        fTexto = new File(fTexto.getParent() + "/" + pedazos[0] + "." + pedazos[1] + ".txt");
        String contenido = "";
        if (fTexto.exists()) {
          FileReader input = new FileReader(fTexto.getPath());
          BufferedReader bufRead = new BufferedReader(input);
          String linea = bufRead.readLine();
          while (linea != null) {
            contenido += linea;
            linea = bufRead.readLine();
          }
          bufRead.close();
        }
        cliente.setFiltro(pedazos[1].substring(0, 10));
        String sValores[] = {(new File(sArchivo)).getName(), this.mi.getFechaCadena(cliente) + "  " + pedazos[1].substring(11, 13) + ":" + pedazos[1].substring(13, 15), contenido};
        listaArchivos.add(sValores);
        sArchivo = br.readLine();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    modelo.put("listaArchivos", listaArchivos);
    return new ModelAndView("copiaSeguridad/ListarDescargas", modelo);
  }
}