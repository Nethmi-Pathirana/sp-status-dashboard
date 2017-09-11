/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.stream.processor.status.dashboard.impl;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.wso2.carbon.stream.processor.status.dashboard.api.*;
import org.wso2.carbon.stream.processor.status.dashboard.internal.WorkerConfiguration;
import org.wso2.carbon.stream.processor.status.dashboard.model.ApiResponseMessageWithCode;
import org.wso2.carbon.stream.processor.status.dashboard.model.Worker;

import org.wso2.carbon.stream.processor.status.dashboard.api.NotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2017-08-07T17:39:38.193Z")
public class WorkerApiServiceImpl extends WorkersApiService {

    private static final Log logger = LogFactory.getLog(WorkerApiServiceImpl.class);
    Map<String,Worker> workerMap = new ConcurrentHashMap<>();

    @Override
    public Response workerPost(String worker) throws NotFoundException {
//        logger.info("Worker Info Add ******************************************************************");
//        logger.info("string received---------------------------------------->   "+ worker);

        String jsonString = null;
        Response.Status status;

        try {
            JSONObject jsonObject= new JSONObject(worker);
            Worker newWorker = new Worker();

            newWorker.setId("10");
            newWorker.setUsername(jsonObject.getString("username"));
            newWorker.setPassword(jsonObject.getString("password"));
            newWorker.setUrl(jsonObject.getString("url"));
            newWorker.setClusterID("cluster B");

            int result= WorkerConfiguration.addWorker(newWorker);

//            logger.info("Add to DB result: -------------------------------------> "+result);
            if(result==1){
                jsonString = new Gson().toJson(new ApiResponseMessage(ApiResponseMessage.SUCCESS,
                        newWorker.getId()+" saved succesfully"));
                return Response.status(Response.Status.OK).entity(jsonString).build();

            }else {
                jsonString = new Gson().toJson(new ApiResponseMessage(ApiResponseMessage.CONFLICT,
                        "There is a worker already " +
                                "exists with same worker id"));
                status = Response.Status.CONFLICT;

            }

        } catch (JSONException e) {
            logger.error("Error occured while converting json string "+e.getMessage());
            jsonString = new Gson().
                    toJson(new ApiResponseMessage(ApiResponseMessage.NOT_FOUND,
                            e.getMessage()));
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status).entity(jsonString).build();
    }

    @Override
    public Response workerDelete(String id) throws NotFoundException {
        // do some magic!
        if(workerMap.get(id.toString())!=null){
            workerMap.remove(id);
            return Response.status(Response.Status.ACCEPTED).entity(id).type(MediaType.APPLICATION_JSON).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity(id).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Override
    public Response workerGet(String  id) throws NotFoundException {


        logger.info("Worker Info get by ID success ******************************************************************");

//        if(workerMap.get(id.toString())!=null){
//            return Response.ok().entity(workerMap.get(id.toString())).type(MediaType.APPLICATION_JSON).build();
//        }
//        else {
//            return Response.status(Response.Status.NOT_FOUND).entity(id).type(MediaType.APPLICATION_JSON).build();
//        }

        return Response.status(Response.Status.NOT_FOUND).entity(id).type(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response workerPut(String  id, Worker worker) throws NotFoundException {
        // do some magic!
        Worker w = workerMap.get(worker.getId().toString());
        w.setId(worker.getId());
        w.setUsername(worker.getUsername());
        w.setPassword(worker.getPassword());
        w.setUrl(worker.getUrl());
        workerMap.put(w.getId().toString(),w);
        return Response.ok().entity(w).type(MediaType.APPLICATION_JSON).build();
    }

}
