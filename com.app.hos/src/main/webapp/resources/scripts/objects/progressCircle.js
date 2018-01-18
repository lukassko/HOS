var progressBars = (function () {

	var arrayBars = new Array();
    
	var Progress = function( element ) {
		this.canvas = element;
	 	this.width = this.canvas.width;
		this.height = this.canvas.height;
		this.init();
	};
	
	Progress.prototype = {
		init: function () {
			var ctx = this.canvas.getContext('2d');
			ctx.lineWidth = 20;
			ctx.font = "40px Arial";
			ctx.fillStyle = "#d19b3d";
			ctx.textAlign="center"; 
			ctx.strokeStyle = '#d19b3d';
			ctx.shadowOffsetX = 0;
			ctx.shadowOffsetY = 0;
			ctx.shadowBlur = 10;
			ctx.shadowColor = '#d19b3d';
			this.context = ctx;
			this.x = this.width / 2;
			this.y = this.height / 2;
			this.finish = 0; 
			this.curr = 0; 
			this.circum =Math.PI * 2;
			this.start = Math.PI / -2;
			var raf =
			    window.requestAnimationFrame ||
			    window.mozRequestAnimationFrame ||
			    window.webkitRequestAnimationFrame ||
			    window.msRequestAnimationFrame;
			window.requestAnimationFrame = raf;
			this.incrAnimate();
		},
	    draw: function(draw_to) {
	    	if (isNaN(draw_to)) {
	    		throw "Input value is NaN";
	    	} 
	    	var draw_to = parseInt(draw_to);
	    	if (draw_to == this.finish || draw_to > 100 || draw_to < 0) {return;}
	    	if (draw_to > this.finish) {
	    		this.finish = draw_to;
	    		this.incrAnimate();
	    	}
	     	if (draw_to < this.finish) {
	     		this.finish = draw_to;
	     		this.decrAnimate();
	     	}
	    },
	    animate: function(draw_to) {
	    	this.context.clearRect(0, 0, this.width, this.height);
			this.context.beginPath();
			this.context.arc(this.x, this.y, 60, this.start, draw_to, false);
			var percent = this.finish + "%";
			this.context.fillText(percent,75,90);
			this.context.stroke();
	    },
	    incrAnimate: function(draw_to) {
	    	this.animate(draw_to);
	    	var self = this;
	    	this.curr++;
	    	if (this.curr < this.finish + 1) {
			requestAnimationFrame(function () {
				 self.incrAnimate(self.circum * self.curr / 100 + self.start);
			 });
	      }
	    },
	    decrAnimate: function(draw_to) {
	    	this.animate(draw_to);
	    	var self = this;
	    	this.curr--;
	    	if (this.curr > this.finish - 1) {
				requestAnimationFrame(function () {
				  	self.decrAnimate(self.circum * self.curr / 100 + self.start);
					});
			}
	    }
	};

	var bars = document.querySelectorAll('.my-progress > canvas');
	bars.forEach(function (bar,index) {
		var progressBar = new Progress(bar);
		arrayBars.push(progressBar);
	})
	//$('.my-progress').data('progress', progressBar);
	
	return {
        getProgresBarArray : arrayBars
    }
})();

