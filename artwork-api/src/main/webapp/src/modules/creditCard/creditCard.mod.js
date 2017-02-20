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
    var mod = ng.module('creditCardModule', ['ngCrud', 'ui.router']);

    mod.constant('creditCardModel', {
        name: 'creditCard',
        displayName: 'Credit Card',
        url: 'creditCard',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            }        }
    });

    mod.config(['$stateProvider',
        function($sp){
            var basePath = 'src/modules/creditCard/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('creditCard', {
                url: '/creditCard?page&limit',
                abstract: true,
                parent: 'clientDetail',
                views: {
                     clientChieldView: {
                        templateUrl: basePath + 'creditCard.tpl.html',
                        controller: 'creditCardCtrl'
                    }
                },
                resolve: {
                    model: 'creditCardModel',
                    creditCards: ['client', '$stateParams', 'model', function (client, $params, model) {
                            return client.getList(model.url, $params);
                        }]                }
            });
            $sp.state('creditCardList', {
                url: '/list',
                parent: 'creditCard',
                views: {
                    creditCardView: {
                        templateUrl: basePath + 'list/creditCard.list.tpl.html',
                        controller: 'creditCardListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardNew', {
                url: '/new',
                parent: 'creditCard',
                views: {
                    creditCardView: {
                        templateUrl: basePath + 'new/creditCard.new.tpl.html',
                        controller: 'creditCardNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardInstance', {
                url: '/{creditCardId:int}',
                abstract: true,
                parent: 'creditCard',
                views: {
                    creditCardView: {
                        template: '<div ui-view="creditCardInstanceView"></div>'
                    }
                },
                resolve: {
                    creditCard: ['creditCards', '$stateParams', function (creditCards, $params) {
                            return creditCards.get($params.creditCardId);
                        }]
                }
            });
            $sp.state('creditCardDetail', {
                url: '/details',
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'detail/creditCard.detail.tpl.html',
                        controller: 'creditCardDetailCtrl'
                    }
                }
            });
            $sp.state('creditCardEdit', {
                url: '/edit',
                sticky: true,
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'edit/creditCard.edit.tpl.html',
                        controller: 'creditCardEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('creditCardDelete', {
                url: '/delete',
                parent: 'creditCardInstance',
                views: {
                    creditCardInstanceView: {
                        templateUrl: baseInstancePath + 'delete/creditCard.delete.tpl.html',
                        controller: 'creditCardDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
