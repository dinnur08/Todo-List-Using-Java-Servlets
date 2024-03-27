package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowAllTasksServlet")
public class ShowAllTasksServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "root");

            String query = "SELECT task, duedate, priority, description FROM tasks";
            PreparedStatement pst = con.prepareStatement(query);

            ResultSet resultSet = pst.executeQuery();	

            out.println("<h2>All Tasks:</h2>");
            while (resultSet.next()) {
                String task = resultSet.getString("task");
                String dueDate = resultSet.getString("duedate");
                int priority = resultSet.getInt("priority");
                String description = resultSet.getString("description");

                out.println("<p>Task: " + task + "<br>Due Date: " + dueDate + "<br>Priority: " + priority + "<br>Description: " + description + "</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error occurred while retrieving tasks. Please try again.</h2>");
        }
    }
}
