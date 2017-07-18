var hosWebsocket = (function () {

	var stompClient = null;

	function connect() {
	    var socket = new SockJS('/HOS/device-info');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	    	
	        stompClient.subscribe('/topic/device-info', function (message) {
	        	deviceManager.receiveMessage(message);
	        });
	        
	    });
	};
	
	function disconnect() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	};
	
	function sendCommand(command) {
		var command = JSON.stringify(command);
	    stompClient.send("/HOS/device-broker", {}, command);
	};

	return {
		sendCommand : sendCommand,
		connect : connect,
		disconnect : disconnect
	}
	
})();