<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
		<div id="choose-match">
			<ul id="tressette-ul">
				<li class="tressette-li">
					<a id="put-in-queue">Mettiti in coda per una partita classificata!</a>
				</li>
				<li class="tressette-li">
					<a id="custom-match">Crea la tua partita personalizzata!</a>
				</li>
			</ul>
		</div>
		<!--<form id="gioca_button" action="/MultigamingCompetitionSystem/tressette/gioca" method="get">
			<input type="submit" value="Gioca Ora!" />
		</form> -->
		<%@include file="../../resources/html/footer.html" %>

		<script>
			function insertInQueue() {
				$("#choose-match").html("<img id='loading' src='/MultigamingCompetitionSystem/assets/loading.gif'>Loading</img> <form id='undo-queue'> <input type='submit' value='Annulla' enabled='false'/> </form>");

				$.ajax({
					url: "tressette/gioca",
					success: function (data) {
					}
				});

				$.ajax({
					url: "tressette/gioca",
					data: {
						competitor: "${username}"
					},
					success: function (data) {
					}
				});
				$.ajax({
					url: "tressette/gioca",
					data: {
						inQueue: true,
						competitor: "${username}"
					},
					success: function (data) {
						window.location.href = "/MultigamingCompetitionSystem/tressette/gioca";
					}
				});


			}
			$('#put-in-queue').click(insertInQueue);
		</script>
    </body>
</html>
