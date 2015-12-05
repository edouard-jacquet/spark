$(document).ready(function() {
	
	$('#index-document').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		index('document');
	});
	
	$('#index-suggestion').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		index('suggestion');
	});
	
});

/* ============================== *\
   => Index
\* ============================== */
function index(name) {
	$('#index-loader').css('display', 'inline-block');
	
	if(_GLOBAL_ajax != null) {
		_GLOBAL_ajax.abort();
		_GLOBAL_ajax = null;
	}
	
	_GLOBAL_ajax = $.ajax({
		url: _JAVA_context + '/administration/index',
		method: 'post',
		data: {name: name},
		dataType: 'json'
	});
	
	_GLOBAL_ajax.done(function(response) {
		$('#notifications').css('display', 'none').empty();
		displayNotifications(response.notifications);
		$('#notifications').slideDown(500);
	});

	_GLOBAL_ajax.always(function() {
		$('#index-loader').css('display', 'none');
	});
}