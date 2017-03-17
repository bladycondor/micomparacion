package org.fautapo.web.copiaSeguridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class Restaurar implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {
      return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");
    }
    modelo.put("cliente", cliente);

    String sArchivo = cliente.getString(request, "archivo");
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("copia_seguridad");
    formatoFecha.setCodigo("dibrap");
    String sCarpeta = this.mi.getDibBuscarParametro(formatoFecha) + "/";
    try {
      // Execute a command
      String sComando[] = {"tbRestaurar.sh", request.getRealPath("/"), sCarpeta + sArchivo};
      Process pr = Runtime.getRuntime().exec(sComando);
      InputStream is = pr.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader (is));
      String aux = br.readLine();
      while (aux != null) {
        aux = br.readLine();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ModelAndView("Error", "mensaje", "Hubo un problema al restaurar la Base de Datos: " + e.getMessage());
    }
    return new ModelAndView("Aviso", "mensaje", "Se restauró correctamente la copia de seguridad a la Base de Datos");
  }
}