function getData() {
	$.ajax({
		url: "http://localhost:8080/rest/api/users",
		type: "GET",
		dataType: "json",
		success: function(data) {
			console.log(data);
			$.each(data, function(index){
				var tr = $('<tr>');
				tr.append('<td>' + data[index].id + '</td>');
				tr.append('<td>' + data[index].name + '</td>');
				tr.append('</tr>');
				$('#usersTable').append(tr);
			});
		}
	});
}

$(document).ready(function() {
	$('#button').click(function() {
		alert('button clicked');
		getData();
	});
});