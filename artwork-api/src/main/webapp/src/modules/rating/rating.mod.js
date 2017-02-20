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
    var mod = ng.module('ratingModule', ['ngCrud', 'ui.router']);

    mod.constant('ratingModel', {
        name: 'rating',
        displayName: 'Rating',
        url: 'rating',
        fields: {
            score: {
                displayName: 'Score',
                type: 'Integer',
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
            var basePath = 'src/modules/rating/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('rating', {
                url: '/rating?page&limit',
                abstract: true,
                parent: 'artistDetail',
                views: {
                     artistChieldView: {
                        templateUrl: basePath + 'rating.tpl.html',
                        controller: 'ratingCtrl'
                    }
                },
                resolve: {
                    model: 'ratingModel',
                    ratings: ['artist', '$stateParams', 'model', function (artist, $params, model) {
                            return artist.getList(model.url, $params);
                        }]                }
            });
            $sp.state('ratingList', {
                url: '/list',
                parent: 'rating',
                views: {
                    ratingView: {
                        templateUrl: basePath + 'list/rating.list.tpl.html',
                        controller: 'ratingListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('ratingNew', {
                url: '/new',
                parent: 'rating',
                views: {
                    ratingView: {
                        templateUrl: basePath + 'new/rating.new.tpl.html',
                        controller: 'ratingNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('ratingInstance', {
                url: '/{ratingId:int}',
                abstract: true,
                parent: 'rating',
                views: {
                    ratingView: {
                        template: '<div ui-view="ratingInstanceView"></div>'
                    }
                },
                resolve: {
                    rating: ['ratings', '$stateParams', function (ratings, $params) {
                            return ratings.get($params.ratingId);
                        }]
                }
            });
            $sp.state('ratingDetail', {
                url: '/details',
                parent: 'ratingInstance',
                views: {
                    ratingInstanceView: {
                        templateUrl: baseInstancePath + 'detail/rating.detail.tpl.html',
                        controller: 'ratingDetailCtrl'
                    }
                }
            });
            $sp.state('ratingEdit', {
                url: '/edit',
                sticky: true,
                parent: 'ratingInstance',
                views: {
                    ratingInstanceView: {
                        templateUrl: baseInstancePath + 'edit/rating.edit.tpl.html',
                        controller: 'ratingEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('ratingDelete', {
                url: '/delete',
                parent: 'ratingInstance',
                views: {
                    ratingInstanceView: {
                        templateUrl: baseInstancePath + 'delete/rating.delete.tpl.html',
                        controller: 'ratingDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
