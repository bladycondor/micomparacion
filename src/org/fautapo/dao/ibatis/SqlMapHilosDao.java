package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.HilosDao;
import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

import org.fautapo.dao.HilosDao;
import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;

public class SqlMapHilosDao extends SqlMapClientDaoSupport implements HilosDao {


  public List getListarUsuariosHilos(Usuarios usuario) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUsuariosHilos", usuario);
  }
  
  public List getListarTiposHilos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposHilos", null);
  }
  
  public int setRegistrarHilo(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarHilo", hilo);
    return i.intValue();
  }

  public List getListarTiposSegmentos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposSegmentos", null);
  }

  public List getListarSegmentos(Hilos hilo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarSegmentos", hilo);
  }

  public List getListarDestinatarios(Hilos hilo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDestinatarios", hilo);
  }

  public int setRegistrarSegmento(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarSegmento", hilo);
    return i.intValue();
  }

  public int setRegistrarSgmAdjunto(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarSgmAdjunto", hilo);
    return i.intValue();
  }

  public List getListarAdjuntosHilos(Hilos hilo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAdjuntosHilos", hilo);
  }

  public Hilos getBuscarHilo(Hilos hilo) throws DataAccessException {
    return (Hilos) getSqlMapClientTemplate().queryForObject("getBuscarHilo", hilo);
  }

  public List getListarHilosMios(Hilos hilo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarHilosMios", hilo);
  }

  public List getListarHilosAMi(Hilos hilo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarHilosAMi", hilo);
  }

  public int getNroMensajes(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getNroMensajes", hilo);
    return i.intValue();
  }

  public int getNroMensajesNuevos(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getNroMensajesNuevos", hilo);
    return i.intValue();
  }

  public int setBorrarHilo(Hilos hilo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setBorrarHilo", hilo);
    return i.intValue();
  }

}

