package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MarkAsCompleteServlet")
public class MarkAsCompleteServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String taskToDelete = request.getParameter("taskName");
        out.println(taskToDelete);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "root");

            String query = "UPDATE tasks SET completed = 'true' WHERE task = ? and username= ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, taskToDelete);
            pst.setString(2, username);

            int numRowsAffected = pst.executeUpdate();
            if (numRowsAffected > 0) {
            	request.setAttribute("tasksHtml", generateTasksHtml(con,username));

                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/taskList.jsp");
                dispatcher.forward(request, response);
            } else {
                out.println("<h2>Task Not marked as complete</h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error</h2>");
        }
    }
    private String generateTasksHtml(Connection con,String username) throws SQLException {
        String sql = "SELECT task, priority, duedate, duetime, completed FROM tasks where completed='false' and username=?";
    
    	PreparedStatement statement = con.prepareStatement(sql);
    	statement.setString(1, username);
    	ResultSet resultSet = statement.executeQuery();
        try{

            
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

            	// Reschedule button with adjusted styles
            	tasksHtml.append("<td><form action='reschedule.jsp' method='GET'>");
            	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
            	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #4CAF50; color: white; border: none; border-radius: 3px;'>Reschedule</button></form></td>");

            	// Edit button with adjusted styles
            	tasksHtml.append("<td><form action='edit.jsp' method='GET'>");
            	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
            	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #008CBA; color: white; border: none; border-radius: 3px;'>Edit</button></form></td>");

            	// Remove button with adjusted styles
            	tasksHtml.append("<td><form action='delete' method='POST'>");
            	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
            	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #f44336; color: white; border: none; border-radius: 3px;'>Remove</button></form></td>");

            	// Mark as Complete button with adjusted styles
            	tasksHtml.append("<td><form action='markComplete' method='POST'>");
            	tasksHtml.append("<input type='hidden' name='taskName' value='" + resultSet.getString("task") + "'>");
            	tasksHtml.append("<button type='submit' style='padding: 5px 10px; font-size: 12px; background-color: #555555; color: white; border: none; border-radius: 3px;'>Mark as Complete</button></form></td>");

            	tasksHtml.append("</tr>");

            }
            tasksHtml.append("</tbody>");
            tasksHtml.append("</table>");

            return tasksHtml.toString();
        }catch(Exception e) {
        	StringBuilder tasksHtml = new StringBuilder();
        	tasksHtml.append("<table>");
        	return tasksHtml.toString();
        }
    }
}

