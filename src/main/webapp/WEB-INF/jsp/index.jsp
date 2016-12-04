<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<title>OnCourse</title>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script src="<c:url value='/js/index.js' />"></script>
	</head>
	<body>
		<h2>OnCourse</h2>
		<p>
			<security:authorize access="anonymous">
				<a href="<c:url value='/login' />">Login</a>
			</security:authorize>
			<security:authorize access="authenticated">
				<a href="<c:url value='/logout' />">Logout</a>
			</security:authorize>
		</p>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">Home</a></li>
				<security:authorize access="authenticated">
					<security:authorize access="hasAnyRole('ADMIN','ADVISOR')">
						<li><a href="#tabs-2">Courses</a></li>
						<li><a href="#tabs-3">Departments</a></li>
						<li><a href="#tabs-4">Programs</a></li>
					</security:authorize>
					<li><a href="#tabs-5">Grades</a></li>
				</security:authorize>
			</ul>
			<div id="tabs-1">
				<p>OnCourse is a system that helps students to stay on course in their academic career.</p>
			</div>
			<security:authorize access="authenticated">
				<security:authorize access="hasAnyRole('ADMIN','ADVISOR')">
					<div id="tabs-2">
						<h3>Courses</h3>
						<p><a class="addCourse" href="javascript:void(0)">Add Course</a></p>
						<table id="courses" border="1">
							<tr><th>Code</th><th>Name</th><th>Units</th><th></th></tr>
							<c:forEach items="${courses}" var="course">
								<tr data-course-id="${course.id}">
									<td data-field="code">${course.code}</td>
									<td data-field="name">${course.name}</td>
									<td data-field="units" style="text-align: center;">${course.units}</td>
									<td><a class="editCourse" href="javascript:void(0)">Edit</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="tabs-3">
						<h3>Departments</h3>
						<table border="1">
							<tr><th>Name</th><th>Programs</th></tr>
							<c:forEach items="${departments}" var="department">
								<tr>
									<td>${department.name}</td>
									<td style="text-align: center">${fn:length(department.programs)}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="tabs-4">
						<h3>Programs</h3>
						<table border="1">
							<tr><th>Department</th><th>Name</th></tr>
							<c:forEach items="${programs}" var="program">
								<tr data-program-id="${program.id}">
									<td>${program.department.name}</td>
									<td><a class="viewProgram" href="javascript:void(0)">${program.name}</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</security:authorize>
				<div id="tabs-5">
					<h3>${user.name}'s Grades</h3>
					<p><a class="addGrade" href="javascript:void(0)">Add Grade</a></p>				
					<table id="grades" border="1">
						<tr><th>Term</th><th>Course</th><th>Grade</th></tr>
						<c:forEach items="${gradeRecords}" var="gradeRecord">
						<tr data-grade-record-id="${gradeRecord.id}">
							<td>${gradeRecord.term.fullName}</td>
							<td>${gradeRecord.course.code}</td>
							<td style="text-align: center">${gradeRecord.grade.symbol}</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</security:authorize>
		</div>
		<div id="course-form">
			<form>
				<table>
					<tr><th>Code</th><td><input name="courseCode" type="text" /></td></tr>
					<tr><th>Name</th><td><input name="courseName" type="text" /></td></tr>
					<tr><th>Units</th><td><input name="courseUnits" type="number" /></td></tr>
				</table>
				<input name="courseId" type="hidden" />
			</form>
		</div>
		<div id="program-detail">
		</div>
		<div id="grade-form">
			<form>
				<p>
					<select name="season">
						<option value="F">Fall</option>
						<option value="W">Winter</option>
						<option value="S">Spring</option>
						<option value="X">Summer</option>
					</select>
					<select name="year">
						<c:forEach items="${years}" var="year">
							<option>${year}</option>
						</c:forEach>
					</select>
					<select name="courseId">
						<c:forEach items="${courses}" var="course">
							<option value="${course.id}">${course.code}</option>
						</c:forEach>
					</select>
					<select name="gradeId">
						<c:forEach items="${grades}" var="grade">
							<option value="${grade.id}">${grade.symbol}</option>
						</c:forEach>
					</select>
				</p>
			</form>
		</div>
	</body>
</html>
