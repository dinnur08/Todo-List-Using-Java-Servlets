<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Task Manager</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            text-align: center;
            margin: 20px;
            background-color: #080710;
            color: #ffffff;
        }

        h1 {
            color: #ffffff;
            margin-bottom: 20px;
        }

        nav {
            background-color: #333;
            padding: 10px 0;
        }

        ul {
            list-style: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }

        li {
            margin: 0 10px;
        }

        a {
            text-decoration: none;
            color: #ffffff;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s;
            display: inline-block;
        }

        a:hover {
            background-color: #555;
        }

        button, a button {
            padding: 10px 15px;
            margin: 5px;
            cursor: pointer;
            background-color: #ffffff;
            color: #080710;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }

        button:hover, a button:hover {
            background-color: #f5f5f5;
        }

        .task-section {
            margin: 20px;
        }

        table {
            text-align: left;
            overflow: hidden;
            width: 80%;
            margin: 0 auto;
            display: table;
            padding: 0 0 8em 0;
        }

        td, th {
            padding-bottom: 2%;
            padding-top: 2%;
            padding-left: 2%;  
        }

        tr:nth-child(odd) {
            background-color: #323C50;
        }

        tr:nth-child(even) {
            background-color: #2C3446;
        }

        th {
            background-color: #1F2739;
        }

        td:first-child { color: #FB667A; }

        @media (max-width: 800px) {
            td:nth-child(4),
            th:nth-child(4) { display: none; }
        }
    </style>
</head>
<body>
    <h1>Task Manager</h1>

    <nav>
        <ul>
            <li><a href="Main.html">Add Task</a></li>
             
            
            <li><a href="logout" onclick="return confirm('Are you sure you want to log out?')">LogOut</a></li>
        </ul>
    </nav>

    <div class="task-section">
        <form action="pending" method="GET">
            <button type="submit">Completed Tasks</button>
        </form>
    </div>

    <div class="task-section">
        <table>
            <tbody>
                <%-- Tasks will be dynamically added here --%>
                <%= request.getAttribute("tasksHtml") %>
            </tbody>
        </table>
    </div>
</body>
</html>
