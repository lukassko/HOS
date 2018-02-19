
var chartsApi = (function() {
	
	var series = [];
	var cpu = [];
	var ram = [];
	
	function _setStatus(data) {
		cpu = [];
		ram = [];
		$.each(data , function(index, statuses) {
			  var time = statuses.time;
			  var date = Date.UTC(time.year, time.month, time.day, time.hour, time.minutes, time.seconds);
			  cpu.push([date,statuses.cpu]);
			  ram.push([date,statuses.ram]);
		});
	}
	
	function _getSeries() {
		series = [];
		var cpuSerie = {
				name: 'CPU Usage',
				data : cpu
		} 
		var ramSerie = {
				name: 'RAM Usage',
				data : ram
		} 
		series.push(cpuSerie);
		series.push(ramSerie);
		return series;
	}
	
	return {
		setStatus: _setStatus,
		getSeries: _getSeries
	}
	
})();
	