package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.DocentesDao;
import org.fautapo.domain.Docentes;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-10
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-10
*/

public class SqlMapDocentesDao extends SqlMapClientDaoSupport implements DocentesDao {

  public Docentes getComprobarDocente(Docentes docente) throws DataAccessException {
    return (Docentes) getSqlMapClientTemplate().queryForObject("getComprobarDocente", docente);
  }
  
  public Docentes getBuscarDocente(Docentes docente) throws DataAccessException {
    return (Docentes) getSqlMapClientTemplate().queryForObject("getBuscarDocente", docente);
  }
   public Docentes getBuscarDocentexdepartamento(Docentes docente) throws DataAccessException {
    return (Docentes) getSqlMapClientTemplate().queryForObject("getBuscarDocentexdepartamento", docente);
  }
  
  public int setCambioPinDocente(Docentes docente) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setCambioPinDocente", docente);
    return i.intValue();
  } 
  
  public List getBuscarListaDocentesNombres(Docentes docente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getBuscarListaDocentesNombres", docente);
  }
  
  public List getBuscarListaDocentesDip(Docentes docente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getBuscarListaDocentesDip", docente);
  }
  
  public List getListarTiposDocentes() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposDocentes", null);
  }
 
  public List getListarTiposAsignaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAsignaciones", null);
  }
  
  public List getListarTiposFunciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposFunciones", null);
  }
 
  //Docentes Todos 
  public List getListarDocentesTodos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDocentesTodos", null);
  }
 
  //INICIO - Admin. Docente
  public int setRegistrarDocente(Docentes docente) {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarDocente", docente);
    return i.intValue();
  }
  
  public int setEliminarDocente(Docentes docente) {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarDocente", docente);
    return i.intValue();
  }
  //FIN - Admin. Docente  
  //Inicio Cambio PIN Docente General
  public int setModificarApodoClaveDocente(Docentes docente) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setModificarApodoClaveDocente", docente);
    return i.intValue();
  }
  //Fin Cambio PIN Docente General
 

//INICIO - METODOS ADICIONADOS POR LA UAP
  public List getListarDocentesPorDpto(Docentes docente) throws DataAccessException{
       return  getSqlMapClientTemplate().queryForList("getListarDocentesPorDpto",docente);
  }

  //Auxiliares Todos 
  public List getListarAuxiliaresTodos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAuxiliaresTodos", null);
  }

  public Docentes getBuscarAuxiliar(Docentes docente) throws DataAccessException {
    return (Docentes) getSqlMapClientTemplate().queryForObject("getBuscarAuxiliar", docente);
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}