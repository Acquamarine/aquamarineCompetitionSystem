<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			
		</table>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			var i = 0;
			function createColumn(htmlToIns, row, id) {
				colToAppend = document.createElement("td");
				if(id!==""){
					colToAppend.id=id;
					colToAppend.className="Clickable";
				}
				colToAppend.className+=" ranking-column ranking-table";
				colToAppend.innerHTML =htmlToIns;
				row.appendChild(colToAppend);
			}
			<c:forEach items = "${usersRanking}"  var = "userRanking" varStatus="loop" >
				console.log(${userRanking});
			rowToAppend = document.createElement("tr");
			createColumn((i + 1) + ".",rowToAppend,"");
			createColumn("coming soon",rowToAppend,"");
			createColumn("${userRanking.getKey()}",rowToAppend,"${userRanking.getKey()}");
			createColumn("${userRanking.getValue()}",rowToAppend,"");
                        var defeats = ${usersDefeatsAndVictories.get(loop.index).getKey()};
                        var victories = ${usersDefeatsAndVictories.get(loop.index).getValue()};
			createColumn(victories,rowToAppend,"");
			createColumn(defeats,rowToAppend,"");
                        var winRate = victories/(defeats+victories)*100;
			createColumn(winRate,rowToAppend,"");
			$('#ranking').append(rowToAppend,"");
			i++;
			</c:forEach>
				$('.Clickable').click(function(){window.location.href = "/MultigamingCompetitionSystem/userProfile?user=" +this.id;});
				$('.Clickable').css("cursor","pointer");
		</script>
    </body>
</html>
