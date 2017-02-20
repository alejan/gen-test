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
    var mod = ng.module('adressModule', ['ngCrud', 'ui.router']);

    mod.constant('adressModel', {
        name: 'adress',
        displayName: 'Adress',
        url: 'adresses',
        fields: {
            city: {
                displayName: 'City',
                type: 'String',
                required: true
            },
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/adress/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('adress', {
                url: '/adresses?page&limit',
                abstract: true,
                parent: 'clientDetail',
                views: {
                     clientChieldView: {
                        templateUrl: basePath + 'adress.tpl.html',
                        controller: 'adressCtrl'
                    }
                },
                resolve: {
                    model: 'adressModel',
                    adresss: ['client', '$stateParams', 'model', function (client, $params, model) {
                            return client.getList(model.url, $params);
                        }]                }
            });
            $sp.state('adressList', {
                url: '/list',
                parent: 'adress',
                views: {
                    adressView: {
                        templateUrl: basePath + 'list/adress.list.tpl.html',
                        controller: 'adressListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('adressNew', {
                url: '/new',
                parent: 'adress',
                views: {
                    adressView: {
                        templateUrl: basePath + 'new/adress.new.tpl.html',
                        controller: 'adressNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('adressInstance', {
                url: '/{adressId:int}',
                abstract: true,
                parent: 'adress',
                views: {
                    adressView: {
                        template: '<div ui-view="adressInstanceView"></div>'
                    }
                },
                resolve: {
                    adress: ['adresss', '$stateParams', function (adresss, $params) {
                            return adresss.get($params.adressId);
                        }]
                }
            });
            $sp.state('adressDetail', {
                url: '/details',
                parent: 'adressInstance',
                views: {
                    adressInstanceView: {
                        templateUrl: baseInstancePath + 'detail/adress.detail.tpl.html',
                        controller: 'adressDetailCtrl'
                    }
                }
            });
            $sp.state('adressEdit', {
                url: '/edit',
                sticky: true,
                parent: 'adressInstance',
                views: {
                    adressInstanceView: {
                        templateUrl: baseInstancePath + 'edit/adress.edit.tpl.html',
                        controller: 'adressEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('adressDelete', {
                url: '/delete',
                parent: 'adressInstance',
                views: {
                    adressInstanceView: {
                        templateUrl: baseInstancePath + 'delete/adress.delete.tpl.html',
                        controller: 'adressDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
