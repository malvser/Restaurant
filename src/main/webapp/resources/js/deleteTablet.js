$('#delete_tablet').click(function(){
    var data = { 'toDelete[]' : []};
    $(":checked").each(function() {
        data['toDelete[]'].push($(this).val());
    });
    $.post("/tablet/delete", data, function(data, status) {
        window.location.href = "/tabletList";
    });
});