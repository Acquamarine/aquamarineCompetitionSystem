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
        <div class="InventoryContainer">
            <div class="InventoryHeader">
				<div class="PlatformGamesTitle">Your Inventory</div>
				<div class="HeaderDetails">
					<div class="CompetitorVirtualPoints Inline">Credit: ${buyer.getVirtualPoints()} vp</div>
					<div class="GameSelectorDiv Inline">
						<select id="GameSelector" onchange="changeSelectedGame()" >
							<option value="Tressette1v1">Tressette1v1</option>
						</select>
					</div>
				</div>
			</div>
            <div class="InventoryContent">
                <c:forEach items="${inventoryMap.keySet()}" var="category">
                    <c:forEach items="${inventoryMap.get(category).getItems()}" var="itemInCategory">
                        <div class="InventoryItemContainer">
                            <div class="ItemBanner Inline">
                                <img class="Image" src="/MultigamingCompetitionSystem/assets/items/${itemInCategory.getCategory()}/${itemInCategory.getName()}.png"/>
                            </div>
                            <div class="ItemDetails Inline">
                                <div class="ItemName Detail">${itemInCategory.getDisplayName()}</div>
                                <div class="ItemGame Detail">Game: ${itemInCategory.getGame()}</div>
                                <div class="ItemCategory Detail">Category: ${itemInCategory.getCategory().toString()}</div>
                                <div class="EquipOption Detail">
									<%String buttonValue = "Equip!";
										String buttonClass = "Equip";
									%>
										<c:if  test="${buyer.getInventory().containsItem(itemInCategory)}">
											<%buttonValue = "Unequip!";
												buttonClass = "Unequip";
											%>
										</c:if>
									<form action="/MultigamingCompetitionSystem/inventory?<%=buttonClass%>=${itemInCategory.getId()}" method="post">
										<input  class="Submit EquipItemButton <%=buttonClass%>"  type="submit" value="<%=buttonValue%>"/>
									</form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>

            </div>
        </div>

        <%@include file="../../resources/html/footer.html" %>


    </body>
</html>
