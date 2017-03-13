alert('MELO');

(function () {
	alert('SIEMA');
	
	var Progress = function( element ) {
		this.canvas = document.getElementById('bar');
		//this.canvas = element;
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
			if (this.curr < this.finish + 1) {
			    requestAnimationFrame(function () {
			    	alert(progress.circum * progress.curr / 100 + progress.start);
				    progress.animate(progress.circum * progress.curr / 100 + progress.start);
				});
			}
		}
	};

	//var bars = document.querySelectorAll('#bar');
	//var progressBar = new Progress(bars[0]);
	//for (var i = 0; i < bars.length; i++) {
	//	var progressBar = new Progress(bars[i]);
		//alert('OK');
		//obj.data('progress',progressBar);
	//}

})();


	

