
function addZero(n) {
	return n.toString().length == 1 ?  n = '0' + n: n;
}

function getTimeAsString(year,month,day,hour,minute,second){
	return year + '/' + month + '/' + day + ' ' + hour + ':' + minute + ':' + second;
}


function setSystemInfo(message) {
	$("#system-info").text(message);
	$("#system-info").fadeIn(1000);
	setTimeout(function(){
		$("#system-info").fadeOut(2000)
	}, 3000 );
};