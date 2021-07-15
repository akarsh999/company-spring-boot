<%@page import="com.company.demo.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!doctype html>
<%
List<Employee> eList = (List<Employee>) request.getAttribute("EmpList");
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


	<h3 class="text-center">Employee Search</h3>
	<a href="empHome" class="btn btn-primary btn-lg active" role="button"
		aria-pressed="true">Home</a>
	<a href="empviewAll" class="btn btn-primary btn-lg active"
		role="button" aria-pressed="true">All Employee</a>
	<a href="logout" class="btn btn-primary btn-lg active" role="button"
		aria-pressed="true">Logout</a>
	<nav class="navbar navbar-light bg-light">

		<form action="empview" class="mt-5">
			<div class="form-group">

				<input class="form-control mr-sm-2 text-center mx-auto px-10"
					type="search" placeholder="Enter Employee id" aria-label="Search"
					name="empID">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>

			</div>
		</form>
	</nav>


	<table class="table">
		<thead>
			<tr>
				<th scope="col">Employee id</th>
				<th scope="col">First Name</th>
				<th scope="col">Last Name</th>
				<th scope="col">Designation</th>
				<th scope="col">Department</th>
				<th scope="col">Email</th>
				<th scope="col">Mobile Number</th>
				<th scope="col">Resigned</th>
				<th scope="col">Edit Details</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Employee e : eList) {
			%>
			<tr>
				<td><%=e.getEmp_id()%></td>
				<td><%=e.getFirst_name()%></td>
				<td><%=e.getLast_name()%></td>
				<td><%=e.getDesignation()%></td>
				<td><%=e.getDept_name()%></td>
				<td><%=e.getEmail()%></td>
				<td><%=e.getMob_no()%></td>
				<td><%=e.isHasResigned()%></td>
				<td><a href="empEdit/<%=e.getEmp_id()%>" class="btn btn-primary"
					role="button" aria-pressed="true">Edit</a></td>

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