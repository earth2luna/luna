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
	var ii = layer.load();
	jQuery.post("/resources/operation", {
		key : key,
		op : op
	}, function(data) {
		layer.close(ii);
		if (1 == data.code) {
			if (6 == op) {
				open(data.data);
			} else {
				refreshData(pageObject.runtime.page);
			}
		} else {
			layer.alert(data.message, {
				icon : 2
			});
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

function openNewWindow(rsId) {
	open("/content/query?rsId=" + rsId);
}

jQuery(function() {

	jQuery(":radio").change(function() {
		refreshData(pageObject.runtime.page);
	});

	jQuery(":radio[value=1]").trigger("change");

	// 任务
	jQuery(document).on(
			"change",
			"table.script-table .script-operator-select",
			function() {
				var select = jQuery(this).find(":checked");
				var title = select.parent().siblings(
						":input:hidden.script-resource-title").val();
				var key = select.parent().siblings(
						":input:hidden.script-resource-id").val();
				var value = select.val();
				if (-1 != value) {
					layer.confirm("你确定要将'" + title + "'执行'" + select.text()
							+ "'吗？", {
						btn : [ '必须的', '不要' ]
					// 按钮
					}, function(index) {
						operation(key, value);
						layer.close(index);
					}, function() {
						select.siblings("option[value=-1]").attr("selected",
								true);
					});
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
								layer.alert(data.message, {
									icon : 2
								});
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