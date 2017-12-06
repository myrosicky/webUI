var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {

	$scope.connected = false;
	jQuery("#chatBoard").val("");
	jQuery("#chatBoard").attr('readonly','readonly');
	
	jQuery(function(){
		
		setTimeout(function(){
			$scope.connect();
		}, 1000);
		
		jQuery("#danmup").DanmuPlayer({
//			src:"http://localhost:9090/client/video/level5.mp4",
			src:"rtmp://localhost/SOSample",
		    height: "480px", 
			    width: "800px" 
			    ,speed : 15000,	
//			    ,urlToGetDanmu:"http://localhost:9090/client/chatRoom/get.do"
//			    ,urlToPostDanmu:"http://localhost:9090/client/chatRoom/send.do"
		 });
	});
	
	
	$scope.connect = function(){
		if(!$scope.connected){
			var socket = new SockJS('/client/gs-guide-websocket');
			$scope.stompClient = Stomp.over(socket);
			$scope.stompClient.connect({}, function (frame) {
			    console.log('Connected: ' + frame);
			    $scope.connected = true;
			    $scope.stompClient.subscribe('/topic/comment', function (msg) {
			    		var body = msg.body;
			    		$scope.i_comment = "";
			    		if(body != null){
			    			var text = JSON.parse(body).text;
			    			jQuery("#chatBoard").val(jQuery("#chatBoard").val() + "\nvisitor:" + text);
			    			jQuery("#danmup .danmu-input").val(text);
			    			jQuery("#danmup .send-btn").click(); 
			    		}
			        });
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
		 $scope.stompClient.send("/app/queryComment", {}, JSON.stringify({
			 "text" : $scope.i_comment
			 }));
	};	
	
	
});