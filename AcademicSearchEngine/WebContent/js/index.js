$(function() {
	// 载入时启动的默认函数
	leftSide();
	
	// 行为绑定
	$(".author_tooltip").on("mouseover", function(e) {
		$(this).tooltip('show');
	});
	
	//home.jsp
	$('#myCarousel').carousel();
});

function leftSide() {
	if ($("#stype").val() == "core0") {
		showFields();
		showPlaces();
	} else {
		$("#stat_left").hide();
	}
}

function showFields() {
	var name = $("#skey").val();
	if (name != "") {
		var url = "http://localhost:8080/AcademicSearchEngine/field?name=" + name;
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var fieldlist = data.fields;
				var len = fieldlist.length;
				for ( var i = 0; i < len; i++) {
					$("#fieldBar").append('<div class="accordion-inner">' + fieldlist[i] + '</div>');
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("showFields ajax error!");
			}
		});
	}
}

function showPlaces() {
	var name = $("#skey").val();
	if (name != "") {
		var url = "http://localhost:8080/AcademicSearchEngine/place?name=" + name;
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var placelist = data.places;
				var len = placelist.length;
				for ( var i = 0; i < len; i++) {
					$("#placeBar").append('<div class="accordion-inner">' + placelist[i] + '</div>');
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("showPlaces ajax error!");
			}
		});
	}
}