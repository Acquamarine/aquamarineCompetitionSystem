<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/tressetteGioca.css" rel="stylesheet" type="text/css"/>
        <title>Gioca a Tressette!</title>
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
			for (i = 0; i < 13; i++) {
				divToAppend = document.createElement("div");
				divToAppend.id = "player1-card" + i;
				divToAppend.className = "player-cards";
				toAppend = document.createElement("li");
				toAppend.appendChild(divToAppend);
				toAppend.className = "li-cards";
				document.getElementById("player1-cards-list").appendChild(toAppend);
			}
			for (i = 0; i < 13; i++) {
				divToAppend = document.createElement("div");
				divToAppend.id = "player2-card" + i;
				divToAppend.className = "player-cards";
				toAppend = document.createElement("li");
				toAppend.appendChild(divToAppend);
				toAppend.className = "li-cards";
				document.getElementById("player2-cards-list").appendChild(toAppend);
			}
		</script>
    </body>
</html>
