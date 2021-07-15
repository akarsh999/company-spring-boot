<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.company.demo.entity.Departments"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!doctype html>
<%
List<Departments> dList = (List<Departments>) request.getAttribute("DeptList");
%>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Employee Authentication</title>
</head>
<body>
	<h3 class="text-center">Department Search</h3>
	<a href="empHome" class="btn btn-primary btn-lg active" role="button"
		aria-pressed="true">Home</a>
	<a href="logout" class="btn btn-primary btn-lg active" role="button"
		aria-pressed="true">Logout</a>
	<nav class="navbar navbar-light bg-light">
		<form action="deptview" class="mt-5">
			<div class="form-group">

				<input class="form-control mr-sm-2 text-center mx-auto px-10"
					type="search" placeholder="Enter Department Name"
					aria-label="Search" name="deptName">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>

			</div>

		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Department id</th>
				<th scope="col">Department Name</th>
				<th scope="col">Department HOD</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Departments d : dList) {
			%>
			<tr>
				<td><%=d.getDept_id()%></td>
				<td><%=d.getDept_name()%></td>
				<td><%=d.getHod()%></td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>






	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>