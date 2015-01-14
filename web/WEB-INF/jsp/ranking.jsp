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
				<td class="ranking-heading ranking-table">Won Matches</td>
				<td class="ranking-heading ranking-table">Lost Matches</td>
				<td class="ranking-heading ranking-table">Percentage of won Matches</td>
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
			<c:forEach items = "${usersRanking}"  var = "userRanking" >
				console.log(${userRanking});
			rowToAppend = document.createElement("tr");
			createColumn((i + 1) + ".",rowToAppend,"");
			createColumn("coming soon",rowToAppend,"");
			createColumn("${userRanking.getKey()}",rowToAppend,"${userRanking.getKey()}");
			createColumn("${userRanking.getValue()}",rowToAppend,"");
			createColumn("coming soon",rowToAppend,"");
			createColumn("coming soon",rowToAppend,"");
			createColumn("coming soon",rowToAppend,"");
			$('#ranking').append(rowToAppend,"");
			i++;
			</c:forEach>
				$('.Clickable').click(function(){window.location.href = "/MultigamingCompetitionSystem/userProfile?user=" +this.id;});
				$('.Clickable').css("cursor","pointer");
		</script>
    </body>
</html>
