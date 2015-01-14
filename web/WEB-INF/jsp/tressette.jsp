<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
        <link href="/MultigamingCompetitionSystem/css/tressette.css" rel="stylesheet" type="text/css"/>
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
        <title>Tressette</title>

    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div>
            <img id="tressette-img" src="/MultigamingCompetitionSystem/assets/Tressette.png"/>
        </div>
        <div id="tressette-descr">
            <h1> Per una descrizione dettagliata del gioco vai su 
                <a href="http://www.tressette.info/tressette/regolamento-del-tressette.html">www.tressette.info/tressette/regolamento-del-tressette.html</a>
        </div>
        <div id="choose-match">
            <ul id="tressette-ul">
                <li class="tressette-li">
                    <a id="put-in-ranked-queue">Mettiti in coda per una partita classificata!</a>
                </li>
                <li class="tressette-li">
                    <a id="put-in-queue">Mettiti in coda per una partita di allenamento!</a>
                </li>
            </ul>
        </div>
        <!--<form id="gioca_button" action="/MultigamingCompetitionSystem/tressette/gioca" method="get">
                <input type="submit" value="Gioca Ora!" />
        </form> -->
        <%@include file="../../resources/html/footer.html" %>

        <script>
			<c:if test="${empty nickname}">
			$('#put-in-ranked-queue').remove();
			</c:if>
			function initMatch() {
				$.ajax({
					url: "tressette/gioca",
					success: function (data) {
						console.log("play call");
					}
				});
			}
			function insertInQueue(rankedQueue) {
				console.log(rankedQueue);
				console.log("inserting in queue");
				$("#choose-match").html("<img id='loading' src='/MultigamingCompetitionSystem/assets/loading.gif'>Loading</img> <form  id='undo-queue' method='post'> <input class='Submit' type='submit' value='Undo'/> </form>");
				$('#undo-queue').attr("action","/MultigamingCompetitionSystem/tressette?undoQueue=" + rankedQueue);
				$.ajax({
					url: "tressette",
					data: {
						addToRankedQueue: rankedQueue
					},
					success: function (data) {
						console.log("add in queue success");
					}
				});
				$.ajax({
					url: "tressette",
					data: {
						inQueue: true
					},
					success: function (data) {
						console.log("match is ready");
						initMatch();
						window.location.href = "/MultigamingCompetitionSystem/tressette/gioca";
					}
				});


			}
			$('#put-in-ranked-queue').click(function () {
				insertInQueue(true);
			});
			$('#put-in-queue').click(function () {
				insertInQueue(false);
			});



        </script>
    </body>
</html>
