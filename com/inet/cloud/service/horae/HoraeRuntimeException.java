package com.inet.cloud.service.horae;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * HoraeRuntimeException.
 *
 * @author Dzung Nguyen
 * @version $Id: HoraeRuntimeException.java 2011-04-18 11:24:44z nguyen_dv $
 *
 * @since 1.0
 */
public abstract class HoraeRuntimeException extends RuntimeException {
  //~ class properties ========================================================
  private static final long serialVersionUID = -3517067925560267235L;

  /* the given new line. */
  protected static final String NL = System.getProperty("line.separator");

  //~ class members ===========================================================
  /**
   * Creates the {@link HoraeRuntimeException} object from the given exception message.
   *
   * @param msg the given exception message.
   */
  public HoraeRuntimeException(String msg) {
    super(msg);
  }

  /**
   * Creates {@link HoraeRuntimeException} object from the given the cause of exception.
   *
   * @param cause the given the cause of exception.
   */
  protected HoraeRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates the {@link HoraeRuntimeException} object from the exception message
   * and the cause of exception.
   *
   * @param msg the given exception message.
   * @param cause the given cause of exception.
   */
  public HoraeRuntimeException(String msg, Throwable cause) {
    super(msg, cause);
  }

  /**
   * Prints the stack trace to error device.
   */
  @Override
  public void printStackTrace() {
    System.err.println(this);
  }

  /**
   * Prints the stack trace to {@link java.io.PrintStream} object.
   */
  @Override
  public void printStackTrace(PrintStream s) {
    s.println(this);
  }

  /**
   * Prints the stack trace to {@link java.io.PrintWriter} object.
   */
  @Override
  public void printStackTrace(PrintWriter s) {
    s.print(this);
  }

  /**
   * Converts the exception to string.
   */
  @Override
  public String toString() {
    // create builder.
    StringBuilder builder = new StringBuilder();

    // build exception value.
    for (Throwable e = this; e != null; e = e.getCause()) {
      builder.append(NL);

      builder.append(e == this ? "Exception:" : "Caused By:").append(NL)
             .append("----------").append(NL);

      // check exception is error.
      if (e instanceof AssertionError
          || e instanceof IllegalArgumentException
          || e instanceof IllegalStateException
          || e instanceof NullPointerException) {
        builder.append("**************************************************************").append(NL)
               .append("**                                                          **").append(NL)
               .append("**  PLEASE SEND THIS STACK TRACE TO D5NGUYENVAN@GMAIL.COM   **").append(NL)
               .append("**           HELP US FIX BUGS OR IMPROVE PRODUCT            **").append(NL)
               .append("**                                                          **").append(NL)
               .append("**************************************************************").append(NL);
      }

      // append exception information.
      try {
        appendExceptionTo(builder, e);
      } catch (IOException ioex) {
        throw new AssertionError(ioex);
      }

      // show stack trace information.
      StackTraceElement[] elements = e.getStackTrace();
      if (elements != null && elements.length > 0) {
        builder.append(">>> Stack trace: ").append(NL);

        for (StackTraceElement element : elements) {
          builder.append(">>>        at ")
                 .append(element.getClassName())
                 .append('.')
                 .append(element.getMethodName())
                 .append('(')
                 .append(element.getFileName())
                 .append(':')
                 .append(Math.max(1, element.getLineNumber()))
                 .append(')')
                 .append(NL);
        }
      }
    }

    // return document.
    return builder.toString();
  }

  /**
   * Append the additional exception information.
   *
   * @param out the specified appendable to append exception information to.
   * @param ex the exception information.
   */
  protected void appendExceptionTo(Appendable out, Throwable ex) throws IOException {
    out.append(">>> Type: ").append(ex.getClass().getName()).append(NL);
    out.append(">>> Message: ").append(ex.getMessage()).append(NL);
  }
}
