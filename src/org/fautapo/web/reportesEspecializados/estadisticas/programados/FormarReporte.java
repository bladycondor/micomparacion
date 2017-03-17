/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.estadisticas.programados;

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

    //Buscamos Tipo Evaluacion
    Libretas datosTipoEvaluacion = new Libretas();
    datosTipoEvaluacion.setId_tipo_evaluacion(f.iRequest("id_tipo_evaluacion"));
    datosTipoEvaluacion = this.mi.getTpsBuscarTipoEvaluacion(datosTipoEvaluacion);
    modelo.put("datosTipoEvaluacion", datosTipoEvaluacion);

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

    // Definicion de la consulta SQL
    sql += "\n SELECT * FROM mi_rep_programados_y_no(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ", " +f.sRequest("id_tipo_evaluacion")+ ")";
    sql += "\n AS (id_programa integer, programa dtexto, nro_matriculados bigint, nro_programados bigint, nro_noprogramados bigint)";

    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="ID programa###Programa###Nro. Matriculados###Nro. Programados###Nro. No Programados";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 1, "desde", 100, etiquetas);

    modelo.put("periodo", f.sRequest("periodo"));
    modelo.put("gestion", f.sRequest("gestion"));
    return new ModelAndView("reportesEspecializados/estadisticas/programados/FormarReporte", modelo);
  }
}