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
    var mod = ng.module('shoppingCartModule', ['ngCrud', 'ui.router']);

    mod.constant('shoppingCartModel', {
        name: 'shoppingCart',
        displayName: 'Shopping Cart',
        url: 'shoppingCarts',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            client: {
                displayName: 'Client',
                type: 'Reference',
                model: 'clientModel',
                options: [],
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/shoppingCart/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('shoppingCart', {
                url: '/shoppingCarts?page&limit',
                abstract: true,
                
                views: {
                     mainView: {
                        templateUrl: basePath + 'shoppingCart.tpl.html',
                        controller: 'shoppingCartCtrl'
                    }
                },
                resolve: {
                    references: ['$q', 'Restangular', function ($q, r) {
                            return $q.all({
                                client: r.all('clients').getList()
                            });
                        }],
                    model: 'shoppingCartModel',
                    shoppingCarts: ['Restangular', 'model', '$stateParams', function (r, model, $params) {
                            return r.all(model.url).getList($params);
                        }]
                }
            });
            $sp.state('shoppingCartList', {
                url: '/list',
                parent: 'shoppingCart',
                views: {
                    shoppingCartView: {
                        templateUrl: basePath + 'list/shoppingCart.list.tpl.html',
                        controller: 'shoppingCartListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('shoppingCartNew', {
                url: '/new',
                parent: 'shoppingCart',
                views: {
                    shoppingCartView: {
                        templateUrl: basePath + 'new/shoppingCart.new.tpl.html',
                        controller: 'shoppingCartNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('shoppingCartInstance', {
                url: '/{shoppingCartId:int}',
                abstract: true,
                parent: 'shoppingCart',
                views: {
                    shoppingCartView: {
                        template: '<div ui-view="shoppingCartInstanceView"></div>'
                    }
                },
                resolve: {
                    shoppingCart: ['shoppingCarts', '$stateParams', function (shoppingCarts, $params) {
                            return shoppingCarts.get($params.shoppingCartId);
                        }]
                }
            });
            $sp.state('shoppingCartDetail', {
                url: '/details',
                parent: 'shoppingCartInstance',
                views: {
                    shoppingCartInstanceView: {
                        templateUrl: baseInstancePath + 'detail/shoppingCart.detail.tpl.html',
                        controller: 'shoppingCartDetailCtrl'
                    }
                }
            });
            $sp.state('shoppingCartEdit', {
                url: '/edit',
                sticky: true,
                parent: 'shoppingCartInstance',
                views: {
                    shoppingCartInstanceView: {
                        templateUrl: baseInstancePath + 'edit/shoppingCart.edit.tpl.html',
                        controller: 'shoppingCartEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('shoppingCartDelete', {
                url: '/delete',
                parent: 'shoppingCartInstance',
                views: {
                    shoppingCartInstanceView: {
                        templateUrl: baseInstancePath + 'delete/shoppingCart.delete.tpl.html',
                        controller: 'shoppingCartDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('shoppingCartItem', {
                url: '/item',
                parent: 'shoppingCartDetail',
                abstract: true,
                views: {
                    shoppingCartChieldView: {
                        template: '<div ui-view="shoppingCartItemView"></div>'
                    }
                },
                resolve: {
                    item: ['shoppingCart', function (shoppingCart) {
                            return shoppingCart.getList('item');
                        }],
                    model: 'itemModel'
                }
            });
            $sp.state('shoppingCartItemList', {
                url: '/list',
                parent: 'shoppingCartItem',
                views: {
                    shoppingCartItemView: {
                        templateUrl: baseInstancePath + 'item/list/shoppingCart.item.list.tpl.html',
                        controller: 'shoppingCartItemListCtrl'
                    }
                }
            });
            $sp.state('shoppingCartItemEdit', {
                url: '/edit',
                parent: 'shoppingCartItem',
                views: {
                    shoppingCartItemView: {
                        templateUrl: baseInstancePath + 'item/edit/shoppingCart.item.edit.tpl.html',
                        controller: 'shoppingCartItemEditCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    pool: ['Restangular', 'model', function (r, model) {
                            return r.all(model.url).getList();
                        }]
                }
            });
	}]);
})(window.angular);
