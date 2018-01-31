"use strict";

let Modal = function(modalElement) {

    let modalEl = modalElement;
    let modalTitleEl = modalEl.find(".modal-title").first();
    let modalBodyEl = modalEl.find(".modal-body").first();
    let btnEl = $("button[data-toggle='modal']").first();

    this.show = function(title, description) {
        modalTitleEl.text(title);
        modalBodyEl.text(description);
        btnEl.click();
    }

};