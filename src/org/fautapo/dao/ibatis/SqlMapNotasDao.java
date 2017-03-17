package org.fautapo.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.NotasDao;
import org.fautapo.domain.Notas;
import org.fautapo.domain.Estudiantes;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class SqlMapNotasDao extends SqlMapClientDaoSupport implements NotasDao {

  //Mover notas
  public Notas getMtcMoverNoMatriculados(Notas nota) throws DataAccessException {
    return (Notas) getSqlMapClientTemplate().queryForObject("getMtcMoverNoMatriculados", nota);
  }

  public Notas getMtcMoverMatriculados(Notas nota) throws DataAccessException {
    return (Notas) getSqlMapClientTemplate().queryForObject("getMtcMoverMatriculados", nota);
  }
  //Fin mover notas
  // Reporte acta de calificaciones
  public List getListarActaCalificaciones(Notas nota) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActaCalificaciones", nota);
  }
  //Fin Reporte acta de calificaciones
  // Reporte acta de calificaciones PRECIERRE
  public List getListarActaCalificacionesPreCierre(Notas nota) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActaCalificacionesPreCierre", nota);
  }
  //Fin Reporte acta de calificaciones PRECIERRE

  // Reporte acta de calificaciones Por Fase
  public List getListarActaCalificacionesPorFase(Notas nota) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarActaCalificacionesPorFase", nota);
  }
  //Fin Reporte acta de calificaciones Por Fase

  //INICIO - Impresion de Certificado de Notas
  public List getListarCertificadoNotasTodas(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCertificadoNotasTodas", estudiante);
  }

  public List getListarCertificadoNotasNivel(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCertificadoNotasNivel", estudiante);
  }


  public List getListarCertificadoNotasAprobadas(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCertificadoNotasAprobadas", estudiante);
  }
  //FIN - Impresion de Certificado de Notas

  //INICIO2 - Impresion de Certificado de Notas
  public List getListarCertificadoNotasTodas2(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCertificadoNotasTodas2", estudiante);
  }

  public List getListarCertificadoNotasAprobadas2(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCertificadoNotasAprobadas2", estudiante);
  }
  //FIN - Impresion de Certificado de Notas



  //INICIO - Rectificacion de notas
  public List getListarNotasRectificar(Notas nota) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNotasRectificar", nota);
  }

  public Notas getBuscarNota(Notas nota) throws DataAccessException {
    return (Notas) getSqlMapClientTemplate().queryForObject("getBuscarNota", nota);
  }

  public Notas getEstListarFichaAcademicaBuscar(Notas nota) throws DataAccessException {
    return (Notas) getSqlMapClientTemplate().queryForObject("getEstListarFichaAcademicaBuscar", nota);
  }

  public Notas getEstListarFichaAcademicaBuscarBuscarAnulada(Notas nota) throws DataAccessException {
    return (Notas) getSqlMapClientTemplate().queryForObject("getEstListarFichaAcademicaBuscarBuscarAnulada", nota);
  }

  public int setRegistrarRectificacion(Notas nota) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarRectificacion", nota);
    return i.intValue();
  }

  public int setRegistrarRectificacionNota(Notas nota) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setRegistrarRectificacionNota", nota);
    return i.intValue();
  }

  //FIN - Rectificacion de notas

  //INICIO - Historial Academico
  public List getListarPlanMateriasNotas(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPlanMateriasNotas", estudiante);
  }

  public List getListarPlanMateriasNotas2(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPlanMateriasNotas2", estudiante);
  }

public List getListarPlanMateriasNotas3(Estudiantes estudiante) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarPlanMateriasNotas3", estudiante);
  }
  public double getBuscarPromedioDeNotas(Notas nota) throws DataAccessException {
    Double d =  (Double) getSqlMapClientTemplate().queryForObject("getBuscarPromedioDeNotas", nota);
    return d.doubleValue();
  }

  public int getCantidadMateriasAprobadas(Estudiantes estudiante) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getCantidadMateriasAprobadas", estudiante);
    return i.intValue();
  }
  
  //FIN - Historial Academico
  
}