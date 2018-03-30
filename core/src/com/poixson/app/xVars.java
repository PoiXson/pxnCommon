package com.poixson.app;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLogRoot;
import com.poixson.tools.Keeper;


public final class xVars {
	private xVars() {}

	// defaults
	private static final boolean DEFAULT_DEBUG              = false;

	private static final boolean DEFAULT_CONSOLE_COLOR      = true;

	public static final int      SEARCH_DEBUG_PARENTS = 1;
	public static final String[] SEARCH_DEBUG_FILES =
		new String[] {
			"debug",
			".debug"
		};



	// debug mode
	private static final AtomicReference<Boolean> debug =
			new AtomicReference<Boolean>(null);

	// original out/err/in
	private static final AtomicReference<PrintStream> originalOut =
			new AtomicReference<PrintStream>(null);
	private static final AtomicReference<PrintStream> originalErr =
			new AtomicReference<PrintStream>(null);
	private static final AtomicReference<InputStream> originalIn  =
			new AtomicReference<InputStream>(null);

	// console color
	private static final AtomicReference<Boolean> colorEnabled =
			new AtomicReference<Boolean>(null);



	// init
	static {
		Keeper.add(new xVars());
	}



	// ------------------------------------------------------------------------------- //
	// debug mode



	public static boolean isDebug() {
		final Boolean bool = debug.get();
		// default
		if (bool == null)
			return DEFAULT_DEBUG;
		return bool.booleanValue();
	}
	public static boolean notDebug() {
		return ! isDebug();
	}
	public static Boolean peekDebug() {
		return debug.get();
	}
	public static void setDebug(final boolean enable) {
		final Boolean previous =
			debug.getAndSet(
				Boolean.valueOf(enable)
			);
		if (previous != null) {
			if (previous.booleanValue()) {
				xLogRoot.get()
					.info("Enabled debug mode");
			} else {
				final xLogRoot log = xLogRoot.peek();
				if (log != null) {
					log.info("Disabled debug mode");
				}
			}
		}
	}



	// ------------------------------------------------------------------------------- //
	// original out/err/in



	// out stream
	public static PrintStream getOriginalOut() {
		final PrintStream out = originalOut.get();
		return (
			out == null
			? System.out
			: out
		);
	}
	public static void setOriginalOut(final PrintStream out) {
		if (out != null) {
			originalOut.set(out);
		}
	}

	// err stream
	public static PrintStream getOriginalErr() {
		final PrintStream err = originalErr.get();
		return (
			err == null
			? System.err
			: err
		);
	}
	public static void setOriginalErr(final PrintStream err) {
		if (err != null) {
			originalErr.set(err);
		}
	}

	// in stream
	public static InputStream getOriginalIn() {
		final InputStream in = originalIn.get();
		return (
			in == null
			? System.in
			: in
		);
	}
	public static void setOriginalIn(final InputStream in) {
		if (in != null) {
			originalIn.set(in);
		}
	}



	// ------------------------------------------------------------------------------- //
	// console color



	public static boolean isColorEnabled() {
		final Boolean bool = colorEnabled.get();
		// default
		if (bool == null)
			return DEFAULT_CONSOLE_COLOR;
		return bool.booleanValue();
	}
	public static boolean isColorDisabled() {
		return ! isColorEnabled();
	}
	public static Boolean peekColorEnabled() {
		return colorEnabled.get();
	}
	public static void setColorEnabled(final boolean enable) {
		if (enable) {
			if (System.console() == null) {
				final xLogRoot log = xLogRoot.peek();
				if (log != null) {
					log.fine("Cannot enable console color");
				}
			}
		}
		final Boolean previous =
			colorEnabled.getAndSet(
				Boolean.valueOf(enable)
			);
		if (previous != null) {
			final xLogRoot log = xLogRoot.peek();
			if (log != null) {
				if (previous.booleanValue()) {
					log.fine("Enabled console color");
				} else {
					log.fine("Disabled console color");
				}
			}
		}
	}



}
