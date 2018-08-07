var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {

	$scope.connected = false;
	jQuery("#chatBoard").val("");
	jQuery("#chatBoard").attr('readonly','readonly');
	
	jQuery(function(){
		
		
		flowplayer("liveStreamPlayer", "http://localhost:9090/flowplayer/flowplayer-3.2.18.swf", {
			 
		    clip: {
		        url: 'livestream', // stream name
		        live: true,
		        provider: 'hddn'
		    },
		 
		    // streaming plugins are configured under the plugins node
		    plugins: {
		 
		        hddn: {
		            url: "http://localhost:9090/flowplayer.rtmp/flowplayer.rtmp-3.2.13.swf", // no need to change
		 
//		            netConnectionUrl: 'rtmp://localhost:1953/oflaDemo'
		            netConnectionUrl: 'rtmp://localhost:1935/live'
		        }
		    }
		});
		
		jQuery("#liveStreamPlayer").danmu({
			width:644,
			height:276,
			zindex:100,
		    speed:20000,
		    sumTime:65535,
		    opacity:1,
		    font_size_small:16,
		    font_size_big:24,
		    top_botton_danmu_time:6000
		  });
		
		setTimeout(function(){
			$scope.connect();
			jQuery("#liveStreamPlayer").danmu('danmuStart');
		}, 1000);
		
	});
	
	
	$scope.connect = function(){
		if(!$scope.connected){
			var socket = new SockJS('/gs-guide-websocket');
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
//			    			jQuery("#danmup .danmu-input").val(text);
//			    			jQuery("#danmup .send-btn").click(); 
			    			
			    			send2(text);
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
	
	var send2 = function(text){
	    var color = "white";
	    var position = "0";
	    var time = jQuery("#liveStreamPlayer").data("nowTime")+1;
	    var size = "1";
	    var text_obj='{ "text":"'+text+'","color":"'+color+'","size":"'+size+'","position":"'+position+'","time":'+time+'}';
//	    $.post("stone.php",{danmu:text_obj});
	    var text_obj='{ "text":"'+text+'","color":"'+color+'","size":"'+size+'","position":"'+position+'","time":'+time+'}';
	    var new_obj=eval('('+text_obj+')');
	    jQuery("#liveStreamPlayer").danmu("addDanmu",new_obj);
	  };
	
});