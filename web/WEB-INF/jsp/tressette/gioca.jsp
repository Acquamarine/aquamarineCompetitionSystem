<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor"%>
<%@page import="java.util.Map"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.games.tressette.Tressette1v1"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.RegisteredUser"%>
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
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
        <script>
			<%String deckCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";%>
			var graphicComplete = true;
			function gameComplete() {
				$.ajax({
					url: "./gioca",
					data: {
						gameComplete: true
					},
					success: function (data) {
						obj = JSON.parse(data);
						$('#player1-cards').remove();
						$('#player2-cards').remove();
						$('#cards-on-table').children().remove();
						console.log(obj);
						var elements = obj.results;
						var i = 0;
						for (element in elements) {
							console.log(element + " " + elements[element]);
							externalDivToAppend = document.createElement("div");
							externalDivToAppend.className = "finalScore";
							externalDivToAppend.id = "finalScore" + i;
							i++;
							externalDivToAppend.innerHTML = " " + element + " Points " + elements[element];
							document.getElementById('cards-on-table').appendChild(externalDivToAppend);
						}
						//$('#player1-other-info').html(${user} + " Points " + obj.results['${user}']);
						//$('#player2-other-info').html(${matched} + " Points " + obj.results['${matched}']);
					}
				});
			}
			function cardsClick() {
				var parentId = this.id;
				console.log(parentId);
				if (graphicComplete) {
					$.ajax({
						url: "./gioca",
						data: {
							cardId: parentId
						},
						success: function (data) {
						}
					});
				}
			}
			$(document).ready(function () {
				$(".player2-cards").click(cardsClick);
			});

			$(window).bind('beforeunload', function () {
			<% int previousIndex = (int) request.getSession().getAttribute("eventIndex");
				if(previousIndex > 0 && previousIndex % 2 != 0){
					request.getSession().setAttribute("eventIndex", previousIndex - 1);
					request.getSession().setAttribute("reloaded", true);
				}%>
			});

			function removeCardsFromTable() {
				$('#card-played-0').children("img").remove();
				$('#card-played-1').children("img").remove();
			}
			function distributeCard(card, player, deck, complete) {
				setTimeout(function () {
					$('#deck').html("<img id='deck-image' class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/" + card + ".png'>" + deck + "</img>");
				}, 2000);

				setTimeout(function () {
					$('#deck').html("<img  id='deck-image' class='cards_img' src=<%=deckCoverPath%>/>" + deck + "</img>");
					if (player === "${nickname}" && $('#' + card).length === 0) {
						externalDivToAppend = document.createElement("div");
						externalDivToAppend.className = "player2-cards-container";
						divToAppend = document.createElement("div");
						var cardPath = "/MultigamingCompetitionSystem/assets/carte_napoletane/" + card + ".png";
						divToAppend.innerHTML = "<img  class='cards_img' src=" + cardPath + " />";
						divToAppend.className = "player2-cards";
						divToAppend.id = card;
						toAppend = document.createElement("li");
						externalDivToAppend.appendChild(divToAppend);
						toAppend.appendChild(externalDivToAppend);
						toAppend.className = "li-cards";
						document.getElementById("player2-cards-list").appendChild(toAppend);
						$('#' + card).click(cardsClick);
					} else if (player !== "${nickname}") {
						divToAppend = document.createElement("div");
						divToAppend.innerHTML = "<img class='cards_img' src=<%=deckCoverPath%>/>";
						divToAppend.className = "player1-cards";
						toAppend = document.createElement("li");
						toAppend.appendChild(divToAppend);
						toAppend.className = "li-cards";
						document.getElementById("player1-cards-list").appendChild(toAppend);
					}
					if (deck === 0 && $('#deck').length) {
						$('#deck').remove();
					}
					graphicComplete = complete;
				}, 3000);
			}
			var index = ${eventIndex};
			console.log(index);
			function eventHandler(reloaded) {
				console.log(index);
				$.ajax({
					url: "./gioca",
					data: {
						eventIndex: index
					},
					success: function (data) {
						graphicComplete = false;
						if (data !== "") {
							index++;
							obj = JSON.parse(data);
							if ("${nickname}" === obj.actionPlayer) {
								$('#' + obj.card).parent("div").parent("li").remove();
							}
							else {
								console.log(reloaded);
								if (reloaded === false) {
									$('.li-cards').first().remove();
								}
							}
							$('#card-played-' + obj.round).html("<img class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/" + obj.card + ".png'/>");
							//console.log(obj.round);
							if (obj.round === 1) {
								setTimeout(function () {
									$('#card-played-0').children("img").remove();
									$('#card-played-1').children("img").remove();
									if (!$('#deck').length) {
										graphicComplete = true;
									}
								}, 1000);
								if ($('#deck').length) {
									distributeCard(obj.picked0, obj.winner, obj.deck + 1, false);
									console.log($('#deck-image').text);
									setTimeout("distributeCard(obj.picked1,obj.looser,obj.deck,true)", 2000);

								}
							}
							else {
								graphicComplete = true;
							}
							if (obj.gameover) {
								gameComplete();
							} else {
								if (obj.round === 1) {
									$('#turn').html(obj.winner + ' tocca a te!');
								} else if ("${nickname}" === obj.actionPlayer) {
									$('#turn').html('${matched} tocca a te!');
								} else {
									$('#turn').html('${nickname} tocca a te!');
								}

								eventHandler(false);
							}
						}
					}
				});
			}
			$(document).ready(function () {
				console.log(<%=request.getSession().getAttribute("reloaded")%>);
				eventHandler(<%=request.getSession().getAttribute("reloaded")%>);
			<%request.getSession().setAttribute("reloaded", false);%>
			<%
				ICompetitor user = (ICompetitor) request.getSession().getAttribute("matchedCompetitor");
				System.out.println(user.getNickname());
				System.out.println(user.getEquip("Tressette1v1").getEquipMap());
				if(user.getEquip("Tressette1v1").getEquipMap().containsKey(ItemCategory.CARD_COVER)){
					String cover = user.getEquip("Tressette1v1").getEquipMap().get(ItemCategory.CARD_COVER).getName();
					deckCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/" + cover + ".png";
				}%>
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
                    <li class="player_other_info"> ${matched}</li>
                </ul>
                <div id="player1-cards">
                    <ul id="player1-cards-list">
						<c:forEach items = "${matchedPlayerCards}"  var = "card" >
							<c:if test='${!card.equals("")}'>
								<li class="li-cards">
									<div class="player1-cards">
										<img class='cards_img' src=<%=deckCoverPath%>/>
									</div>
								</li>
							</c:if>
						</c:forEach>
                    </ul>

                </div>
            </div>
            <div id="cards-on-table">
                <ul id="cards-on-table-list">
                    <li class="table-card">
						<c:if test="${deck!=0}">
							<div id="deck">
								<img id="deck-image" class='cards_img' src=<%=deckCoverPath%>></img>${deck}
							</div>
						</c:if>
                    </li>
                    <li class="table-card">
                        <div class= "played-cards" id="card-played-0">

                        </div>
                    </li>
                    <li class="table-card">
                        <div class= "played-cards" id="card-played-1">

                        </div>
                    </li>
                    <li class="table-card">
                        <div class= "played-cards" id="turn">
							${turn} tocca a te!
                        </div>
                    </li>
                </ul>
            </div>
            <div id="player2">
                <div id="player2-cards">
                    <ul id="player2-cards-list">
						<c:forEach items = "${cards}"  var = "card" >
							<c:if test='${!card.equals("")}'>
								<li class="li-cards">
									<div class="player2-cards-container">
										<div class="player2-cards" id='${card.toString()}'>
											<img  class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/${card.toString()}.png' />
										</div>
									</div>
								</li>
							</c:if>
						</c:forEach>
                    </ul>
                </div>
				<ul id="player2_info">
					<li class="player_other_info"> ${nickname}</li>
					<li id="player2-avatar">
					</li>
				</ul>
            </div>
        </div>
        <%@include file="../../../resources/html/footer.html" %>



    </body>
</html>
