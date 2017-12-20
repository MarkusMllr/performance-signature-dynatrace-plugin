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

package de.tsystems.mms.apm.performancesignature.viewer;

import com.offbytwo.jenkins.model.JobWithDetails;
import de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils;
import de.tsystems.mms.apm.performancesignature.viewer.rest.JenkinsServerConnection;
import de.tsystems.mms.apm.performancesignature.viewer.util.ViewerUtils;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

public class ViewerInputTrigger extends Builder implements SimpleBuildStep {
    private final String jenkinsJob;
    private final String triggerId;

    @DataBoundConstructor
    public ViewerInputTrigger(final String jenkinsJob, final String triggerId) {
        this.jenkinsJob = jenkinsJob;
        this.triggerId = triggerId;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws IOException {
        PluginLogger logger = PerfSigUIUtils.createLogger(listener.getLogger());
        JenkinsServerConnection serverConnection = ViewerUtils.createJenkinsServerConnection(jenkinsJob);

        JobWithDetails perfSigJob = serverConnection.getJenkinsJob().details();
        ViewerEnvInvisAction envInvisAction = run.getAction(ViewerEnvInvisAction.class);
        int buildNumber;
        if (envInvisAction != null) {
            buildNumber = envInvisAction.getCurrentBuild();
        } else {
            buildNumber = perfSigJob.getLastBuild().getNumber();
        }

        logger.log(Messages.ViewerInputTrigger_TriggerInputStep(perfSigJob.getName(), buildNumber));
        serverConnection.triggerInputStep(buildNumber, getTriggerId());
        logger.log(Messages.ViewerInputTrigger_TriggeredInputStep(perfSigJob.getName(), buildNumber));
    }

    public String getJenkinsJob() {
        return jenkinsJob;
    }

    public String getTriggerId() {
        return triggerId;
    }

    @Symbol("triggerInputStep")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public ListBoxModel doFillJenkinsJobItems() {
            return ViewerUtils.listToListBoxModel(ViewerUtils.getJenkinsConfigurations());
        }

        public boolean isApplicable(final Class<? extends AbstractProject> aClass) {
            return true;
        }

        public String getDisplayName() {
            return Messages.ViewerInputTrigger_DisplayName();
        }
    }
}
