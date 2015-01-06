<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/login.css" rel="stylesheet" type="text/css"/>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <title>Tressette</title>

    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<div >
			<div id="registrationSuccess"></div>
			<form:form id="login-form" action="/MultigamingCompetitionSystem/login?loggigIn=true&page=${page}" method="post" commandName="userForm">
				<p> Username :<form:input class="loginTextField" path="username" /></p>
				<p> Password :<form:password class="loginTextField" path="password" /></p>
				<p> <input type="submit" value="Login" />  </p>
				</form:form>
		</div>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			<c:if test = "${not empty registrationCompleted}" >
			$('#registrationSuccess').html("Registration has been successful. You can now login!");
			</c:if>

		</script>
    </body>
</html>
