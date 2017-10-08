$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления записи об отсутствии рекламы',
            'message'	: 'Чтобы выбрать запись для удаления, необходимо отметить ее галачкой. Вы уверены, что хотите удалить запись(и)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });

                            $.post("/no_advertisement/delete", date, function(data, status) {
                                window.location.href = "/statistic_no_advertisement";
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


