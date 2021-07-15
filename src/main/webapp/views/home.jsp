<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>WElcome to Home Page</title>
</head>
<body>
	<form action="authenticate" method="post">
		Enter UserName : <input type="text" name="UserName"><br>
		Enter PassWord : <input type="password" name="PassWord"><br>
		<input type="submit">
	</form>
</body>
</html> -->
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

<title>Employee Authentication</title>
</head>
<body>
	<form action="authenticate" method="post">
		<h3 class="text-center">Login Form</h3>
		<div class="mx-sm-5 mb-3">
			<label for="exampleInputEmail1" class="form-label">User id</label> <input type="text" class="form-control"
				id="exampleInputEmail1" name="UserName" aria-describedby="emailHelp">
			<div id="emailHelp" class="form-text">We'll never share your
				details with anyone else.</div>
		</div>
		<div class="mx-sm-5 mb-3">
			<label for="exampleInputPassword1" class="form-label">Password</label>
			<input type="password" name="PassWord" class="form-control"
				id="exampleInputPassword1">
		</div>
		<button type="submit" class="btn btn-primary mx-sm-5 mb-3">Submit</button>
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