package com.poixson.commonapp.plugin;

import com.poixson.commonjava.xLogger.xLog;


public abstract class xJavaPlugin {

	private final xPluginManager manager;
	private final xPluginYML yml;

	private volatile boolean enabled = false;
	private volatile boolean hasInited = false;
	private final Object initLock = new Object();


	public xJavaPlugin(final xPluginManager manager, final xPluginYML yml) {
		if(manager == null) throw new NullPointerException();
		if(yml     == null) throw new NullPointerException();
		this.manager = manager;
		this.yml = yml;
	}


	protected void doInit() {
		if(this.hasInited) throw new RuntimeException("Plugin already inited!");
		synchronized(this.initLock) {
			if(this.hasInited) throw new RuntimeException("Plugin already inited!");
			onInit();
			this.hasInited = true;
		}
	}
	protected void doEnable() {
		if(this.enabled)throw new RuntimeException("Plugin already enabled!");
		synchronized(this.initLock) {
			if(this.enabled)throw new RuntimeException("Plugin already enabled!");
			onEnable();
			this.enabled = true;
		}
	}
	protected void doDisable() {
		if(!this.enabled) throw new RuntimeException("Plugin already disabled!");
		synchronized(this.initLock){
			if(!this.enabled) throw new RuntimeException("Plugin already disabled!");
			this.enabled = false;
			onDisable();
		} 
	}


	protected void onInit() {}
	protected abstract void onEnable();
	protected abstract void onDisable();


	public xPluginManager getPluginManager() {
		return this.manager;
	}
	public xPluginYML getPluginYML() {
		return this.yml;
	}


	public String getPluginName() {
		return getPluginYML().getPluginName();
	}
	public String getPluginVersion() {
		return getPluginYML().getPluginVersion();
	}
	public String getPluginAuthor() {
		return getPluginYML().getPluginAuthor();
	}
	public String getPluginWebsite() {
		return getPluginYML().getPluginWebsite();
	}


	// logger
	private volatile xLog _log = null;
	private final Object logLock = new Object();
	public xLog log() {
		if(this._log == null) {
			synchronized(this.logLock) {
				if(this._log == null)
					this._log = xLog.getRoot(getPluginName());
			}
		}
		return this._log;
	}


}
