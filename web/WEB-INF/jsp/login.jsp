<%-- 
    Document   : login
    Created on : 3-dic-2014, 10.13.57
    Author     : Denise
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="MultigamingCompetitionSystem/css/loginStyle.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
    <body>
		<div id="main">
       <h1>Login</h1>
		<form:form action="login" method="post" commandName="userForm">
			<p> User Name:<form:input path="username" /></p>
			<p> Password :<form:password path="password" /></p>
			<p> <input type="submit" value="Login" /> </p>                    
		</form:form>
		</div>
    </body>
</html>
