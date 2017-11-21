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

package de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.api;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.*;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.model.RecordingStatus;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.model.SessionRecordingOptions;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.model.SessionStoringOptions;
import de.tsystems.mms.apm.performancesignature.util.PerfSigUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveSessionsApi {
    private ApiClient apiClient;

    public LiveSessionsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public LiveSessionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getRecording
     *
     * @param profileid System Profile id (required)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getRecordingCall(String profileid) throws ApiException {
        // create path and map variables
        String localVarPath = ApiClient.API_SUFFIX + "/profiles/{profileid}/session/recording/status"
                .replaceAll("\\{profileid\\}", PerfSigUtils.escapeString(profileid));

        List<Pair> localVarQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();
        localVarHeaderParams.put("Accept", "application/json");

        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, null, localVarHeaderParams, null);
    }

    @SuppressWarnings("rawtypes")
    private Call getRecordingValidateBeforeCall(String profileid) throws ApiException {
        // verify the required parameter 'profileid' is set
        if (profileid == null) {
            throw new ApiException("Missing the required parameter 'profileid' when calling getRecording");
        }

        return getRecordingCall(profileid);
    }

    /**
     * Get session recording status
     * Check if the live session is currently being recorded.
     *
     * @param profileid System Profile id (required)
     * @return RecordingStatus
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public RecordingStatus getRecording(String profileid) throws ApiException {
        ApiResponse<RecordingStatus> resp = getRecordingWithHttpInfo(profileid);
        return resp.getData();
    }

    /**
     * Get session recording status
     * Check if the live session is currently being recorded.
     *
     * @param profileid System Profile id (required)
     * @return ApiResponse&lt;RecordingStatus&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<RecordingStatus> getRecordingWithHttpInfo(String profileid) throws ApiException {
        Call call = getRecordingValidateBeforeCall(profileid);
        Type localVarReturnType = new TypeToken<RecordingStatus>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Build call for postRecording
     *
     * @param profileid System Profile id (required)
     * @param body      Session recording options (optional)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call postRecordingCall(String profileid, SessionRecordingOptions body) throws ApiException {
        // create path and map variables
        String localVarPath = ApiClient.API_SUFFIX + "/profiles/{profileid}/session/recording"
                .replaceAll("\\{profileid\\}", PerfSigUtils.escapeString(profileid));

        List<Pair> localVarQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();
        localVarHeaderParams.put("Accept", "application/json");
        localVarHeaderParams.put("Content-Type", "application/json");

        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, body, localVarHeaderParams, localVarFormParams);
    }

    @SuppressWarnings("rawtypes")
    private Call postRecordingValidateBeforeCall(String profileid, SessionRecordingOptions body) throws ApiException {
        // verify the required parameter 'profileid' is set
        if (profileid == null) {
            throw new ApiException("Missing the required parameter 'profileid' when calling postRecording");
        }

        return postRecordingCall(profileid, body);
    }

    /**
     * Start session recording
     * Start session recording for a specific System Profile. Starting session recording is only possible for pre-production licenses.
     *
     * @param profileid System Profile id (required)
     * @param body      Session recording options (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String postRecording(String profileid, SessionRecordingOptions body) throws ApiException {
        ApiResponse<Void> response = postRecordingWithHttpInfo(profileid, body);
        return PerfSigUtils.getIdFromLocationHeader(response);
    }

    /**
     * Start session recording
     * Start session recording for a specific System Profile. Starting session recording is only possible for pre-production licenses.
     *
     * @param profileid System Profile id (required)
     * @param body      Session recording options (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> postRecordingWithHttpInfo(String profileid, SessionRecordingOptions body) throws ApiException {
        Call call = postRecordingValidateBeforeCall(profileid, body);
        return apiClient.execute(call);
    }

    /**
     * Build call for stopRecording
     *
     * @param profileid System Profile id (required)
     * @param body      (optional)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call stopRecordingCall(String profileid, RecordingStatus body) throws ApiException {
        // create path and map variables
        String localVarPath = ApiClient.API_SUFFIX + "/profiles/{profileid}/session/recording/status"
                .replaceAll("\\{profileid\\}", PerfSigUtils.escapeString(profileid));

        List<Pair> localVarQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();
        localVarHeaderParams.put("Accept", "application/json");
        localVarHeaderParams.put("Content-Type", "application/json");

        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, body, localVarHeaderParams, localVarFormParams);
    }

    @SuppressWarnings("rawtypes")
    private Call stopRecordingValidateBeforeCall(String profileid, RecordingStatus body) throws ApiException {
        // verify the required parameter 'profileid' is set
        if (profileid == null) {
            throw new ApiException("Missing the required parameter 'profileid' when calling stopRecording");
        }

        return stopRecordingCall(profileid, body);
    }

    /**
     * Stop session recording
     * Set recording status to false in order to stop session recording and create a reference to the stored session. This call does not complete until all recorded data is fully processed on the Server. Depending on the environment, it can take a few minutes until an HTTP response message is received. Stopping session recording is only possible for pre-production licenses.
     *
     * @param profileid System Profile id (required)
     * @param body      (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String stopRecording(String profileid, RecordingStatus body) throws ApiException {
        ApiResponse<Void> response = stopRecordingWithHttpInfo(profileid, body);
        return PerfSigUtils.getIdFromLocationHeader(response);
    }

    /**
     * Stop session recording
     * Set recording status to false in order to stop session recording and create a reference to the stored session. This call does not complete until all recorded data is fully processed on the Server. Depending on the environment, it can take a few minutes until an HTTP response message is received. Stopping session recording is only possible for pre-production licenses.
     *
     * @param profileid System Profile id (required)
     * @param body      (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> stopRecordingWithHttpInfo(String profileid, RecordingStatus body) throws ApiException {
        Call call = stopRecordingValidateBeforeCall(profileid, body);
        return apiClient.execute(call);
    }

    /**
     * Build call for storeSession
     *
     * @param profileid System Profile id (required)
     * @param body      Session storing options (optional)
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call storeSessionCall(String profileid, SessionStoringOptions body) throws ApiException {
        // create path and map variables
        String localVarPath = ApiClient.API_SUFFIX + "/profiles/{profileid}/session/store"
                .replaceAll("\\{profileid\\}", PerfSigUtils.escapeString(profileid));

        List<Pair> localVarQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();
        localVarHeaderParams.put("Accept", "application/json");
        localVarHeaderParams.put("Content-Type", "application/json");

        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, body, localVarHeaderParams, localVarFormParams);
    }

    @SuppressWarnings("rawtypes")
    private Call storeSessionValidateBeforeCall(String profileid, SessionStoringOptions body) throws ApiException {
        // verify the required parameter 'profileid' is set
        if (profileid == null) {
            throw new ApiException("Missing the required parameter 'profileid' when calling storeSession");
        }

        return storeSessionCall(profileid, body);
    }

    /**
     * Store session
     * Store all time series and PurePaths in the Server&#39;s memory to a stored session. To limit the data to be stored, specify a start time and end time in the request body, otherwise the last 30 minutes will be stored.
     *
     * @param profileid System Profile id (required)
     * @param body      Session storing options (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String storeSession(String profileid, SessionStoringOptions body) throws ApiException {
        ApiResponse<Void> response = storeSessionWithHttpInfo(profileid, body);
        return PerfSigUtils.getIdFromLocationHeader(response);
    }

    /**
     * Store session
     * Store all time series and PurePaths in the Server&#39;s memory to a stored session. To limit the data to be stored, specify a start time and end time in the request body, otherwise the last 30 minutes will be stored.
     *
     * @param profileid System Profile id (required)
     * @param body      Session storing options (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> storeSessionWithHttpInfo(String profileid, SessionStoringOptions body) throws ApiException {
        Call call = storeSessionValidateBeforeCall(profileid, body);
        return apiClient.execute(call);
    }
}
