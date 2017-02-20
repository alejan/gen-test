/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
(function (ng) {
    var mod = ng.module('orderModule', ['ngCrud', 'ui.router']);

    mod.constant('orderModel', {
        name: 'order',
        displayName: 'Order',
        url: 'order',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/order/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('order', {
                url: '/order?page&limit',
                abstract: true,
                parent: 'shoppingCartDetail',
                views: {
                     shoppingCartChieldView: {
                        templateUrl: basePath + 'order.tpl.html',
                        controller: 'orderCtrl'
                    }
                },
                resolve: {
                    model: 'orderModel',
                    orders: ['shoppingCart', '$stateParams', 'model', function (shoppingCart, $params, model) {
                            return shoppingCart.getList(model.url, $params);
                        }]                }
            });
            $sp.state('orderList', {
                url: '/list',
                parent: 'order',
                views: {
                    orderView: {
                        templateUrl: basePath + 'list/order.list.tpl.html',
                        controller: 'orderListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('orderNew', {
                url: '/new',
                parent: 'order',
                views: {
                    orderView: {
                        templateUrl: basePath + 'new/order.new.tpl.html',
                        controller: 'orderNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('orderInstance', {
                url: '/{orderId:int}',
                abstract: true,
                parent: 'order',
                views: {
                    orderView: {
                        template: '<div ui-view="orderInstanceView"></div>'
                    }
                },
                resolve: {
                    order: ['orders', '$stateParams', function (orders, $params) {
                            return orders.get($params.orderId);
                        }]
                }
            });
            $sp.state('orderDetail', {
                url: '/details',
                parent: 'orderInstance',
                views: {
                    orderInstanceView: {
                        templateUrl: baseInstancePath + 'detail/order.detail.tpl.html',
                        controller: 'orderDetailCtrl'
                    }
                }
            });
            $sp.state('orderEdit', {
                url: '/edit',
                sticky: true,
                parent: 'orderInstance',
                views: {
                    orderInstanceView: {
                        templateUrl: baseInstancePath + 'edit/order.edit.tpl.html',
                        controller: 'orderEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('orderDelete', {
                url: '/delete',
                parent: 'orderInstance',
                views: {
                    orderInstanceView: {
                        templateUrl: baseInstancePath + 'delete/order.delete.tpl.html',
                        controller: 'orderDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
