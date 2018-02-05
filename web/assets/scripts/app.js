"use strict";

let modal = null;
let table = null;
let tableBody = null;
let loader = null;
let indexTables = {};

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
    //$("tr.taskRow." + index).remove();

    $.ajax({
        type: "DELETE",
        url: "/task" + "?taskId=" + taskId,
        cache: true,
        success: function(response){},
        error: function(response) {},
    }).done(function (data, textStatus, xhr) {
        loader.fadeOut(300);
        $("tr.taskRow." + taskId).remove();
    });
};

let toggleTaskDone = function(taskId, e, index, isDone) {
    load(e);
    if(indexTables[index] == null)
        indexTables[index] = !isDone;

    indexTables[index] = !indexTables[index];
    let currRow = $("i.rowDone." + index);

    if(indexTables[index]) {
        currRow.removeClass('fa-check-square-o').addClass('fa-square-o');
        currRow.find(".hiddenText")[0].innerText = 'false';
    }
    else {
        currRow.removeClass('fa-square-o').addClass('fa-check-square-o');
        currRow.find(".hiddenText")[0].innerText = 'true';
    }
    //$("table").trigger("updateAll", [ resort, callback ]);
    //table.tablesorter();
    table.trigger("update");
    table.trigger("appendCache");

    $.ajax({
        type: "PUT",
        url: "/task",
        data: "taskId=" + taskId,
        cache: true,
        success: function(response){},
        error: function(response) {},
    }).done(function (data, textStatus, xhr) {

        loader.fadeOut(300);
    });
};

let load = function(e) {
    loader.fadeIn(300);
    e.stopPropagation();
};

let displayModal = function(taskId, e) {

    tableBody = $("tbody");
    let id = tableBody.find("tr." + taskId + " .rowId")[0].innerText;
    let name = tableBody.find("tr." + taskId + " .rowName")[0].innerText;
    let des = tableBody.find("tr." + taskId + " .rowDescription")[0].innerText;
    modal.show(id + ' - ' + name, des);
};


$("document").ready(function() {       //Main

    loader = $("#loaderContent");

    $("#taskForm").submit(function () {
        loader.fadeIn(300);
    });


    if(window.location.href.match("home")) {
        table = $("#taskTable");
        table.tablesorter();

        modal = new Modal($("#taskModal"));
    }
});