package com.luna.utils.infce;

import com.luna.utils.LangUtils;
import com.luna.utils.RegexUtils;

public class InputStringOutputLong implements IInputOutput<String, Long> {

	@Override
	public Long get(String i) {
		if (LangUtils.isBlank(i) || !(RegexUtils.matches(i, "^[0-9]+$", false)))
			return null;
		return Long.valueOf(i);
	}

}
