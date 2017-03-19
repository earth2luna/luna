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
		if (1 == data.code) {
			if (6 == op) {
				open(data.data);
			} else {
				refreshData(pageObject.runtime.page);
			}
		} else {
			alert(data.message);
		}

	}, "json");
}

function refreshForm(rsId) {
	jQuery.post("/resources/queryForm", {
		rsId : rsId
	}, function(data) {
		jQuery(".script-resources-form-appender").empty().html(data);
	});
}

jQuery(function() {

	jQuery(":radio").change(function() {
		refreshData(pageObject.runtime.page);
	});

	jQuery(":radio[value=1]").trigger("change");

	// 任务
	jQuery(document).on("change", "table.script-table .script-operator-select",
			function() {
				var select = jQuery(this).find(":checked");
				var key = select.parent().siblings(":input:hidden").val();
				var value = select.val();
				if (-1 != value && confirm("你确定要执行'" + select.text() + "'吗？")) {
					operation(key, value);
				}
			});

	refreshForm(null);

	// 执行
	jQuery(document).on(
			"click",
			".script-mdf-resources-form .script-exe:input[type=button]",
			function() {
				jQuery.post("/resources/mdf", jc
						.serializeObject(".script-mdf-resources-form"),
						function(data) {
							if (1 == data.code) {
								refreshData(pageObject.runtime.page);
							} else {
								alert(data.message);
							}
						});
			});
	// 清空
	jQuery(document).on("click",
			".script-mdf-resources-form .script-clear:input[type=button]",
			function() {
				jc.clear(".script-mdf-resources-form");
			});
});