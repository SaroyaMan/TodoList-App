"use strict";

let logout = function () {
    // Sending the form data
    $.ajax({
        type: "POST",
        url: "/logoutUser",
        data: null,
        cache: true,
        success: function(response){
        },
        error: function(response) {
        },
    }).done(function (data, textStatus, xhr) {
        window.location.replace("login.html");
    });
    // In order to prevent redirecting when form data sent
    return false;
};

$("document").ready(function() {       //Main

    let loader = $("#loaderContent");

    let toast = new Toast();

});