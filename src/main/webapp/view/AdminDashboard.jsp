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
<title>Admin DashBoard</title>
</head>
<body>
	<div class="logout-dsgn">
		<a href="/logout"><button class="btn btn-danger">
				<strong>LOGOUT</strong>
			</button></a>	
	</div>
	<div class="approval-dsgn">
		<a href="pending-approvals"><button class="btn btn-info">
				<strong>PENDING APPROVALS</strong>
			</button></a>	
	</div>
	<div class="reportbtn-dsgn">
		<a href="/admin/report"><button class="btn btn-success">
				<strong>GENERATE REPORT</strong>
			</button></a>	
	</div>
	<div class="container">
		<h1 class="hdng-dsgn">USERS INFORMATION</h1>
		<br>
		<div class="search-container">
		<form action="/admin/search" method="POST">
		<input id="searchQuery" type="text" name="searchQuery" class="form-control form-control-sm search-dsgn" placeholder="Search User By Name">
		<div class="search-btn"><input type="submit" class="btn btn-success" value="Search" ></div>
		</form>
		
		</div>
		<table class="table table-striped table-dsgn" border="1">
			<thead style="font-weight:bold">
				<tr>
					<td>ID</td>
					<td>Name</td>
					<td>Email ID</td>
					<td>Profile Image</td>
					<td>Approval Status</td>
				</tr>
			</thead>

			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.emailid}</td>
					<td><img src="/img/${user.imageURL}" width="100"></td>
					<td><a href="user/${user.id}">${user.status}</a></td>
				</tr>
			</c:forEach>
		</table>

	</div>
</body>
</html>