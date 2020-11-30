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

import org.wso2.carbon.identity.core.AbstractIdentityUserOperationEventListener;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.identity.core.util.IdentityCoreConstants;
import org.wso2.carbon.user.core.UserStoreManager;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.DISPLAY_NAME_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.EMAIL_ADDRESS_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.GIVEN_NAME_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.LAST_NAME_CLAIM;

public class CustomUserOperationEventListener extends AbstractIdentityUserOperationEventListener {

    @Override
    public int getExecutionOrderId() {

        int orderId = getOrderId();
        if (orderId != IdentityCoreConstants.EVENT_LISTENER_ORDER_ID) {
            return orderId;
        }
        // This listener should be executed before all the other listeners.
        // 0 and 1 are already reserved for audit loggers, hence using 2.
        return 2;
    }

    @Override
    public boolean doPreAddUser(String userName, Object credential, String[] roleList, Map<String, String> claims,
                                String profile, UserStoreManager userStoreManager) throws UserStoreException {

        if (StringUtils.isEmpty(claims.get(DISPLAY_NAME_CLAIM))) {

            if (StringUtils.isNotEmpty(claims.get(GIVEN_NAME_CLAIM)) ||
                    StringUtils.isNotEmpty(claims.get(LAST_NAME_CLAIM))) {
                String givenName = StringUtils.isBlank(claims.get(GIVEN_NAME_CLAIM)) ? "" :
                        claims.get(GIVEN_NAME_CLAIM) + " ";
                String lastName = StringUtils.isBlank(claims.get(LAST_NAME_CLAIM)) ? "" : claims.get(LAST_NAME_CLAIM);
                claims.put(DISPLAY_NAME_CLAIM, givenName + lastName);
            } else if (StringUtils.isEmpty(claims.get(GIVEN_NAME_CLAIM)) &&
                    StringUtils.isEmpty(claims.get(LAST_NAME_CLAIM)) &&
                    StringUtils.isNotEmpty(claims.get(EMAIL_ADDRESS_CLAIM))) {
                claims.put(DISPLAY_NAME_CLAIM, claims.get(EMAIL_ADDRESS_CLAIM));
            }
        }

        return true;
    }

}
