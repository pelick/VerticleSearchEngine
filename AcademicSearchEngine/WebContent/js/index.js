$(function() {
	// 载入时启动的默认函数
	leftSide();
	
	// 行为绑定
	$(".author_tooltip").on("mouseover", function(e) {
		$(this).tooltip('show');
	});
	
	//home.jsp
	$('#myCarousel').carousel();
	
	//researcher.jsp
	$('#cloudword_btn').on("click", function(e) {
		wordcloud();
	});
	
	$('#coauthorOne_btn').on("click", function(e) {
		coauthorOne();
	});
	
	$('#coauthorTwo_btn').on("click", function(e) {
		coauthorTwo();
	});
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


function wordcloud() {
	var WIDTH = 1200;
	var HEIGHT = 300;
	var ANGLE = 30;
	var str = $("#abstract").val();
	var filterWords = ["is", "the", "and", "a", "by", "of", "to"];
	str = str.replace(/is/g, "");
	str = str.replace(/the/g, "");
	str = str.replace(/and/g, "");
	str = str.replace(/a/g, "");
	str = str.replace(/by/g, "");
	str = str.replace(/of/g, "");
	str = str.replace(/to/g, "");
	str = str.replace(/In/g, "");
	alert(str);
	var words = str.split(" ");
	var fill = d3.scale.category20();

	d3.layout.cloud()
	.size([WIDTH, HEIGHT])
	.words(words
	.map(function(d) {
        return {text: d, size: 10 + Math.random() * 90};
    }))
    .rotate(function() { 
    	return ~~(Math.random() * 2) * ANGLE; 
    })
    .font("Impact").fontSize(function(d) { 
    	return d.size; 
    })
    .on("end", draw).start();

	function draw(words) {
		d3.select("#wordcloud").append("svg")
		.attr("width", WIDTH)
        .attr("height", HEIGHT)
        .append("g")
        .attr("transform", "translate(150,150)")
        .selectAll("text")
        .data(words)
        .enter().append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
	}
}

// coauther
function coauthorOne() {
	var width = 960,
    height = 500;

    var color = d3.scale.category20();

    var force = d3.layout.force().charge(-120).linkDistance(30).size([width, height]);

    var svg = d3.select("#coauthorOne").append("svg").attr("width", width).attr("height", height);

    d3.json("http://localhost:8080/AcademicSearchEngine/test.json", function(error, graph) {
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
    });
}

function coauthorTwo() {
	var margin = {
		top : 80,
		right : 0,
		bottom : 10,
		left : 80
	}, 
	width = 700, 
	height = 700;

	var x = d3.scale.ordinal().rangeBands([ 0, width ]), 
		z = d3.scale.linear().domain([ 0, 4 ]).clamp(true), 
		c = d3.scale.category10().domain(d3.range(10));

	var svg = d3.select("#coauthorTwo").append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.style("margin-left", -margin.left + "px").append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	d3.json("http://localhost:8080/AcademicSearchEngine/test.json", function(miserables) {
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
