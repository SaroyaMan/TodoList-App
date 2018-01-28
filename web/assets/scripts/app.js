"use strict";

let logout = function () {
    // Sending the form data
    $.ajax({
        type: "POST",
        url: "/logoutUser",
        data: null,
        cache: true,
        success: function(response){},
        error: function(response) {},
    }).done(function (data, textStatus, xhr) {
        window.location.replace("login");
    });
    return false;
};

let deleteTask = function (taskId) {

    $("#loaderContent").fadeIn(300);

    console.log("taskId = " + taskId);
    $.ajax({
        type: "DELETE",
        url: "/task" + "?taskId=" + taskId,
        cache: true,
        success: function(response){},
        error: function(response) {},
    }).done(function (data, textStatus, xhr) {
        location.reload();
    });
};


$("document").ready(function() {       //Main

    //let loader = $("#loaderContent");

    let toast = new Toast();

});