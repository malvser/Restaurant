$(document).ready(function(){
	
	$('#buttons-hbox').click(function(){
		
		var elem = $(this).closest('.item');
		
		$.confirm({
			'title'		: 'Подтверждение заказа',
			'message'	: 'You are about to delete this item. <br />It cannot be restored at a later time! Continue?',
			'buttons'	: {
				'Yes'	: {
					'class'	: 'blue',
					'action': function(){
						elem.slideUp();
					}
				},
				'No'	: {
					'class'	: 'gray',
					'action': function(){}	// Nothing to do in this case. You can as well omit the action property.
				}
			}
		});
		
	});
	
});