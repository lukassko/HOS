
var carousel = (function() {

	var carouselRotateId = setInterval(rotate,2000);
	
	var itemWidth = $('#carousel-slides li').outerWidth();

	var leftValue = itemWidth * (-1); 
	
	$('#carousel-slides li:first').before($('#carousel-slides li:last'));
	//set the default item to the correct position 
	$('#carousel-slides ul').css({'left' : leftValue});

	//move the last item before first item, just in case user click prev button

	$('#carousel-next').click(function() {

		var leftIndent = parseInt($('#carousel-slides ul').css('left')) - itemWidth;
		
		$('#carousel-slides ul').animate({'left' : leftIndent}, 500, function () {
			
			$('#carousel-slides li:last').after($('#carousel-slides li:first'));
			
			$('#carousel-slides ul').css({'left' : leftValue});
		});
		
		return false;
	});
	
	function rotate () {
		$('#caroules-next').trigger("click" );
	}

})();

