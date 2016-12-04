$(function(){
	$("#tabs").tabs();
	$(".addCourse").click(function(){
		$("#course-form").find("input[name='courseCode']").val("");
		$("#course-form").find("input[name='courseName']").val("");
		$("#course-form").find("input[name='courseUnits']").val(3);
		$("#course-form").find("input[name='courseId']").val("");
		$("#course-form").dialog("open");
	});
	$(".editCourse").click(function(){
		var code =$(this).closest("tr").find("td[data-field='code']").html();
		var name = $(this).closest("tr").find("td[data-field='name']").html();
		var units = $(this).closest("tr").find("td[data-field='units']").html();
		var id = $(this).closest("tr").attr("data-course-id");
		$("#course-form").find("input[name='courseCode']").val(code);
		$("#course-form").find("input[name='courseName']").val(name);
		$("#course-form").find("input[name='courseUnits']").val(units);
		$("#course-form").find("input[name='courseId']").val(id);
		$("#course-form").dialog("open");
	});
	$(".viewProgram").click(viewProgram);
	$(".addGrade").click(function(){
		$("#grade-form").dialog("open");
	});
	
	$("#course-form").dialog({
		autoOpen: false,
		minWidth: 500,
		title: "Add/Edit Course",
		close: function(event, ui){
			$('input[name=courseId]').val('')
		},
	    buttons: {
	        "Save": function(){
	        	if(!$("input[name='courseId']").val()) addCourse();
	        	else editCourse();
	            $(this).dialog("close");
	        }
	    }
	});				
	$("#program-detail").dialog({
		autoOpen: false,
		minWidth: 500,
		title: "Program Detail"
	});
	$("#grade-form").dialog({
		autoOpen: false,
		minWidth: 500,
		title: "Add Grade",
		buttons: {
	        "Save": function(){
	        	addGrade();
	            $(this).dialog("close");
	        }
	    }
		
	});
});

function editCourse(){
	var code = $("input[name='courseCode']").val();
	var name = $("input[name='courseName']").val();
	var units = $("input[name='courseUnits']").val();
	var id = $("input[name='courseId']").val();
    $.ajax({
        url: "service/course/" + id,
        method: "PUT",
        dataType: "json",
        processData: false,
        contentType: "application/json",
        data: JSON.stringify({
            code: code,
            name: name,
            units: units
        }),
        statusCode:{
        	200: function(){
        		var row = $("#courses").find("tr[data-course-id='" + id + "']");
        		row.find("td[data-field='code']").html(code);
        		row.find("td[data-field='name']").html(name);
        		row.find("td[data-field='units']").html(units);
            }
		}
    });
}

function addCourse(){
	var code = $("input[name='courseCode']").val();
	var name = $("input[name='courseName']").val();
	var units = $("input[name='courseUnits']").val();
    $.ajax({
        url: "service/courses",
        method: "POST",
        dataType: "json",
        processData: false,
        contentType: "application/json",
        data: JSON.stringify({
            code: code,
            name: name,
            units: units
        }),
        success: function(data){
            var newRow = $("<tr data-course-id='" + data.id + "'>" +
                "<td data-field='code'>" + data.code + "</td>" +
                "<td data-field='name'>" + data.name + "</td>" +
                "<td data-field='units' style='text-align: center;'>" + data.units + "</td>" +
                "<td><a class='editCourse' href='javascript:void(0)'>Edit</a></td>"
            )
            $("#courses").append(newRow);
            newRow.find(".editCourse").click(function(){
        		var code =$(this).closest("tr").find("td[data-field='code']").html();
        		var name = $(this).closest("tr").find("td[data-field='name']").html();
        		var units = $(this).closest("tr").find("td[data-field='units']").html();
        		var id = $(this).closest("tr").attr("data-course-id");
        		$("#course-form").find("input[name='courseCode']").val(code);
        		$("#course-form").find("input[name='courseName']").val(name);
        		$("#course-form").find("input[name='courseUnits']").val(units);
        		$("#course-form").find("input[name='courseId']").val(id);
        		$("#course-form").dialog("open");
        	});
        }
    });
}

function viewProgram(){
    var id = $(this).closest("tr").attr("data-program-id");
    $.ajax({
        url: "service/program/" + id,
        contentType : "application/json",
        dataType: "json",
        success: function(program){
        	var newHtml = "<h3>" + program.department.name + ": " + program.name + "</h3>" +
        		"<p>" + ((program.description) ? program.description : "No Description Available") + "</p>";
        	$.each(program.blocks, function(blockIndex, block){
        		newHtml += "<h4>" + blockIndex + ". " + block.name + "</h4>" +
        			"<p>" + ((block.description) ? block.description : "No Description Available") + "</p>" +
        			"<table border='1'>";
        		$.each(block.courses, function(courseIdex, course){
        			newHtml += "<tr><td>" + course.code + "</td><td>" + course.name + "</td></tr>";
        		});
        		newHtml += "</table>";
        	});
        	$("#program-detail").html(newHtml);
        }
    });
    $("#program-detail").dialog("open");
}

function addGrade(){
	var season = $("select[name='season']").val();
	var year = $("select[name='year']").val();
	var courseId = $("select[name='courseId']").val();
	var gradeId = $("select[name='gradeId']").val();
    $.ajax({
        url: "service/grades",
        method: "POST",
        dataType: "json",
        processData: false,
        contentType: "application/json",
        data: JSON.stringify({
        	season: season,
        	year: year,
        	courseId: courseId,
        	gradeId: gradeId
        }),
        success: function(gradeRecord){
        	var newRow = $(
        		"<tr data-grade-record-id='" + gradeRecord.id + "'>" +
					"<td>" + gradeRecord.term.fullName + "</td>" +
					"<td>" + gradeRecord.course.code + "</td>" +
					"<td style='text-align: center'>" + gradeRecord.grade.symbol + "</td>" +
				"</tr>");
            $("#grades").append(newRow);
        }
    });
}