$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления столов',
            'message'	: 'Чтобы выбрать стол(ы) для удаления, необходимо отметить его галочкой. Вы уверены, что хотите удалить стол(ы)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });

                            $.post("/tablet/delete", date, function(data, status) {
                                window.location.href = "/tabletList";
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












