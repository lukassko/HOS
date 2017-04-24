var stompClient = null;


function connect() {
    var socket = new SockJS('/HOS/device-info');
    stompClient = Stomp.over(socket);
    alert("socket2");
    stompClient.connect({}, function (frame) {
    	alert("CONNECTED " + frame);
        stompClient.subscribe('/topic/device-info', function (greeting) {
        	callback(greeting);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    alert("DISCONECT!");
}

function sendName() {
    stompClient.send("/HOS/device-broker", {}, "SIEMA");
}

function callback(message) {
    alert(message);
}

$(function () {
    $( "#send" ).click(function() { sendName(); });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});