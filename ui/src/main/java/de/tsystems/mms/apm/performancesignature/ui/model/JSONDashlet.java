/*
 * Copyright (c) 2014 T-Systems Multimedia Solutions GmbH
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

package de.tsystems.mms.apm.performancesignature.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;

public class JSONDashlet implements Comparable<JSONDashlet> {

    @SerializedName("col")
    @Expose
    private int col;
    @SerializedName("row")
    @Expose
    private int row;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dashboard")
    @Expose
    private String dashboard;
    @SerializedName("chartDashlet")
    @Expose
    private String chartDashlet;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("customName")
    @Expose
    private String customName;
    @SerializedName("customBuildCount")
    @Expose
    private String customBuildCount;
    @SerializedName("show")
    @Expose
    private boolean show;
    @SerializedName("aggregation")
    @Expose
    private String aggregation;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * @param aggregation
     * @param chartDashlet
     * @param col
     * @param measure
     * @param description
     * @param dashboard
     * @param row
     */
    public JSONDashlet(final int col, final int row, final String dashboard, final String chartDashlet, final String measure,
                       final String aggregation, final String description) {
        this.col = col;
        this.row = row;
        this.dashboard = dashboard;
        this.chartDashlet = chartDashlet;
        this.measure = measure;
        this.show = true;
        this.aggregation = aggregation;
        this.description = description;
        this.id = DigestUtils.md5Hex(this.dashboard + this.chartDashlet + this.measure);
        this.customName = generateDashletName();
        this.customBuildCount = "0";
    }

    //gets called only if test case is a unit test
    public JSONDashlet(final int col, final int row, final String id, final String dashboard) {
        this(col, row, dashboard, "", "", "", "");
        this.id = id;
        this.customName = "";
    }

    /**
     * @return The col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col The col
     */
    public void setCol(final int col) {
        this.col = col;
    }

    /**
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row The row
     */
    public void setRow(final int row) {
        this.row = row;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return The dashboard
     */
    public String getDashboard() {
        return dashboard;
    }

    /**
     * @param dashboard The dashboard
     */
    public void setDashboard(final String dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * @return The chartDashlet
     */
    public String getChartDashlet() {
        return chartDashlet;
    }

    /**
     * @param chartDashlet The chartDashlet
     */
    public void setChartDashlet(final String chartDashlet) {
        this.chartDashlet = chartDashlet;
    }

    /**
     * @return The measure
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * @param measure The measure
     */
    public void setMeasure(final String measure) {
        this.measure = measure;
    }

    /**
     * @return The customName
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * @param customName The customName
     */
    public void setCustomName(final String customName) {
        this.customName = customName;
    }

    /**
     * @return The customBuildCount
     */
    public String getCustomBuildCount() {
        return customBuildCount;
    }

    /**
     * @param customBuildCount The customBuildCount
     */
    public void setCustomBuildCount(final String customBuildCount) {
        this.customBuildCount = customBuildCount;
    }

    /**
     * @return The show
     */
    public boolean isShow() {
        return show;
    }

    /**
     * @param show The show
     */
    public void setShow(final boolean show) {
        this.show = show;
    }

    /**
     * @return The aggregation
     */
    public String getAggregation() {
        return aggregation;
    }

    /**
     * @param aggregation The aggregation
     */
    public void setAggregation(final String aggregation) {
        this.aggregation = aggregation;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    public String generateID() {
        return DigestUtils.md5Hex(dashboard + chartDashlet + measure + customName + customBuildCount + aggregation + show
                + RandomStringUtils.randomAscii(16));
    }

    public String generateDashletName() {
        if (StringUtils.isBlank(customName)) {
            return PerfSigUIUtils.generateTitle(measure, chartDashlet, aggregation);
        } else {
            return customName;
        }
    }

    @Override
    public int compareTo(@Nonnull final JSONDashlet that) {
        if (this == that) {
            return 0;
        }
        int r = this.generateDashletName().compareToIgnoreCase(that.generateDashletName());
        if (r != 0) {
            return r;
        }
        // Only equals is exact reference
        return System.identityHashCode(this) >= System.identityHashCode(that) ? 1 : -1;
    }

    // Method overridden to provide explicit declaration of the equivalence relation used
    // as Comparable is also implemented
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    // Method overridden to provide explicit declaration of the equivalence relation used
    // as Comparable is also implemented
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
