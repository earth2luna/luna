jQuery(function() {
	function search(query, pageNow) {
		if (query) {
			jc.pPost("/front/items", {
				query : query,
				pageNow : pageNow,
			}, function(data) {
				jQuery(".bs-docs-section .list-group").html(data);
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
