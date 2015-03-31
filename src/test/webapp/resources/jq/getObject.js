$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/kitty/get/1"
    }).then(function(data) {
       $('.kitty-id').append(data.id);
       $('.kitty-name').append(data.name);
       $('.kitty-message').append(data.message);
    });
});