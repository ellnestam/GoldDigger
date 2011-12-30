/**
 * 
 */
package com.agical.golddigger.server;

import com.meterware.httpunit.WebResponse;

public class AdminWebController {
    private final int port;
    
    public AdminWebController(int port) {
        super();
        this.port = port;
    }

    public WebResponse add(String name, String secretName) {
        return WebController.call("http://localhost:" + port + "/golddigger/admin/ccret/add/" + name + "/" + secretName, 0);
    }
    
    public WebResponse listdiggers() {
        return WebController.call("http://localhost:8066/golddigger/admin/ccret/listdiggers", 0);
    }
    
}