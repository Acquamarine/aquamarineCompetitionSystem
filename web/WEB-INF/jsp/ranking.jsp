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
		<script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<table id="ranking" width="100%">
			<tr>
				<td>Rank</td>
				<td>Tier</td> 
				<td>Player</td>
				<td>Elo</td>
				<td>Won Matches</td>
				<td>Lose Matches</td>
				<td>Percentage of won Matches</td>
			</tr>
		</table>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			var i=0;
			<c:forEach items = "${usersRanking}"  var = "userRanking" >
			rowToAppend = document.createElement("tr");
			colToAppend = document.createElement("td");
			td.html((i+1)+".");
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html("coming soon");
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html(${userRanking.get(i).getKey()});
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html(${userRanking.get(i).getValue()});
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html("coming soon");
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html("coming soon");
			rowToAppend.appendChild(colToAppend);
			colToAppend = document.createElement("td");
			td.html("coming soon");
			rowToAppend.appendChild(colToAppend);
			i++;
			</c:forEach>
		</script>
    </body>
</html>
