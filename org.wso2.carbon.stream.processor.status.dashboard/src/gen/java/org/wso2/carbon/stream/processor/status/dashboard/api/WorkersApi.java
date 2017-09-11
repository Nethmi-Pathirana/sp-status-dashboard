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

package org.wso2.carbon.stream.processor.status.dashboard.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.wso2.carbon.stream.processor.status.dashboard.factories.WorkerApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.stream.processor.status.dashboard.model.InlineResponse201;
import org.wso2.carbon.stream.processor.status.dashboard.model.Worker;
import org.wso2.msf4j.Microservice;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Component(
        name = "stream-processor-status-dashboard-services",
        service = Microservice.class,
        immediate = true
)

@Path("/workers")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the worker API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2017-08-07T17:39:38.193Z")
public class WorkersApi implements Microservice {

 private static final Log log = LogFactory.getLog(WorkersApi.class);
 private final WorkersApiService delegate = WorkerApiServiceFactory.getWorkerApi();

 @POST

 @Consumes({ "application/json", "application/xml","text/plain" })
 @Produces({ "application/json" })
 @io.swagger.annotations.ApiOperation(value = "Add a new worker.", notes = "Adds a new worker.", response = InlineResponse201.class, tags={ "worker", })
 @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 201, message = "Worker is added successfully.", response = InlineResponse201.class),

         @io.swagger.annotations.ApiResponse(code = 409, message = "A worker with the given url already exists.", response = InlineResponse201.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "An unexpected error occured.", response = InlineResponse201.class) })
 public Response workerPost(@ApiParam(value = "Worker object that needs to be added." ,required=true) String worker
 )
         throws NotFoundException {
  return delegate.workerPost(worker);
 }
 @DELETE
 @Path("/{id}")

 @Produces({ "application/json" })
 @io.swagger.annotations.ApiOperation(value = "Deletes a worker.", notes = "Removes the worker with the worker id specified. Path param of **id** determines id of the worker. ", response = void.class, tags={ "worker", })
 @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "The worker is successfully deleted.", response = void.class),

         @io.swagger.annotations.ApiResponse(code = 400, message = "The worker id provided is invalid.", response = void.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "The worker is not found.", response = void.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "An unexpected error occured.", response = void.class) })
 public Response workerDelete(@ApiParam(value = "Id of the worker.",required=true) @PathParam("id") String id
 )
         throws NotFoundException {
  return delegate.workerDelete(id);
 }
 @GET
 @Path("/{id}")

 @Produces({ "application/json" })
 @io.swagger.annotations.ApiOperation(value = "Find worker by ID", notes = "Retrieves the worker with the specified id.", response = InlineResponse201.class, tags={ "worker", })
 @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "The worker is successfully retrieved.", response = InlineResponse201.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "The worker specified is not found.", response = InlineResponse201.class) })
 public Response workerGet(@ApiParam(value = "ID of the worker.",required=true) @PathParam("id") String id
 )
         throws NotFoundException {
  return delegate.workerGet(id);
 }
 @PUT
 @Path("/{id}")
 @Consumes({ "application/json", "application/xml" })
 @Produces({ "application/json" })
 @io.swagger.annotations.ApiOperation(value = "Update an existing worker.", notes = "Updates the worker. ", response = void.class, tags={ "worker", })
 @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully updated the worker.", response = void.class),

         @io.swagger.annotations.ApiResponse(code = 404, message = "Worker not found.", response = void.class),

         @io.swagger.annotations.ApiResponse(code = 500, message = "An unexpected error occured.", response = void.class) })
 public Response workerPut(@ApiParam(value = "ID of worker that needs to be updated.",required=true) @PathParam("id") String id
         ,@ApiParam(value = "Worker object that needs to be updated." ,required=true) Worker worker
 )
         throws NotFoundException {
  return delegate.workerPut(id,worker);
 }

 /**
  * This is the activation method of ServiceComponent. This will be called when it's references are fulfilled
  *
  * @throws Exception this will be thrown if an issue occurs while executing the activate method
  */
 @Activate
 protected void start() throws Exception {
  log.info("***************************************************");
 }

 /**
  * This is the deactivation method of ServiceComponent. This will be called when this component
  * is being stopped or references are satisfied during runtime.
  *
  * @throws Exception this will be thrown if an issue occurs while executing the de-activate method
  */
 @Deactivate
 protected void stop() throws Exception {
  log.info("######################################");
 }

}
