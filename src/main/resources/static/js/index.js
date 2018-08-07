var app = angular.module('myapp', ["ngRoute"]);

app.controller('myCtrl',  function($scope, $location, $http) {

	$http.get("/menu/getMenu.do",
        	{
	        params: {}
        }).then(
       		function(response) {
   		        $scope.menu  = response.data;
   		    }, 
   		    function(response) {
   		    	 alert("error:"+JSON.stringify(response));
   		    }
       );
	
	$scope.gotoPage = function(module){
		jQuery("#contentFrame").attr("src", "/" + module );
	};	
	
	$scope.logout = function() {
//		jQuery("#logoutForm").submit();
	      $http.post("/logout",
		        	{} 
		        ).then(
		       		function(response) {
		    	        window.location = "/login";
		   		    }, 
		   		    function(response) {
		   		    }
		       );
	 };
});


