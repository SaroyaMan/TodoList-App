"use strict";

let modal = null;
let tableElement = null;
let loader = null;

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

let deleteTask = function (taskId, e) {
    load(e);

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

let toggleTaskDone = function(taskId, e) {
    load(e);
    $.ajax({
        type: "PUT",
        url: "/task",
        data: "taskId=" + taskId,
        cache: true,
        success: function(response){},
        error: function(response) {},
    }).done(function (data, textStatus, xhr) {
        location.reload();
    });
};

let load = function(e) {
    loader.fadeIn(300);
    e.stopPropagation();
};

let displayModal = function(rowNum, e) {

    if(modal == null) {
        modal = new Modal($("#taskModal"));
    }
    tableElement = $("tbody");
    let id = tableElement.find("tr .rowId")[+rowNum].innerText;
    let name = tableElement.find("tr .rowName")[+rowNum].innerText;
    let des = tableElement.find("tr .rowDescription")[+rowNum].innerText;
    modal.show(id + ' - ' + name, des);
};


$("document").ready(function() {       //Main

    loader = $("#loaderContent");

    $("#taskForm").submit(function () {
        loader.fadeIn(300);
    });


    if(window.location.href.match("home")) {
        $("#taskTable").tablesorter();
    }
});