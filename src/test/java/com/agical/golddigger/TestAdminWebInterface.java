package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.golddigger.model.fieldcreator.fields.CompetitionFields;
import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.GolddiggerServer;
import com.agical.golddigger.server.WebController;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;


public class TestAdminWebInterface {

    private static final int PORT = 8066;
    private GolddiggerServer server;
    private WebController webController =  new WebController("secretName", PORT, 200);
    private AdminWebController admin = new AdminWebController(PORT);
    @Before
    public void startServer() throws Exception {
        Diggers diggers = new Diggers(CompetitionFields.factory(6));
        server = new GolddiggerServer();
        server.start(diggers, "target/calls.log");
    }
    
    @After
    public void stopServer() throws Exception {
        server.stop();
    }
    @Test
    public void testGuard() throws Exception {
        WebConversation wc = new WebConversation();
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/admin/badccret/listdiggers");
        assertEquals("bad command\n", response.getText());
    }    
    @Test
    public void addDigger() throws Exception {
        WebResponse response = admin.listdiggers();
        assertEquals("", response.getText());
        response = admin.add("name", "secretName");
        response = admin.listdiggers();
        assertEquals("name secretName\n", response.getText());
        response = webController.view();
        assertEquals("www\nwb.\nw.1\n", response.getText());
    }
    
    
}
