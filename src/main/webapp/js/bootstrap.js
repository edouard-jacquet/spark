/* ============================== *\
   => NAVIGATION BAR
\* ============================== */
(function($, window, document, undefined) {
	var pluginName = 'navbar';
	var defaults;

	function Plugin(element, options) {
		this.element = element;
		this.options = $.extend({}, defaults, options);
		this._name = pluginName;
		this.init();
	}
	Plugin.prototype = {
		init: function(options) {
			this._callbackToggle();
		},
		_callbackToggle: function(options) {
			$('.navigation-bar__toggle').bind('click', function(e) {
				e.preventDefault();
				var trigger = $(this);
				var container = $(trigger).parents('.navigation-bar').find('.navigation-bar__body');
				$(container).stop().slideToggle(500, function() {
					$(trigger).toggleClass('active');
					$(container).toggleClass('open');
				});
			});
		}
	};
	$.fn[pluginName] = function(options, extras) {
		return this.each(function() {
			if (!$.data(this, 'plugin_' + pluginName)) {
				$.data(this, 'plugin_' + pluginName, new Plugin(this, options));
			} else if ($.isFunction(Plugin.prototype[options])) {
				$.data(this, 'plugin_' + pluginName)[options](extras);
			}
		});
	}
})(jQuery, window, document);
$(document).ready(function() {
	$('.navigation-bar').navbar();
});


/* ============================== *\
   => NOTIFICATION
\* ============================== */
(function($, window, document, undefined) {
	var pluginName = 'notification';
	var defaults;

	function Plugin(element, options) {
		this.element = element;
		this.options = $.extend({}, defaults, options);
		this._name = pluginName;
		this.init();
	}
	Plugin.prototype = {
		init: function(options) {
			this._callbackClose();
		},
		_callbackClose: function() {
			$('body').on('click', '.notification__close', function(e) {
				e.preventDefault();
				var trigger = $(this);
				var container = $(trigger).parents('.notification');
				$(container).fadeOut(500);
			});
		}
	};
	$.fn[pluginName] = function(options, extras) {
		return this.each(function() {
			if (!$.data(this, 'plugin_' + pluginName)) {
				$.data(this, 'plugin_' + pluginName, new Plugin(this, options));
			} else if ($.isFunction(Plugin.prototype[options])) {
				$.data(this, 'plugin_' + pluginName)[options](extras);
			}
		});
	}
})(jQuery, window, document);
$(document).ready(function() {
	$(document).notification();
});