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

import java.io.IOException;
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

public class Copiar implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {
      return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");
    }
    modelo.put("cliente", cliente);
    String sSistema  = cliente.getString(request, "sistema");
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("copia_seguridad");
    formatoFecha.setCodigo("dibrap");
    String sCarpeta = this.mi.getDibBuscarParametro(formatoFecha);
    boolean estado = new File(sCarpeta).mkdirs();  // Por si acaso, si no existe, creamos la carpeta
    String sDescripcion = cliente.getString(request, "descripcion");

    try {
      // Execute a command
      String sComando[] = {"tbDump.sh", request.getRealPath("/"), sCarpeta, sDescripcion};
      Process proceso = Runtime.getRuntime().exec(sComando);
      InputStream is = proceso.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader (is));
      String aux = br.readLine();
      while (aux != null) {
        aux = br.readLine();
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
      return new ModelAndView("Error", "mensaje", "Hubo un problema al hacer la copia: " + e.getMessage());
    }
    return new ModelAndView("Aviso", "mensaje", "La copia de seguridad de la Base de Datos se realizó correctamente");
  }
}