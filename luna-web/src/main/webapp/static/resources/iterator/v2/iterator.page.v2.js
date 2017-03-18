// create by lyl as laulyl 20160720
//update by lyl as laulyl 20170225 解决了多条件交替分页，如果其中一个条件总条数为0,不清除分页内容的问题
// 公共局部刷新分页
var pageObject = {
	runtime : {},
	refresh : function(config) {
		// 初始化配置项
		var configured = jQuery.extend({
			iteratorLength : 5,
			page : 1,
			pageSize : 10,
			totalCount : 0,
			jQueryParentSelector : "body",
			action : function(i, call) {
				call();
				alert("havent'implements !");
			}
		}, config);
		// 赋值给运行时
		pageObject.runtime = configured;
		// 首先清理一下
		jQuery(configured.jQueryParentSelector).empty();

		var pageInfo = new pageObject.PageInfo(configured);
		// 数据初始化成功才会显示
		if (pageInfo.isTrue) {
			// 初始化迭代长度，不能小于5
			configured.iteratorLength = pageObject.isGt(
					configured.iteratorLength, 5, 5);
			// 计算总页数
			var pages = pageObject.getPages(pageInfo.asyncPageCount,
					pageInfo.asyncPageSize);
			// 如果仅有1页也不显示任何东西
			if (1 != pages) {
				var iterator = new pageObject.Iterator(pages,
						configured.iteratorLength, pageInfo.asyncPageNow);
				var pageFrameWork = pageObject.getPageFrameWork(configured);
				pageObject.render(iterator, configured, pageFrameWork);
			}
		}
	},
	PageInfo : function(configured) {
		var asyncPageCount = configured.totalCount
				|| jQuery(
						configured.jQueryParentSelector
								+ " :input[class='async-page-count']").val();
		var asyncPageNow = configured.page
				|| jQuery(
						configured.jQueryParentSelector
								+ " :input[class='async-page-now']").val();
		var asyncPageSize = configured.pageSize
				|| jQuery(
						configured.jQueryParentSelector
								+ " :input[class='async-page-size']").val();
		if (pageObject.isDigital(asyncPageCount)
				&& pageObject.isDigital(asyncPageNow)
				&& pageObject.isDigital(asyncPageSize)) {
			this.asyncPageCount = parseInt(asyncPageCount);
			this.asyncPageNow = parseInt(asyncPageNow);
			this.asyncPageSize = parseInt(asyncPageSize);
			this.isTrue = true;
		} else {
			this.isTrue = false;
		}
	},
	Iterator : function(pages, iteratorLength, pageNow) {
		this.definedIterator = iteratorLength;
		if (pages < iteratorLength) {
			iteratorLength = pages;
		}
		var iteratorRemain = iteratorLength % 2;
		var iteratorDiv = Math.floor(iteratorLength / 2);

		var iteratorBegin = 0 == iteratorRemain ? pageNow - iteratorDiv + 1
				: pageNow - iteratorDiv;
		var iteratorEnd = pageNow + iteratorDiv;
		if (iteratorBegin <= 0) {
			iteratorBegin = 1;
			iteratorEnd = iteratorLength;
		} else if (iteratorEnd > pages) {
			iteratorBegin = pages - iteratorLength + 1;
			iteratorEnd = pages;
		}
		this.begin = iteratorBegin;
		this.end = iteratorEnd;
		this.pages = pages;
		this.iteratorLength = iteratorLength;
		this.pageNow = pageNow;
		this.iteratorTrigger = 0 == iteratorRemain ? iteratorDiv + 1
				: iteratorDiv + 2;
	},
	getPages : function(totalCount, onceCatchNumber) {
		var catchRemain = totalCount % onceCatchNumber;
		var catchTimesFull = Math.floor(totalCount / onceCatchNumber);
		var maxTimes = 0 == catchRemain ? catchTimesFull : catchTimesFull + 1;
		var pages = maxTimes > 0 ? maxTimes : 1;
		return pages;
	},
	render : function(iterator, configured, asyncPage) {
		asyncPage.empty();
		if (iterator.pageNow > 1) {
			// 上一页
			asyncPage.append(pageObject.getRenderPage(asyncPage,
					iterator.pageNow - 1, "&lt;", null, "上一页", configured));
		} else {
			asyncPage
					.append("<span title=\"上一页\" class=\"font-c\">&lt;</span>");
		}

		// 首页
		if (iterator.begin != 1 && iterator.pages > configured.iteratorLength) {
			asyncPage.append(pageObject.getRenderPage(asyncPage, 1, 1, null,
					"首页", configured));
		}

		// 分隔符
		if (iterator.begin > 2 && iterator.pages > configured.iteratorLength) {
			asyncPage.append("<span>...</span>");
		}

		for (var i = iterator.begin; i <= iterator.end; i++) {
			if (iterator.pageNow == i) {
				asyncPage.append(pageObject.getRenderPage(asyncPage, i, i,
						"current", "当前第" + i + "页", false));
			} else {
				asyncPage.append(pageObject.getRenderPage(asyncPage, i, i,
						null, "第" + i + "页", configured));
			}
		}

		// 隔层
		if (iterator.end < iterator.pages - 1
				&& iterator.pages > configured.iteratorLength) {
			asyncPage.append("<span>...</span>");
		}
		// 最后一页
		if (iterator.end != iterator.pages
				&& iterator.pages > configured.iteratorLength) {
			asyncPage.append(pageObject.getRenderPage(asyncPage,
					iterator.pages, iterator.pages, null, "尾页", configured));
		} else {
			// asyncPage.append("<span title=\"最后一页\" class=\"font-c\">"+
			// iterator.pages + "</span>");
		}

		// 下一页
		if (iterator.pageNow < iterator.pages) {
			asyncPage.append(pageObject.getRenderPage(asyncPage,
					iterator.pageNow + 1, "&gt;", null, "下一页", configured));
		} else {
			asyncPage
					.append("<span title=\"下一页\" class=\"font-c\">&gt;</span>");
		}

		// go 按钮
		var goText = '<span class="txt">到第</span><span class="numBox_v1"><input class="numInput_v1" value="'
				+ iterator.pageNow
				+ '" type="text"><i class="plus_v1" onclick="pageObject.inputValuePlus(this,'
				+ iterator.pages
				+ ');"></i><i class="reduce_v1" onclick="pageObject.inputValueMinus(this)"></i></span><span class="txt">页&nbsp;</span><input  value="go" class="btn-vice script-go" type="button">';
		asyncPage.append(goText);

		asyncPage.find("a").each(function() {
			var i = parseInt(jQuery(this).attr("attr"));
			if (!jQuery(this).hasClass("current"))
				jQuery(this).click({
					config : configured,
					page : i
				}, pageObject.action);
		});

		asyncPage.find(".script-go").click({
			config : configured,
			page : i,
			pages : iterator.pages
		}, pageObject.go);

	},
	getRenderPage : function(bindTaget, code, text, cls, title, configured) {
		cls = cls ? "class=\"" + cls + "\" " : " ";
		configured = false;
		var script = configured ? "href=\"javascript:pageObject.action(" + code
		// + ",'"
		// + Common.utils.getFullUrlTime(configured.url,
		// configured.argument) + "');\" "
		+ ");\" " : "href=\"javascript:void(0)\" ";
		var attr = "attr=\"" + code + "\" ";
		if (title) {
			title = "title=\"" + title + "\" ";
		}
		var tag = "<a " + cls + script + attr + title + ">" + text + "</a>";
		// jQuery(tagA).on("click",configured,pageObject.action);
		return tag;
	},
	getPageFrameWork : function(configured) {
		jQuery(configured.jQueryParentSelector).empty();
		jQuery(configured.jQueryParentSelector)
				.append(
						'<div class="mb15 cl script-page-parentor-cls"><div class="fr"><div class="page_v1 page"></div></div></div>');
		return jQuery(configured.jQueryParentSelector + " .page");
	},
	action : function(clickBinder) {
		var page = clickBinder.data.page;
		var config = clickBinder.data.config;
		config["page"] = page;
		config.action(pageObject.refresh, config);
	},
	isDigital : function(number) {
		if (number && /\d+/.test(number) && number > 0) {
			return true;
		}
		return false;
	},
	isGt : function(number, gt, defaultValue) {
		if (pageObject.isDigital(number) && number > gt) {
			return number;
		}
		return defaultValue;
	},
	inputValuePlus : function(currentSelector, maxValue) {
		var selector = jQuery(currentSelector).siblings("input");
		var currentValue = parseInt(selector.val());
		if (currentValue < maxValue) {
			jQuery(selector).val(currentValue + 1);
		}
	},
	inputValueMinus : function(currentSelector) {
		var selector = jQuery(currentSelector).siblings("input");
		var currentValue = parseInt(selector.val());
		if (currentValue > 1) {
			jQuery(selector).val(currentValue - 1);
		}
	},
	go : function(clickBinder) {
		var page = clickBinder.data.page;
		var pages = clickBinder.data.pages;
		var config = clickBinder.data.config;
		config["page"] = page;
		var selector = jQuery(config.jQueryParentSelector).find(
				"input[class='numInput_v1']");
		if (/^\d+$/.test(selector.val())) {
			var selectPage = parseInt(selector.val());
			if (selectPage && selectPage >= 1 && selectPage <= pages) {
				config.page = selectPage;
				config.action(pageObject.refresh, config);
			}
		}
	}
};
