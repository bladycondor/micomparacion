package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.UsuariosDao;
import org.fautapo.domain.Usuarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-01
*/

public class SqlMapUsuariosDao extends SqlMapClientDaoSupport implements UsuariosDao {

  public Usuarios getBuscarUsuario(Usuarios usuario) throws DataAccessException {
    return (Usuarios) getSqlMapClientTemplate().queryForObject("getBuscarUsuario", usuario);
  }  

  public List getListarUsuarios(Usuarios usuario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUsuarios", usuario);
  }

  public List getListarUsuariosUbicacionOrganica(Usuarios usuario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUsuariosUbicacionOrganica", usuario);
  }
  

  public int getVerificarUsuario(Usuarios usuario) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getVerificarUsuario", usuario);
    return i.intValue();
  }
  
  public int setRegistrarNuevaClave(Usuarios usuario) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarNuevaClave", usuario);
    return i.intValue();
  }
  
  public int setRegistrarUsuario(Usuarios usuario) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarUsuario", usuario);
    return i.intValue();
  }
  
  public int setEliminarUsuario(Usuarios usuario) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarUsuario", usuario);
    return i.intValue();
  }
  
  
}