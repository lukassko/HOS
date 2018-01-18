var hosWebsocket = (function () {

	var socket = null;
	var url = "ws://localhost:8080/HOS/websocket";
	var isConnected = false;
	
	function connect(onConnectCallback) {
	    socket = new WebSocket(url);
	    
	    socket.onopen = function() {
	    	isConnected = true;
	    	setSystemInfo("Connected to websocket");
	    	onConnectCallback();
	    }
	    
	    socket.onmessage = function (event) {
	    	var command = JSON.parse(event.data);
	    	commandManager.receiveCommand(command);
	    };
	    
	    socket.onclose = function() {
	    	setSystemInfo("WebSocket closed");
	    }
	    	 
	    socket.onerror = function() {
	    	setSystemInfo("WebSocket error!");
	    }
	    
	};
	
	function disconnect() {
	    if (socket != null) {
	    	socket.close();
	    }
	};
	
	function sendCommand(command) {
		if (isConnected) {
			var command = JSON.stringify(command);
			socket.send(command);
		} else {
			setSystemInfo("WebSocket is not connected!");
		}
		
	};

	return {
		sendCommand : sendCommand,
		connect : connect,
		disconnect : disconnect
	}
	
})();