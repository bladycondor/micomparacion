package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.InformesDao;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Tramites;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class SqlMapInformesDao extends SqlMapClientDaoSupport implements InformesDao {

  //Administracion de informes
  public List getListarInformes(Informes informe) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarInformes", informe);
  }

  public Informes getBuscarInforme(Informes informe) throws DataAccessException {
    return (Informes) getSqlMapClientTemplate().queryForObject("getBuscarInforme", informe);
  }

  public int setRegistrarInforme(Informes informe) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarInforme", informe);
    return i.intValue();
  }
 
  public int setEliminarInforme(Informes informe) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarInforme", informe);
    return i.intValue();
  }
  //Fin Administracion de informes
 
  //Administracion de tramites
  public List getListarInformesActividad(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarInformesActividad", tramite);
  }

  public Informes getBuscarInforme2(Informes informe) throws DataAccessException {
    return (Informes) getSqlMapClientTemplate().queryForObject("getBuscarInforme2", informe);
  }
  //Fin Administracion de tramites

}