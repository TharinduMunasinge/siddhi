/*
 * Copyright (c) 2005 - 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.siddhi.core.query.function;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.query.api.exception.ExecutionPlanValidationException;

public class ConvertFunctionTestCase {
    static final Logger log = Logger.getLogger(ConvertFunctionTestCase.class);
    private int count;
    private boolean eventArrived;

    @Before
    public void init() {
        count = 0;
        eventArrived = false;
    }

    @Test
    public void convertFunctionTest1() throws InterruptedException {
        log.info("convert function test 1");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS,'string') as valueS, convert(typeF,'float') as valueF, convert(typeD,'double') as valueD , convert(typeI,'int') as valueI , convert(typeL,'long') as valueL , convert(typeB,'bool') as valueB " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                for (Event inEvent : inEvents) {
                    count++;
                    junit.framework.Assert.assertTrue(inEvent.getData(0) instanceof String);
                    junit.framework.Assert.assertTrue(inEvent.getData(1) instanceof Float);
                    junit.framework.Assert.assertTrue(inEvent.getData(2) instanceof Double);
                    junit.framework.Assert.assertTrue(inEvent.getData(3) instanceof Integer);
                    junit.framework.Assert.assertTrue(inEvent.getData(4) instanceof Long);
                    junit.framework.Assert.assertTrue(inEvent.getData(5) instanceof Boolean);
                }
            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 2f, 3d, 4, 5l, true});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        executionPlanRuntime.shutdown();

    }

    @Test
    public void convertFunctionTest2() throws InterruptedException {
        log.info("convert function test 2");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS,'string') as valueS1, convert(typeS,'float') as valueF1, convert(typeS,'double') as valueD1, convert(typeS,'int') as valueI1 , convert(typeS,'long') as valueL1 , convert(typeS,'bool') as valueB1, " +
                "        convert(typeF,'string') as valueS2, convert(typeF,'float') as valueF2, convert(typeF,'double') as valueD2, convert(typeF,'int') as valueI2 , convert(typeF,'long') as valueL2 , convert(typeF,'bool') as valueB2, " +
                "        convert(typeD,'string') as valueS3, convert(typeD,'float') as valueF3, convert(typeD,'double') as valueD3, convert(typeD,'int') as valueI3 , convert(typeD,'long') as valueL3 , convert(typeD,'bool') as valueB3, " +
                "        convert(typeI,'string') as valueS4, convert(typeI,'float') as valueF4, convert(typeI,'double') as valueD4, convert(typeI,'int') as valueI4 , convert(typeI,'long') as valueL4 , convert(typeI,'bool') as valueB4, " +
                "        convert(typeL,'string') as valueS5, convert(typeL,'float') as valueF5, convert(typeL,'double') as valueD5, convert(typeL,'int') as valueI5 , convert(typeL,'long') as valueL5 , convert(typeL,'bool') as valueB5, " +
                "        convert(typeB,'string') as valueS6, convert(typeB,'float') as valueF6, convert(typeB,'double') as valueD6, convert(typeB,'int') as valueI6 , convert(typeB,'long') as valueL6 , convert(typeB,'bool') as valueB6  " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;
                junit.framework.Assert.assertTrue(inEvents[0].getData(0) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(1) == null);
                junit.framework.Assert.assertTrue(inEvents[0].getData(2) == null);
                junit.framework.Assert.assertTrue(inEvents[0].getData(3) == null);
                junit.framework.Assert.assertTrue(inEvents[0].getData(4) == null);
                junit.framework.Assert.assertTrue(inEvents[0].getData(5) instanceof Boolean && !((Boolean) inEvents[0].getData(5)));

                junit.framework.Assert.assertTrue(inEvents[0].getData(6) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(7) instanceof Float);
                junit.framework.Assert.assertTrue(inEvents[0].getData(8) instanceof Double);
                junit.framework.Assert.assertTrue(inEvents[0].getData(9) instanceof Integer);
                junit.framework.Assert.assertTrue(inEvents[0].getData(10) instanceof Long);
                junit.framework.Assert.assertTrue(inEvents[0].getData(11) instanceof Boolean && !((Boolean) inEvents[0].getData(11)));

                junit.framework.Assert.assertTrue(inEvents[0].getData(12) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(13) instanceof Float);
                junit.framework.Assert.assertTrue(inEvents[0].getData(14) instanceof Double);
                junit.framework.Assert.assertTrue(inEvents[0].getData(15) instanceof Integer);
                junit.framework.Assert.assertTrue(inEvents[0].getData(16) instanceof Long);
                junit.framework.Assert.assertTrue(inEvents[0].getData(17) instanceof Boolean && !((Boolean) inEvents[0].getData(17)));

                junit.framework.Assert.assertTrue(inEvents[0].getData(18) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(19) instanceof Float);
                junit.framework.Assert.assertTrue(inEvents[0].getData(20) instanceof Double);
                junit.framework.Assert.assertTrue(inEvents[0].getData(21) instanceof Integer);
                junit.framework.Assert.assertTrue(inEvents[0].getData(22) instanceof Long);
                junit.framework.Assert.assertTrue(inEvents[0].getData(23) instanceof Boolean && !((Boolean) inEvents[0].getData(23)));

                junit.framework.Assert.assertTrue(inEvents[0].getData(24) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(25) instanceof Float);
                junit.framework.Assert.assertTrue(inEvents[0].getData(26) instanceof Double);
                junit.framework.Assert.assertTrue(inEvents[0].getData(27) instanceof Integer);
                junit.framework.Assert.assertTrue(inEvents[0].getData(28) instanceof Long);
                junit.framework.Assert.assertTrue(inEvents[0].getData(29) instanceof Boolean && !((Boolean) inEvents[0].getData(29)));

                junit.framework.Assert.assertTrue(inEvents[0].getData(30) instanceof String);
                junit.framework.Assert.assertTrue(inEvents[0].getData(31) instanceof Float);
                junit.framework.Assert.assertTrue(inEvents[0].getData(32) instanceof Double);
                junit.framework.Assert.assertTrue(inEvents[0].getData(33) instanceof Integer);
                junit.framework.Assert.assertTrue(inEvents[0].getData(34) instanceof Long);
                junit.framework.Assert.assertTrue(inEvents[0].getData(35) instanceof Boolean && ((Boolean) inEvents[0].getData(35)));


            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"WSO2", 2f, 3d, 4, 5l, true});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        executionPlanRuntime.shutdown();

    }

    @Test
    public void convertFunctionTest3() throws InterruptedException {
        log.info("convert function test 3");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS,'bool') as valueB1, convert(typeF,'bool') as valueB2, convert(typeD,'bool') as valueB3 , convert(typeI,'bool') as valueB4 , convert(typeL,'bool') as valueB5 , convert(typeB,'bool') as valueB6 " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;
                junit.framework.Assert.assertTrue(inEvents[0].getData(0) instanceof Boolean && (Boolean) inEvents[0].getData(0));
                junit.framework.Assert.assertTrue(inEvents[0].getData(1) instanceof Boolean && (Boolean) inEvents[0].getData(1));
                junit.framework.Assert.assertTrue(inEvents[0].getData(2) instanceof Boolean && (Boolean) inEvents[0].getData(2));
                junit.framework.Assert.assertTrue(inEvents[0].getData(3) instanceof Boolean && (Boolean) inEvents[0].getData(3));
                junit.framework.Assert.assertTrue(inEvents[0].getData(4) instanceof Boolean && (Boolean) inEvents[0].getData(4));
                junit.framework.Assert.assertTrue(inEvents[0].getData(5) instanceof Boolean && (Boolean) inEvents[0].getData(5));

            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"true", 1f, 1d, 1, 1l, true});
        Thread.sleep(100);
        Assert.assertEquals(1, count);
        executionPlanRuntime.shutdown();

    }


    @Test(expected = ExecutionPlanValidationException.class)
    public void convertFunctionTest4() throws InterruptedException {
        log.info("convert function test 4");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS) as valueB1 " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;

            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"true", 1f, 1d, 1, 1l, true});
        Thread.sleep(100);
        Assert.assertEquals(0, count);
        executionPlanRuntime.shutdown();

    }

    @Test(expected = ExecutionPlanValidationException.class)
    public void convertFunctionTest5() throws InterruptedException {
        log.info("convert function test 5");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS,'string','int') as valueB1 " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;

            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"true", 1f, 1d, 1, 1l, true});
        Thread.sleep(100);
        Assert.assertEquals(0, count);
        executionPlanRuntime.shutdown();

    }

    @Test(expected = ExecutionPlanValidationException.class)
    public void convertFunctionTest6() throws InterruptedException {
        log.info("convert function test 6");

        SiddhiManager siddhiManager = new SiddhiManager();

        String cseEventStream = "" +
                "@config(async = 'true') " +
                "define stream typeStream (typeS string, typeF float, typeD double, typeI int, typeL long, typeB bool) ;";
        String query = "" +
                "@info(name = 'query1') " +
                "from typeStream " +
                "select convert(typeS,string) as valueB1 " +
                "insert into outputStream ;";

        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(cseEventStream + query);

        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count++;

            }

        });
        InputHandler inputHandler = executionPlanRuntime.getInputHandler("typeStream");
        executionPlanRuntime.start();
        inputHandler.send(new Object[]{"true", 1f, 1d, 1, 1l, true});
        Thread.sleep(100);
        Assert.assertEquals(0, count);
        executionPlanRuntime.shutdown();

    }


}
