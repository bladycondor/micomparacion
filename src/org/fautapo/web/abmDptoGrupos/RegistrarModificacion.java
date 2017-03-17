package org.fautapo.web.abmDptoGrupos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class RegistrarModificacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Map modelo = new HashMap();

    int iId_programa = cliente.getInt(request, "id_programa");
    int iId_tipo_evaluacion = cliente.getInt(request, "id_tipo_evaluacion");
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    int iGestion = cliente.getInt(request, "gestion");
    int iPeriodo = cliente.getInt(request, "periodo");
    modelo.put("id_programa", Integer.toString(iId_programa));
    modelo.put("id_prg_plan", Integer.toString(iId_prg_plan));
    modelo.put("id_tipo_evaluacion", Integer.toString(iId_tipo_evaluacion));
    modelo.put("gestion", Integer.toString(iGestion));
    modelo.put("periodo", Integer.toString(iPeriodo));

    int iId_materia = cliente.getInt(request, "id_materia");
    int iId_grupo = cliente.getInt(request, "id_grupo");
    String sCupo_max = cliente.getString(request, "cupo_max");
    String sHoras = cliente.getString(request, "horas");
    String sNro_resolucion = cliente.getString(request, "nro_resolucion");
    String sFec_resolucion = cliente.getString(request, "fec_resolucion");

    //Sacamos los datos de la materia
    Materias materia = new Materias();
    materia.setId_materia(iId_materia);
    materia = this.mi.getMtrBuscarMateria(materia);

    String sql = "UPDATE dpto_grupos SET ";
    sql += "id_grupo="+iId_grupo+", id_materia="+iId_materia+", id_departamento="+materia.getId_departamento()+", id_tipo_evaluacion="+iId_tipo_evaluacion+", gestion="+iGestion+", periodo="+iPeriodo+", ";
    sql += "cupo_max="+sCupo_max+", horas="+sHoras+", nro_resolucion="+sNro_resolucion+", fec_resolucion=_cadena_fecha('"+sFec_resolucion+"'), id_rol="+cliente.getId_rol()+", ult_usuario="+cliente.getId_usuario();
    sql += " WHERE id_dpto_grupo="+cliente.getInt(request, "_yabe");

    Abm tabla = new Abm();
    tabla.setSql(sql);
    try {
      this.mi.setEjecutarConsulta(tabla);
      modelo.put("mensaje", "Los datos se registraron correctamente");
    } catch (Exception e) {
      // No es lo adecuado, pero suma
      String mensajes[] = ((String[]) (e.getCause().getMessage().split("SQLException: ERROR: ")))[1].split("Detail:");

      System.out.println("dibRap - " + mensajes[0]);
      //String problema = "Hubo un error al realizar la transacción en la relación '" + tabla.getTabla() + "'.<br/><br/>" + mensajes[0];
      String problema = mensajes[0];
      if (mensajes.length > 1) {
        System.out.println("dibRap - DETALLE:" + mensajes[1]);
        problema += "<br> DETALLE:" + mensajes[1] + "<hr/>SQL='" + sql + "'";
      }
      return new ModelAndView("Error", "mensaje", problema);
    }

    return new ModelAndView("abmDptoGrupos/Distro", modelo);
  }
}