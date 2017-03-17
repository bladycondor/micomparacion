package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.PerfilesDao;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Planes;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario bladimir
 * @fec_modificacion 2016/04/11
*/

public class SqlMapPerfilesDao extends SqlMapClientDaoSupport implements PerfilesDao {

  public Perfiles getPrfBuscarPerfil(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getPrfBuscarPerfil", perfil);
  }
  
  public List getPstListarPerfiles(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPerfiles", perfil);
  }

  public List getPstListarPerfilesEntidad(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarPerfilesEntidad", perfil);
  }

  public List getPrfListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrfListarConceptos", perfil);
  }

  public List getPstListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarConceptos", perfil);
  }

  public List getEstListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstListarConceptos", perfil);
  }

  public List getDctListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getDctListarConceptos", perfil);
  }

  public List getUsrListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getUsrListarConceptos", perfil);
  }

  public int setPstRegistrarTransaccion(Perfiles perfil) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstRegistrarTransaccion", perfil);
    return i.intValue();
  }

  public List getPrsListarConceptos(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPrsListarConceptos", perfil);
  }

  public int setPrsRegistrarTransaccion(Perfiles perfil) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPrsRegistrarTransaccion", perfil);
    return i.intValue();
  }

  public Perfiles getPrcBuscarPerfil(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getPrcBuscarPerfil", perfil);
  }

  public int setRegistrarTrnDetalle(Perfiles perfil) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarTrnDetalle", perfil);
    return i.intValue();
  }

  public List getTrnListarTiposPerfiles() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTiposPerfiles", null);
  }
  
  public List getTrnMiListarPerfilesProceso(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnMiListarPerfilesProceso", perfil);
  }
  
  public Perfiles getTrnBuscarPerfilProceso(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarPerfilProceso", perfil);
  }
  
  public int getTrnPerfilTieneDescuento(Perfiles perfil) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getTrnPerfilTieneDescuento", perfil);
    return i.intValue();
  }

  public List getTrnListarPerfilesMaterias(Planes plan) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarPerfilesMaterias", plan);
  }
  
  public Perfiles getTrnBuscarPerfilMateria(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarPerfilMateria", perfil);
  }
  
  public List getTrnListarPerfiles() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarPerfiles", null);
  }
  
  public Perfiles getTrnBuscarPerfil(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarPerfil", perfil);
  }
  
  public int setTrnRegistrarPerfilMateria(Planes plan) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setTrnRegistrarPerfilMateria", plan);
    return i.intValue();
  }

  public Perfiles getTrnBuscarTransaccion(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarTransaccion", perfil);
  }
  
  public Perfiles getTrnBuscarTransaccionRecibo(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarTransaccionRecibo", perfil);
  }
  
   public Perfiles getTrnBuscarTransaccionReciboSede(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarTransaccionReciboSede", perfil);
  }
  
  public List getTrnListarTrnDetalles(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTrnDetalles", perfil);
  }
  
  //Tipos descuentos
  public List getTrnListarTiposDescuentos() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTiposDescuentos", null);
  }
  
  public Perfiles getTrnBuscarTipoDescuento(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getTrnBuscarTipoDescuento", perfil);
  } 
  
  public Perfiles getBuscarPerfilConcepto(Perfiles perfil) throws DataAccessException {
    return (Perfiles) getSqlMapClientTemplate().queryForObject("getBuscarPerfilConcepto", perfil);
  }
  
  public List getTrnListarCajeros(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarCajeros", perfil);
  }
   public List getTrnListarCajerosProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarCajerosProv", perfil);
  }

  public List getTrnPrcListarPerfiles(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnPrcListarPerfiles", perfil);
  }

  public List getTrnListarTransacciones(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTransacciones", perfil);
  }
  
  //aqui se agrego
  public List getRepCajasTransaccionesDiarias(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiarias", perfil);
  } 

  public List getRepCajasTransaccionesDiariasGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiariasGlobal", perfil);
  } 
//agregado para provincia darlin
  public List getRepCajasTransaccionesDiariasGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiariasGlobalProv", perfil);
  } 

//fin agregado provincia darlin
//agregado para reporte detallado de conceptos por cajero Trinidad y Provincias
  public List getRepCajasTransaccionesDiariasGlobalxcajero(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiariasGlobalxcajero", perfil);
  } 
  //agregado para reporte detallado de conceptos por cajero Trinidad y Provincias
  public List getRepCajasTransaccionesDiariasGlobalxcajeroProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiariasGlobalxcajeroProv", perfil);
  } 
  
  public List getRepCajasTransaccionesDiariasEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDiariasEntidades", perfil);
  }
  
  public List getRepCajasResumenTesoroEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenTesoroEntidades", perfil);
  }
  
  public List getRepCajasResumenInstitucionalEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenInstitucionalEntidades", perfil);
  }
  
  
  
   public List getRepCajasResumenInstitucionalEntidadesConcepto(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenInstitucionalEntidadesConcepto", perfil);
  }
  
  

  public List getRepCajasResumenEstudiantilEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenEstudiantilEntidades", perfil);
  }
  
   public List getRepCajasResumenEstudiantilEntidadesConcepto(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenEstudiantilEntidadesConcepto", perfil);
  }

  
  public List getRepCajasResumenProfactulativoEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenProfactulativoEntidades", perfil);
  }
  
  public List getRepCajasResumenProfactulativoCarrera(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenProfactulativoCarrera", perfil);
  }

  public List getRepCajasDetalladoEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasDetalladoEntidades", perfil);
  }
    public List getRepCajasDetalladoCarrera(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasDetalladoCarrera", perfil);
  }
  public List getRepCajasTransaccionesDetalleGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleGlobal", perfil);
  } 
  
  public List getRepCajasTransaccionesDetalleGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleGlobalProv", perfil);
  } 

  public List getRepCajasTransaccionesDetalleGlobalAnuladas(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleGlobalAnuladas", perfil);
  } 
   public List getRepCajasTransaccionesDetalleGlobalAnuladasProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleGlobalAnuladasProv", perfil);
  } 

  public List getRepCajasTransaccionesDetalleEntidad(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleEntidad", perfil);
  } 

  public List getRepCajasTransaccionesDetalle(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalle", perfil);
  } 

  public List getRepCajasTransaccionesDetalleAnuladas(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesDetalleAnuladas", perfil);
  } 

  public List getRepCajasResumenMatriculas(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenMatriculas", perfil);
  } 
 public List getRepCajasResumenTesoroCarrera(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenTesoroCarrera", perfil);
  }
  public List getRepCajasResumenMatriculasGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenMatriculasGlobal", perfil);
  } 
//Nro_2
  public List getRepCajasResumenMatriculasGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenMatriculasGlobalProv", perfil);
  }  
  
  
 public List getRepCajasResumenInstitucional(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenInstitucional", perfil);
  } 

 public List getRepCajasResumenInstitucionalGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenInstitucionalGlobal", perfil);
  } 
  // Nro_3
 public List getRepCajasResumenInstitucionalGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenInstitucionalGlobalProv", perfil);
  }   
  
 public List getRepCajasResumenEstudiantil(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenEstudiantil", perfil);
  } 
  
 public List getRepCajasResumenEstudiantilGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenEstudiantilGlobal", perfil);
  } 
  // Nro_4
 public List getRepCajasResumenEstudiantilGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenEstudiantilGlobalProv", perfil);
  }  

  public List getRepCajasResumenProfacultativo(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenProfacultativo", perfil);
  } 

  public List getRepCajasResumenProfacultativoGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenProfacultativoGlobal", perfil);
  } 
//Nro_5
  public List getRepCajasResumenProfacultativoGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenProfacultativoGlobalProv", perfil);
  } 
  
  public List getRepCajasResumenDetalladoMatriculas(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoMatriculas", perfil);
  } 
  
  public List getRepCajasResumenDetalladoMatriculasGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoMatriculasGlobal", perfil);
  } 
  
  public List getRepCajasResumenDetalladoMatriculasGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoMatriculasGlobalProv", perfil);
  } 

  public List getRepCajasResumenDetalladoEstudiantil(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoEstudiantil", perfil);
  } 

  public List getRepCajasResumenDetalladoEstudiantilGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoEstudiantilGlobal", perfil);
  } 
  
  public List getRepCajasResumenDetalladoEstudiantilGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoEstudiantilGlobalProv", perfil);
  } 

  public List getRepCajasResumenDetalladoInstitucional(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoInstitucional", perfil);
  } 

  public List getRepCajasResumenDetalladoInstitucionalGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoInstitucionalGlobal", perfil);
  } 

  public List getRepCajasResumenDetalladoInstitucionalGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoInstitucionalGlobalProv", perfil);
  } 
  
    public List getRepCajasResumenDetallado(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetallado", perfil);
  } 

    public List getRepCajasResumenDetalladoGlobal(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoGlobal", perfil);
  } 
     public List getRepCajasResumenDetalladoGlobalMatricula(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoGlobalMatricula", perfil);
  } 
public List getRepCajasResumenDetalladoGlobalProv(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoGlobalProv", perfil);
  } 
//para la entidad
    public List getRepCajasResumenDetalladoEntidades(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasResumenDetalladoEntidades", perfil);
  } 


  public List getRepCajasTransaccionesPorPrograma(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getRepCajasTransaccionesPorPrograma", perfil);
  } 
// public List getRepCajasGlobalGeneral(Perfiles perfil) throws DataAccessException {
//    return getSqlMapClientTemplate().queryForList("getRepCajasGlobalGeneral", perfil);
//  } 

  //hasta aqui
  
  public List getTrnListarTransaccionesRecibo(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTransaccionesRecibo", perfil);
  }
  
  public List getTrnListarTransaccionesReciboSede(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTransaccionesReciboSede", perfil);
  }
  
  public List getTrnListarTrnDetalles2(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnListarTrnDetalles2", perfil);
  }

  // inicio JOJO
  public List getTrnBuscarPorNroRecibo(Perfiles perfil) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTrnBuscarPorNroRecibo", perfil);
  }

  public void setTrnBorrarDetalle(Perfiles perfil) throws DataAccessException {
    getSqlMapClientTemplate().queryForObject("setTrnBorrarDetalle", perfil);
  }

  public void setTrnBorrarTransaccion(Perfiles perfil) throws DataAccessException {
    getSqlMapClientTemplate().queryForObject("setTrnBorrarTransaccion", perfil);
  }
  // fin JOJO  

  public int getTrnBuscarSiguienteNroRecibo(Perfiles perfil) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getTrnBuscarSiguienteNroRecibo", perfil);
    return i.intValue();
  }

  public void setTrnActualizarNroRecibo(Perfiles perfil) throws DataAccessException {
    getSqlMapClientTemplate().queryForObject("setTrnActualizarNroRecibo", perfil);
  }

}