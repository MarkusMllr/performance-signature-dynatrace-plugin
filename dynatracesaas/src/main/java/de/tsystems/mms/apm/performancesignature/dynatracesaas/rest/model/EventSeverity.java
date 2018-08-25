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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils.toIndentedString;

/**
 * Additional data on the event severity.
 */
@ApiModel(description = "Additional data on the event severity.")

public class EventSeverity {
    @SerializedName("context")
    private ContextEnum context;
    @SerializedName("value")
    private Double value;
    @SerializedName("unit")
    private UnitEnum unit;

    public EventSeverity context(ContextEnum context) {
        this.context = context;
        return this;
    }

    /**
     * The metric used in the event severity calculation.
     *
     * @return context
     **/
    @ApiModelProperty(value = "The metric used in the event severity calculation.")
    public ContextEnum getContext() {
        return context;
    }

    public void setContext(ContextEnum context) {
        this.context = context;
    }

    public EventSeverity value(Double value) {
        this.value = value;
        return this;
    }

    /**
     * The value of the severity.
     *
     * @return value
     **/
    @ApiModelProperty(value = "The value of the severity.")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public EventSeverity unit(UnitEnum unit) {
        this.unit = unit;
        return this;
    }

    /**
     * The unit of the severity value.
     *
     * @return unit
     **/
    @ApiModelProperty(value = "The unit of the severity value.")
    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "class EventSeverity {\n" +
                "    context: " + toIndentedString(context) + "\n" +
                "    value: " + toIndentedString(value) + "\n" +
                "    unit: " + toIndentedString(unit) + "\n" +
                "}";
    }
}

