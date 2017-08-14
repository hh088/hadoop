/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.slider.core.registry;

import com.google.common.base.Preconditions;
import org.apache.hadoop.registry.client.binding.RegistryUtils;
import org.apache.hadoop.yarn.service.conf.SliderKeys;

/**
 * Miscellaneous methods to assist slider registry work
 * 
 */
public class SliderRegistryUtils {


  /**
   * Get the registry path for an instance under the user's home node
   * @param instanceName application instance
   * @return a path to the registry location for this application instance.
   */
  public static String registryPathForInstance(String instanceName) {
    return RegistryUtils.servicePath(
        RegistryUtils.currentUser(), SliderKeys.APP_TYPE, instanceName
    );
  }

  /**
   * Process a path expanding it if needed.
   * Validation is delegated to later as the core registry will need
   * to do that anyway
   * @param path path
   * @return a path maybe with some expansion
   */
  public static String resolvePath(String path) {
    Preconditions.checkArgument(path!=null, "null path");
    Preconditions.checkArgument(!path.isEmpty(), "empty path");
    String newpath = path;
    if (path.startsWith("~/")) {
      // add user expansion
      newpath = RegistryUtils.homePathForCurrentUser() + path.substring(1);
    } else if (path.equals("~")) {
      newpath = RegistryUtils.homePathForCurrentUser();
    }
    return newpath;
  }
}
