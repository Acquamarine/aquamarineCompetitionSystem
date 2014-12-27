<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanCard"%>
<%@page import="java.util.List"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.games.shared.NeapolitanHand"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/tressetteGioca.css" rel="stylesheet" type="text/css"/>
        <title>Gioca a Tressette!</title>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
		<script>
			$(document).ready(function () {
				$(".player2-cards").click(function () {
					$.ajax({
						url: "./gioca",
						data: {
							cardId: this.id
						},
						success: function (data) {
							console.log(data);
							// $("#weather-temp").html("<strong>" + data + "</strong> degrees");
						}
					});
				});
			});
		</script>
    </head>
    <body>
        <%@include file="../../../resources/html/header.html" %>
		<div id="game-table">
			<div id="player1">
				<ul id="player1_info">
					<li id="player1-avatar">
					</li>
					<li class="player_other_info"> {player1_name}  Points: {player1_points}</li>
				</ul>
				<div id="player1-cards">
					<ul id="player1-cards-list">

					</ul>

				</div>
			</div>
			<div id="cards-on-table">
				<div id="remaining-cards">

				</div>
			</div>
			<div id="player2">
				<div id="player2-cards">
					<ul id="player2-cards-list">

					</ul>
					<ul id="player2_info">
						<li class="player_other_info"> {player2_name}  Points: {player2_points}</li>
						<li id="player2-avatar">
						</li>
					</ul>
				</div>
			</div>
		</div>
		<%@include file="../../../resources/html/footer.html" %>

		<script>
			for (i = 0; i < 10; i++) {
				divToAppend = document.createElement("div");
				divToAppend.id = "player1-card" + i;
				divToAppend.className = "player1-cards";
				toAppend = document.createElement("li");
				toAppend.appendChild(divToAppend);
				toAppend.className = "li-cards";
				document.getElementById("player1-cards-list").appendChild(toAppend);
			}

		</script>
		<script>
			<c:forEach items = "${cards}"  var = "card" >
			divToAppend = document.createElement("div");
			divToAppend.setAttribute("href", "./");
			var cardPath = "/MultigamingCompetitionSystem/assets/carte_napoletane/${card.toString()}.png";
			divToAppend.innerHTML = "<img  class='player2_cards_img' src=" + cardPath + " />";
			divToAppend.id = "${card.toString()}";
			divToAppend.className = "player2-cards";
			toAppend = document.createElement("li");
			toAppend.appendChild(divToAppend);
			toAppend.className = "li-cards";
			document.getElementById("player2-cards-list").appendChild(toAppend);
			</c:forEach>
		</script>
	</body>
</html>
