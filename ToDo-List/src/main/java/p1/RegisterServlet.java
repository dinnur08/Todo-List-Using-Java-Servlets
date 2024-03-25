package p1;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public RegisterServlet() {
        super();
        
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        try 
        {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo",
	                    "root", "root");
	
	            String query = "INSERT INTO login (username, email, password) VALUES (?, ?, ?)";
	            PreparedStatement pst = con.prepareStatement(query);
	            pst.setString(1, username);
	            pst.setString(2, email);
	            pst.setString(3, password);
	            
	            int numRowsAffected = pst.executeUpdate();
	            if (numRowsAffected > 0) 
	            {
	                out.println("<h2>Registration Successful!</h2>");
	                
	                response.sendRedirect("Login.html");
	            } 
	            else 
	            {
	                out.println("<h2>Registration Failed. Please try again.</h2>");
	            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();

        }
       
	}

}
