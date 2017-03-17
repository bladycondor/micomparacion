package org.fautapo.web.definirEvaluacion;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-06-30
 */

public class ConfirmarDefinicion implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    int iCantidad=0; int iPonderacion=0; int iResultado=0;
    
    int iId_asignacion  = cliente.getInt(request,"id_asignacion");
    String sId_programa        = request.getParameter("id_programa");
    int iId_modelo_ahorro      = cliente.getInt(request, "id_modelo_ahorro");
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_programa", sId_programa);    
    modelo.put("id_asignacion", Integer.toString(iId_asignacion));    
    
    
    //Verificando asignacion
    if(iId_asignacion == 0)
      return new ModelAndView("Error","mensaje","No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
    
    //Buscamos Asignacion Docente
    Asignaciones datosAsignacion = new Asignaciones();
    datosAsignacion.setId_asignacion(iId_asignacion);
    datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
    if(datosAsignacion == null)
      return new ModelAndView("Error","mensaje","No  se existen datos para la asignaci&oacute;n del docente seleccionado.");
    modelo.put("datosAsignacion", datosAsignacion);
    
    
    //Sacamos el programa 
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("id_programa", sId_programa);
    modelo.put("programa", datosPrograma.getPrograma());
    
    //Sacamos datos de la materia
    Materias datosMateria = new Materias();
    datosMateria.setId_materia(datosAsignacion.getId_materia());
    Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
    modelo.put("sigla", buscarMateria.getSigla());
    modelo.put("materia", buscarMateria.getMateria());
    //Verificamos si el departamento de la materia coincide con la asignacion
    if(datosAsignacion.getId_departamento() != buscarMateria.getId_departamento())
      return new ModelAndView("Error", "mensaje","Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
    
    
    //Verificamos si la materia tiene modelos_ahorros
    if (datosAsignacion.getId_modelo_ahorro() > 0) {
      Asignaciones datos = new Asignaciones();
      datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
      datos.setId_materia(datosAsignacion.getId_materia());
      datos.setId_programa(Integer.parseInt(sId_programa));
      datos.setGestion(datosAsignacion.getGestion());
      datos.setPeriodo(datosAsignacion.getPeriodo());
      List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
      Asignaciones aux = new Asignaciones();
      for (int i = 0; i < materiaAhorro.size(); i++){
        aux = (Asignaciones)materiaAhorro.get(i);
        modelo.put("materia_ahorro", aux.getModelo_ahorro());
      }
    }

    //Sacamos la fase actual
    Libretas datosFase = new Libretas();
    datosFase.setId_fase(datosAsignacion.getId_fase());
    datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosFase.setId_departamento(datosAsignacion.getId_departamento());
    datosFase.setGestion(datosAsignacion.getGestion());
    datosFase.setPeriodo(datosAsignacion.getPeriodo());
    datosFase = this.mi.getLbrBuscarFase(datosFase);
    modelo.put("fase", datosFase.getFase());
    

    //Verificamos si la definicion de evaluacion suma el 100%
    Libretas datosTiposNotas = new Libretas();
    datosTiposNotas.setGestion(datosAsignacion.getGestion());
    datosTiposNotas.setPeriodo(datosAsignacion.getPeriodo());
    datosTiposNotas.setId_materia(datosAsignacion.getId_materia());
    datosTiposNotas.setId_fase(datosAsignacion.getId_fase());
    datosTiposNotas.setId_grupo(datosAsignacion.getId_grupo());
    datosTiposNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosTiposNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datosTiposNotas.setId_departamento(datosAsignacion.getId_departamento());
    List lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);
    modelo.put("lTiposNotas",lTiposNotas);
    
    for (int i=0; i<lTiposNotas.size(); i++) {
      Libretas datosAux = (Libretas) lTiposNotas.get(i);
      iCantidad += cliente.getInt(request,"cantidad:"+datosAux.getId_tipo_nota());
      iPonderacion += cliente.getInt(request,"ponderacion:"+datosAux.getId_tipo_nota());
      
      if ((iCantidad ==0)&&(iPonderacion > 0)) {
	return new ModelAndView("Aviso", "mensaje", "Debe ingresar la cantidad del item para la ponderaci&oacute;n asignada.");
      }
    }
    if (iCantidad == 0) {
      return new ModelAndView("Aviso", "mensaje", "Debe ingresar una cantidad a ser evaluada para el item mayor o igual a 1.");
    }
    
    if ((iPonderacion < 100) ||( iPonderacion >100) ||(iPonderacion == 0)) {
      return new ModelAndView("Aviso", "mensaje", "La ponderaci&oacute;n debe sumar un valor de 100 %.");
    }
    
    //Si todo OK. Primero verificamos si podemos anular la definicion de evaluacion
    for (int i=0; i<lTiposNotas.size(); i++) {
      Libretas datosAux = (Libretas) lTiposNotas.get(i);
      iCantidad  = cliente.getInt(request,"cantidad:"+datosAux.getId_tipo_nota());
      iPonderacion = cliente.getInt(request,"ponderacion:"+datosAux.getId_tipo_nota());
      System.out.println("LA ICANTIDAD -->"+i+"---"+Integer.toString(iCantidad));
      System.out.println("LA iPonderacion -->"+i+"---"+Integer.toString(iPonderacion));
      //Primero anulamos
      if ((iCantidad == 0) && (iPonderacion == 0)) {
        //Eliminamos la definicion de evaluacion
        Libretas datosEvaluacion = new Libretas();
        datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
	System.out.println("LA ID_MATERIA -->"+Integer.toString(datosEvaluacion.getId_materia()));
        datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
        datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());	 
        datosEvaluacion.setId_tipo_nota(datosAux.getId_tipo_nota());
	datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
	datosEvaluacion.setId_departamento(datosAsignacion.getId_departamento());
	datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	datosEvaluacion.setGestion(datosAsignacion.getGestion());
	datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
	datosEvaluacion.setId_rol(cliente.getId_rol());	   //CRCB
	datosEvaluacion.setUlt_usuario(cliente.getId_usuario());
	
	System.out.println("LA id_grupo -->"+Integer.toString(datosEvaluacion.getId_grupo()));
	System.out.println("LA id_modelo_ahorro -->"+Integer.toString(datosEvaluacion.getId_modelo_ahorro()));
	System.out.println("LA id_tipo_nota -->"+Integer.toString(datosEvaluacion.getId_tipo_nota()));
	System.out.println("LA id_fase -->"+Integer.toString(datosEvaluacion.getId_fase()));
	System.out.println("LA id_departamento -->"+Integer.toString(datosEvaluacion.getId_departamento()));
	System.out.println("LA id_tipo_evaluacion -->"+Integer.toString(datosEvaluacion.getId_tipo_evaluacion()));
	System.out.println("LA gestion -->"+Integer.toString(datosEvaluacion.getGestion()));
	System.out.println("EL periodo -->"+Integer.toString(datosEvaluacion.getPeriodo()));
	System.out.println("EL id_rol -->"+Integer.toString(datosEvaluacion.getId_rol()));
	System.out.println("EL Ult_usuario -->"+Integer.toString(datosEvaluacion.getUlt_usuario()));
        iResultado = this.mi.setGrpEliminarEvaluacion(datosEvaluacion);
	if (iResultado != 1) {
          modelo.put("mensaje", "No se podr&aacute; anular la definici&oacute;n donde modifica CANTIDAD y PONDERACION = 0.<br>  Por que la definici&oacute;n cuenta con registros de  notas en libretas.");
          return new ModelAndView("definirEvaluacion/Error", modelo);
	}
      }
    }  
      
    //Luego Registramos
    //Registramos la definicion de evaluacion
    for (int i=0; i<lTiposNotas.size(); i++) {
      Libretas datosAux1 = (Libretas) lTiposNotas.get(i);
      iCantidad  = cliente.getInt(request,"cantidad:"+datosAux1.getId_tipo_nota());
      iPonderacion = cliente.getInt(request,"ponderacion:"+datosAux1.getId_tipo_nota());
      if ((iCantidad > 0) && (iPonderacion >0)) {
        //En caso de que existe actualizamos la definicion de evaluacion
        Libretas datosEvaluacion = new Libretas();
        datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
        datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
        datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());	 
        datosEvaluacion.setId_tipo_nota(datosAux1.getId_tipo_nota()); 
	datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
	datosEvaluacion.setId_departamento(datosAsignacion.getId_departamento());
	datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
	datosEvaluacion.setId_tipo_docente(datosAsignacion.getId_tipo_docente());
	datosEvaluacion.setGestion(datosAsignacion.getGestion());
	datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
	datosEvaluacion.setCantidad(iCantidad);
	datosEvaluacion.setPonderacion(iPonderacion);
	datosEvaluacion.setId_rol(cliente.getId_rol());	   //CRCB
	datosEvaluacion.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setGrpRegistrarEvaluacion(datosEvaluacion);
	if (iResultado != 1) {
          modelo.put("mensaje", "Ocurri&oacute; un error. No se pudo registrar la definici&oacute;n de evaluaci&oacute;n.");
          return new ModelAndView("definirEvaluacion/Error", modelo);
	}
      }
    }
    
    //Sacamos la definicion actual de la evaluacion
    Libretas datosDefinicion = new Libretas();
    datosDefinicion.setGestion(datosAsignacion.getGestion());
    datosDefinicion.setPeriodo(datosAsignacion.getPeriodo());
    datosDefinicion.setId_materia(datosAsignacion.getId_materia());
    datosDefinicion.setId_fase(datosAsignacion.getId_fase());
    datosDefinicion.setId_grupo(datosAsignacion.getId_grupo());
    datosDefinicion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
    datosDefinicion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
    datosDefinicion.setId_departamento(datosAsignacion.getId_departamento());
    List lTiposNotasActual = this.mi.getLbrListarTiposNotas(datosDefinicion);
    modelo.put("lTiposNotas",lTiposNotasActual);
    
    return new ModelAndView("definirEvaluacion/RegistrarDefinicion", modelo);
  }
}
