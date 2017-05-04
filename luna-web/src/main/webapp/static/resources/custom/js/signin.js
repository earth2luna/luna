jQuery(function() {
	var selector_form = jQuery(".script-login-form");

	signin(selector_form);

	function signin(selector_form) {
		var selector_submit = jQuery(".script-login-submit");
		selector_submit.off("click").click(function() {
			var ii = layer.load();
			signIning(selector_submit);
			var data = jc.serializeObject(selector_form);
			jQuery.post("/hello", data, function(ret) {
				layer.close(ii);
				if (1 == ret.code) {
					$.ajaxSettings.async = false;
					jQuery.each(ret.data, function() {
						$.getJSON(this);
					});
				} else {
					signInRest(selector_submit);
					status(false);
					signin(selector_form)
				}
			}, "json");
		});
	}

	function signIning(selector) {
		jQuery(selector).html("<i class=\"fa fa-sign-in fa-fw\"></i> 登陆...");
	}

	function signInRest(selector) {
		jQuery(selector).html("<i class=\"fa fa-sign-in fa-fw\"></i> 登陆");
	}

	function status(bool) {
		var selector = jQuery(".script-status");
		if (bool) {
			selector.removeClass("hidden  alert-danger alert-success")
					.addClass("alert-success");
		} else {
			selector.removeClass("hidden  alert-danger alert-success")
					.addClass("alert-danger");
		}
	}

});