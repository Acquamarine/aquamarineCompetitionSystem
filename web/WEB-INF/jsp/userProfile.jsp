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
			<div class="GlobalHeaderSearchForm">
				<form action="/MultigamingCompetitionSystem/userProfile" method="get">
					<div class="SearchBox">
						<div class="SearchBoxBlock">
							<div class="SearchBoxInput">
								<input type="text" name="user" class="Search"  placeholder="User Nickname">
							</div>
							<div class="SearchButton">
								<input type="submit"  value="Search user">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="CompetitorHeader">
			<div class="ProfileIcon">
				<div class="CompetitorImage">
					<div class="borderImage">
						<img src="">
					</div>
					<div class="CompetitorAvatar">
					</div>
				</div>

			</div>
			<div class="CompetitorInformation">
				<div class="CompetitorName">
					${user}
				</div>
				<div class="CompetitorLadderRank">
					<a href="/MultigamingCompetitionSystem/ranking?game=Tressette1v1" class="LadderRankLink">
						${rankAndElo.getKey()+1}
					</a>
				</div>
			</div>

			<div class="GameSelectorDiv">
				<select id="GameSelector" onchange="changeSelectedGame()" >
					<option value="Tressette1v1">Tressette1v1</option>
				</select>
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
