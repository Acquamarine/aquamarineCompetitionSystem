<%@page import="it.unical.ea.aquamarine.multigamingCompetitionSystem.shopAndItems.ItemCategory"%>
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
		<link href="/MultigamingCompetitionSystem/css/inventory.css" rel="stylesheet" type="text/css">
		<script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
		<div id="inventoryContainer">
			<div id="welcomeUser">
				Hi ${nickname}! This is you inventory:
			</div>

			<div class="ItemsContainer">			
				<c:forEach items="${inventoryMap.keySet()}" var="category">
					<div class="ItemCategory">
						<c:forEach items="${inventoryMap.get(category)}" var="itemsInCategory">
							<div class="ItemContainer Inline">
								<div class="ItemNameAndGame ">
									${itemsInCategory.getDisplayName()} / ${itemsInCategory.getGame()}
								</div>
								<div class="ItemImage ">
									<img class="Image" src="/MultigamingCompetitionSystem/assets/items/${itemsInCategory.getCategory().toString()}/${itemsInCategory.getName()}.png"/>
								</div>
								<div class="EquipItem ">
									<input class="Submit EquipItem EquipItemButton" id='${itemsInCategory.getId()}' type="submit" value="Equip" />
								</div>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			$(document).ready(function () {
				$('.EquipItemButton').click(function (e) {
					console.log(this);
					e.preventDefault(); // <------------------ stop default behaviour of button
					$.ajax({
						url: "/MultigamingCompetitionSystem/inventory",
						data: {
							equipItem: this.id
						},
						success: function (data) {
							console.log("asdfg2");

						}
					});
				});
			});
		</script>
	</body>
</html>
