
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

public class IMProtocol {
    //Named constant for each state of the conversation
    private static final int WAITING = 0;
    private static final int IN_CONVERSATION = 1;
    //initial state
    private int state = WAITING;
    
    /**
     * processInput
     * @param theInput, a String, what the client last said
     * @return a String representing the server's intended reply
     */
    
   
  
    public String processInput(String theInput) 
    {
        String theOutput = null;
        //this enforces the state protocol
        if (state == WAITING) 
        {
            //Server lets user know connection has been made
            theOutput = "Connection Established.";
            //Changes state to in conversation
            state = IN_CONVERSATION;
            
        } else if (state == IN_CONVERSATION) 
        
        {
            //while in conversation
            if (theInput != null) 
            {
                //checks client input
                if (theInput.equalsIgnoreCase("bye"))
                {
                    //if client wants to end, moves back to waiting
                    theOutput = "Server: Bye.";
                    state = WAITING;
                }
                //this is the conversation with the AI
                else if (theInput.equalsIgnoreCase("Hello"))
                {
                   theOutput = "Server: Hello. How are you doing?"; 
                }
                else if (theInput.equalsIgnoreCase("Great"))
                {
                   theOutput = "Server: Interesting, Anything else?"; 
                }
                else if (theInput.equalsIgnoreCase(theInput))
                {
                   theOutput = "Server: bye"; 
                }
            } else 
            {
                theOutput = "Client: " + theInput;
            }
        }
        return theOutput;
    }
}
