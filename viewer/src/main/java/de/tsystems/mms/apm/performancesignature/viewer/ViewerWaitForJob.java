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

import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import de.tsystems.mms.apm.performancesignature.ui.util.PerfSigUIUtils;
import de.tsystems.mms.apm.performancesignature.viewer.rest.JenkinsServerConnection;
import de.tsystems.mms.apm.performancesignature.viewer.util.ViewerUtils;
import hudson.AbortException;
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
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

public class ViewerWaitForJob extends Builder implements SimpleBuildStep {
    static final int WAIT_FOR_POLLING_INTERVAL = 5000;
    private final String jenkinsJob;

    @DataBoundConstructor
    public ViewerWaitForJob(final String jenkinsJob) {
        this.jenkinsJob = jenkinsJob;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws InterruptedException, IOException {
        PluginLogger logger = PerfSigUIUtils.createLogger(listener.getLogger());
        JenkinsServerConnection serverConnection = ViewerUtils.createJenkinsServerConnection(jenkinsJob);

        JobWithDetails job = serverConnection.getJenkinsJob().details();
        ViewerEnvInvisAction envInvisAction = run.getAction(ViewerEnvInvisAction.class);
        BuildWithDetails build;
        if (envInvisAction != null) {
            build = job.getBuildByNumber(envInvisAction.getCurrentBuild()).details();
        } else {
            build = job.getLastBuild().details();
        }

        logger.log(Messages.ViewerWaitForJob_WaitingForJob(job.getName(), String.valueOf(build.getNumber())));
        boolean buildFinished = build.isBuilding();
        while (buildFinished) {
            Thread.sleep(WAIT_FOR_POLLING_INTERVAL);
            buildFinished = build.details().isBuilding();
        }

        logger.log(Messages.ViewerWaitForJob_JenkinsJobFinished());
        BuildResult buildResult = build.details().getResult();

        logger.log(Messages.ViewerWaitForJob_JenkinsJobStatus(buildResult));
        if (!buildResult.equals(BuildResult.SUCCESS) && !buildResult.equals(BuildResult.UNSTABLE)) {
            String output = build.getConsoleOutputText();
            logger.log(output.substring(StringUtils.lastOrdinalIndexOf(output, "\n", 5) + 1)); //get the last 5 lines of console output
            throw new AbortException(Messages.ViewerWaitForJob_JenkinsJobFailed());
        }
    }

    public String getJenkinsJob() {
        return jenkinsJob;
    }

    @Symbol("waitForJob")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public ListBoxModel doFillJenkinsJobItems() {
            return ViewerUtils.listToListBoxModel(ViewerUtils.getJenkinsConfigurations());
        }

        public boolean isApplicable(final Class<? extends AbstractProject> aClass) {
            return true;
        }

        public String getDisplayName() {
            return Messages.ViewerWaitForJob_DisplayName();
        }
    }
}
