<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/xml"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<combos>
  <c:forEach var="combo" items="${listadoCombos.combo}" varStatus="contador">
    <combo id='<c:out value="${combo.id_campo}"/>' indice='<c:out value="${contador.index}"/>' padre='<c:out value="${combo.campo_padre}"/>'><c:out value="${combo.campo}"/></combo>
  </c:forEach>
  <cantidad><c:out value='${fn:length(listadoCombos.combo)}' /></cantidad>
  <nombre><c:out value='${listadoCombos.campo}' /></nombre>
  <pagina><c:out value='${listadoCombos.pagina}' /></pagina>
</combos>
