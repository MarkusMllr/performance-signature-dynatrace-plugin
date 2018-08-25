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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils.toIndentedString;

/**
 * Contains settings for a timeseries query.
 */
@ApiModel(description = "Contains settings for a timeseries query.")

public class TimeseriesQueryMessage {
    @SerializedName("timeseriesId")
    private String timeseriesId;
    @SerializedName("aggregationType")
    private AggregationTypeEnum aggregationType;
    @SerializedName("startTimestamp")
    private Long startTimestamp;
    @SerializedName("endTimestamp")
    private Long endTimestamp;
    @SerializedName("predict")
    private Boolean predict;
    @SerializedName("queryMode")
    private QueryModeEnum queryMode;
    @SerializedName("entities")
    private List<String> entities;
    @SerializedName("tags")
    private List<String> tags;
    @SerializedName("filters")
    private Map<String, String> filters;
    @SerializedName("percentile")
    private Integer percentile;

    public TimeseriesQueryMessage timeseriesId(String timeseriesId) {
        this.timeseriesId = timeseriesId;
        return this;
    }

    /**
     * Case-sensitive identifier of the metric, where you want to read data points.   You can find a list of available built-in Dynatrace metrics in the Available timeseries section, and plugin-driven metrics in the Plugin timeseries section.  You can also execute a GET timeseries request, to obtain the list of available metrics.
     *
     * @return timeseriesId
     **/
    @ApiModelProperty(value = "Case-sensitive identifier of the metric, where you want to read data points.   You can find a list of available built-in Dynatrace metrics in the Available timeseries section, and plugin-driven metrics in the Plugin timeseries section.  You can also execute a GET timeseries request, to obtain the list of available metrics.")
    public String getTimeseriesId() {
        return timeseriesId;
    }

    public void setTimeseriesId(String timeseriesId) {
        this.timeseriesId = timeseriesId;
    }

    public TimeseriesQueryMessage aggregationType(AggregationTypeEnum aggregationType) {
        this.aggregationType = aggregationType;
        return this;
    }

    /**
     * Defines which aggregation type is used for the resulting metric.  If the requested metric doesn&#39;t support the specified aggregation, the request will result in an error.
     *
     * @return aggregationType
     **/
    @ApiModelProperty(value = "Defines which aggregation type is used for the resulting metric.  If the requested metric doesn't support the specified aggregation, the request will result in an error.")
    public AggregationTypeEnum getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(AggregationTypeEnum aggregationType) {
        this.aggregationType = aggregationType;
    }

    public TimeseriesQueryMessage startTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
        return this;
    }

    /**
     * Start of timeframe in milliseconds since Unix epoch.
     *
     * @return startTimestamp
     **/
    @ApiModelProperty(value = "Start of timeframe in milliseconds since Unix epoch.")
    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public TimeseriesQueryMessage endTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
        return this;
    }

    /**
     * End of timeframe in milliseconds since Unix epoch. Must be larger(later) than start timestamp. If the given timestamp is larger than the actual time, then the actual time is used instead.
     *
     * @return endTimestamp
     **/
    @ApiModelProperty(value = "End of timeframe in milliseconds since Unix epoch. Must be larger(later) than start timestamp. If the given timestamp is larger than the actual time, then the actual time is used instead.")
    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public TimeseriesQueryMessage predict(Boolean predict) {
        this.predict = predict;
        return this;
    }

    /**
     * Used to predict future Values
     *
     * @return predict
     **/
    @ApiModelProperty(value = "Used to predict future Values")
    public Boolean isPredict() {
        return predict;
    }

    public void setPredict(Boolean predict) {
        this.predict = predict;
    }

    public TimeseriesQueryMessage queryMode(QueryModeEnum queryMode) {
        this.queryMode = queryMode;
        return this;
    }

    /**
     * Defines the type of result that the call should return. Valid result modes are: series: returns all the data points of the metric in the specified timeframe. total: returns one scalar value for the specified timeframe.   By default, the series mode is used.
     *
     * @return queryMode
     **/
    @ApiModelProperty(value = "Defines the type of result that the call should return. Valid result modes are: series: returns all the data points of the metric in the specified timeframe. total: returns one scalar value for the specified timeframe.   By default, the series mode is used.")
    public QueryModeEnum getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(QueryModeEnum queryMode) {
        this.queryMode = queryMode;
    }

    public TimeseriesQueryMessage entities(List<String> entities) {
        this.entities = entities;
        return this;
    }

    public TimeseriesQueryMessage addEntitiesItem(String entitiesItem) {
        if (this.entities == null) {
            this.entities = new ArrayList<>();
        }
        this.entities.add(entitiesItem);
        return this;
    }

    /**
     * Filters requested data points by entities which should deliver them. You can specify several entities at once.   Allowed values are Dynatrace entity IDs. You can find them in the URL of the corresponding Dynatrace entity page, for example, HOST-007.   If the selected entity doesn&#39;t support the requested metric, the request will result in an error.
     *
     * @return entities
     **/
    @ApiModelProperty(value = "Filters requested data points by entities which should deliver them. You can specify several entities at once.   Allowed values are Dynatrace entity IDs. You can find them in the URL of the corresponding Dynatrace entity page, for example, HOST-007.   If the selected entity doesn't support the requested metric, the request will result in an error.")
    public List<String> getEntities() {
        return entities;
    }

    public TimeseriesQueryMessage setEntities(List<String> entities) {
        this.entities = entities;
        return this;
    }

    public TimeseriesQueryMessage tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public TimeseriesQueryMessage addTagsItem(String tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * List of labels of entities that you want to fetch data for.
     *
     * @return tags
     **/
    @ApiModelProperty(value = "List of labels of entities that you want to fetch data for.")
    public List<String> getTags() {
        return tags;
    }

    public TimeseriesQueryMessage setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public TimeseriesQueryMessage filters(Map<String, String> filters) {
        this.filters = filters;
        return this;
    }

    public TimeseriesQueryMessage putFiltersItem(String key, String filtersItem) {
        if (this.filters == null) {
            this.filters = new HashMap<>();
        }
        this.filters.put(key, filtersItem);
        return this;
    }

    /**
     * A filter is an object, containing map of filter keys and its values. Valid filter keys are: processType: Filters by process type. See Process types for allowed values. osType: Filters by operating system. See OS types for allowed values. serviceType: Filters by service type. See Service types for allowed values. technology: Filters by technology type. See Technology types for allowed values. webServiceName: Filters by web service name. webServiceNamespace: Filters by the web service namespace. host: Filters by entity ID of the host, for example HOST-007.
     *
     * @return filters
     **/
    @ApiModelProperty(value = "A filter is an object, containing map of filter keys and its values. Valid filter keys are: processType: Filters by process type. See Process types for allowed values. osType: Filters by operating system. See OS types for allowed values. serviceType: Filters by service type. See Service types for allowed values. technology: Filters by technology type. See Technology types for allowed values. webServiceName: Filters by web service name. webServiceNamespace: Filters by the web service namespace. host: Filters by entity ID of the host, for example HOST-007.")
    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public TimeseriesQueryMessage percentile(Integer percentile) {
        this.percentile = percentile;
        return this;
    }

    /**
     * In case of the percentile aggregation type, this parameter specifies which percentile of the selected response time metric should be delivered.   Valid values for percentile are between 1 and 99.   Please keep in mind that percentile export is only possible for response-time based metrics such as application and service response times.
     *
     * @return percentile
     **/
    @ApiModelProperty(value = "In case of the percentile aggregation type, this parameter specifies which percentile of the selected response time metric should be delivered.   Valid values for percentile are between 1 and 99.   Please keep in mind that percentile export is only possible for response-time based metrics such as application and service response times.")
    public Integer getPercentile() {
        return percentile;
    }

    public void setPercentile(Integer percentile) {
        this.percentile = percentile;
    }

    @Override
    public String toString() {
        return "class TimeseriesQueryMessage {\n" +
                "    timeseriesId: " + toIndentedString(timeseriesId) + "\n" +
                "    aggregationType: " + toIndentedString(aggregationType) + "\n" +
                "    startTimestamp: " + toIndentedString(startTimestamp) + "\n" +
                "    endTimestamp: " + toIndentedString(endTimestamp) + "\n" +
                "    predict: " + toIndentedString(predict) + "\n" +
                "    queryMode: " + toIndentedString(queryMode) + "\n" +
                "    entities: " + toIndentedString(entities) + "\n" +
                "    tags: " + toIndentedString(tags) + "\n" +
                "    filters: " + toIndentedString(filters) + "\n" +
                "    percentile: " + toIndentedString(percentile) + "\n" +
                "}";
    }
}

