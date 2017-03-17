package org.fautapo.dao.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.fautapo.dao.LibretasDao;
import org.fautapo.domain.Libretas;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-07
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-07
*/

public class SqlMapLibretasDao extends SqlMapClientDaoSupport implements LibretasDao {

  //Administrar libretas
  public Libretas getLbrBuscarFase(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getLbrBuscarFase", libreta);
  }

  public List getGrpListarEvaluacionDefinida(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getGrpListarEvaluacionDefinida", libreta);
  }
      
  public Libretas getLbrBuscarTipoNota(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getLbrBuscarTipoNota", libreta);
  }  
  
  public List getEstBuscarEstudiantesProgramados(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstBuscarEstudiantesProgramados", libreta);
  }  

  public List getPstBuscarPostulantesProgramados(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstBuscarPostulantesProgramados", libreta);
  }  

  public List getEstListarNotasEstudiante(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstListarNotasEstudiante", libreta);
  }

  public List getPstListarNotasPostulante(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getPstListarNotasPostulante", libreta);
  }

  public int setEstInsertarNotaEstudianteFase(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEstInsertarNotaEstudianteFase", libreta);
    return i.intValue();
  }

  public int setPstInsertarNotaPostulanteFase(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setPstInsertarNotaPostulanteFase", libreta);
    return i.intValue();
  }
  
  public int setDctAvanzarFase(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setDctAvanzarFase", libreta);
    return i.intValue();
  }    
  
  public int setDctAvanzarFaseSemiFinal(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setDctAvanzarFaseSemiFinal", libreta);
    return i.intValue();
  }    
  
  public int getEstSumarNotasEstudianteEvalRegular(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getEstSumarNotasEstudianteEvalRegular", libreta);
    return i.intValue();
  }    

  public int getEstSumarNotasEstudianteEvalContinua(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getEstSumarNotasEstudianteEvalContinua", libreta);
    return i.intValue();
  }      

  public int getLbrBuscarFaseMinima(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getLbrBuscarFaseMinima", libreta);
    return i.intValue();
  }

  public int getLbrBuscarFaseMaxima(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("getLbrBuscarFaseMaxima", libreta);
    return i.intValue();
  }  
  //Fin Administrar libretas

  //Definir evaluación
  public List getLbrListarTiposNotas(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrListarTiposNotas", libreta);
  }

  
  public List getLbrListarTiposNotasDefinidas(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrListarTiposNotasDefinidas", libreta);
  }

  
  public Libretas getLbrBuscarTipoNotaDefinida(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getLbrBuscarTipoNotaDefinida", libreta);
  }

  public int setGrpInsertarEvaluacion(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setGrpInsertarEvaluacion", libreta);
    return i.intValue();
  }
  
  public int setGrpModificarEvaluacion(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setGrpModificarEvaluacion", libreta);
    return i.intValue();
  }  

  public int setGrpRegistrarEvaluacion(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setGrpRegistrarEvaluacion", libreta);
    return i.intValue();
  }

  public int setGrpEliminarEvaluacion(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setGrpEliminarEvaluacion", libreta);
    return i.intValue();
  }
  //Fin Definir evaluación
  
  //Cerrar libretas
  public List getListarMateriasCerrarLibreta(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasCerrarLibreta", libreta);
  }
    
  public int setCerrarLibreta(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setCerrarLibreta", libreta);
    return i.intValue();
  }    
  //Fin Cerrar libretas
  
  //Administrar LBR TIPOS NOTAS
  public List getTpsListarTiposEvaluaciones() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTpsListarTiposEvaluaciones", null);
  }
  
  public List getTpsListarTiposEstados() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTpsListarTiposEstados", null);
  }

  public Libretas getTpsBuscarTipoEvaluacion(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getTpsBuscarTipoEvaluacion", libreta);
  }
  
  public List getLbrListarFases(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrListarFases", libreta);
  }

  public List getLbrListarFases2(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrListarFases2", libreta);
  }

  public List getLbrListarTiposNotasFase(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrListarTiposNotasFase", libreta);
  }
  
  public int setLbrInsertarTipoNota(Libretas libreta) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setLbrInsertarTipoNota", libreta);
    return i.intValue();
  }
 
  public int setLbrModificarTipoNota(Libretas libreta) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setLbrModificarTipoNota", libreta);
    return i.intValue();
  } 
  
  //fin lbr_tipos_notas
  
  //reporte de libretas 
  public List getListarNotasFaseEstudiantes(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNotasFaseEstudiantes", libreta);
  }
  //Fin reporte libretas
  
  //reporte resumen de notas 
  public List getListarResumenNotasEstudiantes(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarResumenNotasEstudiantes", libreta);
  }
  
  //Cerrar libretas Por Materia
  public List getListarMateriasCerrarLibretaIndiv(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarMateriasCerrarLibretaIndiv", libreta);
  }
    
  public int setCerrarLibretaPorMateria(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setCerrarLibretaPorMateria", libreta);
    return i.intValue();
  }    
  //Fin Cerrar libretas  Por Materia
  
  //Reportes Notas
  //reporte certificado de calificaciones
  public List getListarCerticadoCalificaciones(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarCerticadoCalificaciones", libreta);
  }
  //fin reporte certificado de calificaciones
  
  //listar detalle de materia notas contnua
  public List getEstListarNotasEvaluacionContinua(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstListarNotasEvaluacionContinua", libreta);
  }
  //fin listar detalle de materia notas contnua
  
  //listar evaluacion contunua estudiantes
  public List getListarEstudiantesEvaluacionContinua(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEstudiantesEvaluacionContinua", libreta);
  }
  //fin listar evaluacion continua estudiantes
  
  //listar fases tipos notas de la definicion de evaluacion
  public List getLbrTiposnotasListarDefinicion(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getLbrTiposnotasListarDefinicion", libreta);
  }
  //fin listar fases tipos notas de la definicion de evaluacion
  //Fin Reportes Notas  
  
  //Retroceder Fase
  //Modifcar fase retroceder fase docente
  public int setModificarFaseDocente(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setModificarFaseDocente", libreta);
    return i.intValue();
  }

  public int setModificarFaseDocenteCerrarLibreta(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setModificarFaseDocenteCerrarLibreta", libreta);
    return i.intValue();
  }

  //Fin Modifcar fase retroceder fase docente
  
  //eliminar notas de fases calculados
  public int setEliminarFaseEstLibretas(Libretas libreta) throws DataAccessException {
    Integer i =  (Integer) getSqlMapClientTemplate().queryForObject("setEliminarFaseEstLibretas", libreta);
    return i.intValue();
  }
  //Fin eliminar notas de fases calculados
  //Fin Retroceder Fase
  
  //Curso verano
  public List getTrnListarEvaluacionesVerano() throws DataAccessException { 
   return getSqlMapClientTemplate().queryForList("getTrnListarEvaluacionesVerano", null);
  } 
  
  //Grados academicos
  public Libretas getBuscarGradoAcademicoPrograma(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getBuscarGradoAcademicoPrograma", libreta);
  }
  
  public List getListarTiposNotas() throws DataAccessException { 
   return getSqlMapClientTemplate().queryForList("getListarTiposNotas", null);
  }
  
  //Buscar Lbr Fases
  public Libretas getBuscarLbrFase(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getBuscarLbrFase", libreta);
  }
  
  public Libretas getBuscarLbrTipoNota(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getBuscarLbrTipoNota", libreta);
  }
  
  public int setLbrRegistrarTipoNota(Libretas libreta) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setLbrRegistrarTipoNota", libreta);
    return i.intValue();
  }
 
  public int setLbrEliminarTipoNota(Libretas libreta) throws DataAccessException {
    Integer i = (Integer) getSqlMapClientTemplate().queryForObject("setLbrEliminarTipoNota", libreta);
    return i.intValue();
  }
  
  //listar estudiantes cierre libreta
  public List getListarEstudiantesParaCierreLibreta(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEstudiantesParaCierreLibreta", libreta);
  }    
  
  public List getListarEstudiantesEnEstLibretas(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEstudiantesEnEstLibretas", libreta);
  }
  
  public List getListarEvaluacionesFinalesFase(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarEvaluacionesFinalesFase", libreta);
  }

  public List getTotalAprobadosReprobadosMateria(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getTotalAprobadosReprobadosMateria", libreta);
  }
  
  //Listar notas ponderadas est_libretas
  public List getListarNotasEstudiantesLibretas(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getListarNotasEstudiantesLibretas", libreta);
  }
  
  //Buscar Tipo Nota
  public Libretas getMiBuscarTipoNota(Libretas libreta) throws DataAccessException {
    return (Libretas) getSqlMapClientTemplate().queryForObject("getMiBuscarTipoNota", libreta);
  }

//INICIO - METODOS ADICIONADOS POR LA UAP
  //NOTAS RECTIFICADAS
  public List getEstListarNotasRectificadasEstudiante(Libretas libreta) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("getEstListarNotasRectificadasEstudiante", libreta);
  }
//FIN - METODOS ADICIONADOS POR LA UAP

}
