// create by lyl as laulyl 20160720
//update by lyl as laulyl 20170225 解决了多条件交替分页，如果其中一个条件总条数为0,不清除分页内容的问题
//update by lyl as laulyl 20170609 此版本为非ajax请求方式,从v2版本改进来的，只保留公共方法，来支持go,加加减减
// 公共局部刷新分页
var PageObject = {
	isDigital : function(number) {
		return number && /\d+/.test(number) && number > 0;
	},
	isGt : function(number, gt, defaultValue) {
		if (PageObject.isDigital(number) && number > gt) {
			return number;
		}
		return defaultValue;
	},
	inputValuePlus : function(currentSelector, maxValue) {
		var selector = jQuery(currentSelector).siblings(":input");
		var currentValue = parseInt(selector.val());
		if (currentValue < maxValue) {
			jQuery(selector).val(currentValue + 1);
		}
	},
	inputValueMinus : function(currentSelector) {
		var selector = jQuery(currentSelector).siblings(":input");
		var currentValue = parseInt(selector.val());
		if (currentValue > 1) {
			jQuery(selector).val(currentValue - 1);
		}
	},
	go : function(currentSelector, route, pages) {
		var selector = jQuery(currentSelector).siblings("span").find(":input");
		var currentValue = parseInt(selector.val());
		if (currentValue && currentValue > 0 && currentValue < pages) {
			location.href = route + "/" + currentValue + "/";
		}
	}
};