<%@ page import="com.todolist.models.data.User" %>
<%@ page import="com.todolist.models.data.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="com.todolist.models.HibernateToDoListDAO" %>
<%
    HttpSession ses = request.getSession();

    User user = null;
    List<Task> taskList = null;

    if(ses.getAttribute("iTaskAppUser") != null) {
        user = (User) ses.getAttribute("iTaskAppUser");
        taskList = HibernateToDoListDAO.getInstance().getUserTasks(user.getId());

    }
    if(user == null) {
        response.sendRedirect("login.html");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">

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

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

        <link rel="stylesheet" href="assets/css/stylesheet.css">
        <script src="assets/utils/toast.js"></script>
        <script src="assets/scripts/app.js"></script>

        <script src="https://use.fontawesome.com/a5033db61a.js"></script>

        <title>Todo List Application</title>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <a class="navbar-brand" href="#">Navbar</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="#"><i class="fa fa-tasks" aria-hidden="true"></i> My Todo List</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"><i class="fa fa-thumb-tack" aria-hidden="true"></i> New/Update task</a>
                        </li>
                    </ul>
                    <b class="navbar-text ml-auto text-success">
                        Welcome Lorem Ipsum!
                    </b>
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
                <table class="table table-hover table-striped">
                    <thead class="thead-inverse">
                    <tr>
                        <th>#</th>
                        <th><i class="fa fa-tags" aria-hidden="true"></i> Name</th>
                        <th><i class="fa fa-info-circle" aria-hidden="true"></i> Description</th>
                        <th><i class="fa fa-cogs" aria-hidden="true"></i> Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for(Task t : taskList) {
                            out.println("<tr>");
                            out.println("<th scopr='row'>" + t.getId() + "</th>");
                            out.println("<td>" + t.getName() + "</td>");
                            out.println("<td>" + t.getDescription() + "</td>");
                            out.println("</tr>");
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
    </body>
</html>
