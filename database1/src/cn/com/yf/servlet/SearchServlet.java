package cn.com.yf.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.yf.utils.DbUtil;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = -4494852483511779111L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		DbUtil db=new DbUtil();
		String keyword=request.getParameter("keyword");
		String sql="SELECT * FROM document WHERE title LIKE '%"+keyword+"%' OR content LIKE '%"+keyword+"%'";
		List<Map<String,Object>> list=db.query(sql);
		request.setAttribute("keyword", keyword);
		request.setAttribute("list",list);
		request.getRequestDispatcher("/form.jsp").forward(request,response);
	}
}
