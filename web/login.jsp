<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
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

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

        <link rel="stylesheet" href="assets/css/auth_stylesheet.css">
        <link rel="stylesheet" href="assets/utils/css/loader.css">
        <script src="assets/utils/scripts/toast.js"></script>
        <script src="assets/scripts/auth/login.js"></script>

    </head>
    <body>
        <!--<img src="assets/images/logo.png">-->
        <div id="authPage">
            <h1>Login</h1>
            <%
                if(request.getAttribute("FULL_NAME") != null) {
                    out.println("<h5 class='text-info'>Welcome Back " + request.getAttribute("FULL_NAME") + "</h5>");
                }
            %>
            <%--<h5 class="text-info">Welcome Back ${FULL_NAME}</h5>--%>
            <form method="POST" autocomplete="off" id="loginForm">
                <input type="email" class="myTxtInput" name="email" placeholder="Email" required="required" />
                <input type="password" class="myTxtInput" name="password" placeholder="Password" required="required" />
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="isRemembmer" id="rememberMeId">
                    <label class="form-check-label" for="rememberMeId">Remember Me</label>
                </div>
                <button type="submit" class="btn btn-primary btn-block btn-large">Let me in.</button>
            </form>
            <p class="message">Not Registered? <a href="register.html">Create an account</a></p>
        </div>
        <div id="loaderContent">
            <div id="loader"></div>
        </div>
    </body>
</html>