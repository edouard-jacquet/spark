$(document).ready(function() {
	
	$('#query').focus();
	
	$('#query').bind('keyup', function(event) {
		var trigger = $(this);
		suggest($(this).val());
	});
	
	$('#control-settings').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		$(trigger).toggleClass('active');

		if(!$('#settings').hasClass('open')) {
			$('#settings').css({
				opacity: 0
			}).delay(125).animate({
				opacity: 1,
				'margin-top': '0'
			}, 250, 'swing', function() {
				$('#settings').addClass('open');
			});
			
			$('#details').animate({
				opacity: 0,
			}, 250, 'swing');
		}
		else {
			$('#settings').animate({
				opacity: 0,
				'margin-top': '-20px'
			}, 250, 'swing', function() {
				$('#settings').removeClass('open');
			});
			
			$('#details').css({
				opacity: 0
			}).delay(125).animate({
				opacity: 1,
			}, 250, 'swing');
		}
	});
	
});