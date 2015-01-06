<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<!DOCTYPE html>
<html>
	<head>
		<title>Register</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
		<link href="/MultigamingCompetitionSystem/css/register.css" rel="stylesheet" type="text/css"/>
		<link href="/MultigamingCompetitionSystem/css/indexStyle.css" rel="stylesheet" type="text/css"/>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	</head>
	<body>

		<%@include file="../../resources/html/header.html" %>
		<div id="first" class="container">
			<!-- freshdesignweb top bar -->

			<div  class="form">
				<form:form id="registerform" action="/MultigamingCompetitionSystem/register?registering=true" method="post" commandName="userForm"> 
					<p class="contact"><label for="name">Name</label></p> 
					<input id="name" name="name" placeholder="First and last name"  tabindex="1" type="text"> 

					<p class="contact"><label for="email">Email</label></p> 
					<input id="email" name="email" placeholder="example@domain.com"  type="email"> 

					<p class="contact"><label for="username">Create a username</label></p> 
					<div>
						<form:input id="username" name="username" placeholder="username" required="" tabindex="2" type="text" path="username"/> 
					</div>
					<p class="contact"><label for="username">Create a nickname</label></p> 

					<div>
						<form:input id="nickname" name="nickname" placeholder="nickname" required="" tabindex="2" type="text" path="nickname"/> 
					</div>

					<p class="contact"><label for="password">Create a password</label></p> 
					<div>
						<form:input type="password" id="password" name="password" required="" path="password" /> 
					</div>
					<p class="contact"><label for="repassword">Confirm your password</label></p> 
					<input type="password" id="repassword" name="repassword" required=""/> 

					<fieldset>
						<label>Birthday</label>
						<label class="month"> 
							<select class="select-style" name="BirthMonth">
								<option value="">Month</option>
								<option  value="01">January</option>
								<option value="02">February</option>
								<option value="03" >March</option>
								<option value="04">April</option>
								<option value="05">May</option>
								<option value="06">June</option>
								<option value="07">July</option>
								<option value="08">August</option>
								<option value="09">September</option>
								<option value="10">October</option>
								<option value="11">November</option>
								<option value="12" >December</option>
						</label>
						</select>    
						<label>Day<input class="birthday" maxlength="2" name="BirthDay"  placeholder="Day" required=""></label>
						<label>Year <input class="birthyear" maxlength="4" name="BirthYear" placeholder="Year" required=""></label>
					</fieldset>

					<select class="select-style gender" name="gender">
						<option value="select">i am..</option>
						<option value="m">Male</option>
						<option value="f">Female</option>
						<option value="others">Other</option>
					</select><br><br>

					<p class="contact"><label for="phone">Mobile phone</label></p> 
					<input id="phone" name="phone" placeholder="phone number" required="" type="text"> <br>
					<input class="buttom" name="submit" id="submit" tabindex="5" value="Sign me up!" type="submit"> 	 
				</form:form> 
			</div>      
		</div>
		<%@include file="../../resources/html/footer.html" %>
		<script>
			var W3CDOM = (document.getElementsByTagName && document.createElement);
			function writeError(obj, message) {
				console.log(obj.id);
				console.log(message);
				validForm = false;
				if (obj.hasError)
					return;
				if (W3CDOM) {
					obj.onchange = removeError;
					var sp = document.createElement('p');
					sp.className = 'error';
					sp.id=obj.id+'error';
					sp.appendChild(document.createTextNode(message));
					obj.parentNode.appendChild(sp);
					obj.hasError = sp;
				}
				else {
					errorstring += obj.name + ': ' + message + '\n';
					obj.hasError = true;
				}

			}

			function removeError()
			{
				$('#'+this.id+'error').remove();
				this.hasError = null;
				this.onchange = null;
			}

			if (${passwordWrong}) {
				writeError(document.getElementById('password'), 'The two passwords are not the same');
			}
			if (${nickUnavailable}) {
				writeError(document.getElementById('nickname'), 'The nickname is not available');
			}
			if (${userUnavailable}) {
				writeError(document.getElementById('username'), 'The username is not available');
			}
		</script>
	</body>
</html>

