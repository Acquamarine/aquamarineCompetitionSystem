<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/tressette.css" rel="stylesheet" type="text/css"/>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <title>Tressette</title>

    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<div>
			<img id="tressette-img" src="/MultigamingCompetitionSystem/assets/Tressette.png"/>;
		</div>
		<div id="tressette-descr">
			<h1> Per una descrizione dettagliata del gioco vai su 
				<a href="http://www.tressette.info/tressette/regolamento-del-tressette.html">www.tressette.info/tressette/regolamento-del-tressette.html</a>
		</div>
		<form id="gioca_button" action="/MultigamingCompetitionSystem/tressette/gioca" method="get">
			<input type="submit" value="Gioca Ora!" />
		</form>
		<%@include file="../../resources/html/footer.html" %>
    </body>
</html>
