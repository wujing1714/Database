package cn.com.yf.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {
     private Connection conn=null;
     private Statement stmt=null;
     private PreparedStatement ps=null;
     private ResultSet rs=null;
     static{
    	 try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动出现了异常");
			e.printStackTrace();
		}
    	 
     }
     private Connection getConn(){
    	 try {
			conn=DriverManager.getConnection("proxool.datasource");
		} catch (SQLException e) {
			System.out.println("获取连接出现了异常");
			e.printStackTrace();
		}
    	 return conn;
     }
     public int update(String sql,Object[] pars){
    	 try {
			ps=getConn().prepareStatement(sql);
			for(int i=0;i<pars.length;i++){
				ps.setObject(i+1, pars[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("执行更新语句出现了异常");
			e.printStackTrace();
		}finally{
			release();
		}
    	 return -1;
     }
     public int update(String sql){
    	 return update(sql,new Object[0]);
     }
     public List<Map<String,Object>> query(String sql,Object[] pars){
    	 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	 try {
			ps=getConn().prepareStatement(sql);
			for(int i=0;i<pars.length;i++){
				ps.setObject(i+1, pars[i]);
			}
			rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			while(rs.next()){
				Map<String,Object> map=new HashMap<String,Object>();
				for(int i=1;i<=rsmd.getColumnCount();i++){
					String key=rsmd.getColumnName(i);
					Object value=rs.getObject(key);
					map.put(key, value);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			System.out.println("执行查询语句出现了异常");
			e.printStackTrace();
		}finally{
			release();
		}
    	 return list;
     }
     public List<Map<String,Object>> query(String sql){
    	 return query(sql,new Object[0]);
     }
     public void batch(String[] sqls){
    	 conn=getConn();
    	 try {
			conn.setAutoCommit(false);//设置事务提交为手动
			stmt=conn.createStatement();
			for(int i=0;i<sqls.length;i++){
				stmt.addBatch(sqls[i]);
			}
			stmt.executeBatch();
		} catch (SQLException e) {
			try {
				conn.rollback();//事物回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				conn.commit();//提交事物
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	 release();
     }
     /*获取参数sql结果集总数量*/
     public int getTotalCount(String sql){
    	  return Integer.parseInt(query("select count(*) as num from ("+sql+") t").get(0).get("num").toString());
     }
     private void release(){
    	 if(rs!=null){
    		 try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("关闭结果集出现了异常");
				e.printStackTrace();
			}
    	 }
    	 if(ps!=null){
    		 try {
				ps.close();
			} catch (SQLException e) {
				System.out.println("关闭预处理语句对象出现了异常");
				e.printStackTrace();
			}
    	 }
    	 if(stmt!=null){
    		 try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println("关闭语句对象出现了异常");
				e.printStackTrace();
			}
    	 }
    	 if(conn!=null){
    		 try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("关闭连接出现了异常");
				e.printStackTrace();
			}
    	 }
     }
}



