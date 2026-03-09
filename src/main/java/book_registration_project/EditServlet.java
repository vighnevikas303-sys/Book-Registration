package book_registration_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final String query = "UPDATE BOOKDATA SET BOOKNAME=?,BOOKEDION=?,BOOKPRICE=? WHERE id=?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        
        int id = Integer.parseInt(req.getParameter("id"));
        String bookName=req.getParameter("bookName");
        String bookEdition=req.getParameter("bookEdition");
        Float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "vikasvighne@123");
            PreparedStatement ps = con.prepareStatement(query);
        		) {
    		ps.setString(1, bookName);
    		ps.setString(2, bookEdition);
    		ps.setFloat(3,bookPrice);
            ps.setInt(4, id);
            int count =ps.executeUpdate();
            if(count==1)
            {
            	pw.println("record edit is successfully");
            }
            else {
            	pw.println("record edit is not successfully");
            }
          
           
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
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
