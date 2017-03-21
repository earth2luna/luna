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

function refreshForm(cId) {
	var rsId = jQuery(".script-content-form :input[name=rsId]").val();
	jQuery.post("/content/queryForm", {
		rsId : rsId,
		cId : cId
	}, function(data) {
		jQuery(".script-content-form-appender").empty().html(data);
	});
}

jQuery(function() {

	refreshForm(null);
	refreshData(pageObject.runtime.page);

	// 清空
	jQuery(document).on("click",
			".script-content-form .script-clear:input[type=button]",
			function() {
				jc.clear(".script-content-form", ":hidden[name=rsId]");
			});

	jQuery(document).on(
			"click",
			".script-content-form .script-submit",
			function() {
				jQuery.post("/content/mdf", jc
						.serializeObject(".script-content-form"),
						function(data) {
							if (1 == data.code) {
								refreshData(pageObject.runtime.page);
							} else {
								layer.alert(data.message,{icon: 2});
							}
						});
			});

});