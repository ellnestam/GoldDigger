package com.agical.golddigger.doc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.agical.bumblebee.collector.BumblebeeCollectors;
import com.agical.bumblebee.junit4.BumbleBeeSuiteRunner;
import com.agical.bumblebee.ruby.RubyCollector;

@RunWith(BumbleBeeSuiteRunner.class)
@SuiteClasses(AGame.class)
@BumblebeeCollectors({RubyCollector.class})
public class GolddiggerDocumentation {
    /*!!
    This is an instruction manual on how to play the Golddigger game. 
    
    The goal of the game is to gather as much gold as possible within the given time frame. 
    The game will end at 20.30.
    
    The game consists of a series of gold fields. Each field must be searched and all gold in the field must be 
    brought back to the bank in order to move to the next field.
        
    This game is running on an HTTP-server. For the examples in this documentation the server resides 
    at =http://localhost:8066/golddigger=. The correct url (e.g. IP address of the server) was provided 
    to you when the game started.
    
    The part of the path that says **veryVerySecret** must be replaced by the password given to you by 
    the game administrator.
    
    Your Golddigger can be controlled by sending specific actions or state requests to the web server. 
    Actions makes your digger interact with the environment, and state requests asks for the current
    state of you player. The possible requests are described in detail in the following sections. 
    In the end of the document we reveal the first couple of fields. 
    
    You cannot gain any advantages by sending parallel requests to the server since only one request per team will 
    be processed at a time. Do write wellbehaving Golddiggers.
    
    The server always return HTTP reponse code 400, unless it utterly fails. That is, the responses below always
    represents the *content* of the response, and never any HTTP status.
    */

}
