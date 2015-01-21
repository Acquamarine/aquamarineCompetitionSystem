<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.persistence.OnDemandPersistenceManager"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.core.users.ICompetitor"%>
<%@page import="java.util.Map"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.items.IItem"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory"%>
<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.games.turnBased.tressette.Tressette1v1"%>
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
        <link href="/MultigamingCompetitionSystem/css/briscolaGioca.css" rel="stylesheet" type="text/css"/>
        <title>Gioca a Briscola!</title>
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
        <script>
            <%String deckCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				String opponentCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				String myTeamCoverPath = "/MultigamingCompetitionSystem/assets/items/CARD_COVER/basic.png";
				ICompetitor opponentCompetitor = ((List<ICompetitor>)request.getSession().getAttribute("players")).get(1);
				Map<ICompetitor,ICompetitor> playersTeamsMap=(Map<ICompetitor,ICompetitor>) request.getSession().getAttribute("playersTeamsMap");
					ICompetitor opponentTeam = playersTeamsMap.get(opponentCompetitor);
				 ICompetitor myTeam = playersTeamsMap.get(0);
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
				$(".My-cards").click(cardsClick);
			});


			function removeCardsFromTable() {
				$('.played-cards').children("img").remove();
			}
			function distributeCard(obj) {
				for (var i = 0; i < 4; i++) {
					
				}
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
									distributeCard(obj);
								}
							}
							else {
								graphicComplete = true;
							}
							if (obj.gameover) {
								gameComplete();
							} else {
								if (obj.round === 1) {
									$('#turn').html(obj.winner + ' is your turn!');
								} else if ("${nickname}" === obj.actionPlayer) {
									$('#turn').html('${matched} is your turn!');
								} else {
									$('#turn').html('${nickname} is your turn!');
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
                <div id="MyTeammate_info"  class="Inline">
                    <div id="MyTeammate-avatar">
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                    </div>
                    <div class="MyTeammate_other_info"> ${players.get(2).getNickname()}</div>
                </div>
                <div id="MyTeammate-cards" class="Inline">
                    <div id="MyTeammate-cards-list" class="${players.get(2).getNickname()}Cards">
                        <c:forEach items = "${hands.get(2).getHandCards()}"  var = "card" >
                            <c:if test='${card!=null}'>
								<div class="MyTeammate-cards Inline ">
									<img class='cards_img' src=<%=myTeamCoverPath%>/>
								</div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
				<div class= "played-cards" id="playedCard2">
				</div>
            </div>
			<div class="CenterOfTable">
				<div id="OpponentFirst">

					<div id="OpponentFirst-cards">
						<div id="OpponentFirst-cards-list" class="${players.get(1).getNickname()}Cards">
							<c:forEach items = "${hands.get(3).getHandCards()}"  var = "card" >
								<c:if test='${card!=null}'>
                                    <div class="OpponentFirst-cards">
                                        <img class='cards_img' src=<%=opponentCoverPath%>/>
                                    </div>
								</c:if>
							</c:forEach>
						</div>
					</div>

					<div id="OpponentFirst_info">
						<div id="OpponentFirst-avatar" >
							<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/female.jpg"/>
						</div>
						<div class="OpponentFirst_other_info"> ${players.get(1).getNickname()}</div>
					</div>
				</div>
				<div class= "played-cards" id="playedCard3">
				</div>
				<div id="cards-on-table">
					<div id="cards-on-table-list">
						<div class="table-card">
							<c:if test="${deck!=0}">
								<div id="deck" class="Inline">
									<img id="deck-image" class='cards_img Inline' src=<%=deckCoverPath%>></img>${deck}
								</div>
								<div id="briscolaCard" class="Inline">
									<img id="briscolaCard-image" class='cards_img Inline' src='/MultigamingCompetitionSystem/assets/carte_napoletane/${briscola}.png'></img>
								</div>
							</c:if>
							<div  id="turn">
								${turn.getNickname()} is your turn!
							</div>
						</div>
					</div>
				</div>
				<div class= "played-cards" id="playedCard1">
				</div>
				<div id="OpponentSecond">
					<div id="OpponentSecond_info">
						<div id="OpponentSecond-avatar">
							<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
						</div>
						<div class="OpponentSecond_other_info"> ${players.get(3).getNickname()}</div>
					</div>

					<div id="OpponentSecond-cards">
						<div id="OpponentSecond-cards-list" class="${players.get(3).getNickname()}Cards">
							<c:forEach items = "${hands.get(1).getHandCards()}"  var = "card" >
								<c:if test='${card!=null}'>
									<div class="OpponentSecond-cards">
										<img class='cards_img' src=<%=opponentCoverPath%>/>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>

				</div>
			</div>
			<div id="Me">
				<div class= "played-cards" id="playedCard0">
				</div>
                <div id="My-cards" class="Inline">
                    <div id="My-cards-list">
                        <c:forEach items = "${hands.get(0).getHandCards()}"  var = "card" >
                            <c:if test='${card!=null}'>
								<div class="My-cards Inline">
									<img id='${card}' class='cards_img' src='/MultigamingCompetitionSystem/assets/carte_napoletane/${card}.png'/>
								</div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div id="My_info" class="Inline">
                    <div id="My-avatar">
						<img class="AvatarImage" src="/MultigamingCompetitionSystem/assets/female.jpg"/>
                    </div>
                    <div class="My_other_info "> ${players.get(0).getNickname()}</div>
                </div>
            </div>

		</div>
        <%@include file="../../../resources/html/footer.html" %>



    </body>
</html>
