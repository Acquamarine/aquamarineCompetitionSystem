<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Multi-gaming competition system</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<form id="tressette_button" action="MultigamingCompetitionSystem/tressette" method="get">
			<input type="submit" value="Tressette" />
		</form>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			if (${loggedIn} === true) {
			<% String name = (String) request.getAttribute("username");%>
				//document.getElementById("login").innerHTML = "<h1>Welcome"+ <% name; %> +"</h1>";
				document.getElementById("login").style.height = "100px";
			}
		</script>
    </body>
</html>
