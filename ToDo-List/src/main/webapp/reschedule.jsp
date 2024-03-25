<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dynamic Todo List</title>
    <style>
    
        body {
		    font-family: 'Poppins', sans-serif;
		    text-align: center;
		    margin: 20px;
		    background-color: #080710;
		    color: #ffffff;
		    display: flex;
    		flex-direction: column;
    		align-items: center;
		}
		
		h2 {
		    color: #ffffff;
		}
		
		form {
		    width: 300px;
		    display: flex;
		    flex-direction: column;
		    margin-bottom: 20px;
		    margin-top: 20px;
		}
		
		label {
		    color: #ffffff;
		}
		
		input, select, button {
		    margin-bottom: 10px;
		    padding: 8px;
		    box-sizing: border-box;
		    background-color: #ffffff;
		    color: #080710;
		    border-radius: 5px;
		    border: none;
		}
		
		input:hover, select:hover, button:hover {
		    background-color: #555;
		}
		
		#todo-list {
		    width: 100%;
		    list-style: none;
		    padding: 0;
		    display: flex;
		    flex-direction: column;
		    align-items: center;
		}
		
		.task {
		    display: block;
		    justify-content: space-between;
		    align-items: center;
		    border: 1px solid #ccc;
		    background-color: #fff;
		    margin-bottom: 5px;
		    padding: 8px;
		}
		
		.task button {
		    margin-left: 10px;
		    padding: 15px 20px;
		    cursor: pointer;
		    background-color: #ffffff;
		    color: #080710;
		    border: none;
		    border-radius: 8px;
		    text-decoration: none;
		}
		
		.task button:hover {
		    background-color: #f5f5f5;
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
            padding: 15px 20px;
            margin: 5px;
            cursor: pointer;
            background-color: #ffffff;
            color: #080710;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            display: inline-block;
        }

        button:hover, a button:hover {
            background-color: #f5f5f5;
        }
		
		
	</style>
</head>
<body>
	<nav>
        <ul>
            <li><a href="Main.html">Add Task</a></li>
            <li><a href="delete.html">Remove Task</a></li>
            <li><a href="edit.html">Edit Task</a></li>
        </ul>
    </nav>
    
    <h2>Reschedule Tasks</h2>
    <form id="todo-form" action="reschedule1" method="post">
        <label for="taskName">Task Name:</label>
		<input type="text" id="taskName" name="task" value="<%= request.getParameter("taskName") %>"><br>

		
        <label for="due-date">Due Date:</label>
        <input type="date" name="duedate">
        
        <label for="due-time">Due Time:</label>
        <input type="time" name="duetime">

        
        <button type="submit">Reschedule Task</button>
    </form>
</body>
</html>
