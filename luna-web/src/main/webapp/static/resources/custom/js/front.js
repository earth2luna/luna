jQuery(function() {

	var selector_render = jQuery(".bs-docs-section .list-group");

	renderPageIteratorFormHtml("*");

	function scrollToElem(selecotr) {
		$('html, body').animate({
			scrollTop : $(selecotr).offset().top
		}, 100);
	}

	function refreshPageIterator(query, pageInfo) {
		pageObject.refresh({
			page : pageInfo.page,
			pageSize : pageInfo.pageSize,
			totalCount : pageInfo.total,
			jQueryParentSelector : ".async-page",
			action : function(action, config) {
				search(query, config.page);
			}
		});
	}

	function search(query, pageNow) {
		if (query) {
			jc.pPost("/front/items", {
				query : query,
				pageNow : pageNow,
			}, function(data) {
				selector_render.html(data);
				renderPageIteratorFormHtml(query, data);
				scrollToElem(".container.bs-docs-container");
			});
		}
	}

	function renderPageIteratorFormHtml(query, data) {
		if (!data) {
			data = selector_render.html();
		}
		if (data) {
			var selector_page_input = jQuery(data).filter(":input:hidden");
			eval('var pageInfo=' + selector_page_input.val());
			refreshPageIterator(query, pageInfo);
		}
	}

	var selector_input = jQuery(".search-wraper .form-control.search");
	selector_input.autosuggest({
		url : '/front/sugget',
		align : 'left',
		method : 'post',
		highlight : true,
		immediate : true,
		minLength : 1,
		queryParamName : 'query',
		nextStep : function(ele) {
			search(ele.data('value'), 1);
		},
		split : ' '
	});
	window.searchBtnSelector=".search-wraper .input-group-btn .btn"; 
	jQuery(searchBtnSelector).off().on(
			"click",
			function() {
				selector_input.trigger('focus');
				var input_val = selector_input.val();
				if (input_val) {
					search(input_val, 1);
				} else {
					if (0 == jQuery(selector_render).children(
							".list-group-item").length) {
						search("*", 1);
					}
				}
			});

	jQuery(document).on("click", ".alert-danger .alert-link", function() {
		selector_input.val("");
		selector_input.trigger('focus');
	});

	$.scrollUp({
		scrollName : "scrollUp",
		scrollFrom : "goTop",
		scrollText : '<i class="fa fa-angle-up"></i>'
	});

	// view

    simpleSearch();

	// jQuery('.thumbnail-origin').zoom();
	jQuery('.thumbnail-origin').parent("div").click(function() {
		jQuery(this).toggleClass("col-xs-6 col-xs-12 col-md-12 col-md-4");
		jQuery(this).find("a").toggleClass("border-px-0 pull-left");
	});

	function simpleSearch() {
		var query = jQuery(".bs-docs-section:eq(0) h1:eq(0)").text();
		if (query) {
			jQuery.post("/front/simpleItems", {
				query : query
			}, function(data) {
				jQuery(".relative-articles").html(data);
			});
		}
	}
});
