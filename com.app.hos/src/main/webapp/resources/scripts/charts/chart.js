
function Chart (id, options) {

	$(id).chart(options);
	var chart = $(id).highcharts();

	this.setSerie = function(id, data) {
		chart.series[id].setData(data, true);
	}
	
	this.addSerie = function(name, data) {
		chart.addSeries({
			name: name,
			data: data
		});
	}
	
	this.removeSeries = function() {
		var seriesLength = chart.series.length;
		for(var i = seriesLength -1; i > -1; i--) {
            chart.series[i].remove();
        }
		chart.colorCounter = 0;
	}
};
