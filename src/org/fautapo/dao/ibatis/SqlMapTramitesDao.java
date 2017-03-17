package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.TramitesDao;
import org.fautapo.domain.Tramites;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-22
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public class SqlMapTramitesDao extends SqlMapClientDaoSupport implements TramitesDao {

  public List getListarFormularioNuevo(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarFormularioNuevo", tramite);
  }

  public int setInsertarTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setInsertarTramite", tramite);
    return i.intValue();
  }
  
  public int getBuscarActividadMinima(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getBuscarActividadMinima", tramite);
    return i.intValue();
  }
  
  public int setInsertarFrLog(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setInsertarFrLog", tramite);
    return i.intValue();
  }
  
  public int setRegistrarValor(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarValor", tramite);
    return i.intValue();
  }
  
  public Tramites getBuscarTramite(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarTramite", tramite);
  }

  public int setRecibirTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRecibirTramite", tramite);
    return i.intValue();
  }
  
  public Tramites getBuscarFrLog(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarFrLog", tramite);
  }

  public int setAvanzarTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setAvanzarTramite", tramite);
    return i.intValue();
  }
  
  public int setConcluirTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setConcluirTramite", tramite);
    return i.intValue();
  }
  
  public int setEliminarFrLog(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setEliminarFrLog", tramite);
    return i.intValue();
  }
  
  public List getListarTramitesMios(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMios", tramite);
  }
  
  public List getListarTramitesMiosFiltrado(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosFiltrado", tramite);
  }
  
  public List getListarTramitesMiosDespachados(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosDespachados", tramite);
  }
  
  public List getListarTramitesMiosDespachadosFiltrado(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosDespachadosFiltrado", tramite);
  }
  
  public List getListarUsuariosActividadSiguiente(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarUsuariosActividadSiguiente", tramite);
  }
  
  public List getListarCamposReferencia(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCamposReferencia", tramite);
  }

  public int setRetrocederTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRetrocederTramite", tramite);
    return i.intValue();
  }

  public List getListarFormulario(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarFormulario", tramite);
  }

  //Administrar mis pendientes agrupados
  public List getListarTramitesMiosAgrupados(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosAgrupados", tramite);
  }
  
  public List getListarTramitesMiosAgrupados2(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosAgrupados2", tramite);
  }
  
  public List getListarTramitesMiosAgrupadosDespachados(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosAgrupadosDespachados", tramite);
  }
  
  public List getListarTramitesMiosAgrupadosDespachados2(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosAgrupadosDespachados2", tramite);
  }
  
  public Tramites getContarTramitesPorFechaEstado(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesPorFechaEstado", tramite);
  }
  
  public Tramites getContarTramitesPorFechaEstado2(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesPorFechaEstado2", tramite);
  }
  
  public Tramites getContarTramitesPorFecha(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesPorFecha", tramite);
  }

  public Tramites getContarTramitesPorFecha2(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesPorFecha2", tramite);
  }

  public Tramites getContarTramitesPorFecha3(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesPorFecha3", tramite);
  }
  //Fin Administrar mis pendientes agrupados

  //Busquedas de tramites
  public List getListarTramitesPorCampos(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesPorCampos", tramite);
  }

  public int getBuscarTramiteExisteUbicacionOrganica(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getBuscarTramiteExisteUbicacionOrganica", tramite);
    return i.intValue();
  }
  
  public Tramites getBuscarTramiteUbicacionOrganica(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarTramiteUbicacionOrganica", tramite);
  }

  public List getListarTramiteLog(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramiteLog", tramite);
  }

  public List getListarTramitesFechaUbicacionOrganica(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesFechaUbicacionOrganica", tramite);
  }

  public List getListarTramitesIniciados(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesIniciados", tramite);
  }

  public List getListarTramitesMovidos(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMovidos", tramite);
  }

  public List getListarTramitesConcluidos(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesConcluidos", tramite);
  }

  public List getListarTramitesIniciadosDetalle(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesIniciadosDetalle", tramite);
  }

  public List getListarTramitesMovidosDetalle(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMovidosDetalle", tramite);
  }

  public List getListarTramitesConcluidosDetalle(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesConcluidosDetalle", tramite);
  }
  //Fin Busquedas de tramites

  //Busqueda ejecutiva
  public List getListarDatosTramite(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarDatosTramite", tramite);
  }
  //Fin Busqueda ejecutiva

  //Bloquear Tramites
  public int setBloquearTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setBloquearTramite", tramite);
    return i.intValue();
  }
  //Fin Bloquear Tramites

  //Desbloquear Tramites
  public int setDesbloquearTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setDesbloquearTramite", tramite);
    return i.intValue();
  }
  //Fin Desbloquear Tramites

  //Anular tramites
  public List getListarTramitesAnulados() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesAnulados", null);
  }
  
  public int setAnularTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setAnularTramite", tramite);
    return i.intValue();
  }
  //Fin Anular tramites

  //Imprimir tramites
  public List getListarTramitesImpresion(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesImpresion", tramite);
  }
  //Fin Imprimir tramites
  
  //Redireccionar tramites
  public List getListarTramites(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramites", tramite);
  }

  public int setRedireccionarTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setRedireccionarTramite", tramite);
    return i.intValue();
  }
  //Fin Redireccionar tramites
  
  //Reingresar tramites
  public Tramites getBuscarTramite2(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarTramite2", tramite);
  }
  
  public int setReingresarTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setReingresarTramite", tramite);
    return i.intValue();
  }
  //Fin Reingresar tramites

  //Administrar correspondencias
  public Tramites getBuscarTipoProceso2(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarTipoProceso2", tramite);
  }    
  
  public List getListarTramitesMiosCorrespondenciaDe(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosCorrespondenciaDe", tramite);
  }

  public List getListarTramitesMiosCorrespondenciaPara(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosCorrespondenciaPara", tramite);
  }  

  public int setAvanzarCorrespondencia(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setAvanzarCorrespondencia", tramite);
    return i.intValue();
  }  

  public int setInsertarTramiteCopia(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setInsertarTramiteCopia", tramite);
    return i.intValue();
  }
  //Fin Administrar correspondencias

  //Busqueda por Fecha y Estados
  public List getListarEstadosTramites() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEstadosTramites", null);
  }
  
  public List getListarTramitesEstadoFechaUbicacionOrganica(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesEstadoFechaUbicacionOrganica", tramite);
  }
  //Fin Busqueda por Fecha y Estados  
  
  //Habilitar Tramites
  public int setHabilitarTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setHabilitarTramite", tramite);
    return i.intValue();
  }
  //Fin Habilitar Tramites  

  public List getListarTramitesPorEstadoFecha(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesPorEstadoFecha", tramite);
  }
  
  //Administrar Kardex
  public List getListarTramitesMiosKardex(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosKardex", tramite);
  }
  
  public List getListarTramitesMiosKardexPorProceso(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosKardexPorProceso", tramite);
  }
  
   //Para ver los siguientes kardexs
  public List getListarTramitesMiosKardexPorProcesoAtendidos(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesMiosKardexPorProcesoAtendidos", tramite);
  }
  
  public Tramites getBuscarMinMaxTramitesMiosKardexPorProceso(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getBuscarMinMaxTramitesMiosKardexPorProceso", tramite);
  }
  
  //Reporte de tramites por funcionarios
  public List getListarTramitesFuncionarios(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesFuncionarios", tramite);
  }
 
  public List getListarTramitesFuncionarioProceso(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesFuncionarioProceso", tramite);
  }

  public List getListarTramitesAtendidos(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesAtendidos", tramite);
  }
  
  public Tramites getContarTramitesAtendidos(Tramites tramite) throws DataAccessException {
    return (Tramites) getSqlMapClientTemplate().queryForObject("getContarTramitesAtendidos", tramite);
  }
  //Fin - Reporte de tramites por funcionarios

  public String getContarPaginas(Tramites tramite) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getContarPaginas", tramite);
  }

  public String getContarPaginasDespachados(Tramites tramite) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getContarPaginasDespachados", tramite);
  }

  public List getListarTramitesCorrelativo(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesCorrelativo", tramite);
  }

  //Administrar tramites concluidos
  public List getListarTramitesConcluidosPorProceso(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesConcluidosPorProceso", tramite);
  }

  public List getListarTramitesConcluidosPorProcesoFiltrado(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarTramitesConcluidosPorProcesoFiltrado", tramite);
  }

  public String getContarPaginasConcluidos(Tramites tramite) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getContarPaginasConcluidos", tramite);
  }

  public String getContarPaginasTramitesGestionProceso(Tramites tramite) throws DataAccessException {
    return (String) getSqlMapClientTemplate().queryForObject("getContarPaginasTramitesGestionProceso", tramite);
  }
  //Fin - Administrar tramites concluidos
  //  SILVIA
  public List getListarCorrespondenciaDes(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCorrespondenciaDes", tramite);
  }

 // Busqueda de Correspondencia
  public List getListarCorrespReference(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCorrespReference", tramite);
  }
  
   // Busqueda de Correspondencia por Remitente
  public List getListarCorrespRemitente(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCorrespRemitente", tramite);
  }

 //  Correspondencia despachadas por fecha
  public List getListarCorrespDesFecha(Tramites tramite) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCorrespDesFecha", tramite);
  }
  //  FIN SILVIA
  
  //Cambiar Estado Tramites
  public int setCambiarEstadoTramite(Tramites tramite) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setCambiarEstadoTramite", tramite);
    return i.intValue();
  }
  //Fin Cambiar Estado Tramites

//INICIO - METODOS ADICIONADOS POR LA UAP
  // TRAMITES ATENDIDOS
  public List getTrListarProcesos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrListarProcesos", null);
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}