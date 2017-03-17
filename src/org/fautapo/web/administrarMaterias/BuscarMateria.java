package org.fautapo.web.administrarMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Departamentos;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class BuscarMateria implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    int iId_materia = cliente.getInt(request, "id_materia");
    int iId_departamento = cliente.getInt(request, "id_departamento");
    String sSigla = request.getParameter("sigla");
    String sMateria = request.getParameter("materia");
    String sAccion = request.getParameter("accion");

    modelo.put("id_departamento", Integer.toString(iId_departamento));
    modelo.put("sigla", sSigla);
    modelo.put("materia", sMateria);
    modelo.put("accion", sAccion);

    //Sacamos los datos de la Materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(iId_materia);
    datosMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("datosMateria", datosMateria);

    //Sacamos los datos del Departamento
    Departamentos datosDepartamento = new Departamentos();
    datosDepartamento.setId_departamento(iId_departamento);
    datosDepartamento = this.mi.getDptBuscarDepartamento(datosDepartamento);
    modelo.put("datosDepartamento", datosDepartamento);

    List lTiposMaterias = this.mi.getMtrListarTiposMaterias();
    modelo.put("lTiposMaterias", lTiposMaterias);
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    if ("Modificar".equals(sAccion)) {
      return new ModelAndView("administrarMaterias/ModificarMateria", modelo);
    }
    
    if ("Eliminar".equals(sAccion)) {
      //Sacamos el listado de los tipos_materias
      Materias datosTipoMateria = new Materias();
      datosTipoMateria.setId_tipo_materia(datosMateria.getId_tipo_materia());
      datosTipoMateria = this.mi.getMtrBuscarTipoMateria(datosTipoMateria);
      modelo.put("datosTipoMateria", datosTipoMateria);
      return new ModelAndView("administrarMaterias/EliminarMateria", modelo);
    }
    
    return new ModelAndView("administrarMaterias/NuevaMateria", modelo);
  }
}