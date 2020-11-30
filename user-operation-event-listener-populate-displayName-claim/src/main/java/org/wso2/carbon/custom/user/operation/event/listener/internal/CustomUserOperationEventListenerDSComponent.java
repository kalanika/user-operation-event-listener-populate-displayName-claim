/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.custom.user.operation.event.listener.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.custom.user.operation.event.listener.DataHolder;
import org.wso2.carbon.user.core.listener.UserOperationEventListener;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.Properties;

public class CustomUserOperationEventListenerDSComponent {

    private static Log log = LogFactory.getLog(CustomUserOperationEventListenerDSComponent.class);

    protected void activate(ComponentContext context) {

        //register the custom listener as an OSGI service.
        context.getBundleContext().registerService(
                UserOperationEventListener.class.getName(),
                DataHolder.getInstance().getCustomUserOperationEventListener(), new Properties());

        log.info("SampleUserOperationEventListenerDSComponent bundle activated successfully..");
    }

    protected void deactivate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("SampleUserOperationEventListenerDSComponent is deactivated ");
        }
    }

    protected void setRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service");
        }
        DataHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("UnSetting the Realm Service");
        }
        DataHolder.getInstance().setRealmService(null);
    }

}
