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
    var mod = ng.module('purchaseModule', ['ngCrud', 'ui.router']);

    mod.constant('purchaseModel', {
        name: 'purchase',
        displayName: 'Purchase',
        url: 'purchase',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/purchase/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('purchase', {
                url: '/purchase?page&limit',
                abstract: true,
                parent: 'clientDetail',
                views: {
                     clientChieldView: {
                        templateUrl: basePath + 'purchase.tpl.html',
                        controller: 'purchaseCtrl'
                    }
                },
                resolve: {
                    model: 'purchaseModel',
                    purchases: ['client', '$stateParams', 'model', function (client, $params, model) {
                            return client.getList(model.url, $params);
                        }]                }
            });
            $sp.state('purchaseList', {
                url: '/list',
                parent: 'purchase',
                views: {
                    purchaseView: {
                        templateUrl: basePath + 'list/purchase.list.tpl.html',
                        controller: 'purchaseListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('purchaseNew', {
                url: '/new',
                parent: 'purchase',
                views: {
                    purchaseView: {
                        templateUrl: basePath + 'new/purchase.new.tpl.html',
                        controller: 'purchaseNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('purchaseInstance', {
                url: '/{purchaseId:int}',
                abstract: true,
                parent: 'purchase',
                views: {
                    purchaseView: {
                        template: '<div ui-view="purchaseInstanceView"></div>'
                    }
                },
                resolve: {
                    purchase: ['purchases', '$stateParams', function (purchases, $params) {
                            return purchases.get($params.purchaseId);
                        }]
                }
            });
            $sp.state('purchaseDetail', {
                url: '/details',
                parent: 'purchaseInstance',
                views: {
                    purchaseInstanceView: {
                        templateUrl: baseInstancePath + 'detail/purchase.detail.tpl.html',
                        controller: 'purchaseDetailCtrl'
                    }
                }
            });
            $sp.state('purchaseEdit', {
                url: '/edit',
                sticky: true,
                parent: 'purchaseInstance',
                views: {
                    purchaseInstanceView: {
                        templateUrl: baseInstancePath + 'edit/purchase.edit.tpl.html',
                        controller: 'purchaseEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('purchaseDelete', {
                url: '/delete',
                parent: 'purchaseInstance',
                views: {
                    purchaseInstanceView: {
                        templateUrl: baseInstancePath + 'delete/purchase.delete.tpl.html',
                        controller: 'purchaseDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
