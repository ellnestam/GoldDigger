/**
 * 
 */
package com.agical.golddigger.server;

import java.io.IOException;
import java.io.OutputStream;

public class VoidOutputStream extends OutputStream {
    public void write(int b) throws IOException {
        // do nothing
    }
}