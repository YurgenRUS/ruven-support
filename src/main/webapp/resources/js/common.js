function trickySelectAll(element) {
    element.selectionStart = 0;
    element.selectionEnd = this.value.length;
}

function clearValue(element) {
    var jQelement = $('#' + element.id);
    if (jQelement.val().trim().length > 0) {
        var val = Number(jQelement.val().trim());
        if (val == 0) {
            jQelement.val('');
        }
    }
}

function makeAutocomplete(jQueryElement, availableTags) {
    $.ui.autocomplete.filter = function (array, term) {
        var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(term), "i");
        return $.grep(array, function (value) {
            return matcher.test(value.label || value.value || value);
        });
    };

    //availableTags = availableTags.slice(0, 10);

    jQueryElement.autocomplete({
        source: function (req, response) {
            var results = $.ui.autocomplete.filter(availableTags, req.term);
            // response(results.slice(0, 5));
            response(results);
        }
    });
}

function fixContentPosition() {
    var textAuto = $('.header-title-wrapper-mobile');
    if (textAuto.length > 0) {
        textAuto.textfill({
            maxFontPixels:14
        });
    }

    var header = $('.header');
    var freeSpace = $('#free-space-before-content');
    freeSpace.css('height', '');
    header.css('height', '');
    var navigation = $('#small-screen-navigation');
    if (!navigation.is(":visible")) {
        navigation = $('#wide-screen-navigation');
    }
    if (navigation.length > 0) {
        var navigationBottomPosition = navigation.position().top + navigation.outerHeight(true);
        var contentTopPosition = $('.content').position().top;
        var shift = navigationBottomPosition - contentTopPosition;
        if (shift > 0) {
            header.height("+=" + shift);
            var freeSpaceShift = shift + em(1);
            freeSpace.height("+=" + freeSpaceShift);
        }
    }

//  Items and Rooms pages

    var buttonAdd = $('#button-add');
    var buttonAddStd = $('#button-add-std');
    if (buttonAdd.length !== 0 && buttonAddStd.length !== 0) {
        if (buttonAddStd.is(":visible")) {
            buttonAdd.height(buttonAddStd.height());
        }
    }

    var windowHeight = window.innerHeight;
    if (windowHeight >= 736) {
        var roomList = $('#room_list');
        var itemList = $('#item_list');
        var footer;
        if (roomList.length !== 0 || itemList.length !== 0) {
            var beforeListElementBottom;
            var instructions = $('#accordion');
            var addSelected = $('#add-selected');
            if (addSelected.length > 0) {
                beforeListElementBottom  = addSelected.position().top + addSelected.height();
            } else {
                beforeListElementBottom = instructions.position().top + instructions.height() + 20;
            }
            footer = $('footer').height();

            if (roomList.length !== 0) {
                roomList.height(windowHeight - beforeListElementBottom - footer - em(1));
            }

            if (itemList.length !== 0) {
                itemList.height(windowHeight - beforeListElementBottom - footer - em(2));
            }
        }
    }
}

function em(input) {
    var emSize = parseFloat($("body").css("font-size"));
    return (emSize * input);
}

$(window).resize(function () {
    fixContentPosition();
});