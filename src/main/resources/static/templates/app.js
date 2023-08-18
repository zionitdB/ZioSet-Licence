		angular.module('myApp', [
	'ui.router',
	'vcRecaptcha',
	'LocalStorageModule',
	'angularUtils.directives.dirPagination',
	'ngCookies',
	'toastr',
	'chart.js',
	'ngJsonExportExcel',
	'ui.bootstrap',
//	'myApp.barcode',
	'myApp.main',
	'myApp.home',
	'myApp.login',
	'myApp.generic',
	'myApp.user',
	'myApp.asset',

	'myApp.assetEmployeeMapped',
	'myApp.assetInfo',
	
	'myApp.notification',
	
	'myApp.profile',
	
	'myApp.employee',
	'myApp.deallocationAsset',
	'myApp.help',
	'myApp.rolePermission',
	'myApp.licence',
	'myApp.licenceLifeNotification',
	'myApp.licenceAllocation',
	'myApp.bundleAppication',
	'myApp.categoryGrouping',
	'myApp.customerSuppliedSoftware',
	'myApp.systemLicence',
	'myApp.licenceExpiry',
	'myApp.saasLicenceDashboard',
	'myApp.installedLiceneExpiry',
	'myApp.uploadLiceneExpiry',
	'myApp.systemDashboard',
	'myApp.installLicenceDashboard',
	'myApp.InstockLicence',
	'myApp.AssignedLicence',
	'myApp.saasLiceneExpiry',
	'myApp.InstallLicence',
	'myApp.renewalLicence',
	'myApp.workerInstalled',
	'myApp.systemDetails',
	'myApp.licenceDetials',
	'myApp.fetchInstallLicence',
	'myApp.emailTemplate',
	'myApp.mailManger',
	'myApp.todayFetchInstallLicence',
	'myApp.branch',
	
	'myApp.reportActiveLicence',
	'myApp.reportExpiryLicence',
	'myApp.reportInstallLicence',
	'myApp.assetUnavailableworker',
	'myApp.unavailableworkerforLast5Days',
	'myApp.reportUnavailableWorker',
])

.value('_', window._)

.constant('ApiEndpoint', {
	
 //url: 'http://allstate.adp.ind.in:8080/',
url: 'http://localhost:8084/',
 serverUrl:'http://20.219.1.165:8092/',
  //url: 'http://20.219.1.165:8084/',
	//url: 'http://10.195.36.182:8080/',
	//url: 'http://10.195.36.182:8082/',
	
	//url: 'http://INPUNINA024Q530:8082/',
//userKey : 'allState',
	headerKey : ''
})
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
  
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }else if($location.path() == '/login' && $rootScope.globals.currentUser){
            	$location.path('/main/home');
            }
        });
    }])

/*.config(function($urlRouterProvider,$locationProvider) {
	 if none of the above states are matched, use this as the fallback
	$urlRouterProvider.otherwise('/main/home');
	 // use the HTML5 History API
	
	 $locationProvider.hashPrefix('');
}); */