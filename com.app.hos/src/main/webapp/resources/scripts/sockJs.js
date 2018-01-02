var hosWebsocket = (function () {

	var stompClient = null;

	function connect() {
	    var socket = new SockJS('/HOS/websocket');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	    	
	        stompClient.subscribe('/topic/websocket', function (command) {
	        	commandManager.receiveCommand(command);
	        });
	        
	        setSystemInfo("Connected to websocket");
	    });
	};
	
	function disconnect() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	};
	
	function sendCommand(command) {
		var command = JSON.stringify(command);
	    stompClient.send("/HOS/websocket", {}, command);
	};

	return {
		sendCommand : sendCommand,
		connect : connect,
		disconnect : disconnect
	}
	
})();