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