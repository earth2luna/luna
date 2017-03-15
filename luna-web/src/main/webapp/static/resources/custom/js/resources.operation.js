jQuery(function() {

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

	jQuery(":radio").change(function() {
		refreshData(1);
	});

	jQuery(":radio").trigger("change");

	jQuery("select").on("click", function() {
		var select = jQuery("table.script-table select:checked");
		confirm("你确定要执行-" + select.text() + " 吗？", function() {
			alert("do");
		});
	});
});