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
		var name = $("#skey").val();
		var core = $("#stype").val();
		var field = $("#sfield").val();
		var place = $("#splace").val();
		showFieldsAndPlaces(name, core, field, place);
	} else {
		$("#stat_left").hide();
	}
}

function showFieldsAndPlaces(name, core, field, place) {
	if (name != "") {
		var url = "";
		if (field == "" && place == "") {
			url = "http://localhost:8080/AcademicSearchEngine/fieldplace?name="+name;
		} else if (place == "") {
			url = "http://localhost:8080/AcademicSearchEngine/fieldplace?name="+name+"&field="+field;
		} else {
			url = "http://localhost:8080/AcademicSearchEngine/fieldplace?name="+name+"&field="+field+"&workplace="+place;
		}
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var fieldlist = data.fields;
				var len1 = fieldlist.length;
				
				for ( var i = 0; i < len1; i++) {
					if (place == "") {
						$("#fieldBar").append('<div class="accordion-inner"><a href="academic?key='+name+
							'&core='+core+'&field='+fieldlist[i]+'">'+fieldlist[i]+'</a></div>');
					} else {
						$("#fieldBar").append('<div class="accordion-inner"><a href="academic?key='+name+
							'&core='+core+'&field='+fieldlist[i]+'&workplace='+place+'">'+fieldlist[i]+'</a></div>');
					}
				}
				
				
				var placelist = data.places;
				var len2 = placelist.length;
				
				for ( var j = 0; j < len2; j++) {
					if (field == "") {
						$("#placeBar").append('<div class="accordion-inner"><a href="academic?key='+name+
							'&core='+core+'&workplace='+placelist[j]+'">'+placelist[j]+'</a></div>');
					} else {
						$("#placeBar").append('<div class="accordion-inner"><a href="academic?key='+name+
							'&core='+core+'&field='+field+'&workplace='+placelist[j]+'">'+placelist[j]+'</a></div>');
					}
				}

			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("showFields ajax error!");
			}
		});
	}
}
