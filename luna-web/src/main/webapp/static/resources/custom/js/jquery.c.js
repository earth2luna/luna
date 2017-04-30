jc = {
	formSelector : ":input:not(:input[type=button],:input[type=reset],:input[type=submit]),:checked,textarea",
	serializeObject : function(formSelector) {
		var data = {};
		jQuery(formSelector).find(jc.formSelector).each(function() {
			var current = jQuery(this);
			var value = current.val();
			var name = current.attr("name");
			if (value && name)
				data[name] = value;
		});
		return data;
	},
	clear : function(formSelector, notSelector) {
		jQuery(formSelector).find(jc.formSelector).not(
				":checked,select,option,:radio,checkbox").not(notSelector).val(
				"");
	},
	sPost : function(url, parameters, fn, type) {
		var ii = layer.load();
		jQuery.post(url, parameters, function(data) {
			layer.close(ii);
			if (1 == data.code) {
				fn(data);
			} else {
				layer.alert(data.message, {
					icon : 2
				});
			}
		}, type);
	},
	pPost : function(url, parameters, fn, type) {
		var ii = layer.load();
		jQuery.post(url, parameters, function(data) {
			layer.close(ii);
			fn(data);
		}, type);
	}
};