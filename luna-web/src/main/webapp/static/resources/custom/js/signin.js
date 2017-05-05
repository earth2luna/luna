jQuery(function() {


	var selector_form = jQuery(".script-login-form");

	signin(selector_form);

	function signin(selector_form) {
		var selector_submit = jQuery(".script-login-submit");
		selector_submit.off().click(function() {
			jQuery(this).off();
			var ii = layer.load();
			signIning(selector_submit);
			var data = jc.serializeObject(selector_form);
			$.ajaxSettings.async = false;
			jQuery.post(getHelloUrl(), data, function(ret) {
				layer.close(ii);
				if (1 == ret.code) {
					status(true);
					jQuery.each(ret.data.nodeDomains, function() {
						$.getJSON(this);
					});
					if (ret.data.ret) {
						location = ret.data.ret;
					}
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
					.addClass("alert-success").html(
							"<strong>Well done!</strong> 登陆成功，即将跳转...");
		} else {
			selector.removeClass("hidden  alert-danger alert-success")
					.addClass("alert-danger").html(
							"<strong>Oh snap!</strong> 用户名或密码错误。");
		}
	}

	function getHelloUrl() {
		var queryString = location.search;
		var hello = "/hello";
		if (queryString) {
			hello += queryString;
		}
		return hello;
	}

});