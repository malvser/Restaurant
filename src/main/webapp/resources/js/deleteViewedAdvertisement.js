$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления записи о показаной рекламе',
            'message'	: 'Чтобы выбрать запись(и) для удаления, необходимо отметить их галочкой. Вы уверены, что хотите удалить запись(и)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                if ($(this).val() != 0) {
                                    date['toDelete[]'].push($(this).val());
                                }
                            });

                            $.post("/viewed_advertisement/delete", date, function(data, status) {
                                window.location.href = "/statistic_viewed_advertisement";
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

