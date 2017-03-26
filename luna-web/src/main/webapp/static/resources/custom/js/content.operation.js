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

function deleteContent(cId) {
	jc.sPost("/content/deleteItem", {
		cId : cId
	}, function() {
		refreshData(pageObject.runtime.page);
	})
}

function deleteItem(cId, title) {
	layer.confirm("你确定要将 '" + cId + "' 删除吗？", {
		btn : [ '必须的', '不要' ]
	// 按钮
	}, function(index) {
		deleteContent(cId);
		layer.close(index);
	}, function() {
		// nothing
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
								refreshForm(null)
							} else {
								layer.alert(data.message, {
									icon : 2
								});
							}
						});
			});

});