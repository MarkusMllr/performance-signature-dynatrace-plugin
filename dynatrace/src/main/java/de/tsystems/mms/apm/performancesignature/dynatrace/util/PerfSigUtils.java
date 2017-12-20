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

package de.tsystems.mms.apm.performancesignature.dynatrace.util;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.UsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import de.tsystems.mms.apm.performancesignature.dynatrace.PerfSigGlobalConfiguration;
import de.tsystems.mms.apm.performancesignature.dynatrace.configuration.CredProfilePair;
import de.tsystems.mms.apm.performancesignature.dynatrace.configuration.DynatraceServerConfiguration;
import de.tsystems.mms.apm.performancesignature.dynatrace.model.BaseReference;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.DTServerConnection;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.json.ApiResponse;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.xml.RESTErrorException;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.xml.model.Agent;
import de.tsystems.mms.apm.performancesignature.dynatrace.rest.xml.model.Dashboard;
import de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils;
import hudson.AbortException;
import hudson.security.ACL;
import hudson.util.ListBoxModel;
import org.apache.commons.collections.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class PerfSigUtils {
    private PerfSigUtils() {
    }

    public static ListBoxModel listToListBoxModel(final List<?> arrayList) {
        final ListBoxModel listBoxModel = new ListBoxModel();
        for (Object item : arrayList) {
            if (item instanceof String) {
                listBoxModel.add((String) item);
            } else if (item instanceof Dashboard) {
                listBoxModel.add(((Dashboard) item).getId());
            } else if (item instanceof Agent) {
                listBoxModel.add(((Agent) item).getName());
            } else if (item instanceof DynatraceServerConfiguration) {
                DynatraceServerConfiguration conf = (DynatraceServerConfiguration) item;
                if (CollectionUtils.isNotEmpty(conf.getCredProfilePairs()))
                    for (CredProfilePair credProfilePair : conf.getCredProfilePairs()) {
                        String listItem = credProfilePair.getProfile() + " (" + credProfilePair.getCredentials().getUsername() + ") @ " +
                                conf.getName();
                        listBoxModel.add(listItem);
                    }
            } else if (item instanceof BaseReference) {
                listBoxModel.add(((BaseReference) item).getId());
            }
        }
        return sortListBoxModel(listBoxModel);
    }

    private static ListBoxModel sortListBoxModel(final ListBoxModel list) {
        Collections.sort(list, new Comparator<ListBoxModel.Option>() {
            @Override
            public int compare(final ListBoxModel.Option o1, final ListBoxModel.Option o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        });
        return list;
    }

    public static List<DynatraceServerConfiguration> getDTConfigurations() {
        return PerfSigGlobalConfiguration.get().getConfigurations();
    }

    public static DynatraceServerConfiguration getServerConfiguration(final String dynatraceServer) {
        for (DynatraceServerConfiguration serverConfiguration : getDTConfigurations()) {
            String strippedName = dynatraceServer.replaceAll(".*@", "").trim();
            if (strippedName.equals(serverConfiguration.getName())) {
                return serverConfiguration;
            }
        }
        return null;
    }

    public static UsernamePasswordCredentials getCredentials(final String credsId) {
        return (credsId == null) ? null : CredentialsMatchers.firstOrNull(
                CredentialsProvider.lookupCredentials(UsernamePasswordCredentials.class, PerfSigUIUtils.getInstance(), ACL.SYSTEM,
                        Collections.<DomainRequirement>emptyList()), CredentialsMatchers.withId(credsId));
    }

    public static ListBoxModel fillAgentItems(final String dynatraceProfile) {
        DynatraceServerConfiguration serverConfiguration = getServerConfiguration(dynatraceProfile);
        if (serverConfiguration != null) {
            CredProfilePair pair = serverConfiguration.getCredProfilePair(dynatraceProfile);
            if (pair != null) {
                DTServerConnection connection = new DTServerConnection(serverConfiguration, pair);
                return listToListBoxModel(connection.getAgents());
            }
        }
        return null;
    }

    public static ListBoxModel fillHostItems(final String dynatraceProfile, final String agent) {
        DynatraceServerConfiguration serverConfiguration = getServerConfiguration(dynatraceProfile);
        if (serverConfiguration != null) {
            CredProfilePair pair = serverConfiguration.getCredProfilePair(dynatraceProfile);
            if (pair != null) {
                DTServerConnection connection = new DTServerConnection(serverConfiguration, pair);
                List<Agent> agents = connection.getAgents();
                ListBoxModel hosts = new ListBoxModel();
                for (Agent a : agents) {
                    if (a.getName().equals(agent)) {
                        hosts.add(a.getHost());
                    }
                }
                return hosts;
            }
        }
        return null;
    }

    public static DTServerConnection createDTServerConnection(final String dynatraceConfiguration) throws AbortException, RESTErrorException {
        return createDTServerConnection(dynatraceConfiguration, true);
    }

    public static DTServerConnection createDTServerConnection(final String dynatraceConfiguration, final boolean validateConnection) throws AbortException, RESTErrorException {
        DynatraceServerConfiguration serverConfiguration = getServerConfiguration(dynatraceConfiguration);
        if (serverConfiguration == null) {
            throw new AbortException(de.tsystems.mms.apm.performancesignature.dynatrace.Messages.PerfSigRecorder_FailedToLookupServer());
        }
        CredProfilePair pair = serverConfiguration.getCredProfilePair(dynatraceConfiguration);
        if (pair == null) {
            throw new AbortException(de.tsystems.mms.apm.performancesignature.dynatrace.Messages.PerfSigRecorder_FailedToLookupProfile());
        }
        DTServerConnection connection = new DTServerConnection(serverConfiguration, pair);
        if (validateConnection && !connection.validateConnection()) {
            throw new RESTErrorException(de.tsystems.mms.apm.performancesignature.dynatrace.Messages.PerfSigRecorder_DTConnectionError());
        }
        return connection;
    }

    public static String getIdFromLocationHeader(ApiResponse<Void> response) {
        List<String> locationList = response.getHeaders().get("Location");
        if (locationList == null || locationList.isEmpty()) {
            return null;
        }
        String locationUrl = locationList.get(0);
        String location = locationUrl.substring(locationUrl.lastIndexOf('/') + 1);
        return unescapeString(location);
    }


    /**
     * Escape the given string to be used as URL query value.
     *
     * @param str String to be escaped
     * @return Escaped string
     */
    public static String escapeString(String str) {
        return PerfSigUIUtils.encodeString(str);
    }

    /**
     * Unescape the given string coming from a query parameter.
     *
     * @param str String to be unescaped
     * @return unescaped string
     */
    public static String unescapeString(String str) {
        try {
            String decoded = URLDecoder.decode(str, "utf8");
            return URLDecoder.decode(decoded, "utf8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
