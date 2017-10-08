$(document).ready(function(){



    $('#modal_dish').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления блюд',
            'message'	: 'Чтобы выбрать блюдо для удаления, необходимо отметить его галачкой. Вы уверены, что хотите удалить блюдо(а)?',
            'buttons'	: {
                'Да'	: {
                    'class'	: 'blue',
                    'action': function(){

                        if($('[type="checkbox"]').is(':checked')) {

                            var date = {'toDelete[]': []};
                            $(":checked").each(function () {
                                date['toDelete[]'].push($(this).val());
                            });
                            console.log("data = " + date);
                            $.post("/dishes/delete", date, function(data, status) {
                                window.location.href = "/dishesList";
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

