<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
  <title>Sistema Integrado - Siringuero</title>
  <link rel="stylesheet" href="./pagina.css" type="text/css">
  <link rel="stylesheet" href="../pagina.css" type="text/css">
  <script language='JavaScript' SRC="./funciones.js"></script>
  <script language='JavaScript' SRC="../funciones.js"></script>
  <meta content="text/html; charset=iso8859-15" http-equiv="Content-Type" />
  <META HTTP-EQUIV="Cache-Control" CONTENT="max-age=0">
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
  <META http-equiv="expires" content="0">
  <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT">
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>

  <frameset rows='13%,*' frameborder='no' border='0' framespacing='0'>
    <frame src='<c:url value="verEncabezadoInforme.fautapo?id_tramite=${id_tramite}&id_informe=${id_informe}&id_proceso=${id_proceso}&id_actividad=${id_actividad}&cantInformes=${cantInformes}&nro_pagina_actual=${nro_pagina_actual}&fechainicio=${fechainicio}&fechafin=${fechafin}&fechadellunes=${fechadellunes}&id_estado=${id_estado}&nombre_informe=${nombre_informe}&aplicacion=${aplicacion}&nro_pagina=${nro_pagina}&filtro=${filtro}&_botoncillo=${_botoncillo}&banderakardex=${banderakardex}"/>' name='encabezado' scrolling='NO' noresize>
    <frame src='<c:url value="verInforme.fautapo?id_tramite=${id_tramite}&id_informe=${id_informe}&id_actividad=${id_actividad}&id_proceso=${id_proceso}&id_tipo_proceso=${id_tipo_proceso}&nombre_informe=${nombre_informe}&aplicacion=${aplicacion}&accion=Ver&nro_pagina=${nro_pagina}&banderakardex=${banderakardex}"/>' name='detalle' scrolling='SI' noresize>
  </frameset>

</html>