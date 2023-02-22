<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Login Page</title>
</head>
<body>
	<div class="d-flex justify-content-center">
		<div class="card">
			<div class="card-body">
				<div align="center">
					<h2>Login Here !!</h2>
				</div>
				<c:if test="${param.error !=null}">
					<strong><div class="alert alert-danger">Invalid
							UserName or Password</div></strong>
				</c:if>
				<c:if test="${param.logout !=null}">
					<strong><div class="alert alert-success">You Have
							Been Logged Out Successfully</div></strong>
					</strong>
				</c:if>
				<%@include file="message.jsp"%>
				<form method="POST" action="/loginCheck">
					<div class="form-group">
						<label>Email ID :</label> <input
							class="form-control form-control-sm" type="text" name="username"
							required="required" />
					</div>
					<div class="form-group">
						<label>Password :</label> <input
							class="form-control form-control-sm" type="password"
							name="password" required="required" />
					</div>



					<div align="center">
						<input class="btn btn-success" type="submit" value="LOGIN" />
					</div>

					<div align="center">
						<a href="/registration">New User?</a>
					</div>

					<div align="center">
						<a href="/forgotpassword">Forgot Password ?</a>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>