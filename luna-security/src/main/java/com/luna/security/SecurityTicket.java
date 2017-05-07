package com.luna.security;

import java.util.Date;

import org.apache.commons.lang.Validate;

import com.luna.utils.LangUtils;

public class SecurityTicket {

	private String _userName;
	private String _userData;
	private String _appPath;
	private Date _expires;

	public boolean isExpired() {
		return System.currentTimeMillis() > this._expires.getTime();
	}

	public SecurityTicket(String _userData, String _appPath) {
		this._userData = _userData;
		this._appPath = LangUtils.defaultValue(_appPath, "/");
		this._expires = new Date(System.currentTimeMillis() + SecurityCursor.LOGIN_STAY_TIME_MILLIS);
	}

	public SecurityTicket(String _userName, String _userData, String _appPath) {
		this(_userData, _appPath);
		Validate.notNull(_userName);
		this._userName = _userName;
	}

	public SecurityTicket() {
	}

	/**
	 * @return the _userName
	 */
	public String get_userName() {
		return _userName;
	}

	/**
	 * @return the _userData
	 */
	public String get_userData() {
		return _userData;
	}

	/**
	 * @return the _appPath
	 */
	public String get_appPath() {
		return _appPath;
	}

	/**
	 * @return the _expires
	 */
	public Date get_expires() {
		return _expires;
	}

	/**
	 * @param _userName
	 *            the _userName to set
	 */
	public void set_userName(String _userName) {
		this._userName = _userName;
	}

	/**
	 * @param _userData
	 *            the _userData to set
	 */
	public void set_userData(String _userData) {
		this._userData = _userData;
	}

	/**
	 * @param _appPath
	 *            the _appPath to set
	 */
	public void set_appPath(String _appPath) {
		this._appPath = _appPath;
	}

	/**
	 * @param _expires
	 *            the _expires to set
	 */
	public void set_expires(Date _expires) {
		this._expires = _expires;
	}

}
