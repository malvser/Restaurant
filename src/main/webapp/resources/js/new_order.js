$(document).ready(function () {


    $('#new_order').click(function () {

        if (document.getElementById('id').value > 0) {
            $.confirm({
                'title': 'Подтверждение приготовленного заказа',
                'message': 'Вы подтверждаете, что заказ приготовлен?',
                'buttons': {
                    'Да': {
                        'class': 'blue',
                        'action': function () {


                            var id = document.getElementById('id').value;

                            console.log("id = " + id);
                            window.location.href = "/cooked_order/" + id;


                        }
                    },
                    'Нет': {
                        'class': 'gray',
                        'action': function () {
                        }
                    }
                }
            });
        } else {
            window.location.href = '/order_for_cooks';
        }

    });

});



