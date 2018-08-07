var applyApp = angular.module('myapp', []);

applyApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

applyApp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl, func){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType: 'text'
        })
        .then(
        	function(response){
        		func(response.data);
	        }, 
		    function(response) {
		    	 alert("error:"+JSON.stringify(response));
		    });
    }
}]);

applyApp.controller('myCtrl',  function($scope, $http, fileUpload) {

	$scope.connect = function(){
		if(!$scope.connected){
			var socket = new SockJS('/gs-guide-websocket');
			$scope.stompClient = Stomp.over(socket);
			$scope.stompClient.connect({}, function (frame) {
		    console.log('Connected: ' + frame);
		    $scope.connected = true;
		    $scope.queryQuota();
		    $scope.stompClient.subscribe('/topic/quota', function (msg) {
		    		// AngularJs is uncompatiable with socketJS
		    		var body = msg.body;
		    		if(body == null){
		    			jQuery("#quota").html(0);
		    		}else{
		    			jQuery("#quota").html(JSON.parse(body).amount);
		    		}
		    		
		        });
		    });
		}else{
			$scope.queryQuota();
		}
	};
	
	$scope.disconnect = function() {
	    if ($scope.stompClient !== null) {
	    	$scope.stompClient.disconnect();
	    }
	    console.log("Disconnected");
	};

	setInterval($scope.queryQuota, 30000);
	$scope.queryQuota = function() {
		//$scope.stompClient.send("/app/queryQuota", {}, JSON.stringify({'month': '9'}));
		$http.get("/quota/queryCurrentMonth.do",
	        	{
		        params: {}
	        }).then(
	       		function(response) {
	       			var quota = response.data[0];
	       			if(quota != null)
	       				$scope.quota = quota.amount;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	};

	$scope.query = function(){
		$http.get("/apply/query.do",
	        	{
		        params: { 
						'area': $scope.area, 
						'country': $scope.country,
						'city': $scope.city
				}
	        }).then(
	       		function(response) {
	   		        $scope.results  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	};	
	
	$scope.save = function(){
		$scope.alertContent = "";
		if($scope.quota == "" || $scope.quota == null || $scope.quota <= 0){
			$scope.alertContent = "Apologize! no enough quota!";
			return;
		}
		$http.post("/apply/save.do",
	        	{
						'area': $scope.i_area, 
						'country': $scope.i_country,
						'image': $scope.imgID
				}
	        ).then(
	       		function(response) {
	       			$scope.closeCreateNewApplyDialog();
	       			$scope.query();
	   		    }, 
	   		    function(response) {
	   		    	$scope.alertContent = response.data.message;
	   		    }
	       );
	};
	
	$scope.delete = function(city){
		
		$http.post("/apply/delete.do",
	        	{
						'city': city
				}
	        ).then(
	       		function(response) {
	   		        $scope.query();
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	};
	
	$scope.submit = function(city){
		$http.post("/apply/submit.do",
	        	{
						'city': city
	        	}
			).then(
	       		function(response) {
	       			$scope.query();
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	};	
	
	$scope.createNewApply = function(){
		$scope.wannaCreateNew = true;
		$scope.i_area = "";
		$scope.i_country = "";
		$scope.alertContent = "";
		$scope.connect();
	};
	
	$scope.closeCreateNewApplyDialog = function(){
		$scope.wannaCreateNew = false;
	};
	
	$scope.uploadImage = function(){
        fileUpload.uploadFileToUrl($scope.i_file, "/file/upload.do", 
        		function(data){
        			$scope.imgSrc = "/file/preview.do?fileID=" + data;
        			$scope.imgID = data;
        		}
        );
	};
	
});

