$(document).ready(function(){


	
	$('#modal').click(function(){
		
		$.confirm({
			'title'		: 'Подтверждение заказа',
			'message'	: 'Для выбора заказа необходимо отметить его галачкой. Подтверждаете заказ с выбранными Вами блюдами?',
			'buttons'	: {
				'Да'	: {
					'class'	: 'blue',
					'action': function(){

						if($('[type="checkbox"]').is(':checked')) {

							var date = {'toOrder[]': []};
							$(":checked").each(function () {
								date['toOrder[]'].push($(this).val());
							});

							function buildElement(tagName, props) {
								var element = document.createElement(tagName);
								for (var propName in props) element[propName] = props[propName];
								return element;
							}

							function submit(link, props) {
								var form = buildElement('form', {
									method: 'post',
									action: link
								});
								for (var propName in props) form.appendChild(
									buildElement('input', {
										type: 'hidden',
										name: propName,
										value: props[propName]
									})
								);
								form.style.display = 'none';
								document.body.appendChild(form);
								form.submit();
							}


							submit('/order', date);

							console.log("date = " + 61);


						}

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

