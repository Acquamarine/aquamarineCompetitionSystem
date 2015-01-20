<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
        <link href="/MultigamingCompetitionSystem/css/tressette.css" rel="stylesheet" type="text/css"/>
        <link href="/MultigamingCompetitionSystem/css/briscola.css" rel="stylesheet" type="text/css"/>
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
        <title>Tressette</title>

    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div class="PlatformGamesTitle">Briscola 2vs2</div>
        <div class="FrontBanner">
            <div class="TressetteBannerImage2"></div>
            <div id="tressette-descr">
            </div>
            <div class="MatchChooserContainer">
                <ul class="MatchChooser">
                    <li id="rankedQueueButton" class="FrontGamePlayButton Inline">
                        <input id="rankedGameInput" class="Submit" type="submit" value="Queue up for a ranked match!" />
                    </li>
                    <li id="normalQueueButton" class="FrontGamePlayButton Inline">
                        <input id="normalGameInput" class="Submit" type="submit" value="Queue up for a normal match!" />
                    </li>
                </ul>
            </div>
        </div>
        <!--<form id="gioca_button" action="/MultigamingCompetitionSystem/tressette/gioca" method="get">
                <input type="submit" value="Gioca Ora!" />
        </form> -->
        <%@include file="../../resources/html/footer.html" %>

        <script>
			<c:if test="${empty nickname}">
			$('#rankedQueueButton').children().first().attr("value", "Login to play ranked matches");
			$('#rankedQueueButton').children().first().css("background-color", "#ff4040");
			$('#rankedQueueButton').children().first().prop("disabled",true);
                        
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
				$(".MatchChooserContainer").html("<img id='loading' src='/MultigamingCompetitionSystem/assets/loading.gif'>Loading</img> <form  id='undo-queue' method='post'> <input class='Submit' type='submit' value='Undo'/> </form>");
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
			$('#rankedGameInput').click(function () {
				insertInQueue(true);
			});
			$('#normalGameInput').click(function () {
				insertInQueue(false);
			});



        </script>
    </body>
</html>
