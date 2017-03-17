package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.AdjuntosDao;
import org.fautapo.domain.Adjuntos;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class SqlMapAdjuntosDao extends SqlMapClientDaoSupport implements AdjuntosDao {

  public int setRegistrarAdjunto(Adjuntos adjunto) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAdjunto", adjunto);
    return i.intValue();
  }

  public List getListarAdjuntos(Adjuntos adjunto) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarAdjuntos", adjunto);
  }

}