<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/user_style.css">
<title>User DashBoard</title>
</head>
<body>
	<div class="logout-dsgn">
		<a href="/logout"><button class="btn btn-danger">
				<strong>LOGOUT</strong>
			</button></a>
	</div>
	<div class="container mt-5">

		<div class="row d-flex justify-content-center">
			<div class="col-md-7">

				<div class="card p-3 py-4">

					<div class="text-center">
						<img src="/img/${user.imageURL}" width="400">
					</div>

					<div class="text-center mt-3">

						<table class="table table-striped" border="1">


							<tr>
								<td class="boldf">User ID</td>
								<td>${user.id}</td>
							</tr>

							<tr>
								<td class="boldf">Name</td>
								<td>${user.name}</td>
							</tr>

							<tr>
								<td class="boldf">Email ID</td>
								<td>${user.emailid}</td>
							</tr>

							<tr>
								<td class="boldf">Mobile No</td>
								<td>${user.mobileno}</td>
							</tr>

						</table>

						<div class="buttons">
						
								<a href="${user.id}/approved"
							onclick="if(!(confirm('Are You Sure You Want To Approve This User?'))) return false">
								<button class="btn btn-outline-primary px-4 approve-btn">Approve</button></a>
						
							<a href="${user.id}/rejected"
								onclick="if(!(confirm('Are You Sure You Want To Reject This User?'))) return false"><button
									class="btn btn-primary px-4 ms-3 reject-btn">Reject</button></a>
									
						</div>

					</div>

				</div>

			</div>

		</div>

	</div>
</body>
</html>