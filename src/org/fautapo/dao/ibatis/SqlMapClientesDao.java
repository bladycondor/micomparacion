package org.fautapo.dao.ibatis;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.ClientesDao;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Instituciones;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapClientesDao extends SqlMapClientDaoSupport implements ClientesDao {

  public Clientes getBuscarConexion(Usuarios usuario) throws DataAccessException {
    return (Clientes) getSqlMapClientTemplate().queryForObject("getBuscarConexion", usuario);
  }

  public Clientes getComprobarUsuario(Usuarios usuario) throws DataAccessException {
    return (Clientes) getSqlMapClientTemplate().queryForObject("getComprobarUsuario", usuario);
  }
  
   public Clientes getComprobarUsuSede(Usuarios usuario) throws DataAccessException {
    return (Clientes) getSqlMapClientTemplate().queryForObject("getComprobarUsuSede", usuario);
  }


  public Instituciones getBuscarInstitucion(Instituciones institucion) throws DataAccessException {
    return (Instituciones) getSqlMapClientTemplate().queryForObject("getBuscarInstitucion", institucion);
  }

  public Instituciones getBuscarInstitucionSede(Instituciones institucion) throws DataAccessException {
    return (Instituciones) getSqlMapClientTemplate().queryForObject("getBuscarInstitucionSede", institucion);
  }

  public String getFechaCadena(Clientes cliente) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getFechaCadena", cliente);
  }

  public String getCadenaFecha(Clientes cliente) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getCadenaFecha", cliente);
  }

  public Integer getUsrBuscarIp(Clientes cliente) throws DataAccessException {
    return (Integer) getSqlMapClientTemplate().queryForObject("getUsrBuscarIp", cliente);
  }
}