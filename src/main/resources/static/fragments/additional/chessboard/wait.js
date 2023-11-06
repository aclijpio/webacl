function updatePage() {
    $.ajax({
        url: '/api/refresh',
        success: function(data) {
            $('#content').html(data);
        }
    });
}


setInterval(updatePage, 5000);

updatePage();