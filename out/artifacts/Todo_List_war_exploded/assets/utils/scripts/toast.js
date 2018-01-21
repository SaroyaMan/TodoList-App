"use strict";

/*
    https://github.com/CodeSeven/toastr
 */

let Toast = function() {

    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-left",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    this.showToast = function(type, msg, miliseconds) {
        // Display an info toast with no title
        toastr.options.timeOut = miliseconds? miliseconds : '3000';
        toastr[type](msg);
    }
};