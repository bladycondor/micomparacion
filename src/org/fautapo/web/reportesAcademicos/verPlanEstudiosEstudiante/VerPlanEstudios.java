package org.fautapo.web.reportesAcademicos.verPlanEstudiosEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Estudiantes;
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

    int iMax = 0; int iVal = 0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    // Comprobamos es quien debe, de acuerdo a su clave
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante.setClave(request.getParameter("clave"+request.getParameter("hora")));

    if (null == this.mi.getComprobarEstudiante(datosEstudiante)) {
      return new ModelAndView("reportesAcademicos/verFichaAcademicaEstudiante/Entrada", "cliente", cliente);
    }
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));
    
    //Sacamos los datos del Estudiante
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(cliente.getId_usuario());
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);

    //Sacamos los datos del Programa
    Programas datosPrograma = new Programas();
    datosPrograma.setId_programa(datosEstudiante.getId_programa());
    datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
    modelo.put("datosPrograma", datosPrograma);

    //try {
    Planes pDatos = new Planes();
    pDatos.setId_plan(datosEstudiante.getId_plan());
    pDatos.setId_programa(datosEstudiante.getId_programa());
    
    int nivel_academico = this.mi.getPlnListarNroNiveles(pDatos);
    List lMateriasNivel = this.mi.getPlnListarMateriasNivel(pDatos);  // nivel_academico, numero
    List lMateriasRequisitos = this.mi.getPlnListarMateriasRequisitos(pDatos); // id_materia, id_materia_ant
    List lMateriasNroRequisitos = this.mi.getPlnListarMateriasNroRequisitos(pDatos);// id_materia, numero
    List lMaterias = this.mi.getPlnListarMaterias(pDatos); //id_materia, materia, sigla

    for(int i = 0; i < nivel_academico; i++) {
      if (iMax < ((Planes)lMateriasNivel.get(i)).getNumero())
        iMax = ((Planes)lMateriasNivel.get(i)).getNumero();
    }
    
    Materias materias[][] = new Materias[iMax][nivel_academico];
    for(int i = 0; i < nivel_academico; i++) {
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
    
    return new ModelAndView("reportesAcademicos/verPlanEstudiosEstudiante/VerPlanEstudios", modelo);
    //}
    //catch (Exception e) {
      //return new ModelAndView("Aviso", "mensaje", "No se registro correctamente el Plan de estudios");
    //}
  }
}