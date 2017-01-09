<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<script type="text/javascript">
		var root='<%=basePath%>';
	</script>
	<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/index.js"></script>
  </head>
  <body>
      <form method="post" action="post/insert">
      		<input name="title" type="text" />
      		<input name="content" type="text" />
      		<input type="submit" value="submit">
      </form>
  </body>
</html>
