
var deviceControlsManager = (function() {
	
	var bars = progressBars.getProgresBarArray;
	var progressMap = new Object();
	
	$('.my-progress').each(function( index ) {
		var data = $(this).attr('data-usage-type');
		progressMap[data] = bars[index];
	});
	
	function _setProgress(type,value) {
		var bars = document.querySelectorAll('.my-progress');
		bars.forEach(function (bar,index) {
			var data = $(bar).attr('data-usage-type');
			if (type === data) {
				var progressBar = progressMap[data];
				progressBar.draw(value);
			}
		});
	};
	
	function _setData(type,value) {
		var divId = '#' + type;
		$(divId).text(value);
	}
	
	return {
		setProgress: _setProgress,
		setData: _setData
	}
	
})();
	