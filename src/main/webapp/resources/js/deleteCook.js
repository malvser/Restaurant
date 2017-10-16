$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления повара(ов)',
            'message'	: 'Чтобы выбрать повара для удаления, необходимо отметить его галочкой. Вы уверены, что хотите удалить повара(ов)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });

                            $.post("/cook/delete", date, function(data, status) {
                                window.location.href = "/cookList";
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


