
<%@ page import="ar.edu.unlam.eduweb.TemaForo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'temaForo.label', default: 'TemaForo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-temaForo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-temaForo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						
					
						<th><g:message code="temaForo.categoriaForo.label" default="Categoria Foro" /></th>
					
						<g:sortableColumn property="fecha" title="${message(code: 'temaForo.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="mensaje" title="${message(code: 'temaForo.mensaje.label', default: 'Mensaje')}" />
					
						<g:sortableColumn property="titulo" title="${message(code: 'temaForo.titulo.label', default: 'Titulo')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${temaForoInstanceList}" status="i" var="temaForoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						
					
						<td>${fieldValue(bean: temaForoInstance.curso, field: "nombre")}</td>
					
						<td><g:formatDate date="${temaForoInstance.fecha}" /></td>
					
						<td>${fieldValue(bean: temaForoInstance, field: "mensaje")}</td>
					
						<td>${fieldValue(bean: temaForoInstance, field: "titulo")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${temaForoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
