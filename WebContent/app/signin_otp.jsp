<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Shiro OTP Signin</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/signin.css" rel="stylesheet">
</head>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="com.msi.shiro.demo.params.SessionParamNames"%>
<body>

	<div class="container">
		<div id="mini_header_contain"></div>
		<%
			String errorDescription = (String) request
					.getAttribute("applicationLoginFailure");
			if (errorDescription != null) {
		%>
		<h3 align="center" style="color: red">
			Login attempt was unsuccessful:
			<%=errorDescription%></h3>
		<%
			}
		%>
		<form class="form-signin" action="signin_otp.jsp" method="post"
			enctype="application/x-www-form-urlencoded">
			<%
				String reqUrl = (String) SecurityUtils
						.getSubject()
						.getSession()
						.getAttribute(
								SessionParamNames.CSESSION__REDIRECT_URL_AFTER_ESCALATION);
			%>
			<input type="hidden" name="originalPage" value="<%=reqUrl%>" />
			<h2 class="form-signin-heading">Please provide OTP</h2>			
			<label for="otp" class="sr-only">OTP</label> 
			<input id="otp" class="form-control" name="otp" type="password" 
			placeholder="Password" required autofocus/>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>

	</div>
	<!-- /container -->
</body>
</html>