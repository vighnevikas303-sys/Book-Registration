package book_registration_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Registerservlet extends HttpServlet {
	private static final String query="INSERT INTO BOOKDATA(BOOKNAME ,BOOKEDION,BOOKPRICE) VALUES(?,?,?)";
       @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
    	   PrintWriter pw=res.getWriter();
    	   res.setContentType("text/html");
    	   
    	  
    	   String bookname=req.getParameter("bookname");
    	   String bookedition=req.getParameter("bookedition");
    	   float bookprice=Float.parseFloat(req.getParameter("bookPrice"));
    	   
    	   try {
    		   Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			 e.printStackTrace();		
			 }
    
       try {

    	   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "vikasvighne@123");
           PreparedStatement ps=con.prepareStatement(query);
           ps.setString(1, bookname);
           ps.setString(2,bookedition);
           ps.setFloat(3, bookprice);
           int count=ps.executeUpdate();
           if(count==1)
           {
        	   pw.println("<h2>  record is sucessfully executed  </h2>");
           }
           else
           {
        	   pw.println("<h2> record is not sucessfully executed  </h2>");

           }
       }
       catch(SQLException se){
    	   se.printStackTrace();	
    	   pw.println("<h1>"+se.getMessage()+"<h1>");
       }
       catch(Exception e)
       {
    	   e.printStackTrace();	
    	   pw.println("<h1>"+e.getMessage()+"<h1>");
       }
       pw.println("<a href='home.html'>Home</a>");
       pw.println("<br>");
       pw.println("<a href='bookList'>Book List</a>");
       
    	   
       }
       @Override
       protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           doGet(req, resp); 
       }
}
