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
<title>Change Password</title>
</head>
<body>
	<div class="d-flex justify-content-center">
		<div class="card">
			<div class="card-body">
				<div align="center">
					<h2>Change Password !!</h2>
				</div>
				<c:if test="${error !=null}"><strong><div class="alert alert-danger">${error}</div></strong></c:if>
				<%@include file="message.jsp"%>
				<form method="POST" action="/change-old-password">
				<input type="hidden" value="${user.id}" name="id" />
					<div class="form-group">
						<label>OLD PASSWORD :</label> <input
							class="form-control form-control-sm" type="password" name="old-password"
						 />
					</div>
					<div class="form-group">
						<label>NEW PASSWORD :</label> <input
							class="form-control form-control-sm" type="password" name="new-password"
						 />
					</div>
					<div class="form-group">
						<label>CONFIRM PASSWORD :</label> <input
							class="form-control form-control-sm" type="password"
							name="confirm-password" />
					</div>
					
					<div align="center">
						<input class="btn btn-success" type="submit" value="SUBMIT" />
					</div>

				</form>
			</div>
		</div>
	</div>

</body>
</html>