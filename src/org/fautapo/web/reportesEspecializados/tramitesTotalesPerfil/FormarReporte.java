/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Dayana Torrico
 @fec_modificacion :: 2008-05-02
*****************************************/
package org.fautapo.web.reportesEspecializados.tramitesTotalesPerfil;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.awt.*;
import java.awt.event.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.domain.*;


public class FormarReporte implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //Declaracion de Variables
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    Funciones f = new Funciones(request, modelo, mi);
    String sql="";

    // Parametros de entrada
    modelo.put("fec_comprobantei", f.sRequest("fec_comprobantei"));
    modelo.put("fec_comprobantef", f.sRequest("fec_comprobantef"));

    int iId_proceso = cliente.getInt(request, "id_proceso");
/*
    //Sacamos los datos del Proceso
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(iId_perfil);
    datosPerfil = this.mi.getTrnBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);
*/

    // Definicion de la consulta SQL
    sql = "SELECT * FROM trn_listar_totales_tramites_atendidos("+iId_proceso+", ";
    sql += "_cadena_fecha('"+f.sRequest("fec_comprobantei")+"'), _cadena_fecha('"+f.sRequest("fec_comprobantef")+"'))";
    sql += "\n AS (proceso dtexto, cantidad dentero2, nombre_completo text)" ;

    System.out.println("\n\n--Listar Procesos-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="Proceso/Tramite###Cantidad###Nombres completo";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 0, "desde", 3, etiquetas);

    return new ModelAndView("reportesEspecializados/tramitesTotalesPerfil/FormarReporte", modelo);
  }
}