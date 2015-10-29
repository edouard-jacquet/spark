var ajax = null;

$(document).ready(function() {
	
});

function isFileAPIEnabled() {
	return !!window.FileReader;
}

function isEventSupported(eventName, element) {
	element = element || document.createElement('div');
	eventName = 'on'+ eventName;
	
	var isSupported = eventName in element;
	
	if(!isSupported) {
		if(!element.setAttribute) {
			element = document.createElement('div');
		}
		if(element.setAttribute && element.removeAttribute) {
			element.setAttribute(eventName, '');
			isSupported = typeof element[eventName] == 'function';
			
			if(typeof element[eventName] != 'undefined') {
				element[eventName] == undefined;
			}
			element.removeAttribute(eventName);
		}
	}
	
	return isSupported;
}

function isEmpty(string) {
	return (!string || string.length === 0);
}

function isBlank(string) {
	return (!string || /^[\s].*$/.test(string));
}

function suggest(query) {
	if(!isEmpty(query) && !isBlank(query)) {
		if(ajax != null) {
			ajax.abort();
			ajax = null;
		}
		
		ajax = $.ajax({
			url: 'search/suggestion',
			method: 'get',
			data: {query: query},
			dataType: 'json'
		});
		
		ajax.done(function(response) {
			if(response.error) {
				return false;
			}
			
			$('#suggestions').css('display', 'none').removeClass('open').empty();
			
			for(var i = 0 ; i < response.data.length ; i++) {	
				$('#suggestions').append("\
					<a class='list-group__item' \
						href='search?query="+ response.data[i].label +"'>\
						"+ response.data[i].label.replace(query, '<strong>'+ query +'</strong>') +"\
					</a>\
				");
			}
			
			$('#suggestions').css('display', 'block').addClass('open');
		});
		
		ajax.fail(function(xhr, textStatus) {
			$('#suggestions').css('display', 'none').removeClass('open');
		});
	}
	else {
		$('#suggestions').css('display', 'none').removeClass('open');
	}
}