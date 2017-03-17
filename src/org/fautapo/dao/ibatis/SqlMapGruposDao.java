package org.fautapo.dao.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.GruposDao;
import org.fautapo.domain.Grupos;
import org.fautapo.domain.Materias;

public class SqlMapGruposDao extends SqlMapClientDaoSupport implements GruposDao {

  //Administrar Horarios
  public List getDptoListarGruposMateria(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDptoListarGruposMateria", materia);
  }
  
  public Grupos getDptoBuscarGrupo(Grupos grupo) throws DataAccessException {
    return (Grupos) getSqlMapClientTemplate().queryForObject("getDptoBuscarGrupo", grupo);
  }
  //Fin Administrar horarios
  //Cambio de grupo
  public List getListarEstudiantesGrupos(Grupos grupo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEstudiantesGrupos", grupo);
  }

  public Grupos setCambiarGrupoEstudiante(Grupos grupo) throws DataAccessException {
    return (Grupos) getSqlMapClientTemplate().queryForObject("setCambiarGrupoEstudiante", grupo);
  }
  //Fin Cambio de grupo
 
 //est_programaciones
 public Grupos getGrpBuscarGrupo(Grupos grupo) throws DataAccessException {
    return (Grupos) getSqlMapClientTemplate().queryForObject("getGrpBuscarGrupo", grupo);
  }
 
  public Grupos getDptoBuscarCupoRestanteGrupo(Grupos grupo) throws DataAccessException {
    return (Grupos) getSqlMapClientTemplate().queryForObject("getDptoBuscarCupoRestanteGrupo", grupo);
  } 
 
  //  inicio - GRUPOS jojo
  public List getPrgListarGrupos(Grupos grupo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrgListarGrupos", grupo);
  }

  public List getMtrListarGruposNoAsignados(Grupos grupo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMtrListarGruposNoAsignados", grupo);
  }

  public Grupos getMiDptoBuscarGrupo(Grupos grupo) throws DataAccessException {
    return (Grupos) getSqlMapClientTemplate().queryForObject("getMiDptoBuscarGrupo", grupo);
  } 
  //  fin - GRUPOS jojo
  
  //dct_asignacion
  public List getDptoListarGruposMateriaTipoEvaluacion(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDptoListarGruposMateriaTipoEvaluacion", materia);
  }
  
   public List getDptoListarGruposMateriaTipoEvaluacionDesignado(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDptoListarGruposMateriaTipoEvaluacionDesignado", materia);
  }

//INICIO - METODOS ADICIONADOS POR LA UAP
 //aux_asignacion
  public List getDptoListarGruposMateriaTipoEvaluacionAuxiliares(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDptoListarGruposMateriaTipoEvaluacionAuxiliares", materia);
  }  
//FIN - METODOS ADICIONADOS POR LA UAP

}