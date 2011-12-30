package com.agical.golddigger.server;

import java.io.IOException;
import java.io.Writer;

public class SynchronizedWriter extends Writer {
    
    private Writer delegate;

    public SynchronizedWriter(Writer delegate) {
        this.delegate = delegate;
    }

    @Override
    public synchronized void close() throws IOException {
        delegate.close();
    }
    
    @Override
    public synchronized void flush() throws IOException {
        delegate.flush();
    }
    
    @Override
    public synchronized void write(char[] cbuf, int off, int len) throws IOException {
        delegate.write(cbuf, off, len);
    }
    
}
