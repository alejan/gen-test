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
    var mod = ng.module('saleModule', ['ngCrud', 'ui.router']);

    mod.constant('saleModel', {
        name: 'sale',
        displayName: 'Sale',
        url: 'sale',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/sale/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('sale', {
                url: '/sale?page&limit',
                abstract: true,
                parent: 'artistDetail',
                views: {
                     artistChieldView: {
                        templateUrl: basePath + 'sale.tpl.html',
                        controller: 'saleCtrl'
                    }
                },
                resolve: {
                    model: 'saleModel',
                    sales: ['artist', '$stateParams', 'model', function (artist, $params, model) {
                            return artist.getList(model.url, $params);
                        }]                }
            });
            $sp.state('saleList', {
                url: '/list',
                parent: 'sale',
                views: {
                    saleView: {
                        templateUrl: basePath + 'list/sale.list.tpl.html',
                        controller: 'saleListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('saleNew', {
                url: '/new',
                parent: 'sale',
                views: {
                    saleView: {
                        templateUrl: basePath + 'new/sale.new.tpl.html',
                        controller: 'saleNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('saleInstance', {
                url: '/{saleId:int}',
                abstract: true,
                parent: 'sale',
                views: {
                    saleView: {
                        template: '<div ui-view="saleInstanceView"></div>'
                    }
                },
                resolve: {
                    sale: ['sales', '$stateParams', function (sales, $params) {
                            return sales.get($params.saleId);
                        }]
                }
            });
            $sp.state('saleDetail', {
                url: '/details',
                parent: 'saleInstance',
                views: {
                    saleInstanceView: {
                        templateUrl: baseInstancePath + 'detail/sale.detail.tpl.html',
                        controller: 'saleDetailCtrl'
                    }
                }
            });
            $sp.state('saleEdit', {
                url: '/edit',
                sticky: true,
                parent: 'saleInstance',
                views: {
                    saleInstanceView: {
                        templateUrl: baseInstancePath + 'edit/sale.edit.tpl.html',
                        controller: 'saleEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('saleDelete', {
                url: '/delete',
                parent: 'saleInstance',
                views: {
                    saleInstanceView: {
                        templateUrl: baseInstancePath + 'delete/sale.delete.tpl.html',
                        controller: 'saleDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
