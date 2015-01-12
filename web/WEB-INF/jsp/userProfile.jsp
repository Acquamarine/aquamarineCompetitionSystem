<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shared.GameConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Multi-gaming competition system</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css">
        <link href="/MultigamingCompetitionSystem/css/userProfile.css" rel="stylesheet" type="text/css">
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div class="GlobalHeaderContainer">
        </div>
        <div class="CompetitorHeader Inline">
            <div class="ProfileIcon Inline">
                <div class="CompetitorImage Inline">
                    <div class="borderImage">
                        <img src="">
                    </div>
                    <div class="CompetitorAvatar">
                        <img class="CompetitorAvatarImage" src="/MultigamingCompetitionSystem/assets/male.png"/>
                    </div>
                </div>

            </div>
            <div class="CompetitorInformation Inline">
                <div class="CompetitorName Inline">
                    ${user} 
                </div>
                <div class="CompetitorLadderRank">
					<% pageContext.setAttribute("unrankedRank", GameConstants.UNRANKED_RANK);%>
                    <c:choose>
                        <c:when test="${rankAndElo.getKey()==unrankedRank}">
                            <a href="/MultigamingCompetitionSystem/ranking?game=Tressette1v1" class="LadderRankLink">
                                Unranked in ${game}
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/MultigamingCompetitionSystem/ranking?game=Tressette1v1" class="LadderRankLink">
                                Rank in ${game}: <span id="rank">${rankAndElo.getKey()+1}</span>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>

            <div class="GameSelectorDiv Inline">
                <select id="GameSelector" onchange="changeSelectedGame()" >
                    <option value="Tressette1v1">Tressette1v1</option>
                </select>
            </div>
        </div>
        <div id="userMatchHistoryContainer">
            <div id="matchHistory Inline">
                <c:forEach items="${matchHistory}" var="matchResult">
                    <div class="SingleMatchContainer">
                        <div class="MatchType">
                            <div class="SubType Inline">
                                <c:choose>
                                    <c:when test="${matchResult.isRankedMatch()}">
                                        Ranked Match
                                    </c:when>
                                    <c:otherwise>
                                        Normal Match
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="MatchDate Inline">
                                ${matchResult.getMatchEndTime()}
                            </div>
                        </div>
                        <div class="MatchStats">
                            <div class="MatchScores Inline">
                                <div class="PlayerMatchDetails Inline">
                                    <div class="CompetitorName">
                                        ${matchResult.getPlayer1().getNickname()}
                                    </div>
                                    <div class="Score">
                                        ${matchResult.getPlayer1Score()}
                                    </div>
                                </div>
                                <div class="MatchPlayersSeparator Inline">
                                    vs
                                </div>
                                <div class="PlayerMatchDetails Inline">
                                    <div class="CompetitorName">
                                        ${matchResult.getPlayer2().getNickname()}
                                    </div>
                                    <div class="Score">
                                        ${matchResult.getPlayer2Score()}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <%@include file="../../resources/html/footer.html" %>

        <script>
			function changeSelectedGame() {
				var x = document.getElementById("GameSelector").value;
				window.location.href = "/MultigamingCompetitionSystem/userProfile?game=" + x + "&user=" +${user};
			}

        </script>
    </body>
</html>
