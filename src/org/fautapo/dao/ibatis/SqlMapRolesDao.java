package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.RolesDao;
import org.fautapo.domain.Roles;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapRolesDao extends SqlMapClientDaoSupport implements RolesDao {

  public Roles getBuscarRol(Roles rol) throws DataAccessException {
    return (Roles) getSqlMapClientTemplate().queryForObject("getBuscarRol", rol);
  }

  public List getListarRoles() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarRoles", null);
  }

  public List getListarRolesCliente(Roles rol) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarRolesCliente", rol);
  }

  public Roles getBuscarRolCliente(Roles rol) throws DataAccessException {
    return (Roles) getSqlMapClientTemplate().queryForObject("getBuscarRolCliente", rol);
  }

  public List getListarAlmacenesCliente(Roles rol) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAlmacenesCliente", rol);
  }

  public Roles getBuscarAlmacenCliente(Roles rol) throws DataAccessException {
    return (Roles) getSqlMapClientTemplate().queryForObject("getBuscarAlmacenCliente", rol);
  }

}