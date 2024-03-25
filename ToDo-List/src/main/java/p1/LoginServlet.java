package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet1
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
                     
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo",
                        "root", "root");

                String query = "SELECT * FROM login WHERE username=? AND password=?";
                PreparedStatement pst = con.prepareStatement(query);
                	HttpSession session = request.getSession();
                    	session.setAttribute("username", username);
                      pst.setString(1, username);
                    pst.setString(2, password);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                    	
                          
                    	
                    	
                        String sql = "SELECT * FROM tasks WHERE completed = 'false' and username=? ;";
                        PreparedStatement statement = con.prepareStatement(sql);
                        statement.setString(1, username);
                        ResultSet resultSet = statement.executeQuery();

                     
                        StringBuilder tasksHtml = new StringBuilder();
                        tasksHtml.append("<table>");
                        tasksHtml.append("<thead>");
                        tasksHtml.append("<tr>");
                        tasksHtml.append("<th>Name</th>");
                        tasksHtml.append("<th>Priority</th>");
                        tasksHtml.append("<th>Due Date</th>");
                        tasksHtml.append("<th>Due Time</th>");
                        tasksHtml.append("<th>Completed</th>");
                        tasksHtml.append("<th>Reschedule</th>");
                        tasksHtml.append("<th>Edit</th>");
                        tasksHtml.append("<th>Remove</th>");
                        tasksHtml.append("<th>Mark as complete</th>");
                        tasksHtml.append("</tr>");
                        tasksHtml.append("</thead>");
                        tasksHtml.append("<tbody>");

                        while (resultSet.next()) {
                            tasksHtml.append("<tr>");
                            tasksHtml.append("<td>").append(resultSet.getString("task")).append("</td>");
                            tasksHtml.append("<td>").append(resultSet.getString("priority")).append("</td>");
                            tasksHtml.append("<td>").append(resultSet.getString("duedate")).append("</td>");
                            tasksHtml.append("<td>").append(resultSet.getString("duetime")).append("</td>");
                            tasksHtml.append("<td>").append(resultSet.getBoolean("completed") ? "Yes" : "No").append("</td>");
                         
                        	tasksHtml.append("<td><form action='reschedule.jsp' method='GET'>");
                        	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
                        	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #4CAF50; color: white; border: none; border-radius: 3px;'>Reschedule</button></form></td>");

                        	
                        	tasksHtml.append("<td><form action='edit.jsp' method='GET' style='text-align: center;>");
                        	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
                        	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #008CBA; color: white; border: none; border-radius: 3px;'>Edit</button></form></td>");

                        	
                        	tasksHtml.append("<td><form action='delete' method='POST' style='text-align: center;>");
                        	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
                        	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #f44336; color: white; border: none; border-radius: 3px;'>Remove</button></form></td>");

                        	
                        	tasksHtml.append("<td><form action='markComplete' method='POST' style='text-align: center;>");
                        	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
                        	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #555555; color: white; border: none; border-radius: 3px;'>Mark as Complete</button></form></td>");

                            
                            tasksHtml.append("</tr>");
                        }


                     tasksHtml.append("</tbody>");
                     tasksHtml.append("</table>");

                     
                     request.setAttribute("tasksHtml", tasksHtml.toString());

                        
                        resultSet.close();
                        statement.close();
                        con.close();

                        
                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/taskList.jsp");
                        dispatcher.forward(request, response);
    	             
                    } else {
                        out.println("<h2>Login Failed. Please check your username and password.</h2>");
                    }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
		
	}
}
