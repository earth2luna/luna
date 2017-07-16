jQuery(function() {
	window.searchBtnSelector = ".search-wraper .input-group-btn";
	var selector_input = jQuery(".search-wraper .form-control.search");

	function search(query) {
		var href = query ? "/query/" + query + "/1/" : "/1/";
		location.href = href;
	}

	selector_input.autosuggest({
		url : '/front/sugget',
		align : 'left',
		method : 'post',
		highlight : true,
		immediate : true,
		minLength : 1,
		queryParamName : 'query',
		nextStep : function(ele) {
			var query = ele.data('value');
			search(query);
		},
		split : ' '
	});

	jQuery(document).on("click", ".alert-danger .alert-link", function() {
		selector_input.val("");
		selector_input.trigger('focus');
		jQuery(searchBtnSelector).click();
	});

	jQuery(searchBtnSelector).off().on("click", function() {
		selector_input.trigger('focus');
		var input_val = selector_input.val();
		search(input_val);
	});

	jQuery.scrollUp({
		scrollName : "scrollUp",
		scrollFrom : "goTop",
		scrollText : '<i class="fa fa-angle-up"></i>'
	});

	// jQuery('.thumbnail-origin').zoom();
	jQuery('.thumbnail-origin').parent("div").click(function() {
		jQuery(this).toggleClass("col-xs-6 col-xs-12 col-md-12 col-md-4");
		jQuery(this).find("a").toggleClass("border-px-0 pull-left");
	});

});
