package org.fautapo.web.administrarMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Departamentos;
import org.fautapo.domain.Materias;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarMaterias implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    int iId_departamento = cliente.getInt(request, "id_departamento");
    String sSigla = request.getParameter("sigla");
    String sMateria = request.getParameter("materia");
    String sBoton = request.getParameter("boton");

    modelo.put("id_departamento", Integer.toString(iId_departamento));
    modelo.put("sigla", sSigla);
    modelo.put("materia", sMateria);
    modelo.put("boton", sBoton);

    if ((iId_departamento>0) && ("Buscar".equals(sBoton))) {
      //Sacamos los datos del Departamento
      Departamentos datosDepartamento = new Departamentos();
      datosDepartamento.setId_departamento(iId_departamento);
      datosDepartamento = this.mi.getDptBuscarDepartamento(datosDepartamento);
      modelo.put("datosDepartamento", datosDepartamento);
      
      //Listamos las materias del departamento
      Materias datosMateria = new Materias();
      datosMateria.setId_departamento(iId_departamento);
      List lMaterias = this.mi.getListarMateriasPorDepartamento(datosMateria);
      modelo.put("lMaterias", lMaterias);
      return new ModelAndView("administrarMaterias/ListarMaterias", modelo);
    }
    
    if ((!"".equals(sSigla)) && ("Buscar - Sigla".equals(sBoton))) {
      Materias datosMateria = new Materias();
      datosMateria.setSigla(sSigla);
      datosMateria.setId_universidad(cliente.getId_universidad());
      datosMateria.setId_departamento(cliente.getId_departamento());
      List lMaterias = this.mi.getListarMateriasPorSigla(datosMateria);
      modelo.put("lMaterias", lMaterias);
    }

    if ((!"".equals(sMateria)) && ("Buscar - Materia".equals(sBoton))) {
      Materias datosMateria = new Materias();
      datosMateria.setMateria(sMateria);
      datosMateria.setId_universidad(cliente.getId_universidad());
      datosMateria.setId_departamento(cliente.getId_departamento());
      List lMaterias = this.mi.getListarMateriasPorMateria(datosMateria);
      modelo.put("lMaterias", lMaterias);
    }
    return new ModelAndView("administrarMaterias/ListarMaterias", modelo);
  }
}