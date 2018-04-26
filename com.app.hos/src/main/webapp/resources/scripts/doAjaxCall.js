	
// TODO:
// create factory method - create object first and later return 
function AjaxCall () {}

AjaxCall.prototype.send = function () {
	var that = this;
	$.ajax({
		url         : this.url,
	    type        : this.type,
	    contentType : this.contentType, //data type which is send
	    data        : this.contentData
	})
	.done(function(response) {
		that.onSuccess(response.status,response);
	})
	.fail(function(response) {
		that.onFailed(response.status,response);
	});
}

function AjaxCallResponse (callback) {
	this.callback = callback;
	AjaxCall.call(this);
}

AjaxCall.prototype.onSuccess = function (status, response) {
	this.callback(status, response);
}

AjaxCall.prototype.onFailed = function (status,response) {
	switch (status) {
		case 401:
			window.removeEventListener("beforeunload",onBeforeWindowUnload);
			onWindowUnload(null);
			writeDocumentContent(response.responseText);
			break;
		default:
			setSystemInfo("Error requesting page: " + page);
	}
}

AjaxCallResponse.prototype = Object.create(AjaxCall.prototype);

function GetViewCall (page,callback) {
	
	AjaxCallResponse.call(this,callback);
	
	this.url = page;
	this.type = "GET";
}

function DeviceStatusCall (serial,from,to,callback) {
	
	AjaxCallResponse.call(this,callback);
	
	this.url = "devices/statuses/"+ serial +"?from=" + from + "&to=" + to;
	this.type = "GET";
	this.contentType = 'aplication/json';
	this.dataType = 'json';
}

function DeleteDeviceCall (serial,callback) {
	
	AjaxCallResponse.call(this,callback);
	
	this.url = "devices/statuses/"+ serial;
	this.type = "DELETE";
	this.contentType = 'aplication/json';
	this.dataType = 'json';
}

function GetActiveUserCall (callback) {
	
	AjaxCallResponse.call(this,callback);
	
	this.url = "user/getactive";
	this.type = "GET";
	this.contentType = 'aplication/json';
	this.dataType = 'json';
}

function LogoutUserCall (callback) {
	
	AjaxCallResponse.call(this,callback);
	
	this.url = "user/logout";
	this.type = "GET";
	this.contentType = 'aplication/json';
	this.dataType = 'json';
}

GetViewCall.prototype = Object.create(AjaxCallResponse.prototype);
DeviceStatusCall.prototype = Object.create(AjaxCallResponse.prototype);
DeleteDeviceCall.prototype = Object.create(AjaxCallResponse.prototype);
GetActiveUserCall.prototype = Object.create(AjaxCallResponse.prototype);
LogoutUserCall.prototype = Object.create(AjaxCallResponse.prototype);
