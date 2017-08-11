$(document).ready(function(){
    var table = $('table.table-striped');
    table
        .on('change', '> tbody input:checkbox',function() {
            $(this).closest('span').toggleClass('checked', $(this).is(':checked'));
        })
        .on('change', '#alls', function(){
            $('> tbody input:checkbox', table).prop('checked', $(this).is(':checked')).trigger('change');
        });
});