<%@ page import="com.todolist.models.data.User" %>
<%@ page import="com.todolist.models.data.Task" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) request.getAttribute("USER");
    List<Task> taskList = (List<Task>) request.getAttribute("TASKS");
    if(user == null || taskList == null) {
        response.sendRedirect("home");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="icon" href="assets/images/logo_icon.png" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!--Bootstrap 4 + jQuery-->
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css"
              integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy"
              crossorigin="anonymous">
        <script
                src="http://code.jquery.com/jquery-3.2.1.min.js"
                integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
                crossorigin="anonymous">
        </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
                integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
                crossorigin="anonymous">

        </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"
                integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4"
                crossorigin="anonymous">
        </script>

        <%--Toast--%>
        <link rel="stylesheet" href="assets/utils/css/toastr.css">
        <script src="assets/utils/scripts/toastr.js"></script>

        <link rel="stylesheet" href="assets/css/stylesheet.css">
        <link rel="stylesheet" href="assets/utils/css/loader.css">
        <script src="assets/utils/scripts/toast.js"></script>
        <script src="assets/utils/scripts/modal.js"></script>
        <script src="assets/utils/scripts/jquery.tablesorter.min.js"></script>
        <script src="assets/scripts/app.js"></script>

        <script src="https://use.fontawesome.com/a5033db61a.js"></script>

        <title>iTask - Home</title>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" id="logo" href="#"></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="#"><i class="fa fa-tasks" aria-hidden="true"></i> My Todo List</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="task"><i class="fa fa-thumb-tack" aria-hidden="true"></i> Create Task</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="#" onclick="logout()"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <main>
            <div class="container-fluid">
                <h2>My ToDo List</h2>
                <table id="taskTable" class="table table-hover table-striped">
                    <thead class="thead-inverse">
                    <tr>
                        <th># Id</th>
                        <th><i class="fa fa-tags" aria-hidden="true"></i> Name</th>
                        <th><i class="fa fa-info-circle" aria-hidden="true"></i> Description</th>
                        <th><i class="fa fa-check-square-o" aria-hidden="true"></i> Is Done</th>
                        <th><i class="fa fa-cogs" aria-hidden="true"></i> Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        int index = 0;
                        for(Task t : taskList) {
                            out.println("<tr class='taskRow " + index + "' onclick='displayModal("+ index +", event)'>");
                            out.println("<td scope='row' class='rowId'>" + t.getId() + "</td>");
                            out.println("<td class='rowName'>" + t.getName() + "</td>");
                            out.println("<td class='rowDescription'>" + t.getDescription().substring(0, t.getDescription().length() > 80 ? 80 : t.getDescription().length()) + (t.getDescription().length() > 80? "..." : "") + "</td>");
                            out.println("<td class='rowDone'><i " + "onclick='toggleTaskDone(" + t.getId() + ", event, " + index + ", " + t.getIsDone() + ")'" + " class='rowDone " + index + " fa " +(t.getIsDone()? "fa-check-square-o" : "fa-square-o")+ "'><span class='hiddenText'>" + t.getIsDone()+ "</span></td>");
                            out.println("<td><a href=# class='text-danger font-weight-bold' onclick='deleteTask(" + t.getId() + ", event, " + index + ")'><i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i> DELETE</a>");
                            out.println("| <a onclick='load(event)' href='task?taskId=" + t.getId() + "'class='text-info font-weight-bold'><i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i> UPDATE</a></td>");
                            out.println("</tr>");
                            ++index;
                        }
                    %>
                    </tbody>
                </table>
            </div>

        </main>
        <footer class="bg-dark text-white mt-4">
            <div class="container-fluid py-3">
                <div class="row">
                    <div class="col-md-3">
                        <h5>About</h5>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">Todo List Application<span class="small"><br>Made for JAVA EE Course in Shenkar</span></div>
                    <div class="col-md-3"></div>
                    <div class="col-md-3 text-right small align-self-end">&copy; Created By Yoav Saroya & Amit Shmuel</div>
                </div>
            </div>
        </footer>

        <div id="loaderContent">
            <div id="loader"></div>
        </div>

        <!-- Button trigger modal -->
        <button hidden data-toggle="modal" data-target="#taskModal"></button>

        <!-- Modal -->
        <div class="modal fade" id="taskModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Name...</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Description...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
