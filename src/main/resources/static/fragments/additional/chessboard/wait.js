const id = window.location.pathname.split('/').pop();
function updatePage() {
    $.ajax({
        url: '/chess/' + id,
        success: function(data) {
            $('#content').html(data);
        }
    });
}


setInterval(updatePage, 5000);

updatePage();