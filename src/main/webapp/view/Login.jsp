<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Login Page</title>
</head>
<body>
	<div class="container">
		<div align="center">
			<h1>Login Here !!</h1>
		</div>
		<c:if test="${param.error !=null}"><div class="alert alert-danger">Invalid UserName or Password</div></c:if>
		<c:if test="${param.logout !=null}"><div class="alert alert-success">You Have Been Logged Out Successfully</div></c:if>
		<div>
		</div>
		<form method="POST" action="/loginCheck">
			<div class="form-group">
				<label>Email ID :</label> <input
					class="form-control form-control-sm" type="text" name="username"
					required="required" />
			</div>
			<div class="form-group">
				<label>Password :</label> <input
					class="form-control form-control-sm" type="text" name="password"
					required="required" />
			</div>
			
			

			<div align="center">
				<input class="btn btn-success" type="submit" value="LOGIN" />
			</div>

			<div align="center">
				<a href="/registration">New User?</a>
			</div>
		</form>
	</div>

</body>
</html>