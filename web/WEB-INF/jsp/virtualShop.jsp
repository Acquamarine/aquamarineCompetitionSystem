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
        <link href="/MultigamingCompetitionSystem/css/virtualShop.css" rel="stylesheet" type="text/css">
        <script src="/MultigamingCompetitionSystem/scripts/jquery-1.11.2.js"></script>
		<script>
			$(document).ready(function () {
				$(window).scrollTop($("#${focus}").offset().top-300);
				console.log($("#${focus}"));
			<%request.getSession().removeAttribute("focus");%>
			});
		</script>
    </head>
    <body >
        <%@include file="../../resources/html/header.html" %>
        <div class="ShopContainer">
            <div class="ShopHeader">
				<div class="PlatformGamesTitle">Virtual Shop</div>
				<div class="HeaderDetails">
					<div class="CompetitorVirtualPoints Inline">Credit: ${buyer.getVirtualPoints()} vp</div>
					<div class="GameSelectorDiv Inline">
						<select id="GameSelector" onchange="changeSelectedGame()" >
							<option value="Tressette1v1">Tressette1v1</option>
						</select>
					</div>
				</div>
			</div>
            <div class="ShopContent">
                <c:forEach items="${availableItems.keySet()}" var="category">
                    <c:forEach items="${availableItems.get(category)}" var="itemInCategory">
                        <div class="ShopItemContainer">
                            <div class="ItemBanner Inline">
                                <img class="Image" src="/MultigamingCompetitionSystem/assets/items/${itemInCategory.getCategory()}/${itemInCategory.getName()}.png"/>
                            </div>
                            <div class="ItemDetails Inline">
                                <div class="ItemName Detail">${itemInCategory.getDisplayName()}</div>
                                <div class="ItemGame Detail">Game: ${itemInCategory.getGame()}</div>
                                <div class="ItemCategory Detail">Category: ${itemInCategory.getCategory().toString()}</div>
                                <div class="ItemPrice Detail">Price: ${itemInCategory.getVirtualPointsPrice()} vp</div>
                                <div class="BuyOption Detail">
									<%String buttonValue = "Buy Now!";
										String buttonClass = "";
										String disabled = "";
									%>
									<c:choose>
										<c:when  test="${buyer.getInventory().containsItem(itemInCategory)}">
											<%buttonValue = "Owned";
												buttonClass = "OwnedButton";
												disabled = "disabled";
											%>
										</c:when>
										<c:when  test="${buyer.getVirtualPoints()<itemInCategory.getVirtualPointsPrice()}">
											<%buttonValue = "Not enough vp";
												buttonClass = "ButtonDisabled";
												disabled = "disabled";
											%>
										</c:when>
									</c:choose>
									<form action="/MultigamingCompetitionSystem/virtualShop?buyItem=${itemInCategory.getId()}" method="post">
										<input  class="Submit BuyItemButton <%=buttonClass%>"  id="VirtualShopButton${itemInCategory.getId()}" type="submit" value="<%=buttonValue%>" <%=disabled%>/>
									</form>
                                </div>
                            </div>
                            <div class="ItemDescription Inline">
                                Description
                                <div class="ItemDescriptionText">${itemInCategory.getDescription()}</div>
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>

            </div>
        </div>

        <%@include file="../../resources/html/footer.html" %>


    </body>
</html>
