"use strict";

$("document").ready(function() {       //Main

    let registerFormElement = $("#registerForm");
    let loader = $("#loaderContent");

    let emailElement = registerFormElement.find("input[type='email']")[0];
    let passwordElement = registerFormElement.find("input[name='password']")[0];
    let passwordConfirmedElement = registerFormElement.find("input[name='confirmPassword']")[0];
    let firstNameElement = registerFormElement.find("input[name='firstName']")[0];
    let lastNameElement = registerFormElement.find("input[name='lastName']")[0];

    let toast = new Toast();

    registerFormElement.submit(function() {

        loader.fadeIn(300);

        // Check that passwords are match
        if(passwordElement.value !== passwordConfirmedElement.value) {
            toast.showToast('error', 'Passwords not match', '3000');
            return false;
        }

        // Getting all values
        let userObj = {
            email: emailElement.value,
            password: passwordElement.value,
            firstName: firstNameElement.value,
            lastName: lastNameElement.value,
        };

        // Sending the form data
        $.ajax({
            type: "POST",
            url: "/registerUser",
            data: userObj,
            cache: true,
            success: function(response){
                console.log("SUCCESS");
                console.log(response);
            },
            error: function(response) {
              console.log("ERROR");
              console.log(response);
            },
        }).done(function (data, textStatus, xhr) {
            let isValidated = +(xhr.getResponseHeader('IS_VERIFY'));
            if(isValidated === 0) {  //ERROR
                let error = xhr.getResponseHeader('ERROR');
                toast.showToast('error', error, '3000');
                loader.fadeOut(300);
            }
            else {                  // SUCCESS
                toast.showToast('success', 'Register succeed! Redirecting to login...', '15000');
                setTimeout( () => window.location.replace("login.jsp"), 2000);
            }
        });

        // In order to prevent redirecting when form data sent
        return false;
    });
});