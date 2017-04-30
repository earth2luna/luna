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
	jQuery(".search-wraper .form-control.search").autosuggest({
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
});
