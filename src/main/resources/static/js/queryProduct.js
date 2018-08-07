var app = angular.module('myApp', ["ngRoute"]);

app.config(function($routeProvider) {
	  $routeProvider
		  .when("/product/:id", {
		    templateUrl : "/product.html"
		   });
	}); 

app.controller('myCtrl',  function($scope, $http) {

	$scope.alertContent = "";

	$scope.query = function(){
		$http.get("/queryProduct/query.do",
	        	{
		        params: { 
						'name': $scope.name
				}
	        }).then(
	       		function(response) {
	   		        $scope.results  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert(response.data.message);
	   		    }
	       );
	       
	};	
	$scope.viewProdDetail = function(id){
		$http.get("/client/queryProduct/viewProdDetail.do",
	        	{
		        params: { 
						'id': id
				}
	        }).then(
	       		function(response) {
	   		        $scope.results  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert(response.data.message);
	   		    }
	       );
	};	
	
});