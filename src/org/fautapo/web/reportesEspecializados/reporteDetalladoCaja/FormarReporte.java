/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.reporteDetalladoCaja;

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

    // Definicion de la consulta SQL

    sql += "\n SELECT d.id_concepto, max(c.concepto) AS concepto, sum(d.cantidad), sum(d.pagado)";
    sql += "\n FROM trn_detalles d INNER JOIN trn_conceptos c USING(id_concepto) WHERE d.id_estado = 'A' AND d.costo>0";
    sql += f.verificar ("_cadena_fecha('"+f.sRequest("fec_comprobantei")+"')", "<=", "d.fec_registro::date", "");
    sql += f.verificar ("_cadena_fecha('"+f.sRequest("fec_comprobantef")+"')", ">=", "d.fec_registro::date", "");
    sql += "\n GROUP BY  d.id_concepto \n ORDER BY  concepto";






//System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="id_concepto###Concepto###Cantidad###Pagado";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 0, "desde", 4, etiquetas);

    return new ModelAndView("reportesEspecializados/reporteDetalladoCaja/FormarReporte", modelo);
  }
}