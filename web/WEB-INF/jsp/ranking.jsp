<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Multi-gaming competition system</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css">
		<link href="/MultigamingCompetitionSystem/css/ranking.css" rel="stylesheet" type="text/css">
		<script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<table class="ranking-table" id="ranking">
			<tr>
				<td class="ranking-heading ranking-table">Rank</td>
				<td class="ranking-heading ranking-table">Tier</td> 
				<td class="ranking-heading ranking-table">Player</td>
				<td class="ranking-heading ranking-table">Elo</td>
				<td class="ranking-heading ranking-table">Victories</td>
				<td class="ranking-heading ranking-table">Defeats</td>
				<td class="ranking-heading ranking-table">Victory rate</td>
			</tr>
			<c:forEach items = "${usersRanking}"  var = "userRanking" varStatus="loop" >
				<tr>
					<td class="ranking-column ranking-table">
						${loop.index+1}.
					</td>
					<td class="ranking-column ranking-table">
						coming soon
					</td>
					<td class="ranking-column ranking-table">
						<a class="NoStyle" action="/MultigamingCompetitionSystem/userProfile?user=${userRanking.getKey()}">
							${userRanking.getKey()}
						</a>
					</td>
					<td class="ranking-column ranking-table">
						${userRanking.getValue()}
					</td>
					<td class="ranking-column ranking-table">
						${usersDefeatsAndVictories.get(loop.index).getValue()}
					</td>
					<td class="ranking-column ranking-table">
						${usersDefeatsAndVictories.get(loop.index).getKey()}
					</td>
					<td class="ranking-column ranking-table">
						<c:choose>
							<c:when test="${usersDefeatsAndVictories.get(loop.index).getValue()+usersDefeatsAndVictories.get(loop.index).getKey()!=0}">
                                                                <fmt:formatNumber type="number" pattern="###.#%" value="${usersDefeatsAndVictories.get(loop.index).getValue()/(usersDefeatsAndVictories.get(loop.index).getValue()+usersDefeatsAndVictories.get(loop.index).getKey())}" />
							</c:when>
							<c:otherwise>
								0.0
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>

		</table>
		<%@include file="../../resources/html/footer.html" %>

    </body>
</html>
