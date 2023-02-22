<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Forgot Password</title>
</head>
<body>
	<div class="container">
		<div align="center">
			<h1> Forgot Password !!</h1>
		</div>
		
		<%@include file="message.jsp"%>	
		
		<form method="POST" action="/change-password">
			<div class="form-group">
				<label>Enter Password :</label> <input
					class="form-control form-control-sm" type="password" name="password" id="password"
					required="required" />
			</div>


			<div align="center">
				<input class="btn btn-success" type="submit" value="CHANGE PASSWORD" />
			</div>

		</form>
	</div>
	
</body>
</html>