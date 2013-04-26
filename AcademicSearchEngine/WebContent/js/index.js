$(function() {
	// 载入时启动的默认函数
	leftSide();
	highlight(document.body, $("#skey").val());
	$('#related_btn').click(rightSide());
	$('#related_btn').remove();
	
	// index.jsp
	$(".author_tooltip").on("mouseover", function(e) {
		$(this).tooltip('show');
	});

	$(".save_author").on("click", function(e) {
		saveAuthor($(this).attr("saveurl"));
	});
	
	$(".save_paper").on("click", function(e) {
		savePaper($(this).attr("saveurl"));
	});
	
	//home.jsp
	$('#myCarousel').carousel();
	
	//researcher.jsp
	$('#cloudword_btn').on("click", function(e) {
		loading("load_three");
		showWordCloud();
	});
	
	$('.coauthor_btn').on("click", function(e) {
		showVisualization("coauthor", $("#dname").val());
	});
	
	$('.cofield_btn').on("click", function(e) {
		showVisualization("cofield", $("#dfield").val());
	});
	
	$('.coplace_btn').on("click", function(e) {
		showVisualization("coplace", $("#dplace").val());
	});
	
	$('.copaper_btn').on("click", function(e) {
		showVisualization("copaper", $("#dpaper").val());
	});
	
	$('#related_btn').on("click", function(e) {
		rightSide();
	});
});

function saveAuthor(params) {
	$.ajax({
		type : 'GET',
		url : "saveauthor?"+params,
		dataType : 'json',
		success : function(data) {
			// 判断是否登录
			// hint by boostrap alert 
			// change the button
			alert(data.type);
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("saveAuthor ajax error!");
		}
	});
}

function savePaper(params) {
	$.ajax({
		type : 'GET',
		url : "savepaper?"+params,
		dataType : 'json',
		success : function(data) {
			// 判断是否登录
			// hint by boostrap alert 
			// change the button
			alert(data.type);
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("savePaper ajax error!");
		}
	});
}

function showVisualization(action, val) {
	
	$('#graph_one').remove();
	$('#graph_two').remove();
	$('#coauthorOne').append('<div id="load_one"></div><div id="graph_one"></div>');
	$('#coauthorTwo').append('<div id="load_two"></div><div id="graph_two"></div>');
	loading("load_one");
	coauthorOne(action, val);
	loading("load_two");
	coauthorTwo(action, val);
}

function history(action) {
	if (action == "coauthor") {
		$('#history').append('<tr class="info"><td>'+action+'</td><td>'+$("#dname").val()+
		  '</td><td>success</td><td><button class="btn btn-info coauthor_btn" type="button">go</button></td>'+
		  '<td><button class="btn btn-inverse" type="button">del</button></td></tr>');
	} else if (action == "cofield") {
		$('#history').append('<tr class="success"><td>'+action+'</td><td>'+$("#dfield").val()+
		  '</td><td>success</td><td><button class="btn btn-success cofield_btn" type="button">go</button></td>'+
		  '<td><button class="btn btn-inverse" type="button">del</button></td></tr>');
	} else if (action == "coplace") {
		$('#history').append('<tr class="warning"><td>'+action+'</td><td>'+$("#dplace").val()+
		  '</td><td>success</td><td><button class="btn btn-warning coplace_btn" type="button">go</button></td>'+
		  '<td><button class="btn btn-inverse" type="button">del</button></td></tr>');
	} else if (action == "copaper") {
		$('#history').append('<tr class="error"><td>'+action+'</td><td>'+$("#dpaper").val()+
		  '</td><td>success</td><td><button class="btn btn-danger coplace_btn" type="button">go</button></td>'+
		  '<td><button class="btn btn-inverse" type="button">del</button></td></tr>');
	}
		
}

function rightSide() {
	var field = $("#rfield").val();
	var place = $("#rplace").val();
	var name = $("#rname").val();
	if (field != null || place != null || name != null) {
		var url = "http://localhost:8080/AcademicSearchEngine/related?field="+field+"&place="+place+"&name="+name;
		$.ajax({
			type : 'GET',
			url : url,
			dataType : 'json',
			success : function(data) {
				var list = data.researcherlist;
				var len = list.length;
				
				for ( var i = 0; i < len; i++) {
					$("#related_author").append('<p>'+list[i].picurl+'<br/>'+list[i].name+'<br/></p>');
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("Related ajax error!");
			}
		});
	}
}

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

function showWordCloud() {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/AcademicSearchEngine/wordcloud?name="+$("#rname").val(),
		dataType : 'json',
		success : function(data) {
			wordcloud(data.abs);
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("showWordCloud ajax error!");
		}
	});
}

function wordcloud(str) {
	var WIDTH = 900;
	var HEIGHT = 300;
	var ANGLE = 30;
	var fill = d3.scale.category20();

	d3.layout.cloud().size([WIDTH, HEIGHT]).words(str.map(function(d) {
        return {text: d, size: 20 + Math.random() * 50};
    })).rotate(function() { 
    	return ~~(Math.random() * 2) * ANGLE; 
    }).font("Impact").fontSize(function(d) { 
    	return d.size; 
    }).on("end", draw).start();
	
	$('#load_three').remove();
	
	function draw(words) {
		d3.select("#wordcloud").append("svg").attr("width", WIDTH).attr("height", HEIGHT)
          .append("g").attr("transform", "translate(450,150)").selectAll("text").data(words)
          .enter().append("text").style("font-size", function(d) { return d.size + "px"; })
          .style("font-family", "Impact").style("fill", function(d, i) { return fill(i); })
          .attr("text-anchor", "middle").attr("transform", function(d) {
            return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
          }).text(function(d) { return d.text; });
	}
}

function loading(name) {
	var opts = {
		lines : 11, // The number of lines to draw
		length : 9, // The length of each line
		width : 4, // The line thickness
		radius : 7, // The radius of the inner circle
		rotate : 0, // The rotation offset
		color : '#000', // #rgb or #rrggbb
		speed : 1, // Rounds per second
		trail : 45, // Afterglow percentage
		shadow : false, // Whether to render a shadow
		hwaccel : false, // Whether to use hardware acceleration
		className : 'spinner', // The CSS class to assign to the spinner
		zIndex : 2e9, // The z-index (defaults to 2000000000)
		top : 'auto', // Top position relative to parent in px
		left : 'auto' // Left position relative to parent in px
	};
	var target = document.getElementById(name);
	new Spinner(opts).spin(target);
}

// coauther
function coauthorOne(action, val) {
	var url;
    if (action == ("coauthor")) {
    	url = "http://localhost:8080/AcademicSearchEngine/coauthor?name="+val;
    } else if (action == ("cofield")) {
    	url = "http://localhost:8080/AcademicSearchEngine/cofield?field="+val;
    } else if (action == ("coplace")) {
    	url = "http://localhost:8080/AcademicSearchEngine/coplace?place="+val;
    } else if (action == ("copaper")) {
    	url = "http://localhost:8080/AcademicSearchEngine/copaper?text="+val;
    }
	var width = 400, height = 400;
    var color = d3.scale.category20();
    
    var fisheye = d3.fisheye.circular().radius(120);
    
    var force = d3.layout.force().charge(-120).linkDistance(30).size([width, height]);
    var svg = d3.select("#graph_one").append("svg").attr("width", width).attr("height", height);

    d3.json(url, function(error, graph) {
    	$('#load_one').remove();
    	history(action);
    	graph = graph.json;
    	
    	force.nodes(graph.nodes) .links(graph.links).start();

    	var link = svg.selectAll(".link").data(graph.links)
    			      .enter().append("line").attr("class", "link")
    			      .style("stroke-width", function(d) { return Math.sqrt(d.value); });

    	var node = svg.selectAll(".node").data(graph.nodes).enter().append("circle")
    				  .attr("class", "node").attr("r", 5)
    				  .style("fill", function(d) { return color(d.group); })
    				  .call(force.drag);

    	node.append("title").text(function(d) { return d.name; });
    	force.on("tick", function() {
    		link.attr("x1", function(d) { return d.source.x; })
    		    .attr("y1", function(d) { return d.source.y; })
    		    .attr("x2", function(d) { return d.target.x; })
    		    .attr("y2", function(d) { return d.target.y; });
    		node.attr("cx", function(d) { return d.x; })
                .attr("cy", function(d) { return d.y; });
    	});
    	
    	svg.on("mousemove", function() {
    	      fisheye.focus(d3.mouse(this));

    	      node.each(function(d) { d.fisheye = fisheye(d); })
    	          .attr("cx", function(d) { return d.fisheye.x; })
    	          .attr("cy", function(d) { return d.fisheye.y; })
    	          .attr("r", function(d) { return d.fisheye.z * 4.5; });

    	      link.attr("x1", function(d) { return d.source.fisheye.x; })
    	          .attr("y1", function(d) { return d.source.fisheye.y; })
    	          .attr("x2", function(d) { return d.target.fisheye.x; })
    	          .attr("y2", function(d) { return d.target.fisheye.y; });
    	    });
    	
    });
}

function coauthorTwo(action, val) {
	var url;
    if (action == ("coauthor")) {
    	url = "http://localhost:8080/AcademicSearchEngine/coauthor?name="+val;
    } else if (action == ("cofield")) {
    	url = "http://localhost:8080/AcademicSearchEngine/cofield?field="+val;
    } else if (action == ("coplace")) {
    	url = "http://localhost:8080/AcademicSearchEngine/coplace?place="+val;
    } else if (action == ("copaper")) {
    	url = "http://localhost:8080/AcademicSearchEngine/copaper?text="+val;
    }
	var margin = {
		top : 80,
		right : 0,
		bottom : 0,
		left : 100
	}, 
	width = 400, 
	height = 400;
	var x = d3.scale.ordinal().rangeBands([ 0, width ]), 
		z = d3.scale.linear().domain([ 0, 4 ]).clamp(true), 
		c = d3.scale.category10().domain(d3.range(10));
	var svg = d3.select("#graph_two").append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.style("margin-left", -margin.left + "px").append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	d3.json(url, function(miserables) {
		$('#load_two').remove();
		miserables = miserables.json;
		var matrix = [], nodes = miserables.nodes, n = nodes.length;
		// Compute index per node.
		nodes.forEach(function(node, i) {
			node.index = i;
			node.count = 0;
			matrix[i] = d3.range(n).map(function(j) {
				return { x : j, y : i, z : 0};
			});
		});

		// Convert links to matrix; count character occurrences.
		miserables.links.forEach( function(link) {
			matrix[link.source][link.target].z += link.value;
			matrix[link.target][link.source].z += link.value;
			matrix[link.source][link.source].z += link.value;
			matrix[link.target][link.target].z += link.value;
			nodes[link.source].count += link.value;
			nodes[link.target].count += link.value;
		});

		// Precompute the orders.
		var orders = {
			name : d3.range(n).sort(function(a, b) {
				return d3.ascending(nodes[a].name, nodes[b].name);
			}),
			count : d3.range(n).sort(function(a, b) {
				return nodes[b].count - nodes[a].count;
			}),
			group : d3.range(n).sort(function(a, b) {
				return nodes[b].group - nodes[a].group;
			})
		};

		// The default sort order.
		x.domain(orders.name);
		svg.append("rect").attr("class", "background").attr("width", width).attr("height", height);
		var row = svg.selectAll(".row").data(matrix).enter()
					 .append("g").attr("class", "row").attr("transform", function(d, i) {
						 return "translate(0," + x(i) + ")";
					 }).each(row);

		row.append("line").attr("x2", width);
		row.append("text").attr("x", -6).attr("y", x.rangeBand() / 2)
		   .attr("dy", ".32em").attr("text-anchor", "end").text(function(d, i) {
			   return nodes[i].name;
		   });
		var column = svg.selectAll(".column").data(matrix).enter().append("g")
						.attr("class", "column").attr( "transform", function(d, i) {
							return "translate(" + x(i)+ ")rotate(-90)";
						});
		column.append("line").attr("x1", -width);
		column.append("text").attr("x", 6)
			  .attr("y",x.rangeBand() / 2)
			  .attr("dy", ".32em")
			  .attr("text-anchor", "start")
			  .text(function(d, i) {
				  return nodes[i].name;
			  });

		function row(row) {
			var cell = d3.select(this).selectAll(".cell").data(row.filter(function(d) {
				return d.z;
			})).enter().append("rect").attr("class", "cell").attr("x", function(d) {
				return x(d.x);
			}).attr("width", x.rangeBand()).attr("height", x.rangeBand()).style("fill-opacity", function(d) {
				return z(d.z);
			}).style("fill", function(d) { 
				return nodes[d.x].group == nodes[d.y].group ? c(nodes[d.x].group): null;
			}).on("mouseover", mouseover).on("mouseout", mouseout);
		}

		function mouseover(p) {
			d3.selectAll(".row text").classed("active", function(d, i) {return i == p.y;});
			d3.selectAll(".column text").classed("active",function(d, i) {return i == p.x;});
		}

		function mouseout() {
			d3.selectAll("text").classed("active", false);
		}

		d3.select("#order").on("change", function() {
			clearTimeout(timeout);
			order(this.value);
		});

		function order(value) {
			x.domain(orders[value]);
			var t = svg.transition().duration(2500);
			t.selectAll(".row").delay(function(d, i) {
				return x(i) * 4;
			}).attr("transform", function(d, i) {
				return "translate(0," + x(i) + ")";
			}).selectAll(".cell").delay(function(d) {
				return x(d.x) * 4;
			}).attr("x", function(d) {
				return x(d.x);
			});

			t.selectAll(".column").delay(function(d, i) {
				return x(i) * 4;
			}).attr("transform", function(d, i) {
				return "translate(" + x(i) + ")rotate(-90)";
			});
		}

		var timeout = setTimeout(function() {
			order("group");
			d3.select("#order").property("selectedIndex", 2).node().focus();
		}, 5000);
	});
}

function highlight(n, k){ 
	
		var cs = n.childNodes;
		var i = 0, l = cs.length;
		var t, c, pos, rest;
		t = document.createElement('font');
		t.color = 'red';
		t.innerHTML = k;

		for (; i < l; i++){
			c = cs[i];
			if (c.nodeType == 3){
				t = t.cloneNode(true);
			
				pos = c.nodeValue.indexOf(k);
				if (pos != -1) {
					rest = c.splitText(pos);
					rest.replaceData(0, k.length, '');
					n.insertBefore(t,rest);
				}
			}
			else highlight(cs[i],k);
		}
	
} 
