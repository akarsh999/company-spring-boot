<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
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

<title>Employee Information</title>
</head>
<body>
	<form action="saveEmp" method="post" modelAttribute="Employee">
		<h3 class="text-center">Employee Detail Form</h3>
		<div class="form-group mx-sm-5 mb-2">
			<label for="exampleInputEmail1" class="form-label">User id</label> <input
				type="text" class="form-control" id="exampleInputEmail1"
				placeholder="Enter User ID" name="emp_id"
				aria-describedby="emailHelp">
			<div id="emailHelp" class="form-text">We'll never share your
				details with anyone else.</div>
		</div>
		<div class="row">
			<div class="col mx-sm-5">
				<label for="exampleInputEmail1" class="form-label">First
					Name</label> <input type="text" class="form-control"
					placeholder="Enter First name" name="first_name">
			</div>
			<div class="col mx-sm-5 mb-3">
				<label for="exampleInputEmail1" class="form-label">Last Name</label>
				<input type="text" class="form-control"
					placeholder="Enter Last name" name="last_name">
			</div>
		</div>
		<!-- <div class="mb-3">
			<label for="exampleInputEmail1" class="form-label">Designation</label>
			<input type="text" class="form-control" id="exampleInputEmail1"
				name="designation" aria-describedby="emailHelp">
		</div> -->
		<div class="form-group mx-sm-5 mb-2">
			<label for="exampleFormControlSelect1">Designation</label> <select
				class="form-control" id="exampleFormControlSelect1"
				name="designation">
				<option selected>Choose...</option>
				<option>SoftwareEngineer</option>
				<option>SeniorSoftwareEngineer</option>
				<option>TechnicalLead</option>
				<option>TechnicalManager</option>
				<option>HOD</option>
				<option>DataAnalyst</option>
				<option>FrontEndDeveloper</option>
				<option>BackEndDeveloper</option>
				<option>BusinessAnalyst</option>
				<option>DevOpsManager</option>
				<option>VP</option>
			</select>
		</div>
		<div class="mx-sm-5 mb-2">
			<label for="exampleInputEmail1" class="form-label">Department</label>
			<input type="text" class="form-control" id="exampleInputEmail1"
				placeholder="Enter Department name" name="dept_name"
				aria-describedby="emailHelp">
		</div>
		<div class="mx-sm-5 mb-2">
			<label for="exampleInputEmail1" class="form-label">Mobile
				Number</label> <input type="text" class="form-control"
				placeholder="Enter Mobile Number" id="exampleInputEmail1"
				name="mob_no" aria-describedby="emailHelp">
		</div>
		<div class="mx-sm-5 mb-2">
			<label for="exampleInputEmail1" class="form-label">Email</label> <input
				type="text" class="form-control" id="exampleInputEmail1"
				placeholder="Enter Email ID" name="email"
				aria-describedby="emailHelp">
		</div>
<!-- 		<div class="mx-sm-5 mb-2">
			<label for="exampleInputEmail1" class="form-label">Resigned</label> <input
				type="text" class="form-control" id="exampleInputEmail1"
				placeholder="Enter Resignation Status" name="hasResigned"
				aria-describedby="emailHelp">
		</div> -->
		<div class="form-group">
			<span class="mx-sm-5 mb-3"> Has Resigned</span>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type=radio name="hasResigned"
					id="inlineRadio1" value="true">
				<label class="form-check-label" for="inlineRadio1">Yes</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type=radio name="hasResigned"
					id="inlineRadio2" value="false">
				<label class="form-check-label" for="inlineRadio2">No</label>
			</div>
		</div>
		<button type="submit" class="btn btn-primary mx-sm-5 mb-2">Submit</button>
	</form>

	<!-- Optional JavaScript; choose one of the two! -->

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<!-- Option 2: Separate Popper and Bootstrap JS -->
	<!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    -->
</body>
</html>