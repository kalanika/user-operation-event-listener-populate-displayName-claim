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

package org.wso2.carbon.custom.user.operation.event.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.custom.user.operation.event.listener.constants.Constants;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.common.AbstractUserOperationEventListener;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CustomUserOperationEventListener extends AbstractUserOperationEventListener {

    private static final Log audit = CarbonConstants.AUDIT_LOG;
    private static String AUDIT_MESSAGE = "Initiator : %s | Action : %s | Target : %s ";
    private static Log log = LogFactory.getLog(CustomUserOperationEventListener.class);

    @Override
    public int getExecutionOrderId() {

        //This listener should execute before the IdentityMgtEventListener
        //Hence the number should be < 1357 (Execution order ID of IdentityMgtEventListener)
        return 1356;
    }

    @Override
    public boolean doPreAddUser(String userName, Object credential, String[] roleList, Map<String, String> claims,
                                String profile, UserStoreManager userStoreManager) throws UserStoreException {

        if (!StringUtils.isNotEmpty(claims.get(Constants.DISPLAY_NAME_CLAIM))) {
            String lastName = claims.get(Constants.LAST_NAME_CLAIM);
            String givenName = claims.get(Constants.GIVEN_NAME_CLAIM);
            String emailAddress = claims.get(Constants.EMAIL_ADDRESS_CLAIM);

            if (StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(givenName)) {

                claims.put(Constants.DISPLAY_NAME_CLAIM, givenName + " " + lastName);
            } else {
                claims.put(Constants.DISPLAY_NAME_CLAIM, emailAddress);
            }
        }

        return true;
    }

}
