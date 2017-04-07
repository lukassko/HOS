(function () {
	var Progress = function( element ) {
		this.canvas = element;
	 	this.width = this.canvas.width;
		this.height = this.canvas.height;
		this.init();
	};
	
	Progress.prototype = {
		init: function () {
			var ctx = this.canvas.getContext('2d');
			ctx.lineWidth = 45;
			ctx.font = "80px Arial";
			ctx.textAlign="center"; 
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
			this.context.arc(this.x, this.y, 120, this.start, draw_to, false);
			var percent = this.finish + "%";
			this.context.fillText(percent,150,175);
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

	var bars = document.querySelectorAll('#bar');
	var tmpBar = bars[0];
	var progressBar = new Progress(tmpBar);
	$('.my-progress').data('progress', progressBar);
})();

