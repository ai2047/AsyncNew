$(function() {

    $('#connect').click(function() {
        $.get('download').done(function(data){
            console.log(data);
        })
    });
});