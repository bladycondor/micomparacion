/****************************************
 @usuario          :: Luis Jordan
 @fec_registro     :: 2007-06-26
 @ult_usuario      :: Jorge Copa
 @fec_modificacion :: 2007-11-13
*****************************************/
package org.fautapo.web.reportesEspecializados.estadisticas.postulantesAprobados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.fautapo.domain.*;

public class FormarReporte implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public String[][] armarMatriz(String sql) {
    Abm abm = new Abm();
    abm.setSql(sql);
    List valores = this.mi.getEjecutarListado(abm);
    String datos[][] = null;
    int nro_datos = valores.size();
    if (nro_datos > 0) {
      abm = (Abm) valores.get(0);
//      int nro_campos = abm.getValores().split("#~~#").length;
      int nro_campos = abm.getValores().split(",").length;
      datos = new String[nro_datos][nro_campos];
      for (int j = 0; j < nro_datos; j++) {
        Abm fila = (Abm) valores.get(j);
//        String campos[] = fila.getValores().split("#~~#");  // Separador de campos
        String campos[] = fila.getValores().split(",");  // Separador de campos
        for (int i = 0; i < nro_campos; i++)
          datos[j][i] = campos[i];
      }
    }
    return datos;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //Declaracion de Variables
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion termino, debe volver a la pagina inicial e ingresar de nuevo.");
    Map modelo = new HashMap();
    Funciones f = new Funciones(request, modelo, mi);

    modelo.put("lSexos", this.mi.getListarTiposSexos());

    String sql="\n SELECT * FROM mi_rep_pst_aprobados_sexo_nacionalidad(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ", " +f.sRequest("id_tipo_admision")+ ")";
    sql += "\n AS (programa dtexto, id_plan dtexto, pais dtexto, tipo_sexo dtexto, cantidad bigint)";
    modelo.put("datos", armarMatriz(sql));

    sql =  "\n SELECT * FROM mi_rep_pst_paises_aprobados(" +f.sRequest("gestion")+ ", " +f.sRequest("periodo")+ ", " +f.sRequest("id_tipo_admision")+ ")";
    sql += "\n AS (id_pais integer, pais dtexto, nacionalidad dtexto2)";
    modelo.put("cabeza", armarMatriz(sql));

    Estudiantes estudiante = new Estudiantes();
    estudiante.setId_tipo_admision(cliente.getInt(request, "id_tipo_admision"));
    modelo.put("admision", this.mi.getBuscarTipoAdmision(estudiante));

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

    modelo.put("titulo", "Postulantes Aprobados por Sexo y Nacionalidad");
    modelo.put("periodo", f.sRequest("periodo"));
    modelo.put("gestion", f.sRequest("gestion"));
    return new ModelAndView("reportesEspecializados/estadisticas/postulantesAprobados/FormarReporte", modelo);
  }
}