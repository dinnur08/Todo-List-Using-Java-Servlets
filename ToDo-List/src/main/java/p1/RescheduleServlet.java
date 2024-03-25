package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RescheduleServlet")
public class RescheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RescheduleServlet() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        
        String taskToUpdate = request.getParameter("task");
        
        Map<String, String> columnValues = new HashMap<>();
        String newDueDate = request.getParameter("duedate");
        String newDueTime = request.getParameter("duetime");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (newDueDate != null && !newDueDate.isEmpty()) {
            columnValues.put("duedate", newDueDate);
        }
        if (newDueTime != null && !newDueTime.isEmpty()) {
            columnValues.put("duetime", newDueTime);
        }


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "root");

            StringBuilder updateQuery = new StringBuilder("UPDATE tasks SET ");
            for (String columnName : columnValues.keySet()) {
                updateQuery.append(columnName).append(" = ?, ");
            }
            updateQuery.delete(updateQuery.length() - 2, updateQuery.length());
            updateQuery.append(" WHERE task = ?");

            PreparedStatement pst = con.prepareStatement(updateQuery.toString());

            int i = 1;
            for (String columnName : columnValues.keySet()) {
                pst.setString(i++, columnValues.get(columnName));
            }
            pst.setString(i, taskToUpdate);

            int numRowsAffected = pst.executeUpdate();
            
            if (numRowsAffected > 0) {
                // Task updated successfully, reload the task list
                String tasksHtml = generateTasksHtml(con, username);
                request.setAttribute("tasksHtml", tasksHtml);
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/taskList.jsp");
                dispatcher.forward(request, response);
            } else {
                // No task found with the given name
                out.println("<p>No Task Found with the Given Name. Please check the task name and try again.</p>");
            }

            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateTasksHtml(Connection con, String username) throws SQLException {
        String sql = "SELECT task, priority, duedate, duetime, completed FROM tasks where completed='false' and username=?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        try {
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
        } catch (Exception e) {
            StringBuilder tasksHtml = new StringBuilder();
            tasksHtml.append("<table>");
            return tasksHtml.toString();
        } finally {
            statement.close();
            resultSet.close();
        }
    }
}
