package com.agical.golddigger.server;

import java.io.IOException;
import java.io.Writer;

public class VoidWriter extends Writer {
    public void close() throws IOException {}
    public void flush() throws IOException {}
    public void write(char[] cbuf, int off, int len) throws IOException {}
}
