$(document).ready(function(){



    $('#delete').click(function(){

        $.confirm({
            'title'		: 'Подтверждение удаления пользователей',
            'message'	: 'Чтобы выбрать пользователя для удаления, необходимо отметить его галочкой. Вы уверены, что хотите удалить пользователя(пользователей)?',
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
                            $.post("/user/delete", date, function(data, status) {
                                window.location.href = "/userlist";
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

