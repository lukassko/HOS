	
// TODO:
// create factory method - create object first and later return 
function AjaxCall () {};

AjaxCall.prototype.send = function () {
	var that = this;
	$.ajax({
		url         : this.url,
	    type        : this.type,
	    contentType : 'aplication/json', //data type which is send
	    dataType    : 'json', //expected data type
	    data        : this.contentData
	})
	.done(function(response) {
		that.onSuccess(response);
	})
	.fail(function(response) {
		that.onFailed(response.status,response.responseText);
	});
};

function DeviceStatusAjax (serial,from,to) {
	AjaxCall.call(this);
	
	this.url = "devices/statuses/"+ serial +"?from=" + from + "&to=" + to;
	this.type = "get";
	this.contentData = {};
	this.onSuccess = function (response) {
		console.log(response);
	};
	
	this.onFailed = function (status, response) {
		console.log("status " + status);
	};
};

DeviceStatusAjax.prototype = Object.create(AjaxCall.prototype);

