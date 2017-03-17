/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.estadisticas.notasEstudiante;

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

    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_estudiante(cliente.getInt(request, "id_estudiante"));
    estudiante = this.mi.getEstBuscarEstudiantePrs(estudiante);

    // Parametros de entrada
    //modelo.put("fec_comprobantei", f.sRequest("fec_comprobantei"));
    //modelo.put("fec_comprobantef", f.sRequest("fec_comprobantef"));

    // Definicion de la consulta SQL
    sql += "\n SELECT * FROM mi_rep_notas_estudiante(" +estudiante.getId_programa()+ ", '" +estudiante.getId_plan()+ "', " +estudiante.getId_estudiante()+ ", " +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ")";
    sql += "\n AS (nivel_academico dentero, materia dtexto, nro_nota dentero, tipo_nota dtexto, nota dnota2)";

    System.out.println("\n\n--Listar Transacciones-->\n" + sql + ";\n\n\n");

    //definicion de las etiquetas
    String etiquetas="Nivel Academico###Materia###Nro. de Nota###Calificacion###Nota";

    //Generacion de la matriz
    f.matriz_etiquetas(sql, "datos", 0, "desde", 4, etiquetas);
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

    modelo.put("titulo", "Notas del Estudiante");
    modelo.put("periodo", f.sRequest("periodo"));
    modelo.put("gestion", f.sRequest("gestion"));
    Programas programa = new Programas();
    programa.setId_programa(estudiante.getId_programa());
    programa = this.mi.getPrgBuscarPrograma(programa);
    modelo.put("programa", programa);
    return new ModelAndView("reportesEspecializados/estadisticas/notasEstudiante/FormarReporte", modelo);
  }
}