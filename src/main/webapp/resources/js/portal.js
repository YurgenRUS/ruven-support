var wrapper;

function tableInit(){
    var columnsForExport = [1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17];

    var surveys = $('#surveys-table');
    var table = surveys.DataTable({
        "sPaginationType": "full_numbers",
        "autoWidth": true,
        "scrollX": true,
        "oLanguage": {
            "sSearch": "Search everywhere:"
        },
        "columns": [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {
                "data": "id"
            },
            {
                "data": "move_type"
            },
            {
                "data": "contact"
            },
            {
                "data": "email"
            },
            {
                "data": "phone"
            },
            {
                "data": "mobile"
            },
            {
                "data": "from_briefly"
            },
            {
                "data": "to_briefly"
            },
            {
                "data": "from"
            },
            {
                "data": "to"
            },
            {
                "data": "s_comments"
            },
            {
                "data": "o_comments"
            },
            {
                "data": "d_comments"
            },
            {
                "data": "status"
            },
            {
                "data": "submitted"
            },
            {
                "data": "created"
            },
            {
                "data": "total"
            }
        ],
        "columnDefs": [
            {
                "targets": [9, 10, 11],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [12, 13, 14, 16],
                "visible": false
            }
        ],
        "order": [[1, 'desc']],
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'copy',
                text: 'Copy',
                className: 'webinv-button',
                exportOptions: {
                    columns: columnsForExport
                }
            },
            {
                extend: 'csv',
                text: 'Get as CSV',
                filename: 'surveys',
                className: 'webinv-button',
                exportOptions: {
                    columns: columnsForExport
                }
            },
            {
                extend: 'pdf',
                text: 'Get as PDF',
                filename: 'surveys',
                className: 'webinv-button',
                exportOptions: {
                    columns: columnsForExport
                }
            },
            {
                extend: 'print',
                text: 'Print',
                filename: 'surveys',
                className: 'webinv-button',
                exportOptions: {
                    columns: columnsForExport
                }
            },
            {
                extend: 'csv',
                text: 'Get selected as CSV',
                filename: 'surveys',
                exportOptions: {
                    exportOptions: {
                        columns: columnsForExport
                    },
                    modifier: {
                        selected: true
                    }
                },
                className: 'webinv-button'
            },
            {
                extend: 'pdf',
                text: 'Get selected as PDF',
                filename: 'surveys',
                exportOptions: {
                    exportOptions: {
                        columns: columnsForExport
                    },
                    modifier: {
                        selected: true
                    }
                },
                className: 'webinv-button'
            },
            {
                extend: 'print',
                text: 'Print selected',
                filename: 'surveys',
                exportOptions: {
                    exportOptions: {
                        columns: columnsForExport
                    },
                    modifier: {
                        selected: true
                    }
                },
                className: 'webinv-button'
            }
        ],
        select: true
    });

    surveys.DataTable().columns.adjust();

    surveys.find('tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            row.child.hide();
            tr.removeClass('shown');
        } else {
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });

    $('#from-date').change(function () {
        // table.draw();
        $('#surveys-table').DataTable().columns.adjust().draw();
    });

    $('#to-date').change(function () {
        // table.draw();
        $('#surveys-table').DataTable().columns.adjust().draw();
    });

    var update = $('#update-surveys');
    update.find('img').attr('src', contextPath + "/images/refresh_icon_20_white.png");
    update.prop('disabled', false);
}

function searchByStatus(){
    var table = $('#surveys-table').DataTable();
    switch ($("select#survey-status").val()) {
        case 'compiling':
            table.columns(14).search('COMPILING', true);
            break;
        case 'submitted':
            table.columns(14).search('SUBMITTED', true);
            break;
        default:
            table.columns(14).search('');
    }
}

function tableDestroy() {
    var update = $('#update-surveys');
    update.prop('disabled', true);
    update.find('img').attr('src', contextPath + "/images/circle.png");
    var surveys = $('#surveys-table');
    surveys.dataTable().fnDestroy();
    wrapper.hide();
}

function tableInitWithDelay() {
    var select = $("select#survey-status");
    var fromDate = $("#from-date");
    var toDate = $("#to-date");
    var fromDateValue = fromDate.val();
    var toDateValue = toDate.val();
    var selectValue = select.prop('selectedIndex');
    select.prop('selectedIndex',0);

    setTimeout(function(){
        tableInit();
        $("select#survey-status").prop('selectedIndex', selectValue);
        $("#from-date").val(fromDateValue);
        $("#to-date").val(toDateValue);
        searchByStatus();
        wrapper.show();
        $('#surveys-table').DataTable().columns.adjust().draw();
    }, 2000);
}

$(document).ready(function () {
    wrapper = $('#surveys-table-wrapper');
    $.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
            var status = $('select#survey-status').val();
            var fromDateValue = document.getElementById('from-date').value.trim().replace(/[^\d.-]/g, '');
            var toDateValue = document.getElementById('to-date').value.trim().replace(/[^\d.-]/g, '');
            var dateColumnIndex;
            switch (status) {
                case '':
                case 'compiling':
                    dateColumnIndex = 16;
                    break;
                case 'submitted':
                    dateColumnIndex = 15;
                    break;
            }

            var dateValue = aData[dateColumnIndex].replace(/[^\d.-]/g, '');

            if (dateValue.length === 0) {
                return fromDateValue.length === 0 && toDateValue.length === 0;
            } else if (fromDateValue.length === 0 && toDateValue.length === 0) {
                return true;
            }

            if (fromDateValue.length > 0 && fromDateValue.length < 10) {
                return false;
            }

            if (toDateValue.length > 0 && toDateValue.length < 10) {
                return false;
            }

            var fromDate;
            var toDate;

            if (fromDateValue.length >= 10) {
                fromDateValue = fromDateValue.substring(0, 10);
                fromDate = new Date(fromDateValue);
            }

            if (toDateValue.length >= 10) {
                toDateValue = toDateValue.substring(0, 10);
                toDate = new Date(toDateValue);
            }

            if (dateValue.length >= 10) {
                dateValue = dateValue.substring(0, 10);
                var date = new Date(dateValue);
            } else {
                return false;
            }

            if (fromDate <= date && toDateValue.length === 0) {
                return true;
            } else if (fromDate <= date && toDate >= date) {
                return true;
            }
            return false;
        }
    );


    $.fn.dataTableExt.oPagination.iFullNumbersShowPages = 5;
    $.fn.dataTableExt.oStdClasses.sPageFirst = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageLast = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageButton = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageButtonActive = "webinv-button active";
    $.fn.dataTableExt.oStdClasses.sPageButtonStaticDisabled = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPagePrevious = "webinv-button";
    $.fn.dataTableExt.oStdClasses.sPageNext = "webinv-button";

    $('#from-date').datepicker({
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 3,
        dateFormat: "yy-mm-dd",
        onClose: function (selectedDate) {
            $('#to-date').datepicker("option", "minDate", selectedDate);
        }
    });

    $('#to-date').datepicker({
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 3,
        dateFormat: "yy-mm-dd",
        onClose: function (selectedDate) {
            $('#from-date').datepicker("option", "maxDate", selectedDate);
        }
    });

    $("select#survey-status").change(function(){
        searchByStatus();
        $('#surveys-table').DataTable().columns.adjust().draw();
    });

    tableInit();
});

/* Formatting function for row details - modify as you need */
function format(d) {
    var result = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
        '<tr>' +
        '<td>Status:</td>' +
        '<td>' + d.status;
    if (d.submitted && d.submitted.length > 0) {
        result += ' | Submit date: ' + d.submitted;
    }
    result +=
        '</td></tr><tr>' +
        '<td>Origin:</td>' +
        '<td>' + d.from + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Destination:</td>' +
        '<td>' + d.to + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Survey comments:</td>' +
        '<td>' + d.s_comments + '</td>' +
        '</tr>' +
        '<tr>' +
        '<tr>' +
        '<td>Origin comments:</td>' +
        '<td>' + d.o_comments + '</td>' +
        '</tr>' +
        '<tr>' +
        '<tr>' +
        '<td>Destination comments:</td>' +
        '<td>' + d.d_comments + '</td>' +
        '</tr>' +
        '<tr>' +
        '<td>Totals:</td>' +
        '<td>' + d.total + '</td>' +
        '</tr>' +
        '</table>';
    return result;
}
