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
                <div class="TressetteBannerImage"></div>
                <div class="TressetteDescription"></div>
                <div class="TressettePlayButton">
                    <form id="tressette_button" action="/MultigamingCompetitionSystem/tressette" method="get">
                            <input class="Submit" type="submit" value="Play Tressette" />
                    </form>
                </div>
            </div>
        </div>
        
        <%@include file="../../resources/html/footer.html" %>
    </body>
</html>
