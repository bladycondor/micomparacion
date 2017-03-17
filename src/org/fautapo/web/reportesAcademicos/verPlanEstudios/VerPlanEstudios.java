package org.fautapo.web.reportesAcademicos.verPlanEstudios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Planes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class VerPlanEstudios implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    String _nombres = cliente.getNombres();
    
    String sId_facultad = request.getParameter("id_facultad");
    String sId_programa = request.getParameter("id_programa");
    String sId_plan = request.getParameter("id_plan");

    modelo.put("id_facultad", sId_facultad);
    modelo.put("id_programa", sId_programa);
    modelo.put("id_plan", sId_plan);

    //Sacamos los datos de la Facultad
    Facultades datosFacultad = new Facultades();
    datosFacultad.setId_facultad(Integer.parseInt(sId_facultad));
    datosFacultad = this.mi.getFclBuscarFacultad(datosFacultad);
    modelo.put("datosFacultad", datosFacultad);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(Integer.parseInt(sId_programa));
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    System.out.println("id_plan:"+sId_plan);
    System.out.println("id_programa:"+sId_programa);

    //try {
    Planes pDatos = new Planes();
    pDatos.setId_plan(sId_plan);
    pDatos.setId_programa(Integer.parseInt(sId_programa));
    
    int nivel_academico = this.mi.getPlnListarNroNiveles(pDatos);
    List lMateriasNivel = this.mi.getPlnListarMateriasNivel(pDatos);  // nivel_academico, numero
    List lMateriasRequisitos = this.mi.getPlnListarMateriasRequisitos(pDatos); // id_materia, id_materia_ant
    List lMateriasNroRequisitos = this.mi.getPlnListarMateriasNroRequisitos(pDatos);// id_materia, numero
    List lMaterias = this.mi.getPlnListarMaterias(pDatos); //id_materia, materia, sigla

    int iMax = 0;
    int iVal = 0;

    for(int i = 0; i < nivel_academico; i++) {
      if (iMax < ((Planes)lMateriasNivel.get(i)).getNumero())
        iMax = ((Planes)lMateriasNivel.get(i)).getNumero();
    }
    
    Materias materias[][] = new Materias[iMax][nivel_academico];

    for(int i = 0; i < nivel_academico; i++){
      int iPos = 0;
      for (int j = iVal; j < iVal + ((Planes)lMateriasNivel.get(i)).getNumero(); j++){
        materias[iPos][i] = (Materias) lMaterias.get(j);
	iPos++;
      }
      iVal += ((Planes)lMateriasNivel.get(i)).getNumero();
      Materias mAux = new Materias();
      for (int j = ((Planes)lMateriasNivel.get(i)).getNumero(); j < iMax; j++){
        mAux.setId_materia(0);
	mAux.setMateria("");
	mAux.setSigla("");
        materias[iPos][i] = mAux;
	iPos++;
      }
    }

    modelo.put("lMateriasRequisitos",lMateriasRequisitos);
    modelo.put("lMateriasNroRequisitos", lMateriasNroRequisitos);
    modelo.put("lMateriasNroRequisitos", lMateriasNroRequisitos);
    modelo.put("nivel_academico", Integer.toString(nivel_academico));
    modelo.put("materias", materias);
    
    return new ModelAndView("reportesAcademicos/verPlanEstudios/VerPlanEstudios", modelo);
    //}
    //catch (Exception e) {
      //return new ModelAndView("Aviso", "mensaje", "No se registro correctamente el Plan de estudios");
    //}
  }
}