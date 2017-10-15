$(document).ready(function () {


    $('#modal').click(function () {


        var log = document.getElementById('log').value;

        if (log != "empty") {
            var bonus = document.getElementById('bonus').value;
            console.log("bonus = " + bonus);
            if (bonus != "little") {


                console.log("login = " + log.toString());

                $.confirm({
                    'title': 'Подтверждение снятие бонусов',
                    'message': 'Для выбора заказа необходимо отметить его галочкой. Вы хотели бы воспользоваться своими бонусами для скидки на блюда? , ',
                    'buttons': {
                        'Да': {
                            'class': 'blue',
                            'action': function () {

                                if ($('[type="checkbox"]').is(':checked')) {

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

                                    console.log("date = " + date);
                                    submit('/made/order_bonus', date);


                                }

                            }
                        },
                        'Нет': {
                            'class': 'gray',
                            'action': function () {
                                if ($('[type="checkbox"]').is(':checked')) {

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

                                    console.log("date = " + date);
                                    submit('/made/order', date);


                                }

                            }
                        },
                        'Отмена': {
                            'class': 'gray',
                            'action': function () {
                            }
                        }
                    }
                });
            } else {
                $.confirm({
                    'title': 'Подтверждение заказа',
                    'message': 'Для выбора заказа необходимо отметить его галочкой. Подтверждаете заказ с выбранными Вами блюдами?',
                    'buttons': {
                        'Да': {
                            'class': 'blue',
                            'action': function () {

                                if ($('[type="checkbox"]').is(':checked')) {

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

                                    console.log("date = " + date);
                                    submit('/made/order', date);


                                }

                            }
                        },
                        'Нет': {
                            'class': 'gray',
                            'action': function () {
                            }
                        }
                    }
                });
            }
        } else {
            console.log("login = " + log.toString());

            $.confirm({
                'title': 'Подтверждение заказа',
                'message': 'Для выбора заказа необходимо отметить его галочкой. Подтверждаете заказ с выбранными Вами блюдами?',
                'buttons': {
                    'Да': {
                        'class': 'blue',
                        'action': function () {

                            if ($('[type="checkbox"]').is(':checked')) {

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

                                console.log("date = " + date);
                                submit('/made/order', date);


                            }

                        }
                    },
                    'Нет': {
                        'class': 'gray',
                        'action': function () {
                        }
                    }
                }
            });
        }

    });

});

