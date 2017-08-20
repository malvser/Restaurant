$('#delete_advertisement').click(function(){
var data = { 'toDelete[]' : []};
$(":checked").each(function() {
data['toDelete[]'].push($(this).val());
});
$.post("/advertisement/delete", data, function(data, status) {
window.location.href = "/advertisementList";
});
});