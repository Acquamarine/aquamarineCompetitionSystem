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
		<script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div class="PlatformGames">
            <div class="PlatformGamesTitle">Competitive Games</div>
            <div class="TressetteBanner">
                <div class="TressetteBannerImage Inline"></div>
                <div class="TressetteDescription Inline">
                    <div class="TressetteDescriptionTitle">Defeat your opponents on the table with Tressette 1vs1</div>
                    <div class="TressetteDetails">Tressette is an italian card game. The proposed variant is designed for 2 players. Even being a card game, the outcomes of tressette heavily relies on players' game skills. Join the challenge and climb the ladder!</div>
                    <div class="TressettePlayButton">
                    <form id="tressette_button" action="/MultigamingCompetitionSystem/tressette" method="get">
                        <input class="Submit" type="submit" value="Play Tressette" />
                    </form>
                </div>
                </div>
                
            </div>
        </div>
        
        <%@include file="../../resources/html/footer.html" %>
    </body>
</html>
