/*******************************************************************************
 * Copyright 2013-2018 QaProSoft (http://www.qaprosoft.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qaprosoft.carina.core.foundation.webdriver.listener;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;

import com.qaprosoft.zafira.client.ZafiraSingleton;
import com.qaprosoft.zafira.models.dto.TestArtifactType;

/**
 * ScreenRecordingListener - starts/stops video recording for desktop drivers.
 * 
 * @author akhursevich
 */
public class DesktopRecordingListener implements IDriverCommandListener {

    private boolean recording = false;
    
    private TestArtifactType artifact;
    
    public DesktopRecordingListener(TestArtifactType artifact) {
        this.artifact = artifact;
    }

    @Override
    public void beforeEvent(Command command) {
        if (recording && DriverCommand.QUIT.equals(command.getName())) {
            if (ZafiraSingleton.INSTANCE.isRunning()) {
                ZafiraSingleton.INSTANCE.getClient().addTestArtifact(artifact);
            }
        }
    }

    @Override
    public void afterEvent(Command command) {
        if (!recording && command.getSessionId() != null) {
            recording = true;
        }
    }
}
