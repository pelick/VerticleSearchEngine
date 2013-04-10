$(function() {
	// 载入时启动的默认函数
	leftSide();
	// colorfy(document.body, $("#skey").val());
	
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
		var url = "http://localhost:8080/AcademicSearchEngine/field?name="
				+ name;
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var fieldlist = data.fields;
				var len = fieldlist.length;
				for ( var i = 0; i < len; i++) {
					$("#fieldBar").append(
							'<div class="accordion-inner">' + fieldlist[i]
									+ '</div>');
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
		var url = "http://localhost:8080/AcademicSearchEngine/place?name="
				+ name;
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var placelist = data.places;
				var len = placelist.length;
				for ( var i = 0; i < len; i++) {
					$("#placeBar").append(
							'<div class="accordion-inner">' + placelist[i]
									+ '</div>');
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("showPlaces ajax error!");
			}
		});
	}
}

//function colorfy(n, k) {
//
//	var cs = n.childNodes;
//	var i = 0, l = cs.length;
//	var t, c, pos, rest;
//	t = document.createElement('font');
//	t.color = 'red';
//	t.innerHTML = k;
//
//	for (; i < l; i++) {
//		c = cs[i];
//		if (c.nodeType == 3) {
//			t = t.cloneNode(true);
//
//			pos = c.nodeValue.indexOf(k);
//			if (pos != -1) {
//				rest = c.splitText(pos);
//				rest.replaceData(0, k.length, '');
//				n.insertBefore(t, rest);
//			}
//
//		} else
//			yit(cs[i], k);
//	}
//}