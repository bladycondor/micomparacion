/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesAcademicos.postulantesAprobadosTipoAdmision;

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

    //Recuperamos datos del jsp
    int iId_programa = cliente.getInt(request, "id_programa");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));

    //Sacamos los datos del programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(iId_programa);
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    // Definicion de la consulta SQL
    sql += "\n SELECT * ";
    sql += "\n FROM reporte_postulantes_aprobados_tipo_admision("+iId_programa+"','"+iGestion+"','"+iPeriodo+")";
    sql += "\n AS()";

    System.out.println("\n\n--consulta---->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="id_cajero###Fecha###Cajero###Pagado";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 1, "desde", 4, etiquetas);

    return new ModelAndView("reportesAcademicos/postulantesAprobadosTipoAdmision/FormarReporte", modelo);
  }
}