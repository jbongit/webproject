
<%
String message = (String) session.getAttribute("message");
if (message != null) {
%>

<div class="alert alert-success" role="alert">
	<strong>${message} --> <a class="linkdsgn" href="/login">Login Now !!</a></strong>
	
</div>

<%
session.removeAttribute("message");
}
%>

<%
String success = (String) session.getAttribute("success");
if (success!=null) {
%>

<div class="alert alert-success" role="alert">
	<strong>${success} --> <a class="linkdsgn" href="/user/${user.emailid}">Go To User DashBoard!!</a></strong>
	
</div>

<%
session.removeAttribute("success");
}
%>