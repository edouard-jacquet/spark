$(document).ready(function() {
	
	var left = 5;
	
	$('#left-time').html(left +' seconds');
	
	setInterval(function() {
		left -= 1;
		if(left > 0) {
			var text = left +' second';
			if(left > 1) {text += 's';}
			$('#left-time').html(text);
		}
		else {
			window.location.href = 'home';
		}
	}, 1000);
	
});

