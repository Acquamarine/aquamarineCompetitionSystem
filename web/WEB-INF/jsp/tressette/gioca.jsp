<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/tressetteGioca.css" rel="stylesheet" type="text/css"/>
        <title>Gioca a Tressette!</title>
    </head>
    <body>
        <%@include file="../../../resources/html/header.html" %>
		<div id="game-table">
			<div id="player1">
				<div id="player1-avatar">
					
				</div>
				<div id="player1-cards">
					
				</div>
			</div>
			<div id="cards-on-table">
				
			</div>
			<div id="player2">
				<div id="player2-avatar">
					
				</div>
				<div id="player2-cards">
					
				</div>
			</div>
		</div>
		<%@include file="../../../resources/html/footer.html" %>
		
		<script>
			playerCards = document.getElementById("player1-cards").innerHTML;
			for(i=0; i<13;i++){
				playerCards= playerCards + "<div class='cards' id='card"+i+"'";
			}
		</script>
    </body>
</html>
