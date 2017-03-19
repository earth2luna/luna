/**
 * @author lyl
 * @date 20160903
 * @version 1.0.0
 * @description
 * 
 * <pre>
 * 支持动态校验，随着入参的变化而校验不同的元素
 * 将第一个有错误的元素聚焦
 * 对于不同的元素有不同的处理方式
 * 默认元素后面跟&lt;span class=&quot;verror&quot;&gt;errorMessage &lt;/span&gt;
 * </pre>
 */

/**
 * @demo
 * 
 * <pre>
 * var option = {
 * 	action : &quot;/floor/mdf&quot;,
 * 	rulers : {
 * 		chineseName : {
 * 			required : &quot;不能为空&quot;,
 * 			chineseWord : &quot;必须为中文&quot;,
 * 			rangelength : &quot;长度范围在{1}至{50}之间&quot;
 * 		},
 * 		englishName : {
 * 			required : &quot;不能为空&quot;,
 * 			englishGrop : &quot;必须为英文词组&quot;,
 * 			rangelength : &quot;长度范围在{2}至{50}之间&quot;
 * 		},
 * 		linkUrl : {
 * 			required : &quot;不能为空&quot;
 * 		},
 * 		attachedUrl : {
 * 			required : &quot;不能为空&quot;,
 * 			validateHandler : function(result, element, text, validator) {
 * 				var target = element.parent(&quot;div&quot;).next(&quot;.attachedImg&quot;)
 * 						.children(&quot;img&quot;);
 * 				if (result) {
 * 					target.removeAttr(&quot;style&quot;);
 * 				} else {
 * 					target.css({
 * 								border : &quot;red solid 1px&quot;,
 * 								padding : &quot;0px&quot;
 * 							});
 * 				}
 * 			}
 * 		},
 * 		iconUrl : {
 * 			required : &quot;不能为空&quot;,
 * 			validateHandler : function(result, element, text, validator) {
 * 				var target = element.parent(&quot;div&quot;).next(&quot;.iconImg&quot;)
 * 						.children(&quot;img&quot;);
 * 				if (result) {
 * 					target.removeAttr(&quot;style&quot;);
 * 				} else {
 * 					target.css({
 * 								border : &quot;red solid 1px&quot;,
 * 								padding : &quot;0px&quot;
 * 							});
 * 				}
 * 			}
 * 		},
 * 		sort : {
 * 			required : &quot;不能为空&quot;,
 * 			positiveInteger : &quot;必须为正整数&quot;,
 * 			range : &quot;范围值在{1}至{100}之间&quot;
 * 		},
 * 		description : {
 * 			required : &quot;不能为空&quot;,
 * 			chineseWord : &quot;必须为中文&quot;,
 * 			rangelength : &quot;长度范围在{5}至{50}之间&quot;
 * 		}
 * 	},
 * 	methods : {
 * 		englishGrop : function(elemet, message, value) {
 * 			return {
 * 				result : !this.methods.required(elemet, message, value)
 * 						|| /&circ;[a-zA-Z\s]+$/.test(value),
 * 				message : message
 * 			};
 * 		}
 * 	},
 * 	callbackHandler : function(callBackData) {
 * 		if (callBackData &amp;&amp; 1 == callBackData.code) {
 * 			alert(callBackData.message);
 * 		} else {
 * 			alert(callBackData.message);
 * 		}
 * 		Floor.goBack();
 * 	}
 * };
 * </pre>
 */

// extend jQuery function
jQuery.extend(jQuery.fn, {
			lv : function(options) {
				(new jQuery.Lv(this, options)).rebind();
			}
		});

// Constructor for simple validator
jQuery.Lv = function(form, options) {
	this.resetSetting();
	this.settings = jQuery.extend(true, {}, jQuery.Lv.defaults, options);
	this.currentForm = form;
};
// extends simple validator
jQuery.extend(jQuery.Lv, {
	defaults : {
		submitSelector : ":submit",// 提交按钮选择器
		action : null,// form action
		rulers : {},// 定义校验规则
		methods : {},// 自定义的校验方法
		successValidateClass : null,// 成功校验后默认的样式规则
		failedValidateClass : null,// // 失败校验后默认的样式规则
		ignoreValidateSelector : ":hidden",// 忽略校验的选择器
		addValidateSelector : null,// 增加校验的选择器
		ignoreValueSelector : null,// 忽略提交的选择器
		addValueSelector : null,// 增加提交的选择器
		// 返回值处理器
		callbackHandler : function(data) {
			alert(JSON.stringify(data));
		},
		// 默认校验结果处理器
		validateHandler : function(result, element, text, validator) {
			var failedValidateAppender = validator.settings.failedValidateClass
					? 'class="' + validator.settings.failedValidateClass + '"'
					: 'style="color:red;"';
			var successValidateAppender = validator.settings.successValidateClass
					? 'class="' + validator.settings.successValidateClass + '"'
					: null;

			var append = function(appenderClass, text) {
				if (0 != element.next(".lverror").length)
					element.next(".lverror").remove();
				element.after('<span class="lverror">&nbsp;<span '
						+ (appenderClass) + '>' + text + '</span></span>');
			};
			if (result) {
				if (successValidateAppender) {
					append(successValidateAppender, text);
				} else {
					element.next(".lverror").remove();
				}
			} else {
				append(failedValidateAppender, text);
			}
		},
		// 默认提交处理器
		submitHandler : function(validator) {
			var form = {};
			validator.elements(true).each(function() {
						var val = jQuery.trim(jQuery(this).val());
						var name = jQuery.trim(jQuery(this).attr("name"));
						if (val && name)
							form[name] = val;
					});
			if (validator.settings.action)
				jQuery.ajax({
							type : "post",
							url : validator.settings.action,
							data : form,
							async : true,
							dataType : "json",
							success : function(data) {
								validator.settings.callbackHandler(data)
							}
						});
		}
	},
	prototype : {
		resetSetting : function() {
			jQuery.Lv.defaults.submitSelector = ":submit";// 提交按钮选择器
			jQuery.Lv.defaults.action = null;// form action
			jQuery.Lv.defaults.rulers = {};// 定义校验规则
			jQuery.Lv.defaults.methods = {}, // 自定义的校验方法
			jQuery.Lv.defaults.successValidateClass = null;// 成功校验后默认的样式规则
			jQuery.Lv.defaults.failedValidateClass = null, // // 失败校验后默认的样式规则
			jQuery.Lv.defaults.ignoreValidateSelector = ":hidden";// 忽略校验的选择器
			jQuery.Lv.defaults.addValidateSelector = null;// 增加校验的选择器
			jQuery.Lv.defaults.ignoreValueSelector = null;// 忽略提交的选择器
			jQuery.Lv.defaults.addValueSelector = null;// 增加提交的选择器
		},
		rebind : function() {
			var validator = this;
			this.currentForm
					.off()
					.on(
							"focusout keyup",
							":text, [type='password'], select, option, textarea,[type='radio'], [type='checkbox']",
							function() {
								(validator.ruler(jQuery(this), jQuery(this)
												.attr("name"), validator))
							}).on("click", "select, option,[type='checkbox']",
							function() {
								(validator.ruler(jQuery(this), jQuery(this)
												.attr("name"), validator))
							}).on("focusin focusout", "[type='file']",
							function() {
								(validator.ruler(jQuery(this), jQuery(this)
												.attr("name"), validator))
							}).on("submit", validator.settings.focusHandler,
							function() {
								if (!validator.shuffle()) {
									validator.settings.submitHandler(validator);
								}
								return false;
							});

		},
		shuffle : function() {
			var validator = this;
			var elements = this.elements().filter(function(index) {
				return validator.ruler(jQuery(this), jQuery(this).attr("name"),
						validator);
			}).first().trigger("focus");
			return elements.length;
		},
		elements : function(isValue) {
			var notSelector = null;
			var addSelector = null;
			if (isValue) {
				notSelector = this.settings.ignoreValueSelector;
				addSelector = this.settings.addValueSelector;
			} else {
				notSelector = this.settings.ignoreValidateSelector;
				addSelector = this.settings.addValidateSelector;
			}
			return this.currentForm.find("input, select, textarea")
					.not(":submit, :reset, :disabled").add(addSelector)
					.not(notSelector);
		},
		ruler : function(element, name, validator) {
			var ruler = null;
			if (!name || jQuery.isEmptyObject(this.settings.rulers)
					|| jQuery.isEmptyObject(ruler = this.settings.rulers[name]))
				return false;
			var ret = true;
			var validateHandler = this.settings.rulers[name]['validateHandler']
					|| validator.settings.validateHandler;
			var called = null;
			jQuery.each(ruler, function(methodName, methodValue) {
						if ('validateHandler' == methodName)
							return true;
						var method = validator.settings.methods[methodName]
								|| validator.methods[methodName];
						if ('function' == typeof method) {
							try {
								called = method.call(validator, element,
										ruler[methodName], jQuery.trim(element
												.val()));
								return !(ret = !called.result);
							} catch (e) {
								throw e;
							}
						} else {
							throw new Error('no support method [' + methodName
									+ ']!');
						}
					});
			if (ret) {
				validateHandler(false, element, called.message, validator);
			} else {
				validateHandler(true, element, "", validator);
			}
			return ret;

		},
		digitalFormat : function(input) {
			if ("string" == typeof input) {
				var array = new Array();
				input.replace(/\{\s*-?([1-9][0-9]*|0)(\.[0-9]+)?\s*\}/g,
						function(segment) {
							var digital = jQuery.trim(segment.substring(1,
									segment.length - 1))
							if (digital)
								array.push(parseFloat(digital));
						});
				return {
					array : array,
					format : input.replace(/(\{\s*)|(\s*\})/g, "")
				};
			}
			throw new Error("no support not string type for digitalFormat-["
					+ JSON.stringify(input) + "]");
		},
		methods : {
			required : function(elemet, message, value) {
				return {
					result : ("string" == typeof message
							&& "string" == typeof value && 0 < value.length),
					message : message
				};
			},
			englishWord : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^[a-zA-Z]+$/
							.test(value)),
					message : message
				};
			},
			englishGroup : function(elemet, message, value) {
				return {
					result : !this.methods.required(elemet, message, value).result
							|| /^[a-zA-Z\s]+$/.test(value),
					message : message
				};
			},
			chineseWord : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^[\u4e00-\u9fa5]+$/
							.test(value)),
					message : message
				};
			},
			email : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
							.test(value)),
					message : message
				};
			},
			// 数字
			digital : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^[0-9]+$/
							.test(value)),
					message : message
				};
			},
			// 实数
			realNumber : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^-?([1-9][0-9]*|0)(\.[0-9]+)?$/
							.test(value)),
					message : message
				};
			},
			// 整数
			Integer : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^-?([1-9][0-9]*|0)$/
							.test(value)),
					message : message
				};
			},
			// 正整数
			positiveInteger : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || /^([1-9][0-9]*|0)$/
							.test(value)),
					message : message
				};
			},
			date : function(elemet, message, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || !/Invalid|NaN/
							.test(new Date(value).toString())),
					message : message
				};
			},
			minlength : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				return {
					result : (!this.methods.required(elemet, message, value).result || format.array[0] <= value.length),
					message : format.format
				};
			},
			maxlength : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				return {
					result : (!this.methods.required(elemet, message, value).result || format.array[0] >= value.length),
					message : format.format
				};
			},
			rangelength : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				return {
					result : (!this.methods.required(elemet, message, value).result || (format.array[0] <= value.length && format.array[1] >= value.length)),
					message : format.format
				};
			},
			min : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				return {
					result : (!this.methods.required(elemet, message, value).result || format.array[0] <= parseFloat(value)),
					message : format.format
				};
			},
			max : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				return {
					result : (!this.methods.required(elemet, message, value).result || format.array[0] >= parseFloat(value)),
					message : format.format
				};
			},
			range : function(elemet, message, value) {
				var format = this.digitalFormat(message);
				var val = parseFloat(value);
				return {
					result : (!this.methods.required(elemet, message, value).result || (format.array[0] <= val && format.array[1] >= val)),
					message : format.format
				};
			},
			contains : function(elemet, message, value) {

			},
			equalTo : function(elemet, selector, value) {
				return {
					result : (!this.methods.required(elemet, message, value).result || value == jQuery
							.trim(jQuery(selector).val())),
					message : message
				};
			},
			remote : function(elemet, object, value) {
				if ("object" != typeof object)
					return false;
				var result = false;
				jQuery.ajax({
							type : object.type ? object.type : "post",
							url : object.url,
							data : object.data ? object.data : {},
							async : true,
							dataType : "json",
							success : function(data) {
								result = data && "true" == data || true == data
										|| "true" == data.code
										|| true == data.code;
							}
						});
				return {
					result : !this.methods.required(elemet, object.message,
							value).result
							|| result,
					message : object.message
				};
			}
		}
	}
});
