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
	<div class="d-flex justify-content-center">
	<div class="card">
		<div class="card-body">
		<div align="center">
			<h2> Forgot Password !!</h2>
		</div>
		
		<%@include file="message.jsp"%>	
		<c:if test="${error !=null}"><strong><div class="alert alert-danger">${error}</div></strong></c:if>
		<form method="POST" action="/verify">
			<div class="form-group">
				<label>Email ID :</label> <input
					class="form-control form-control-sm" type="text" name="emailid" id="emailid" value="${emailid}"
					required="required" />
			</div>

			<div align="center">
				<a class="btn btn-success" href="#" onclick="getValue()">SUBMIT</a>
			</div>
			
			<div class="form-group">
				<label>OTP :</label> <input
					class="form-control form-control-sm" type="text" name="otp"
					required="required" />
			</div>

			<div align="center">
				<input class="btn btn-success" type="submit" value="VERIFY" />
			</div>

			<div align="center">
				<a href="/login">Login Now !!</a>
			</div>
		</form>
	</div>
	</div>
	</div>
	
	<script type="text/javascript">
	function getValue(){
		var text=document.getElementById("emailid");
		var textValue=text.value;
		window.location.href="/sendOTP?emailid="+textValue;
	}
	</script>
	
</body>
</html>