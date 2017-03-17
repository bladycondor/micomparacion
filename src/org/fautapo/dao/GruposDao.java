package org.fautapo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Materias;

public interface GruposDao {

  //Administrar Horarios
  List getDptoListarGruposMateria(Materias materia) throws DataAccessException;
  Grupos getDptoBuscarGrupo(Grupos grupo) throws DataAccessException;
  //Fin Administrar Horarios

  //Cambio de grupos
  List getListarEstudiantesGrupos(Grupos grupo) throws DataAccessException;
  Grupos setCambiarGrupoEstudiante(Grupos grupo) throws DataAccessException;  
  //Fin Cambio de grupos

  //est_programaciones
  Grupos getGrpBuscarGrupo(Grupos grupo) throws DataAccessException;  
  Grupos getDptoBuscarCupoRestanteGrupo(Grupos grupo) throws DataAccessException;  

  //  inicio - GRUPO jojo
  List getPrgListarGrupos(Grupos grupo) throws DataAccessException;
  List getMtrListarGruposNoAsignados(Grupos grupo) throws DataAccessException;
  Grupos getMiDptoBuscarGrupo(Grupos grupo) throws DataAccessException;
  //  fin - GRUPO jojo
  //dct_asignacion
  List getDptoListarGruposMateriaTipoEvaluacion(Materias materia) throws DataAccessException;
 List getDptoListarGruposMateriaTipoEvaluacionDesignado(Materias materia) throws DataAccessException;
 
//INICIO - METODOS ADICIONADOS POR LA UAP
  //aux_asignacion
  List getDptoListarGruposMateriaTipoEvaluacionAuxiliares(Materias materia) throws DataAccessException;  
//FIN - METODOS ADICIONADOS POR LA UAP

}
