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
            <div class="FrontBanner">
                <div class="FrontGameBannerImage TressetteBannerImage Inline"></div>
                <div class="FrontGameDescription Inline">
                    <div class="FrontGameDescriptionTitle">Defeat your opponents on the table with Tressette 1vs1</div>
                    <div class="FrontGameDetails">Tressette is an italian card game. The proposed variant is designed for 2 players. Even being a card game, the outcomes of tressette heavily relies on players' game skills. Join the challenge and climb the ladder!</div>
                    <div class="FrontGamePlayButton">
                        <form class="play_game_button" action="/MultigamingCompetitionSystem/tressette" method="get">
                            <input class="Submit" type="submit" value="Play Tressette" />
                        </form>
                    </div>
                </div>
            </div>
            <div class="FrontBanner">
                <div class="FrontGameBannerImage BriscolaBannerImage Inline"></div>
                <div class="FrontGameDescription Inline">
                    <div class="FrontGameDescriptionTitle">Lead your team to the victory in Briscola 2vs2 </div>
                    <div class="FrontGameDetails">An italian card game, designed to be played with a trusted partner. Build your team and queue up for a Briscola showdown. Claim the ranking first spot! </div>
                    <div class="FrontGamePlayButton">
                        <form class="play_game_button" action="/MultigamingCompetitionSystem/briscola" method="get">
                            <input class="Submit" type="submit" value="Play Briscola" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="../../resources/html/footer.html" %>
    </body>
</html>
