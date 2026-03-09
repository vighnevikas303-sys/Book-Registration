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

@WebServlet("/editScreen")
public class BookeditServlet extends HttpServlet {
    private static final String query = "SELECT ID,BOOKNAME,BOOKEDION,BOOKPRICE FROM BOOKDATA WHERE id=?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        
        int id = Integer.parseInt(req.getParameter("id"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "vikasvighne@123");
            PreparedStatement ps = con.prepareStatement(query);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) { // ✅ Move cursor to first row
                pw.println("<form action='editurl?id=" + id + "' method='post'>");
                pw.println("<table align='center'>");
                
                pw.println("<tr><td>Book Name</td>");
                pw.println("<td><input type='text' name='bookName' value='" + rs.getString(2) + "'></td></tr>");
                
                pw.println("<tr><td>Book Edition</td>");
                pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(3) + "'></td></tr>");
                
                pw.println("<tr><td>Book Price</td>");
                pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(4) + "'></td></tr>");
                
                pw.println("<tr>");
                pw.println("<td><input type='submit' value='Edit'></td>");
                pw.println("<td><input type='reset' value='Cancel'></td>");
                pw.println("</tr>");
                
                pw.println("</table>");
                pw.println("</form>");
            } else {
                pw.println("<h2>No record found with ID " + id + "</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
        
        pw.println("<a href='home.html'>Home</a>");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
