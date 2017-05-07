jQuery(function() {
	// 获取input 标签
	function getInput(name, placeholder) {
		var input = "<input name=\"" + name + "\" width=\"100\" placeholder=\""
				+ placeholder + "\" />";
		return input;
	}

	var selector_select = jQuery(".script-super-select");
	var selector_parameter = jQuery(".script-super_select-parameter");
	selector_select.off().on("change", function() {
		jQuery(".script-super-select-show").empty();
		selector_parameter.empty();
		var currentValue = jQuery(this).val();
		if ('2' == currentValue) {
			selector_parameter.append(getInput("DELETEDIDS", "输入DELETED IDS"));
		}
	});
	jQuery(selector_select).trigger("change");
});

// 执行任务
function doIt() {
	var selector_show = jQuery(".script-super-select-show");
	selector_show.html("正在同步......");
	var selector = jQuery(".script-super-select");
	var data = {
		op : selector.val()
	};
	selector.siblings(".script-super_select-parameter").find(":input").each(
			function() {
				var value = jQuery(this).val();
				var name = jQuery(this).attr("name");
				if (name && value) {
					data[name] = value;
				}
			});
	jc.sPost("/super/doIt", data, function(data) {
		selector_show.html("同步完成!");
	}, "json");
}