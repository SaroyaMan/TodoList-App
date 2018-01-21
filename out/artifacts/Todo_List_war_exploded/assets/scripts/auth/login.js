"use strict";

$("document").ready(function() {       //Main

    let loader = $("#loaderContent");

    let emailElement = $("input[type='email']")[0];
    let passwordElement = $("input[type='password']")[0];
    let rememberMeElement = $("input[type='checkbox']")[0];

    let toast = new Toast();


    $("#loginForm").submit(function() {

        loader.fadeIn(300);

        // Getting all values
        let userObj = {
            email: emailElement.value,
            password: passwordElement.value,
            rememberMe: rememberMeElement.checked,
        };

        // Sending the form data
        $.ajax({
            type: "POST",
            url: "/loginUser",
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
                toast.showToast('success', 'Login succeed! Redirecting to Homepage...', '15000');
                setTimeout( () => window.location.replace("index.jsp"), 1000);
            }
        });
        // In order to prevent redirecting when form data sent
        return false;
    });
});