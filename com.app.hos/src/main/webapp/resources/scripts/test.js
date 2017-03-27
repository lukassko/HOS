(function () {
	
	var Progress = function( element ) {
		this.canvas = element;
		$('#parent').data( 'progressCircle', this );
	    this.width = this.canvas.width;
	    this.height = this.canvas.height;
		this.init();
	};
	
	Progress.prototype = {
		init: function () {
			alert('INIT');
			var ctx = this.canvas.getContext('2d');
			ctx.lineWidth = 45;
			ctx.strokeStyle = '#0099ff';
			ctx.shadowOffsetX = 0;
			ctx.shadowOffsetY = 0;
			ctx.shadowBlur = 10;
			ctx.shadowColor = '#0099ff';
			this.context = ctx;
			this.x = this.width / 2;
		    this.y = this.height / 2;
		    this.finish = 66; 
		    this.curr = 0; 
			this.circum =Math.PI * 2;
			this.start = Math.PI / -2;
			var raf =
			    window.requestAnimationFrame ||
			    window.mozRequestAnimationFrame ||
			    window.webkitRequestAnimationFrame ||
			    window.msRequestAnimationFrame;
			window.requestAnimationFrame = raf;
			this.animate();
		},
		animate: function(draw_to) {
			this.context.clearRect(0, 0, this.width, this.height);
			this.context.beginPath();
			this.context.arc(this.x, this.y, 120, this.start, draw_to, false);
			this.context.stroke();
			this.curr++;
			var self = this;
			if (this.curr < this.finish + 1) {
			    requestAnimationFrame(function () {
			    	progressBar.animate(self.circum * self.curr / 100 + self.start);
				});
			}
		}
	};
	var bars = document.querySelectorAll('#bar');
	var tmpBar = bars[0];
	var progressBar = new Progress(tmpBar);
	//for (var i = 0; i < bars.length; i++) {
	//	var progressBar = new Progress(bars[i]);
		//alert('OK');
		//obj.data('progress',progressBar);
	//}

})();


	

