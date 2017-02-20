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
    var mod = ng.module('commentModule', ['ngCrud', 'ui.router']);

    mod.constant('commentModel', {
        name: 'comment',
        displayName: 'Comment',
        url: 'comment',
        fields: {
            text: {
                displayName: 'Text',
                type: 'String',
                required: true
            },
            email: {
                displayName: 'Email',
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
            var basePath = 'src/modules/comment/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('comment', {
                url: '/comment?page&limit',
                abstract: true,
                parent: 'artworkDetail',
                views: {
                     artworkChieldView: {
                        templateUrl: basePath + 'comment.tpl.html',
                        controller: 'commentCtrl'
                    }
                },
                resolve: {
                    model: 'commentModel',
                    comments: ['artwork', '$stateParams', 'model', function (artwork, $params, model) {
                            return artwork.getList(model.url, $params);
                        }]                }
            });
            $sp.state('commentList', {
                url: '/list',
                parent: 'comment',
                views: {
                    commentView: {
                        templateUrl: basePath + 'list/comment.list.tpl.html',
                        controller: 'commentListCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('commentNew', {
                url: '/new',
                parent: 'comment',
                views: {
                    commentView: {
                        templateUrl: basePath + 'new/comment.new.tpl.html',
                        controller: 'commentNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('commentInstance', {
                url: '/{commentId:int}',
                abstract: true,
                parent: 'comment',
                views: {
                    commentView: {
                        template: '<div ui-view="commentInstanceView"></div>'
                    }
                },
                resolve: {
                    comment: ['comments', '$stateParams', function (comments, $params) {
                            return comments.get($params.commentId);
                        }]
                }
            });
            $sp.state('commentDetail', {
                url: '/details',
                parent: 'commentInstance',
                views: {
                    commentInstanceView: {
                        templateUrl: baseInstancePath + 'detail/comment.detail.tpl.html',
                        controller: 'commentDetailCtrl'
                    }
                }
            });
            $sp.state('commentEdit', {
                url: '/edit',
                sticky: true,
                parent: 'commentInstance',
                views: {
                    commentInstanceView: {
                        templateUrl: baseInstancePath + 'edit/comment.edit.tpl.html',
                        controller: 'commentEditCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
            $sp.state('commentDelete', {
                url: '/delete',
                parent: 'commentInstance',
                views: {
                    commentInstanceView: {
                        templateUrl: baseInstancePath + 'delete/comment.delete.tpl.html',
                        controller: 'commentDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
	}]);
})(window.angular);
