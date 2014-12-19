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
    </head>
    <body>
        <div id="main">
            <div id="header">
				<div id="logo"></div>
				<div id="login">
					<form:form action="MultigamingCompetitionSystem/index" method="post" commandName="userForm">
						<p> Username :<form:input class="loginTextField" path="username" /> </p>
						<p>	Password :<form:password class="loginTextField" path="password" /></p>
						<p> <input type="submit" value="Login" /> </p>                    
						</form:form>
						<input type="submit" value="signIn"/>
				</div>
				<ul id="menuList">
					<li class="menuListItems" id="firstItemOfTheMenu">
						<a href="http://www.google.com">Informazioni sul gioco</a>
					</li>
					<li class="menuListItems">
						<a href="http://www.google.com">Home</a>
					</li>
					<li class="menuListItems">
						<a href="http://www.google.com">Ranking</a>
					</li >
					<li class="menuListItems" id="lastItemOfTheMenu">
						<a href="http://www.google.com">Contacts</a>
					</li>
				</ul>
            </div>
            <div id="content">
            </div>           
        </div>
				<script>
					if(${loggedIn}===true){
						document.getElementById("login").innerHTML = "<h1>Welcome ${username}</h1>";
						document.getElementById("login").style.height = "100px";
					}
				</script>
    </body>
</html>
