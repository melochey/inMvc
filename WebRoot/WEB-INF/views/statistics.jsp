<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>add work</title>
<link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<style type="text/css">
input {
	vertical-align: middle;
	height: 34px;
}
.itm {
	padding: 5px 0;
	border-top: 1px dotted #ccc;
	margin-top:4px;
	overflow:hidden;
}
.head{overflow:hidden;width: 50px;height: 50px;float: left;}
.head img {

    width: 50px;
    height: 50px;
    margin-right: -100px;
}
.cntwrap {
    margin-left: 60px;
}
.cnt {
    line-height: 20px;
}
.f-brk {
    word-wrap: break-word;
    word-break: break-all;
    white-space: normal;
}
.s-fc7, a.s-fc7:hover {
    color: #0c73c2;
}
div.rp {
    margin-top: 15px;
    text-align: right;
}
.time {
    float: left;
    margin: 0 !important;
}
.s-fc4, a.s-fc4:hover {
    color: #999;
}
.pageCss{padding-top:20px;padding-bottom:20px;overflow:hidden}
.pageCss li{float:left;text-decoration: none;padding:4px 8px;list-style-type: none;margin-right:6px;cursor: pointer;background-color: #eaeaea;}
.cur{background-color:#23A70F !important;color:#fff}
.pageWrapper{float:left;margin-left:34px;}
.compare{margin:20px 20px;}
.compare p{font-size: 16px;}
.title{background-color:#23A70F;color:#fff;padding-left:16px;line-height:48px;font-size:16px;font-weight:bold;}
.axis--x path {
  display: block;
}

.line {
  fill: none;
  stroke: #13C131;
  stroke-width: 1.5px;
}
#areaChart div{width:50%;position:relative;float:left}
path {  stroke: #fff; }
path:hover {  opacity:0.9; }
rect:hover {  fill:blue; }
.axis {  font: 10px sans-serif; }
.legend tr{    border-bottom:1px solid grey; }
.legend tr:first-child{    border-top:1px solid grey; }
.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.x.axis path {  display: none; }
.legend{
    border-collapse: collapse;
    border-spacing: 0px;
    position:absolute;
    right:0;bottom:0
}
.legend td{
    padding:4px 5px;
    vertical-align:bottom;
}
.legendFreq, .legendPerc{
    align:right;
    width:50px;
}
.fstyle{stroke:#fff;stroke-width:1;fill:none}
#clear{clear:both}
</style>
</head>
<body>
	<div class="container">
		<div class="compare">
			<p class='title'>用户比较</p>
		    <p>&nbsp;&nbsp;&nbsp;&nbsp;本次直播一共有<span style='font-size:20px;color:red;font-weight:bold'>${num}</span>位用户发言，比上次直播多了<span style='font-size:20px;color:red;font-weight:bold'>${ln}</span>位用户</p>
		</div>
		<div class="compare">
					<p class="title">小耳朵评论性别比例</p>
					<div id="areaChart">
						<div id="left"></div>
						<div id="right"></div>
					</div>
		</div>
		<div id="clear">
		</div>
		<div class="compare">
			<p class="title">直播实时小耳朵数变化</p >
			<div id="columnChart"><svg width="1100" height="500"></svg></div>
		</div>
		<div class="pageWrapper" title='评论长度排序' pageSize="30" renderLine="maxLine" url="maxLenComment?fmid=${fmid}&&categoryid=${categoryid}" id="wrapper1">
			<div name="url"></div>
			<div name="name"></div>
			<div name="comment"></div>
		</div>
		<div class="pageWrapper" title='最活跃用户排序' width='500'  pageSize="10"  renderLine="initLiine" url="mostActiviteUsers?fmid=${fmid}&&categoryid=${categoryid}" id="wrapper2">
			<div name="url"></div>
			<div name="name"></div>
			<div name="num"></div>
		</div>
		<div class="pageWrapper" title='送荔枝用户' width='500' renderLine="maxLine" url="payMoneyComment?fmid=${fmid}&&categoryid=${categoryid}" id="wrapper3">
			<div name="url"></div>
			<div name="name"></div>
			<div name="comment"></div>
		</div>
		<div class="pageWrapper" title='第一个进群' width='500' renderLine="sortLine" url="firstCome?fmid=${fmid}&&categoryid=${categoryid}" id="wrapper4">
			<div name="url"></div>
			<div name="name"></div>
		</div>
	</div>
	<script type="text/javascript">
		function maxLine(attr){
			var line='<div class="itm"><div class="head"><a href="javascript:void(0)"><img src="'+attr[0]+'"></a></div><div class="cntwrap"><div class=""><div class="cnt f-brk"><a href="javascript:void(0)" class="s-fc7">'+attr[1]+'</a>：'+attr[2]+'</div></div></div></div>';
			return line;
		}
		function initLiine(attr){
			var line='<div class="itm"><div class="head"><a href="javascript:void(0)"><img src="'+attr[0]+'"></a></div><div class="cntwrap"><div class=""><div class="cnt f-brk"><a href="javascript:void(0)" class="s-fc7">'+attr[1]+'</a>：'+attr[2]+'条评论</div></div></div></div>';
			return line;
		}
		var we=0;
		function sortLine(attr){
			we++;
			var line='<div class="itm"><div class="head"><a href="javascript:void(0)"><img src="'+attr[0]+'"></a></div><div class="cntwrap"><div class=""><div class="cnt f-brk"><a href="javascript:void(0)" class="s-fc7">'+attr[1]+'</a>：第'+we+'名</div></div></div></div>';
			return line;
		}
	</script>
	<script type="text/javascript" src="<%=basePath%>/js/pageWrapper.js"></script>
	<script src="<%=basePath%>/js/d3.v4.min.js"></script>
	<script type="text/javascript">
		function getLegend(d,aD){ // Utility function to compute percentage.
	        return d3.format(".2%")(d.num/d3.sum(aD.map(function(v){ return v.num; })));
	    }
		function drawPie(name,suff,wrapper){
			//var color=d3.scale.category10();//生成随机颜色
			var color={"male":"#2980b9", "female":"#e74c3c","unknow":"#95a5a6"};
			var pieData;
			$.ajax({
				type:"get",
				url:name+"?fmid=${fmid}&&categoryid=${categoryid}",
				async:false,
				success:function(da){
					pieData=eval("("+da+")");
				}
			});
			//var pieData=[{"num":100,"type":"male"},{"num":150,"type":"female"},{"num":20,"type":"unknow"}];
			var pie = d3.pie().sort(null).value(function(d) { return d.num; });
			var w=300;
			var h=300;
			var outerRadius=w/2;//外半径
			var innerRadius=0;//内半径
			var arc=d3.arc()
				.innerRadius(innerRadius)
				.outerRadius(outerRadius);

			var svg=d3.select("#"+wrapper)
				.append("svg")
				.attr("width",w)
				.attr("height",h);
			var arcs=svg.selectAll("g.arc")
				.data(pie(pieData))
				.enter()
				.append("g")
				.attr("class","arc")
				.attr("transform","translate("+outerRadius+","+outerRadius+")");
			arcs.append("path")
				.attr("fill",function(d){
					return color[d.data.type];
				})
				.attr("d",arc);

			arcs.append("text")
				.attr("transform",function(d){
					return "translate("+arc.centroid(d)+")";//定位文字到图形的中心
				})
				.attr("text-anchor","middle").attr("class","fstyle")
				.text(function(d){
					return d.value+suff;
				});
			var legend = d3.select("#"+wrapper).append("table").attr('class','legend');
			var tr = legend.append("tbody").selectAll("tr").data(pieData).enter().append("tr");
			 tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
	            .attr("width", '16').attr("height", '16')
				.attr("fill",function(d){ return color[d.type]; });	
			 tr.append("td").text(function(d){ return d.type;});
			 tr.append("td").attr("class",'legendPerc')
	            .text(function(d){ return getLegend(d,pieData);});
		}
		
		
		function drawColumn(){
			var svg = d3.select("#columnChart svg"),
		    margin = {top: 20, right: 20, bottom: 30, left: 50},
		    width = +svg.attr("width") - margin.left - margin.right,
		    height = +svg.attr("height") - margin.top - margin.bottom,
		    g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
		
		var parseTime = d3.timeParse("%d-%b-%y");
		
		var x = d3.scaleTime()
		    .rangeRound([0, width]);
		
		var y = d3.scaleLinear()
		    .rangeRound([height, 0]);
		
		var line = d3.line()
		    .x(function(d) { return x(new Date(d.createTime)); })
		    .y(function(d) { return y(+d.online); });
		
		d3.json("online?fmid=${fmid}&&categoryid=${categoryid}",function(error, data) {
		  if (error) throw error;
		  x.domain(d3.extent(data, function(d) { return new Date(d.createTime); }));
		  y.domain(d3.extent(data, function(d) { return +d.online; }));
		
		  g.append("g")
		      .attr("class", "axis axis--x")
		      .attr("transform", "translate(0," + height + ")")
		      .call(d3.axisBottom(x));
		
		  g.append("g")
		      .attr("class", "axis axis--y")
		      .call(d3.axisLeft(y))
		    .append("text")
		      .attr("fill", "#000")
		      .attr("transform", "rotate(-90)")
		      .attr("y", 6)
		      .attr("dy", "0.71em")
		      .style("text-anchor", "end")
		      .text("小耳朵数");
		
		  g.append("path")
		      .datum(data)
		      .attr("class", "line")
		      .attr("d", line);
		});
		}
		drawPie("sexlist","位小耳朵","left");
		drawPie("totalsex","条评论","right");
		drawColumn();
	</script>
</body>
</html>