$(function() {
	// home.jsp
	// 自动触发点击这些button，然后调用ajax去后台获取用户的历史记录并显示
	$('#user_author_btn').click(getUserAuthor($('#user_author_btn').attr("user")));
	$('#user_author_btn').remove();
	$('#user_paper_btn').click(getUserPaper($('#user_paper_btn').attr("user")));
	$('#user_paper_btn').remove();
	$('#share_history_btn').click(getUserGift($('#share_history_btn').attr("user")));
	$('#share_history_btn').remove();
	$('#recommend_btn').click(doRecommend($('#recommend_btn').attr("user")));
	$('#recommend_btn').remove();
	
	$('#tagword_btn').on("click", function(e) {
		loading("load_tag");
		$('#tagword_btn').fadeOut('slow');
		showWordCloud($(this).attr("user"));
	});
	
	// index.jsp
	leftSide();
	rightSide();
	highlight(document.body, $("#skey").val());
	// 收藏成功与失败的提示
	$('#save_fail').hide();
	$('#save_success').hide();
	
	// 和注册相关的触发事件绑定
	$('#register').on("click", function(e) {
		$('#registerModal').modal('show');
	});
	
	$('#register_btn').on("click", function(e) {
		var user = new UserModel();
		user.username = $('#inputUsername').val();
		user.password = $('#inputPassword').val();
		user.name = $('#inputName').val();
		user.email = $('#inputEmail').val();
		user.weibo = $('#inputWeibo').val();
		user.github = $('#inputGithub').val();
		user.homepage = $('#inputHomepage').val();
		user.interests = $('#inputInterests').val();
		doRegister(user);
	});
	
	// 和收藏学者，论文相关的事件触发和绑定
	$('#save_btn').on("click", function(e) {
		var user = document.getElementById("save_user").innerHTML;
		var type = document.getElementById("save_type").innerHTML;
		var c = document.getElementById("save_content").innerHTML;
		var tag = $("#save_tag").val();
		if (type == "author") {
			saveAuthor('user='+user+'&tag='+tag+'&author='+c);
		} else if (type == "paper") {
			savePaper('user='+user+'&tag='+tag+'&title='+c);
		}
	});

	$(".save_author").on("click", function(e) {
		var user = $(this).attr("user");
		if (user == "") {
			$('#loginModal').modal('show');
		} else {
			document.getElementById("save_user").innerHTML = "";
			document.getElementById("save_type").innerHTML = "";
			document.getElementById("save_content").innerHTML = "";
			$('#save_user').append(user);
			$('#save_type').append("author");
			$('#save_content').append($(this).attr("author"));
			$('#saveModal').modal('show');
		}
	});
	
	$(".save_paper").on("click", function(e) {
		var user = $(this).attr("user");
		if (user == "") {
			$('#loginModal').modal('show');
		} else {
			document.getElementById("save_user").innerHTML = "";
			document.getElementById("save_type").innerHTML = "";
			document.getElementById("save_content").innerHTML = "";
			$('#save_user').append(user);
			$('#save_type').append("paper");
			$('#save_content').append($(this).attr("title"));
			$('#saveModal').modal('show');
		}
	});
	
	// 在index.jsp的论文搜索里，对于论文发表者有个tooltip的效果
	$(".author_tooltip").on("mouseover", function(e) {
		$(this).tooltip('show');
	});

	//about.jsp
	$('#myCarousel').carousel();
	
	//researcher.jsp
	// 触发词云生成
	$('#cloudword_btn').on("click", function(e) {
		loading("load_three");
		$('#cloudword_btn').fadeOut('slow');
		showWordCloud(undefined);
	});
	
	// 异步请求右侧相关学者列表
	$('#related_btn').click(relatedSide());
	$('#related_btn').remove();
	$('#related_btn').on("click", function(e) {
		relatedSide();
	});
	
	// pagerank重排序论文集
	$('#rankpaper_btn').on("click", function(e) {
		loading("load_rank");
		$('#rankpaper_btn').fadeOut('slow');
		doPaperRank();
	});
	
	//discover.jsp
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
});

function doPaperRank() {
	$.ajax({
		type : 'POST',
		url : "rankpaper?name="+$("#rname").val(),
		dataType : 'json',
		success : function(data) {
			$('#unrank_papers').remove();
			$('#load_rank').remove();
			var list = data.ranklist;
			for (var i = 0; i < list.length; i ++) {
				
				if (list[i].view_url.length > 5 && list[i].conference.length > 5) {
					$('#ranked_papers').append("<blockquote><p>"+list[i].title+
							"<a href='"+list[i].view_url+"'<i class='icon-share-alt'></i></a></p>"+
							"<p class='muted'><small>"+list[i].author+
							" @"+list[i].conference+
							"</small></p></blockquote>");
				} else if (list[i].view_url.length > 5) {
					$('#ranked_papers').append("<blockquote><p>"+list[i].title+
							"<a href='"+list[i].view_url+"'<i class='icon-share-alt'></i></a></p>"+
							"<p class='muted'><small>"+list[i].author+
							"</small></p></blockquote>");
				} else if (list[i].conference.length > 5) {
					$('#ranked_papers').append("<blockquote><p>"+list[i].title+
							"<p class='muted'><small>"+list[i].author+
							" @"+list[i].conference+
							"</small></p></blockquote>");
				} else {
					$('#ranked_papers').append("<blockquote><p>"+list[i].title+
							"<p class='muted'><small>"+list[i].author+
							"</small></p></blockquote>");
				}
			}
			
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("doPaperRank ajax error!");
		}
	});
}

function UserModel() {
	this.username = "";
	this.password = "";
	this.name = "";
	this.email = "";
	this.weibo = "";
	this.github = "";
	this.homepage = "";
	this.interests = "";
}

function doRegister(user) {
	$.ajax({
		type : 'POST',
		url : "register?username="+user.username+"&password="+user.password+"&name="+user.name
			+"&email="+user.email+"&weibo="+user.weibo+"&github="+user.github+"&homepage="+
			user.homepage+"&interests="+user.interests,
		dataType : 'json',
		success : function(data) {
			$('#registerModal').modal('hide');
			$('#loginModal').modal('show');
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("userAuthor ajax error!");
		}
	});
}

function doRecommend(name) {
	$.ajax({
		type : 'GET',
		url : "recommend?user="+name,
		dataType : 'json',
		success : function(data) {
			var list = data.list;
			for (var i = 0; i < list.length; i ++) {
				$('#tab2').append('<div class="alert alert-error"><strong>论文推荐</strong><br />'
						+ '<span class="label label-important">关键词</span> ' + list[i].sk + '<br />'
						+'<span class="label label-info">Title</span><a href="academic?core=core1&key='+list[i].title+'">'+ list[i].title +'</a><br />'
						+'<a href="user?username='+list[i].author+'">'+list[i].author +'</a> 收藏于 '+list[i].date
						+'</div>');
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("recommend ajax error!");
		}
	});
}

function getUserAuthor(name) {
	$.ajax({
		type : 'GET',
		url : "userauthor?user="+name,
		dataType : 'json',
		success : function(data) {
			var list = data.list;
			for (var i = 0; i < list.length; i ++) {
				$('#user_author').append('<p><b>'+list[i].name+'</b><br /> '
						+'<em>'+list[i].field+'</em><br />'
						+'<span class="label label-info">Date</span> '+list[i].date+' <span class="label label-important">Keyword</span> '+list[i].tag+'</p>');
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("userAuthor ajax error!");
		}
	});
}

function getUserPaper(name) {
	$.ajax({
		type : 'GET',
		url : "userpaper?user="+name,
		dataType : 'json',
		success : function(data) {
			var list = data.list;
			for (var i = 0; i < list.length; i ++) {
				$('#user_paper').append('<p><b>'+list[i].title+'</b><br />'
						+'<em>'+list[i].author+'</em><br />'
						+'<span class="label label-info">Date</span> '+list[i].date+' <span class="label label-important">Keyword</span> '+list[i].sk+'</p>');
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("userPaper ajax error!");
		}
	});
}

function getUserGift(name) {
	$.ajax({
		type : 'GET',
		url : "usergift?user="+name,
		dataType : 'json',
		success : function(data) {
			var list = data.list;
			for (var i = 0; i < list.length; i ++) {
				$('#share_history').append('<p><em>'+list[i].user+'</em> 分享的   <em>'+list[i].type+'</em><br />'
						+list[i].url+'<br />'
						+list[i].content+'<br />'
						+'<span class="label label-info">Date</span> '+list[i].date+' <span class="label label-important">Keyword</span> '+list[i].tag+'</p>');
			}
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("userGift ajax error!");
		}
	});
}

function saveAuthor(params) {
	$.ajax({
		type : 'GET',
		url : "saveauthor?"+params,
		dataType : 'json',
		success : function(data) {
			var type = data.type;
			$('#saveModal').modal('hide');
			if (type > 0) {
				$('#save_success').fadeIn("slow").fadeOut(3000);
			} else {
				$('#save_fail').fadeIn("slow").fadeOut(3000);
			}
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
			var type = data.type;
			$('#saveModal').modal('hide');
			if (type > 0) {
				$('#save_success').fadeIn("slow").fadeOut(3000);
			} else {
				$('#save_fail').fadeIn("slow").fadeOut(3000);
			}
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

// discover.jsp的记录发现历史的table
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

function relatedSide() {
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
					$("#related_author").append("<p><a class='author_tooltip' data-toggle='tooltip' data-placement='right' href='researcher?name="+list[i].name+
							"' data-original-title='"+list[i].field+"'>"+list[i].name+"</a></p>");
				}
				
				$(".author_tooltip").on("mouseover", function(e) {
					$(this).tooltip('show');
				});
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("Related ajax error!");
			}
		});
	}
}

// index.jsp左侧的研究领域和工作地列表
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

// index.jsp右侧热门和最新排行显示
function rightSide() {
	$.ajax({
		type : 'GET',
		url : "searchhistory?user="+$("#suser").val()+"&type="+$("#stype").val()+"&sk="+$("#skey").val(),
		dataType : 'json',
		success : function(data) {
			var hotlist = data.hotlist;
			var newlylist = data.newlylist;
	
			for ( var i = 0; i < hotlist.length; i++) {
				$("#hotBar").append('<div class="accordion-inner">'+hotlist[i]+'</div>');
				
			}
			for ( var j = 0; j < newlylist.length; j++) {
				$("#newlyBar").append('<div class="accordion-inner">'+newlylist[j]+'</div>');
			}
			
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("rightSide ajax error!");
		}
	});
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

// 展示某位用户的收藏标签云
function showWordCloud(user) {
	var the_url = "";
	if (user != undefined) {
		the_url =  "wordcloud?user="+user;
	} else {
		the_url = "wordcloud?name="+$("#rname").val();
	}
	alert(the_url);
	$.ajax({
		type : 'GET',
		url : the_url,
		dataType : 'json',
		success : function(data) {
			wordcloud(data.abs);
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert("showWordCloud ajax error!");
		}
	});
}

// 真正wordcloud生成效果代码
function wordcloud(str) {
	var WIDTH = 850;
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
	$('#load_tag').remove();
	
	function draw(words) {
		d3.select(".wordcloud").append("svg").attr("width", WIDTH).attr("height", HEIGHT)
          .append("g").attr("transform", "translate(450,150)").selectAll("text").data(words)
          .enter().append("text").style("font-size", function(d) { return d.size + "px"; })
          .style("font-family", "Impact").style("fill", function(d, i) { return fill(i); })
          .attr("text-anchor", "middle").attr("transform", function(d) {
            return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
          }).text(function(d) { return d.text; });
	}
}

// 每次等待ajax传入数据时的那个loading图，用spinner这个工具实现
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

// 发现页 力导向图效果生成
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

//发现页 矩阵图效果生成
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

// 高亮显示，k是关键词
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
		} else { 
			highlight(cs[i],k);
		}
	}
} 
