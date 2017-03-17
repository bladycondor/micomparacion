/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.porCajeroPerfiles;

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
    sql += "\n SELECT t.id_perfil, _fecha_cadena(t.fec_pago::date), max(p.perfil), count(id_perfil), sum(t.pagado)";
    sql += "\n FROM transacciones t INNER JOIN trn_perfiles p USING(id_perfil) WHERE t.id_estado = 'A' AND t.cajero = " + f.sRequest("id_usuario");
	sql += "\n and t.id_sede = 1";
    sql += f.verificar ("_cadena_fecha('"+f.sRequest("fec_comprobantei")+"')", "<=", "t.fec_pago::date", "");
    sql += f.verificar ("_cadena_fecha('"+f.sRequest("fec_comprobantef")+"')", ">=", "t.fec_pago::date", "");
    sql += "\n GROUP BY t.fec_pago::date, t.id_perfil ORDER BY 2, 3";

   System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="id_perfil###Fecha###Perfil###Cantidad###Pagado";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 1, "desde", 4, etiquetas);

    return new ModelAndView("reportesEspecializados/porCajeroPerfiles/FormarReporte", modelo);
  }
}