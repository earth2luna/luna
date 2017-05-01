jQuery(function() {

	function scrollToElem(selecotr) {
		$('html, body').animate({
			scrollTop : $(selecotr).offset().top
		}, 500);
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
				jQuery(".bs-docs-section .list-group").html(data);
				var selector_page_input = jQuery(data).filter(":input:hidden");
				eval('var pageInfo=' + selector_page_input.val());
				refreshPageIterator(query, pageInfo);
				scrollToElem(".container.bs-docs-container");
			});
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

	jQuery(".search-wraper .input-group-btn .btn").off().on("click",
			function() {
				search(selector_input.val(), 1);
			});
});
