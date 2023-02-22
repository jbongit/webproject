<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
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
<title>Registration Page</title>
</head>
<body>	
	<div class="d-flex justify-content-center">
		<div class="card">
			<div class="card-body">
				<div align="center" class="card-title">
					<h2>Registration Form !!</h2>
				</div>

				<form method="POST" action="/saveUser" enctype="multipart/form-data">
					<%@include file="message.jsp"%>
					<div class="form-group">
						<label>Name :</label> <input class="form-control form-control-sm"
							type="text" name="name" value="${user.name}" required="required" />
						<c:if test="${nameError != null}">
							<div class="invalid-feedback d-block">${nameError}</div>
						</c:if>
					</div>
					<div class="form-group">
						<label>Email ID :</label> <input
							class="form-control form-control-sm" type="text" name="emailid"
							value="${user.emailid}" required="required" />
						<c:if test="${emailidError != null}">
							<div class="invalid-feedback d-block">${emailidError}</div>
						</c:if>
					</div>

					<div class="form-group">
						<label>Password :</label> <input
							class="form-control form-control-sm" type="password"
							name="password" value="${user.password}" required="required" />
						<c:if test="${passwordError != null}">
							<div class="invalid-feedback d-block">${passwordError}</div>
						</c:if>
					</div>

					<div class="form-group">
						<label> Mobile No. :</label> <input
							class="form-control form-control-sm" type="text" name="mobileno"
							value="${user.mobileno}" required="required" />
						<c:if test="${mobilenoError != null}">
							<div class="invalid-feedback d-block">${mobilenoError}</div>
						</c:if>
					</div>

					<div class="form-group">
						<label> Image :</label> <input type="file"
							class="form-control-file" id="profileImage" name="profileImage"
							accept="image/*" required="required" />
					</div>
					<div align="center">
						<input class="btn btn-success" type="submit" value="SUBMIT" />
					</div>
					<div align="center">
						<a href="/login">Login Now !!</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>