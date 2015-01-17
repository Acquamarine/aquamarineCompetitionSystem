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
    </head>
    <body>
        <%@include file="../../resources/html/header.html" %>
        <div class="ShopContainer">
            <div class="ShopHeader"></div>
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
                                    <input class="Submit BuyItemButton" id='${itemsInCategory.getId()}' type="submit" value="Buy now!" />
                                </div>
                            </div>
                            <div class="ItemDescription Inline">
                                Descrizione
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>

            </div>
        </div>

        <%@include file="../../resources/html/footer.html" %>
        
        <script>
			$(document).ready(function () {
				$('.BuyItemButton').click(function (e) {
					console.log(this);
					e.preventDefault(); // <------------------ stop default behaviour of button
					$.ajax({
						url: "/MultigamingCompetitionSystem/virtualShop",
						data: {
							buyItem: this.id
						},
						success: function (data) {
							console.log("transaction succeded");

						}
					});
				});
			});
		</script>
    </body>
</html>
