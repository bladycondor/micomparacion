package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.MateriasDao;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapMateriasDao extends SqlMapClientDaoSupport implements MateriasDao {
  
  //PLANES
  public List getPlnListarMaterias(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPlnListarMaterias", plan);
  }
  // FIN PLANES
  
  //LIBRETAS
  public Materias getMtrBuscarMateria(Materias materia) throws DataAccessException {
    return (Materias) getSqlMapClientTemplate().queryForObject("getMtrBuscarMateria", materia);
  }
  //FIN LIBRETAS
  
  //EST_PROGRAMACIONEEstIANTE
  public List getEstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstPrgListarProgramacionMateriasAut", materia);
  }
  
  public List getDptoListarMateriaGrupo(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDptoListarMateriaGrupo", materia);
  }
  
  public List getPstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstPrgListarProgramacionMateriasAut", materia);
  }
  // FIN EST_PROGRAMACIONEEstIANTE
  
  //PRORAMACIONES COMO ESTUDIANTE
  public List getEstListarProgramacionMateriasReq(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstListarProgramacionMateriasReq", materia);
  }  
  //FIN PRORAMACIONES COMO ESTUDIANTE
  
  //Programacion automatica
   public Materias getDptoListarMateriaGrupoMinimo(Materias materia) throws DataAccessException {
    return (Materias) getSqlMapClientTemplate().queryForObject("getDptoListarMateriaGrupoMinimo", materia);
  }
  //Fin Programacion automatica
  
  public List getListarMateriasGradoPlanPrograma(Materias materia) throws DataAccessException { 
    return getSqlMapClientTemplate().queryForList("getListarMateriasGradoPlanPrograma", materia);
  }
                    
  //INICIO - Admin Materias
  public List getListarMateriasPorDepartamento(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPorDepartamento", materia);
  }
  
  public List getListarMateriasPorSigla(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPorSigla", materia);  
  }
  
  public List getListarMateriasPorMateria(Materias materia) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasPorMateria", materia);
  }

  public Materias getMtrBuscarTipoMateria(Materias materia) throws DataAccessException { 
    return (Materias) getSqlMapClientTemplate().queryForObject("getMtrBuscarTipoMateria", materia);
  }

  public List getMtrListarTiposMaterias() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getMtrListarTiposMaterias", null);
  }

  public int setRegistrarMateria(Materias materia) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarMateria", materia);
    return i.intValue();
  }

  public int setEliminarMateria(Materias materia) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEliminarMateria", materia);
    return i.intValue();
  }
  //FIN - Admin Materias  
  
}