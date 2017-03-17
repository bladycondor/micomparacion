package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.CamposDao;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Clientes;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class SqlMapCamposDao extends SqlMapClientDaoSupport implements CamposDao {

  //Administracion de campos
  public List getListarFormularios() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarFormularios", null);
  }

  public List getListarFormulariosAcceso(Clientes cliente) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarFormulariosAcceso", cliente);
  }

  public List getListarCampos(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCampos", campo);
  }

  public Campos getBuscarFormulario(Campos campo) throws DataAccessException {
    return (Campos) getSqlMapClientTemplate().queryForObject("getBuscarFormulario", campo);
  }

  public Campos getBuscarCampoForm(Campos campo) throws DataAccessException {
    return (Campos) getSqlMapClientTemplate().queryForObject("getBuscarCampoForm", campo);
  }

  public List getListarTiposValidaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposValidaciones", null);
  }

  public Campos getBuscarTipoValidacion(Campos campo) throws DataAccessException {
    return (Campos) getSqlMapClientTemplate().queryForObject("getBuscarTipoValidacion", campo);
  }

  public int setRegistrarCampo(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarCampo", campo);
    return i.intValue();
  }
 
  public int setEliminarCampo(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarCampo", campo);
    return i.intValue();
  }
  //Fin Administracion de campos

  //Administracion de acl
  public Campos getBuscarFormulario1(Campos campo) throws DataAccessException {
    return (Campos) getSqlMapClientTemplate().queryForObject("getBuscarFormulario1", campo);
  }

  public List getListarTiposPermisos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTiposPermisos", null);
  }

  public List getListarCamposAcl(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposAcl", campo);
  }

  public int setRegistrarAcl(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAcl", campo);
    return i.intValue();
  }
 
  public int setEliminarAcl(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarAcl", campo);
    return i.intValue();
  }
  //Fin Administracion de acl

  //Administración de Reportes
  public List getListarCamposProceso(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposProceso", campo);
  }

  public List getListarCamposReporte(Campos campo) throws DataAccessException {
    System.out.println("cadena  "+campo.getCadena());
    System.out.println("campos  "+campo.getCampos());
    System.out.println("cadena_1  "+campo.getCadena_1());
    System.out.println("tablita  "+campo.getTablita());
    System.out.println("campos_suma  "+campo.getCampos_suma());
    return getSqlMapClientTemplate().queryForList("getListarCamposReporte", campo);
  }

  public List getListarCamposReporte2(Campos campo) throws DataAccessException {
    System.out.println("1-cadena  "+campo.getCadena());
    System.out.println("1-campos  "+campo.getCampos());
    System.out.println("1-cadena_1  "+campo.getCadena_1());
    System.out.println("1-tablita  "+campo.getTablita());
    return getSqlMapClientTemplate().queryForList("getListarCamposReporte2", campo);
  }
  
  public String getListarTotalesDatos(Campos campo) throws DataAccessException {
    String i = (String) getSqlMapClientTemplate().queryForObject("getListarTotalesDatos", campo);
    return i;
  }
  //Fin Administración de Reportes

  //Reporte de campos por actividades
  public String getListarCamposActividad(Campos campo) throws DataAccessException {
    String s = (String) getSqlMapClientTemplate().queryForObject("getListarCamposActividad", campo);
    return s;
  //Fin Reporte de campos por actividades
  }

  //Administracion de acl dibRap
  public List getListarCamposAcl2(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposAcl2", campo);
  }

  public int setRegistrarAcl2(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarAcl2", campo);
    return i.intValue();
  }
 
  public int setEliminarAcl2(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarAcl2", campo);
    return i.intValue();
  }
  //Fin Administracion de acl dibRap

  //Administracion de formularios
  public int setRegistrarFormulario(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarFormulario", campo);
    return i.intValue();
  }
 
  public int setEliminarFormulario(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarFormulario", campo);
    return i.intValue();
  }
  //Fin Administracion de formularios
  
  //Administrar Campos y Acl Proceso Kardex
  public Campos getBuscarTipoPermiso(Campos campo) throws DataAccessException {
    return (Campos) getSqlMapClientTemplate().queryForObject("getBuscarTipoPermiso", campo);
  }
  
  public int setRegistrarCampoAclProcesoKardex(Campos campo) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarCampoAclProcesoKardex", campo);
    return i.intValue();
  }
  //Administrar Campos y Acl Proceso Kardex

  public List getListarCamposReferenciaProceso(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposReferenciaProceso", campo);
  }

  public List getListarCamposReporteProceso(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposReporteProceso", campo);
  }

  //INICIO - Administrar Reportes
  public List getListarTuplasCampo(Campos campo) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTuplasCampo", campo);
  }
  //NUEVO - Administrar Reportes

}