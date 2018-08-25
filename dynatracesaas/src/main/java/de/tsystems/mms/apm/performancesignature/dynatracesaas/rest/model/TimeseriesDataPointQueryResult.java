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

import java.util.HashMap;
import java.util.Map;

import static de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils.toIndentedString;

/**
 * List of datapoints, as well as attributes describing the unit of the returned data points, the result resolution of the metric and the selected type of aggregation.
 */
@ApiModel(description = "List of datapoints, as well as attributes describing the unit of the returned data points, the result resolution of the metric and the selected type of aggregation.")

public class TimeseriesDataPointQueryResult {
    @SerializedName("dataPoints")
    private Map<String, Map<Long, Double>> dataPoints;
    @SerializedName("unit")
    private UnitEnum unit;
    @SerializedName("aggregationType")
    private AggregationTypeEnum aggregationType;
    @SerializedName("resolutionInMillisUTC")
    private Long resolutionInMillisUTC;
    @SerializedName("entities")
    private Map<String, String> entities;
    @SerializedName("timeseriesId")
    private String timeseriesId;

    /**
     * Metric data points   A JSON object that maps the ID of the entity that delivered the data points and an array, which consists of arrays of the data point float values.  May contain more that one entity ID per record (for example, a host and its network interface). In such cases, entity IDs are separated by commas. A datapoint contains a value and a timestamp, at which the value was recorded. There are three versions of data points: Numeric datapoint: contains a numeric value Enum datapoint: contains an enum value (currently only for availability timeseries) Prediction datapoint: Similar to the numeric datapoint, but it contains a confidence interval, within which the future values are expected to be
     *
     * @return dataPoints
     **/
    @ApiModelProperty(value = "Metric data points   A JSON object that maps the ID of the entity that delivered the data points and an array, which consists of arrays of the data point float values.  May contain more that one entity ID per record (for example, a host and its network interface). In such cases, entity IDs are separated by commas. A datapoint contains a value and a timestamp, at which the value was recorded. There are three versions of data points: Numeric datapoint: contains a numeric value Enum datapoint: contains an enum value (currently only for availability timeseries) Prediction datapoint: Similar to the numeric datapoint, but it contains a confidence interval, within which the future values are expected to be")
    public Map<String, Map<Long, Double>> getDataPoints() {
        return dataPoints;
    }

    public TimeseriesDataPointQueryResult unit(UnitEnum unit) {
        this.unit = unit;
        return this;
    }

    /**
     * Unit of data points.
     *
     * @return unit
     **/
    @ApiModelProperty(value = "Unit of data points.")
    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public TimeseriesDataPointQueryResult aggregationType(AggregationTypeEnum aggregationType) {
        this.aggregationType = aggregationType;
        return this;
    }

    /**
     * The type of data points aggregation.
     *
     * @return aggregationType
     **/
    @ApiModelProperty(value = "The type of data points aggregation.")
    public AggregationTypeEnum getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(AggregationTypeEnum aggregationType) {
        this.aggregationType = aggregationType;
    }

    public TimeseriesDataPointQueryResult resolutionInMillisUTC(Long resolutionInMillisUTC) {
        this.resolutionInMillisUTC = resolutionInMillisUTC;
        return this;
    }

    /**
     * Resolution of data points.
     *
     * @return resolutionInMillisUTC
     **/
    @ApiModelProperty(value = "Resolution of data points.")
    public Long getResolutionInMillisUTC() {
        return resolutionInMillisUTC;
    }

    public void setResolutionInMillisUTC(Long resolutionInMillisUTC) {
        this.resolutionInMillisUTC = resolutionInMillisUTC;
    }

    public TimeseriesDataPointQueryResult entities(Map<String, String> entities) {
        this.entities = entities;
        return this;
    }

    public TimeseriesDataPointQueryResult putEntitiesItem(String key, String entitiesItem) {
        if (this.entities == null) {
            this.entities = new HashMap<>();
        }
        this.entities.put(key, entitiesItem);
        return this;
    }

    /**
     * Entities where the data points originate.  A JSON object that maps the entity ID in Dynatrace and the actual name of the entity.
     *
     * @return entities
     **/
    @ApiModelProperty(value = "Entities where the data points originate.  A JSON object that maps the entity ID in Dynatrace and the actual name of the entity.")
    public Map<String, String> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, String> entities) {
        this.entities = entities;
    }

    public TimeseriesDataPointQueryResult timeseriesId(String timeseriesId) {
        this.timeseriesId = timeseriesId;
        return this;
    }

    /**
     * The ID of the metric.
     *
     * @return timeseriesId
     **/
    @ApiModelProperty(value = "The ID of the metric.")
    public String getTimeseriesId() {
        return timeseriesId;
    }

    public void setTimeseriesId(String timeseriesId) {
        this.timeseriesId = timeseriesId;
    }

    @Override
    public String toString() {
        return "class TimeseriesDataPointQueryResult {\n" +
                "    dataPoints: " + toIndentedString(dataPoints) + "\n" +
                "    unit: " + toIndentedString(unit) + "\n" +
                "    aggregationType: " + toIndentedString(aggregationType) + "\n" +
                "    resolutionInMillisUTC: " + toIndentedString(resolutionInMillisUTC) + "\n" +
                "    entities: " + toIndentedString(entities) + "\n" +
                "    timeseriesId: " + toIndentedString(timeseriesId) + "\n" +
                "}";
    }

    // Add a container for the root element
    public static class Container {
        @SerializedName("result")
        public TimeseriesDataPointQueryResult result;
    }
}

