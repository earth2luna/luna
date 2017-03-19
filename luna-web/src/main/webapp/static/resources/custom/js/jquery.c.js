jc = {
	formSelector : ":input,:input[type=hidden],:checked,textarea",
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
	}
};