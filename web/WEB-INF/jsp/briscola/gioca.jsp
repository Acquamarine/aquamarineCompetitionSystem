<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager"%>
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
        <title>Gioca a Briscola!</title>
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
        <script>
            <%String deckCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				String opponentCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				String myTeamCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				ICompetitor opponentTeam = (ICompetitor) request.getSession().getAttribute("matchedCompetitor");
				ICompetitor myTeam = (ICompetitor) request.getSession().getAttribute("myTeam");
				System.out.println(opponentTeam.getNickname());
				System.out.println(opponentTeam.getEquip("Briscola").getEquipMap());
				if(opponentTeam.getEquip("Briscola").getEquipMap().containsKey(ItemCategory.CARD_COVER)){
					String cover = opponentTeam.getEquip("Briscola").getEquipMap().get(ItemCategory.CARD_COVER).getName();
					opponentCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/" + cover + ".png";
				}
				if(myTeam.getEquip("Briscola").getEquipMap().containsKey(ItemCategory.CARD_COVER)){
					String cover = myTeam.getEquip("Briscola").getEquipMap().get(ItemCategory.CARD_COVER).getName();
					myTeamCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/" + cover + ".png";
				}
			%>
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
						divToAppend.innerHTML = "<img class='cards_img' src=<%=opponentCoverPath%>/>";
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
			function eventHandler() {
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
								$('.li-cards').first().remove();
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

								eventHandler();
							}
						}
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
            <div id="MyTeammate">
                <div id="MyTeammate_info">
                    <div id="MyTeammate-avatar" class="Inline">
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                    </div>
                    <div class="MyTeammate_other_info" class="Inline"> ${MyTeammate}</div>
                </div>
                <div id="MyTeammate-cards">
                    <div id="MyTeammate-cards-list">
                        <c:forEach items = "${playersCards.get(2)}"  var = "card" >
                            <c:if test='${card!=null}'>
                                <div class="li-cards Inline">
                                    <div class="MyTeammate-cards">
                                        <img class='cards_img' src=<%=myTeamCoverPath%>/>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
				<div class= "played-cards" id="playedCard2">
				</div>
            </div>
            <div id="OpponentFirst" class="Inline">
                <div id="OpponentFirst_info" class="Inline">
                    <div id="OpponentFirst-avatar" >
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/female.jpg"/>
                    </div>
                    <div class="OpponentFirst_other_info"> ${OpponentFirst}</div>
                </div>
                <div id="OpponentFirst-cards" class="Inline">
                    <div id="OpponentFirst-cards-list">
                        <c:forEach items = "${playersCards.get(3)}"  var = "card" >
                            <c:if test='${card!=null}'>
                                <div class="li-cards">
                                    <div class="OpponentFirst-cards">
                                        <img class='cards_img' src=<%=opponentCoverPath%>/>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
				<div class= "played-cards Inline" id="playedCard3">
				</div>
            </div>
			<div id="cards-on-table" class="Inline">
                <div id="cards-on-table-list">
                    <div class="table-card">
                        <c:if test="${deck!=0}">
                            <div id="deck" class="Inline">
                                <img id="deck-image" class='cards_img Inline' src=<%=deckCoverPath%>></img>${deck}
                            </div>
							<div id="briscolaCard" class="Inline">
                                <img id="briscolaCard-image" class='cards_img Inline' src=${briscolaCard}></img>${deck}
							</div>
                        </c:if>
                        <div  id="turn">
                            ${turn} tocca a te!
                        </div>
                    </div>
                </div>
            </div>
            <div id="OpponentSecond" class="Inline">
				<div class= "played-cards Inline" id="playedCard1">
				</div>
                <div id="OpponentSecond-cards" class="Inline">
                    <div id="OpponentSecond-cards-list">
                        <c:forEach items = "${playersCards.get(1)}"  var = "card" >
                            <c:if test='${card!=null}'>
                                <div class="li-cards">
                                    <div class="OpponentSecond-cards">
                                        <img class='cards_img' src=<%=opponentCoverPath%>/>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div id="OpponentSecond_info" class="Inline">
                    <div id="OpponentSecond-avatar">
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                    </div>
                    <div class="OpponentSecond_other_info"> ${OpponentSecond}</div>
                </div>
            </div>
			<div id="Me">
					<div class= "played-cards" id="playedCard0">
					</div>
                <div id="My-cards">
                    <div id="My-cards-list">
                        <c:forEach items = "${playersCards.get(0)}"  var = "card" >
                            <c:if test='${card!=null}'>
                                <div class="li-cards Inline">
                                    <div class="My-cards">
                                        <img class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/" + ${card.toString()} + ".png'/>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div id="My_info">
                    <div id="My-avatar" class="Inline">
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/female.jpg"/>
                    </div>
                    <div class="My_other_info Inline"> ${Me}</div>
                </div>
            </div>

		</div>
        <%@include file="../../../resources/html/footer.html" %>

		

    </body>
</html>
