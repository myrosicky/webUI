var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {

	$scope.connected = false;
	setTimeout(function(){
		$scope.connect();
	}, 1000);
	$scope.connect = function(){
		if(!$scope.connected){
			var socket = new SockJS('/client/gs-guide-websocket');
			$scope.stompClient = Stomp.over(socket);
			$scope.stompClient.connect({}, function (frame) {
			    console.log('Connected: ' + frame);
			    $scope.connected = true;
		    });
		}
	};
	
	$scope.disconnect = function() {
	    if ($scope.stompClient !== null) {
	    	$scope.stompClient.disconnect();
	    }
	    console.log("Disconnected");
	};

	$scope.send = function(){
		var comment = $scope.i_comment;
		 $scope.stompClient.send("/app/queryComment", {}, JSON.stringify({
			 "text" : comment
			 }));
		 $scope.i_comment = "";
	};	
});