package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.DominiosDao;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-22
*/

public class SqlMapDominiosDao extends SqlMapClientDaoSupport implements DominiosDao {

  //Administracion de dominios
  public List getListarDominios() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDominios", null);
  }

  public List getListarDominiosAcceso(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDominiosAcceso", cliente);
  }

  public List getListarTiposDominios() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposDominios", null);
  }

  public Dominios getBuscarDominio(Dominios dominio) throws DataAccessException {
    return (Dominios) getSqlMapClientTemplate().queryForObject("getBuscarDominio", dominio);
  }
  
  public Dominios getBuscarTipoDominio(Dominios dominio) throws DataAccessException {
    return (Dominios) getSqlMapClientTemplate().queryForObject("getBuscarTipoDominio", dominio);
  }
  
  public int setRegistrarDominio(Dominios dominio) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarDominio", dominio);
    return i.intValue();
  }
 
  public int setEliminarDominio(Dominios dominio) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarDominio", dominio);
    return i.intValue();
  }
  
  public int getBuscarDominioOtrasTb(Dominios dominio) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getBuscarDominioOtrasTb", dominio);
    return i.intValue();
  }
  //Fin Administracion de dominios
  
  //Administracion de tuplas
  public List getListarTuplas(Dominios dominio) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTuplas", dominio);
  }

  public List getListarTuplasPadre(Dominios dominio) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTuplasPadre", dominio);
  }

  public Dominios getBuscarTupla(Dominios dominio) throws DataAccessException {
    return (Dominios) getSqlMapClientTemplate().queryForObject("getBuscarTupla", dominio);
  }

  public int setRegistrarTupla(Dominios dominio) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarTupla", dominio);
    return i.intValue();
  }
 
  public int setEliminarTupla(Dominios dominio) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarTupla", dominio);
    return i.intValue();
  }
  //Fin Administracion de tuplas
  
  //Administracion de tramites
  public int getBuscarTieneHijos(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getBuscarTieneHijos", tramite);
    return i.intValue();
  }

  public List getListarCombos2(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCombos2", tramite);
  }

  public int getBuscarTuplaPadre(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getBuscarTuplaPadre", tramite);
    return i.intValue();
  }

  public Dominios getBuscarTupla2(Dominios dominio) throws DataAccessException {
    return (Dominios) getSqlMapClientTemplate().queryForObject("getBuscarTupla2", dominio);
  }

  public int setRegistrarTempTupla(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarTempTupla", tramite);
    return i.intValue();
  }

  public int setLimpiarTempTuplas() throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setLimpiarTempTuplas", null);
    return i.intValue();
  }
  //Fin Administracion de tramites
 
}