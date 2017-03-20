function refreshPageIterator(pageInfo) {
	pageObject.refresh({
		page : pageInfo.page,
		pageSize : pageInfo.pageSize,
		totalCount : pageInfo.total,
		jQueryParentSelector : ".async-page",
		action : function(action, config) {
			refreshData(config.page);
		}
	});
}

function refreshData(pageNow) {
	var rsId = jQuery(".script-content-form :input[name=rsId]").val();
	jQuery.post("/content/queryItems", {
		rsId : rsId,
		pageNow : pageNow
	}, function(data) {
		jQuery("table.script-table tbody").empty().html(data);
		eval('var pageInfo=' + jQuery(data).filter(":input:hidden").val());
		refreshPageIterator(pageInfo);
	});
}


jQuery(function() {

	refreshData(pageObject.runtime.page);
	
	jQuery(".script-content-form .script-submit").click(function() {

	});

});