/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.estadisticas.titulados;

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

    // Definicion de la consulta SQL
    sql += "\n SELECT * FROM mi_rep_titulados(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ")";
    sql += "\n AS (programa text, id_plan dtexto, nro_estudiantes bigint)";

    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");
    //definicion de las etiquetas
    String etiquetas="Programa###Plan###Cantidad";
    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 0, "desde", 2, etiquetas);

    sql  = "\n SELECT * FROM mi_rep_fcl_titulados(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ")";
    sql += "\n AS (facultad text, nro_estudiantes bigint)";
    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");
    //definicion de las etiquetas
    String etiquetas2="Facultad###Cantidad";
    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos2", 0, "desde2", 1, etiquetas2);

    sql =  "\n SELECT * FROM mi_rep_unv_titulados(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ")";
    sql += "\n AS (nro_estudiantes bigint)";
    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");
    //definicion de las etiquetas
    String etiquetas3="Cantidad";
    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos3", 0, "desde3", 1, etiquetas3);

    modelo.put("nombres", cliente.getNombres());

    //Sacamos los datos de la institucion
    Instituciones datosInstitucion = new Instituciones();
    datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
    datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
    if (datosInstitucion != null)
      modelo.put("datosInstitucion", datosInstitucion);
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    modelo.put("titulo", "N&uacute;mero de  Estudiantes titulados por gesti&oacute;n y periodo");
    modelo.put("periodo", f.sRequest("periodo"));
    modelo.put("gestion", f.sRequest("gestion"));
    return new ModelAndView("reportesEspecializados/estadisticas/titulados/FormarReporte", modelo);
  }
}