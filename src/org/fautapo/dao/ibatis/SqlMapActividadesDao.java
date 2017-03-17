package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.ActividadesDao;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Clientes;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-15
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-17
*/

public class SqlMapActividadesDao extends SqlMapClientDaoSupport implements ActividadesDao {
  
  //Administracion de actividades
  public List getListarActividades(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActividades", actividad);
  }

  public List getListarActividadesNoLimbo(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActividadesNoLimbo", actividad);
  }

  public List getListarActividadesLimbo(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActividadesLimbo", actividad);
  }

  public List getListarTiposAlertasAct(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAlertasAct", actividad);
  }

  public Actividades getBuscarProceso(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarProceso", actividad);
  }

  public List getListarProcesosAcceso(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosAcceso", cliente);
  }

  public List getListarProcesosAccesoTramites(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosAccesoTramites", cliente);
  }

  public List getListarProcesosAccesoTramites2(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosAccesoTramites2", cliente);
  }

  public List getListarProcesosAccesoCorresp(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosAccesoCorresp", cliente);
  }

  public Actividades getBuscarTipoAlerta(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarTipoAlerta", actividad);
  }

  public List getListarTiposAlertas(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposAlertas", actividad);
  }

  public Actividades getBuscarActividad(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarActividad", actividad);
  }

  public Actividades getBuscarActividadOrden(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarActividadOrden", actividad);
  }

  public List getListarUbicacionesOrganicas() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUbicacionesOrganicas", null);
  }

  public Actividades getBuscarUbicacionOrganica(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarUbicacionOrganica", actividad);
  }

  public List getListarTiposActuaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposActuaciones", null);
  }

  public Actividades getBuscarTipoActuacion(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarTipoActuacion", actividad);
  }
 
  public int setRegistrarActividad(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarActividad", actividad);
    return i.intValue();
  }
 
  public int setReiniciarTiposAlertas(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setReiniciarTiposAlertas", actividad);
    return i.intValue();
  }
 
  public int setRegistrarTipoAlerta(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarTipoAlerta", actividad);
    return i.intValue();
  }

  public int setEliminarActividad(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarActividad", actividad);
    return i.intValue();
  }
  //Fin Administracion de actividades
  
  public List getListarTiposProcesos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposProcesos", null);
  }

  //Redireccionar tramites
  public List getListarActividades2(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActividades2", actividad);
  } 
  
  public List getListarUsuariosRolActividad(Actividades actividad) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUsuariosRolActividad", actividad);
  } 
  //Fin Redireccionar tramites

  //Reporte de actividades por roles
  public String getListarActividadesRoles(Actividades actividad) throws DataAccessException {
    String s = (String) getSqlMapClientTemplate().queryForObject("getListarActividadesRoles", actividad);
    return s;
  }
  //Fin Reporte de actividades por roles

  //Tipos duraciones
  public List getListarTiposDuraciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposDuraciones", null);
  }

  public Actividades getBuscarTipoDuracion(Actividades actividad) throws DataAccessException {
    return (Actividades) getSqlMapClientTemplate().queryForObject("getBuscarTipoDuracion", actividad);
  }
  //Fin Tipos duraciones
  
  //Administrar Kardex
   public List getListarProcesosAccesoKardex() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosAccesoKardex", null);
  }
 
  public List getListarProcesosKardexs() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarProcesosKardexs", null);
  }
  
  public int setRegistrarProcesoKardex(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarProcesoKardex", actividad);
    return i.intValue();
  }
  
  public int setModificarProcesoKardex(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setModificarProcesoKardex", actividad);
    return i.intValue();
  }
  
  public int setEliminarProcesoKardex(Actividades actividad) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarProcesoKardex", actividad);
    return i.intValue();
  }
  
  //Fin Administrar Kardex
 
}