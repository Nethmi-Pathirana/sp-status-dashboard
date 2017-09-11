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

package org.wso2.carbon.stream.processor.status.dashboard.factories;

import org.wso2.carbon.stream.processor.status.dashboard.api.WorkersApiService;
import org.wso2.carbon.stream.processor.status.dashboard.impl.WorkerApiServiceImpl;

public class WorkerApiServiceFactory {
    private final static WorkersApiService service = new WorkerApiServiceImpl();

    public static WorkersApiService getWorkerApi() {
        return service;
    }
}
