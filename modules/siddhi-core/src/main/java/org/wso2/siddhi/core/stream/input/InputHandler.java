/*
 * Copyright (c) 2005 - 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wso2.siddhi.core.stream.input;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.event.Event;

public class InputHandler {

    private static final Logger log = Logger.getLogger(InputHandler.class);

    protected String streamId;
    protected int streamIndex;
    protected InputProcessor inputProcessor;
    protected InputProcessor pausedInputPublisher;

    public InputHandler(String streamId, int streamIndex, InputProcessor inputProcessor) {
        this.streamId = streamId;
        this.streamIndex = streamIndex;
        this.inputProcessor = inputProcessor;
        this.pausedInputPublisher = this.inputProcessor;
    }

    public String getStreamId() {
        return streamId;
    }

    public void send(Object[] data) throws InterruptedException {
        if (inputProcessor != null) {
            inputProcessor.send(System.currentTimeMillis(), data, streamIndex);
        }
    }

    public void send(long timeStamp, Object[] data) throws InterruptedException {
        if (inputProcessor != null) {
            inputProcessor.send(timeStamp, data, streamIndex);
        }
    }

    public void send(Event event) throws InterruptedException {
        if (inputProcessor != null) {
            inputProcessor.send(event, streamIndex);
        }
    }

    public void send(Event[] events) throws InterruptedException {
        if (inputProcessor != null) {
            inputProcessor.send(events, streamIndex);
        }
    }

    void disconnect() {
        this.inputProcessor = null;
    }

    void resume() {
        this.inputProcessor = pausedInputPublisher;
    }
}
