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
                    var parentId = this.parentNode.parentNode.id;
                    $.ajax({
                        url: "./gioca",
                        data: {
                            cardId: parentId
                        },
                        success: function (data) {
                        }
                    });
                });
            });
        </script>
        <script>
			function removeCardsFromTable() {
				$('#card-played-0').children("img").remove();
				$('#card-played-1').children("img").remove();
			}
			function distributeCard(card,player) {
				setTimeout(function () {
					$('#deck').html("<img class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/" + card + ".png'/>");
				}, 2000);

				setTimeout(function () {
					$('#deck').html("<img class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/Dorso.png'/>");
					if (player === "${user}" && $('#' + card).length === 0) {
						externalDivToAppend = document.createElement("div");
						externalDivToAppend.className = "player2-cards-container";
						divToAppend = document.createElement("div");
						var cardPath = "/MultigamingCompetitionSystem/assets/carte_napoletane/" + card + ".png";
						divToAppend.innerHTML = "<img  class='cards_img' src=" + cardPath + " />";
						divToAppend.className = "player2-cards";
						toAppend = document.createElement("li");
						toAppend.id = card;
						externalDivToAppend.appendChild(divToAppend);
						toAppend.appendChild(externalDivToAppend);
						toAppend.className = "li-cards";
						document.getElementById("player2-cards-list").appendChild(toAppend);
					} else if (player !== "${user}") {
						divToAppend = document.createElement("div");
						divToAppend.id = "player1-card" + i;
						divToAppend.className = "player1-cards";
						toAppend = document.createElement("li");
						toAppend.appendChild(divToAppend);
						toAppend.className = "li-cards";
						document.getElementById("player1-cards-list").appendChild(toAppend);
					}
				}, 3000);
			}
			var index = 0;
			function eventHandler() {
				$.ajax({
					url: "./gioca",
					data: {
						eventIndex: index
					},
					success: function (data) {
						if (data !== "") {
							index++;
							obj = JSON.parse(data);
							if ("${user}" === obj.actionPlayer) {
								$('#' + obj.card).remove();
								// $("#weather-temp").html("<strong>" + data + "</strong> degrees");
							}
							else {
								$('.player1-cards').first().remove();
							}
							$('#card-played-' + obj.round).html("<img class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/" + obj.card + ".png'/>");
							console.log(obj.round);
							if (obj.round === 1) {
								setTimeout(function () {
									$('#card-played-0').children("img").remove();
									$('#card-played-1').children("img").remove();
								}, 1000);
								distributeCard(obj.picked0,obj.winner);
								console.log(obj.winner);
								console.log(obj.looser);
								setTimeout("distributeCard(obj.picked1,obj.looser)", 2000);



                            }
                        }
                        eventHandler();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log("ajax timeout");
                        eventHandler();
                    }
                });
            }
            $(document).ready(function () {
                eventHandler();
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
                    <li class="player_other_info"> {player}  Points: {player1_points}</li>
                </ul>
                <div id="player1-cards">
                    <ul id="player1-cards-list">

                    </ul>

                </div>
            </div>
            <div id="cards-on-table">
                <ul id="cards-on-table-list">
                    <li class="table-card">
                        <div id="deck">
                            <img class='cards_img' src="/MultigamingCompetitionSystem/assets/carte_napoletane/Dorso.png"/>
                        </div>
                    </li>
                    <li class="table-card">
                        <div class= "played-cards" id="card-played-0">

                        </div>
                    </li>
                    <li class="table-card">
                        <div class= "played-cards" id="card-played-1">

                        </div>
                    </li>
                </ul>
            </div>
            <div id="player2">
                <div id="player2-cards">
                    <ul id="player2-cards-list">

                    </ul>
                    <ul id="player2_info">
                        <li class="player_other_info"> ${user}  Points: {player2_points}</li>
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
            if ("${card}" !== "") {
                externalDivToAppend = document.createElement("div");
                externalDivToAppend.className = "player2-cards-container";
                divToAppend = document.createElement("div");
                divToAppend.setAttribute("href", "./");
                var cardPath = "/MultigamingCompetitionSystem/assets/carte_napoletane/${card.toString()}.png";
                divToAppend.innerHTML = "<img  class='cards_img' src=" + cardPath + " />";
                divToAppend.className = "player2-cards";
                toAppend = document.createElement("li");
                toAppend.id = "${card.toString()}";
                externalDivToAppend.appendChild(divToAppend);
                toAppend.appendChild(externalDivToAppend);
                toAppend.className = "li-cards";
                document.getElementById("player2-cards-list").appendChild(toAppend);
            }
            </c:forEach>
        </script>
    </body>
</html>
