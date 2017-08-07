$(document).ready(function(){
	
	$('#buttons-hbox').click(function(){
		
		<!-- var elem = $(this).closest('.form-control'); -->

		$.confirm({
			'title'		: 'Подтверждение заказа',
			'message'	: 'Подтверждаете заказ с выбранными Вами блюдами?',
			'buttons'	: {
				'Да'	: {
					'class'	: 'blue',
					'action': function(){
                        $.each('.form-control', function(){

                        });


					}
				},
				'Нет'	: {
					'class'	: 'gray',
					'action': function(){}	// Nothing to do in this case. You can as well omit the action property.
				}
			}
		});
		
	});
	
});