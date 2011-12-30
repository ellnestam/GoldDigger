/**
 * 
 */
package com.agical.golddigger.server;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class WebController {
    private static WebConversation wc = new WebConversation();
    private String secretName;
    private int port;
    private final int serverSleep;
    
    public WebController(String secretName, int port, int serverSleep) {
        super();
        this.secretName = secretName;
        this.port = port;
        this.serverSleep = serverSleep;
    }
    public WebResponse view() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/view", serverSleep);
    }
    public WebResponse score() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/score", serverSleep);
    }

    public WebResponse carrying() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/carrying", serverSleep);
    }

    public WebResponse drop() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/drop", serverSleep);
    }

    public WebResponse nextField() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/next", serverSleep);
    }

    public WebResponse grab() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/grab", serverSleep);
    }

    public WebResponse moveWest() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/move/west", serverSleep);
    }
    public static WebResponse call(String url, int sleep) {
        try {
            WebRequest webRequest = new GetMethodWebRequest(url);
            webRequest.setHeaderField("sleep", sleep + "");
            return wc.getResponse(webRequest);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public WebResponse moveNorth() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/move/north", serverSleep);
    }

    public WebResponse moveSouth() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/move/south", serverSleep);
    }

    public WebResponse moveEast() {
        return call("http://localhost:" + port + "/golddigger/digger/" + secretName + "/move/east", serverSleep);
    }

}