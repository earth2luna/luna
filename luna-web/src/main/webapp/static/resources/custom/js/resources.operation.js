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
	var sts = jQuery(":radio:checked").val();
	jQuery.post("/resources/queryItems", {
		sts : sts,
		pageNow : pageNow
	}, function(data) {
		jQuery("table.script-table tbody").empty().html(data);
		eval('var pageInfo=' + jQuery(data).filter(":input:hidden").val());
		refreshPageIterator(pageInfo);

	});
}

function operation(key, op) {
	jQuery.post("/resources/operation", {
		key : key,
		op : op
	}, function(data) {
		alert(JSON.stringify(data));
	}, "json");
}

jQuery(function() {

	jQuery(":radio").change(function() {
		refreshData(1);
	});

	jQuery(":radio").trigger("change");

	jQuery(document).on("change", "table.script-table .script-operator-select",
			function() {
				var select = jQuery(this).find(":checked");
				var key = select.parent().siblings(":input:hidden").val();
				var value = select.val();
				if (-1 != value && confirm("你确定要执行(" + select.text() + ")吗？")) {
					operation(key, value);
				}
			});

});