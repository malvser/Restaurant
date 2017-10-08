$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления записи о приготовленных заказах',
            'message'	: 'Чтобы выбрать запись(и) для удаления, необходимо отметить ее галачкой. Вы уверены, что хотите удалить запись(и)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });

                            $.post("/cooked_order/delete", date, function(data, status) {
                                window.location.href = "/statistic_cooked_order";
                            });


                        }

                    }
                },
                'Нет'	: {
                    'class'	: 'gray',
                    'action': function(){}
                }
            }
        });

    });

});

