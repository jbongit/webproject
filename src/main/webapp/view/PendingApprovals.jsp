<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/user_style.css">
<title>Pending Approvals</title>
</head>
<body>
	<div class="logout-dsgn">
		<a href="/logout"><button class="btn btn-danger">
				<strong>LOGOUT</strong>
			</button></a>	
	</div>
	<div class="approval-dsgn">
		<a href="dashboard"><button class="btn btn-info">
				<strong>ADMIN DASHBOARD</strong>
			</button></a>	
	</div>
	<div class="container">
		<h1 class="hdng-dsgn">PENDING APPROVALS</h1>
		<br>
		<table class="table table-striped table-dsgn" border="1">
			<thead style="font-weight:bold">
				<tr>
					<td>ID</td>
					<td>Name</td>
					<td>Email ID</td>
					<td>Profile Image</td>
				</tr>
			</thead>

			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.id}</td>
					<td><a href="user/${user.id}">${user.name}</a></td>
					<td>${user.emailid}</td>
					<td><img src="/img/${user.imageURL}" width="100"></td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>