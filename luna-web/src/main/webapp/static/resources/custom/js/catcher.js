PlusMinus = {
	event_parent_selector : ".script-p-m",
	plusHtml : "<span class=\"input-group-addon\"><a href=\"javascript:void(0);\" onclick=\"PlusMinus.plus(this);\"><i class=\"fa fa-plus\" aria-hidden=\"true\"></i></a></span>",
	minusHtml : "<span class=\"input-group-addon\"><a href=\"javascript:void(0);\" onclick=\"PlusMinus.minus(this);\"><i class=\"fa fa-minus\" aria-hidden=\"true\"></i></a></span>",
	init : function() {
		$(this.event_parent_selector).append(this.plusHtml);
		$(this.event_parent_selector).parent("li").each(function(){
			if($(this).siblings("li").length>1){
				$(this).children(PlusMinus.event_parent_selector).append(PlusMinus.minusHtml);
			}
		});
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
		form_seletor.ajaxSubmit(function(data){
			if (1 == data.code) {
//				layer.alert(data.message, {
//					icon : 1
//				});
				location.href="/catcher/list";
			} else {
//				layer.alert(data.message, {
//					icon : 2
//				});
				alert(data.message);
			}
		});
	},
	tableRenderInit:function(page){
		var table=$('.table tbody');
		if(0<table.length){
			jc.pPost("/catcher/items",{page:page},function(data){
				var selector_page_input = jQuery(data).filter(":input:hidden");
				eval('var pageInfo=' + selector_page_input.val());
				Catcher.refreshPageIterator(pageInfo);
				table.html(data);
			},"text");
		}
	},
	refreshPageIterator:function(pageInfo) {
		pageObject.refresh({
			page : pageInfo.page,
			pageSize : pageInfo.pageSize,
			totalCount : pageInfo.total,
			jQueryParentSelector : ".async-page",
			action : function(action, config) {
				Catcher.tableRenderInit(pageInfo.page);
			}
		});
	},
	edit:function(key){
		open("/catcher/view?key="+key);
	},
	del:function(key){
		jc.sPost("/catcher/del",{key:key},function(data){
			Catcher.tableRenderInit(pageObject.runtime.page);
			layer.alert(data.message, {
				icon : 1
			});
		});
	},
	catching:function(key){
		jc.sPost("/catcher/catching",{key:key},function(data){
			layer.alert(data.message, {
				icon : 1
			});
		});
	},
	copy:function(key){
		jc.sPost("/catcher/copy",{key:key},function(data){
			Catcher.tableRenderInit(pageObject.runtime.page);
			layer.alert(data.message, {
				icon : 1
			});
		});
	}
};

$(function() {
	PlusMinus.init();
	Catcher.tableRenderInit(1);
});