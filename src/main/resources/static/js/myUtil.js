function showBar(e, str) {
    e.popover({content: str});
    e.popover('show');
    e.on('hidden.bs.popover', function () {
        e.popover('destroy');
    });
}

function checkEmpty(e) {
    var ret = true;
    e.forEach(function (item) {
        if (item.val() == "") {
            ret = false;
            showBar(item, "This field couldn't be empty.");
        }
    });
    return ret;
}