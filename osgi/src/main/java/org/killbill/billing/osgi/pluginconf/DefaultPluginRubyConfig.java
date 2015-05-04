/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.osgi.pluginconf;

import java.io.File;
import java.util.Properties;

import org.killbill.billing.osgi.api.config.PluginRubyConfig;

public class DefaultPluginRubyConfig extends DefaultPluginConfig implements PluginRubyConfig {

    private static final String PROP_RUBY_MAIN_CLASS_NAME = "mainClass";
    private static final String PROP_RUBY_REQUIRE = "require";

    private final String rubyMainClass;
    private final String rubyLoadDir;
    private final String rubyRequire;

    public DefaultPluginRubyConfig(final String pluginName, final String version, final File pluginVersionRoot, final Properties props) throws PluginConfigException {
        super(pluginName, version, props, pluginVersionRoot);
        this.rubyMainClass = props.getProperty(PROP_RUBY_MAIN_CLASS_NAME);
        final File rubyGemsDir = new File(pluginVersionRoot.getAbsoluteFile(), "gems");
        this.rubyLoadDir = rubyGemsDir.isDirectory() ? rubyGemsDir.getAbsolutePath() : null;
        this.rubyRequire = props.getProperty(PROP_RUBY_REQUIRE);
        validate();
    }

    @Override
    protected void validate() throws PluginConfigException {
        if (rubyMainClass == null) {
            throw new PluginConfigException("Missing property " + PROP_RUBY_MAIN_CLASS_NAME + " for plugin " + getPluginVersionnedName());
        }
        if (rubyLoadDir != null && ! new File(rubyLoadDir).exists()) {
            throw new PluginConfigException("Missing gem installation directory " + rubyLoadDir + " for plugin " + getPluginVersionnedName());
        }
    }

    @Override
    public String getRubyMainClass() {
        return rubyMainClass;
    }

    @Deprecated
    public String getRubyLoadDir() {
        return rubyLoadDir;
    }

    @Override
    public String getRubyRequire() {
        return rubyRequire;
    }

    @Override
    public PluginLanguage getPluginLanguage() {
        return PluginLanguage.RUBY;
    }
}
