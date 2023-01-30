<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Update Page</title>
</head>
<body>
	<div class="container">
		<div align="center">
			<h1>Update Details !!</h1>
		</div>

		<form method="POST" action="/updateUser" enctype="multipart/form-data">
			<%@include file="message.jsp"%>
			<input type="hidden" value="${user.id}" name="id" />
			<div class="form-group">
				<label>Name :</label> <input class="form-control form-control-sm"
					type="text" name="name" value="${user.name}" required="required" />
			</div>
			<div class="form-group">
				<label>Email ID :</label> <input
					class="form-control form-control-sm" type="text" name="emailid" value="${user.emailid}"
					required="required" />
			</div>

			<div class="form-group">
				<label>Password :</label> <input
					class="form-control form-control-sm" type="text" name="password" value="${user.password}"
					required="required" />
			</div>

			<div class="form-group">
				<label> Mobile No. :</label> <input
					class="form-control form-control-sm" type="text" name="mobileno" value="${user.mobileno}"
					required="required" />
			</div>

			<div class="form-group">
				<label> Image :</label> <input type="file" class="form-control-file"
					id="profileImage" name="profileImage" accept="image/*" />
			</div>
			<div align="center">
				<input class="btn btn-success" type="submit" value="SUBMIT" />
			</div>
		</form>
	</div>
</body>
</html>