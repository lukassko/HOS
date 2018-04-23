
$(window).click(function(e) {
	$('.dropdown-content').each(function(){
		if ($(this).hasClass('show')) {
			$(this).removeClass('show');
		}
	});
});

$('.dropdown .dropbtn').each(function() {
	$(this).on("click", function(event) {
		$('.dropdown-content').each(function(){
			event.stopPropagation();
			$(this).toggleClass('show');
		});
	 });
});

