<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'form.jsp' starting page</title>
  </head>
  
  <body>
  	<%
  		List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("list");
	%>
    <form action="<%=path %>/SearchServlet" method="post">
		<div align="center" style="margin-top:100px">
    	<input type="text" name="keyword" value="<%if(request.getAttribute("keyword")==null){%>""<%}%><%=request.getAttribute("keyword") %>" style="width:500px;height:35px;font-size:20px"/>
    	<input type="submit" value="搜索" style="width:100px;height:35px;font-size:20px"/><br/><br/><br/>
    	<%for(int i=0;i<list.size();i++){
    		%>
    		<div align="left" style="margin-left:250px;margin-right:200px">
    		<a href="<%=list.get(i).get("link") %>" target="_blank">
    		<jsp:useBean id="replace" scope="page" class="cn.com.yf.utils.Replace" />
			<%=replace.replace(list.get(i).get("title").toString(),request.getAttribute("keyword").toString(),"<font color='#FF0000'><strong>" + request.getAttribute("keyword") + "</strong></font>")%></a></div><br />
    		
    		<div align="left" style="margin-left:250px;margin-right:200px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap">
    		<jsp:useBean id="replace1" scope="page" class="cn.com.yf.utils.Replace" />
			<%=replace.replace(list.get(i).get("content").toString(),request.getAttribute("keyword").toString(),"<font color='#FF0000'><strong>" + request.getAttribute("keyword") + "</strong></font>")%>
    		</div><br/>
    		<%
    	}	
    	%>
    	</div>
    </form>
  </body>
</html>
