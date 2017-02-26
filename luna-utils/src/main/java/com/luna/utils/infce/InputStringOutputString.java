package com.luna.utils.infce;

import com.luna.utils.LangUtils;

public class InputStringOutputString implements IInputOutput<String, String> {

	@Override
	public String get(String i) {
		if (LangUtils.isBlank(i))
			return null;
		return i;
	}

}
