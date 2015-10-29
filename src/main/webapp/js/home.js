$(document).ready(function() {
	
	$('#query').focus();
	
	$('#query').bind('keyup', function(event) {
		var trigger = $(this);
		suggest($(this).val());
	});
	
});