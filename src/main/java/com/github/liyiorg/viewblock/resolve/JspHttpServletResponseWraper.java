package com.github.liyiorg.viewblock.resolve;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class JspHttpServletResponseWraper extends HttpServletResponseWrapper {
	private JspPrintWriter tmpWriter;
	private ByteArrayOutputStream output;

	public JspHttpServletResponseWraper(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
		output = new ByteArrayOutputStream();
		tmpWriter = new JspPrintWriter(output);
	}

	public void finalize() throws Throwable {
		super.finalize();
		output.close();
		tmpWriter.close();
	}

	public String getContent() {
		// 刷新该流的缓冲，详看java.io.Writer.flush()
		tmpWriter.flush();
		return tmpWriter.getByteArrayOutputStream().toString();
	}

	// 覆盖getWriter()方法，使用我们自己定义的Writer
	public PrintWriter getWriter() throws IOException {
		return tmpWriter;
	}

	public void close() throws IOException {
		tmpWriter.close();
	}

	// 自定义PrintWriter，为的是把response流写到自己指定的输入流当中
	// 而非默认的ServletOutputStream
	private class JspPrintWriter extends PrintWriter {
		ByteArrayOutputStream myOutput; // 此即为存放response输入流的对象

		public JspPrintWriter(ByteArrayOutputStream output) {
			super(output);
			myOutput = output;
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return myOutput;
		}
	}
}
