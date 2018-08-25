/*
 * Copyright (c) 2014-2018 T-Systems Multimedia Solutions GmbH
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

/*
 * Dynatrace Environment API
 * Documentation of the Dynatrace REST API. Refer to the [help page](https://www.dynatrace.com/support/help/shortlink/section-api) to read about use-cases and examples.
 *
 * OpenAPI spec version: 1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package de.tsystems.mms.apm.performancesignature.dynatracesaas.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils.toIndentedString;

/**
 * HostFromRelationships
 */

public class HostFromRelationships {
    @SerializedName("isNetworkClientOfHost")
    private List<String> isNetworkClientOfHost;

    public HostFromRelationships isNetworkClientOfHost(List<String> isNetworkClientOfHost) {
        this.isNetworkClientOfHost = isNetworkClientOfHost;
        return this;
    }

    public HostFromRelationships addIsNetworkClientOfHostItem(String isNetworkClientOfHostItem) {
        if (this.isNetworkClientOfHost == null) {
            this.isNetworkClientOfHost = new ArrayList<>();
        }
        this.isNetworkClientOfHost.add(isNetworkClientOfHostItem);
        return this;
    }

    /**
     * @return isNetworkClientOfHost
     **/
    public List<String> getIsNetworkClientOfHost() {
        return isNetworkClientOfHost;
    }

    public void setIsNetworkClientOfHost(List<String> isNetworkClientOfHost) {
        this.isNetworkClientOfHost = isNetworkClientOfHost;
    }

    @Override
    public String toString() {
        return "class HostFromRelationships {\n"
                + "    isNetworkClientOfHost: " + toIndentedString(isNetworkClientOfHost) + "\n"
                + "}";
    }
}

