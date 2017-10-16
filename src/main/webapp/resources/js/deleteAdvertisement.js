$(document).ready(function(){



    $('#modal').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления рекламы',
            'message'	: 'Чтобы выбрать рекламу для удаления, необходимо отметить его галочкой. Вы уверены, что хотите удалить рекламу?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });

                            $.post("/advertisement/delete", date, function(data, status) {
                                window.location.href = "/advertisementList";
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

