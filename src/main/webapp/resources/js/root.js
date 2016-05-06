var contextPath = 'http://localhost:8080/support/';
var table;

$(document).ready(function () {
    $.fn.dataTableExt.oPagination.iFullNumbersShowPages = 5;
    $.fn.dataTableExt.oStdClasses.sPageFirst = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageLast = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageButton = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageButtonActive = "webinv-button active";
    $.fn.dataTableExt.oStdClasses.sPageButtonStaticDisabled = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPagePrevious = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageNext = "webinv-button";

    loadUsersTable();
});

function refreshTable() {
    $('#users-table').DataTable().draw();
}


function loadUsersTable() {
    var table = $('#users-table').DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            url: contextPath + "rest/users/list",
            data: function (d) {
            }
        },
        columns: [
            {data: null, defaultContent: ''},
            {data: "name"},
            {data: "role"},
            {data: "login"}
        ],
        columnDefs: [
            {
                orderable: false,
                targets: [0, 1, 2, 3]
            },
            {
                className: 'select-checkbox',
                targets: 0
            }
        ],
        select: {
            style: 'multi',
            selector: 'td:first-child'
        },
        scrollX: true,
        lengthMenu: [[-1], ["All"]],
        dom: '<lfr<t>ip>'
    });
}
