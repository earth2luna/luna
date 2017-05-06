jQuery(function() {
	$(document)
			.on(
					'click',
					'.thumbnail',
					function(evt) {
						evt.preventDefault();
						var imgTarget = $(this).find('img'), maxWidth = $(this)
								.parent('div').outerWidth(), sourceWidth = imgTarget
								.width(), sourceHeight = imgTarget.height();

						if (imgTarget.hasClass('img_show')) {

							imgTarget.animate({
								'maxWidth' : sourceWidth,
								'maxHeight' : sourceHeight
							}, 500);
							imgTarget.removeClass('img_show');
						} else {
							imgTarget.animate({
								'maxWidth' : sourceWidth > maxWidth ? maxWidth
										: sourceWidth,
								'maxHeight' : sourceHeight
							}, 500);
							imgTarget.addClass('img_show');
						}
						
					});
});