$(document).ready(function() {
    getMemoList();
})

function getMemoList() {
    $.ajax({
        type: 'GET',
        url: '/memo',
        success: function (response) {
            console.log(response);
        }
    })
}