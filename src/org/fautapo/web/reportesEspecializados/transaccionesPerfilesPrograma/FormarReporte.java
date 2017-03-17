/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Dayana Torrico
 @fec_modificacion :: 2008-05-02
*****************************************/
package org.fautapo.web.reportesEspecializados.transaccionesPerfilesProgramas;

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
    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_perfil = cliente.getInt(request, "id_perfil");

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //Sacamos los datos del Programa
    Perfiles datosPerfil = new Perfiles();
    datosPerfil.setId_perfil(iId_perfil);
    datosPerfil = this.mi.getTrnBuscarPerfil(datosPerfil);
    modelo.put("datosPerfil", datosPerfil);

    // Definicion de la consulta SQL
    sql = "SELECT * FROM trn_listar_transacciones_perfil_programa("+iId_programa+", "+iId_perfil+", ";
    sql += "_cadena_fecha('"+f.sRequest("fec_comprobantei")+"'), _cadena_fecha('"+f.sRequest("fec_comprobantef")+"'))";
    sql += "\n AS (nombre text, fec_pago text, hora text, tipo_descuento dtexto, cantidad dentero2, total dmoneda2, descuento dmoneda2, pagado dmoneda2)";

    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="Nombre###Fecha###Hora###Tipo de Descuento###Cantidad###Total###Descuento###Pagado";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 0, "desde", 4, etiquetas);

    return new ModelAndView("reportesEspecializados/transaccionesPerfilesProgramas/FormarReporte", modelo);
  }
}