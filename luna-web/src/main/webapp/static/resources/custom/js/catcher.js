PlusMinus = {
	event_parent_selector : ".script-p-m",
	plusHtml : "<span class=\"input-group-addon\"><a href=\"javascript:void(0);\" onclick=\"PlusMinus.plus(this);\"><i class=\"fa fa-plus\" aria-hidden=\"true\"></i></a></span>",
	minusHtml : "<span class=\"input-group-addon\"><a href=\"javascript:void(0);\" onclick=\"PlusMinus.minus(this);\"><i class=\"fa fa-minus\" aria-hidden=\"true\"></i></a></span>",
	init : function() {
		$(this.event_parent_selector).append(this.plusHtml);
	},
	plus : function(current) {
		var selector_li = $(current).parents('li');
		if (0 == selector_li.find(".fa-minus").length) {
			selector_li.after(selector_li.prop('outerHTML')).next("li").find(
					this.event_parent_selector).append(this.minusHtml);
		} else {
			selector_li.after(selector_li.prop('outerHTML'));
		}
	},
	minus : function(current) {
		var selector_li = $(current).parents('li');
		selector_li.remove();
	}
};

Catcher = {
	submit : function() {
		var form_seletor = $('.script-catcher-form');
		//循环所有OL
		form_seletor.find(PlusMinus.event_parent_selector).parents("ol").each(
				function() {
					//循环所有OL下的LI
					$(this).children("li").each(function(index, element) {
						//循环所有OL LI 下的表单元素
						$(element).find(":input,select").each(function(){
							//替换NAME
							var originName=$(this).attr("originName")||$(this).attr("originname");
							if(-1!=originName.indexOf("[]")){
								$(this).attr("name",originName.replace(/(\s*\[\s*\]\s*)/g, "["+index+"]"))
							}else{
								$(this).attr("name",originName+"["+index+"]")
							}
							
						});
					});
				});
		console.log($('.script-catcher-form').formToArray());
		form_seletor.ajaxSubmit(function(data){
			console.log(data);
			alert(JSON.stringify(data));
		});
	}
};

$(function() {
	PlusMinus.init();
});