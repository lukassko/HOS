var hosWebsocket = (function () {
	var stompClient = null;

	function connect() {
	    var socket = new SockJS('/HOS/device-info');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        stompClient.subscribe('/topic/device-info', function (greeting) {
	        	callback(greeting);
	        });
	    });
	}
	
	function disconnect() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	}
	
	function sendCommand(command) {
	    stompClient.send("/HOS/device-broker", {}, command);
	}
	
	function callback(message) {
	}
	
	//$(function () {
	//    $( "#send" ).click(function() { sendCommand(); });
	//    $( "#connect" ).click(function() { connect(); });
	//    $( "#disconnect" ).click(function() { disconnect(); });
	//});
	
	return {
		sendCommand : sendCommand
	}
	
})();