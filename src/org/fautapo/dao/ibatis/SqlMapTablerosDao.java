package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.TablerosDao;
import org.fautapo.domain.Tableros;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class SqlMapTablerosDao extends SqlMapClientDaoSupport implements TablerosDao {

  public List getListarNoticias() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNoticias", null);
  }
  
  public List getListarTiposTableros() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposTableros", null);
  }
 
  public List getListarTiposAvisos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAvisos", null);
  } 

  public int setRegistrarTablero(Tableros tablero) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarTablero", tablero);
    return i.intValue();
  }
 
  public Tableros getBuscarTablero(Tableros tablero) throws DataAccessException {
    return (Tableros) getSqlMapClientTemplate().queryForObject("getBuscarTablero", tablero);
  } 

  public int setEliminarTablero(Tableros tablero) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarTablero", tablero);
    return i.intValue();
  }
 
  // INICIO - MI 
  public List getListarNoticiasRol(Tableros tablero) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNoticiasRol", tablero);
  }

  public List getListarRolesNoticias() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarRolesNoticias", null);
  }
  // FIN - MI 

}